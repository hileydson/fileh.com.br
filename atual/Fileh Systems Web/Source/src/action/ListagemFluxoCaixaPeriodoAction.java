package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import dao.FluxoCaixaDAO;
import dao.FormaPagamentoDAO;
import entidades.Entidade;
import entidades.FluxoCaixa;
import entidades.FormaPagamento;
import entidades.Usuario;

public class ListagemFluxoCaixaPeriodoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			String data_inicio = "";
			String data_fim = "";
			String descricao = "";
			String forma_pagamento = "";


			
			
			//trata e busca descrição do registro do fluxo de caixa
			if (request.getParameter("descricao") != null) request.getSession().setAttribute("descricao", request.getParameter("descricao"));
			if (request.getSession().getAttribute("descricao") == null) request.getSession().setAttribute("descricao","");
			descricao = request.getSession().getAttribute("descricao").toString();
			
			


			if( (request.getParameter("flu_cd_entidade") != null) || (request.getParameter("flu_ds_forma_pagamento") != null) || (request.getParameter("flu_dt_cadastro") != null)  || (request.getParameter("flu_dt_cadastro2") != null)){
				if(request.getParameter("flu_cd_entidade") != null){
					if (request.getParameter("flu_cd_entidade").equalsIgnoreCase("-1")){
						request.getSession().setAttribute("entidade_fluxo_caixa", null);

					}else{
						request.getSession().setAttribute("entidade_fluxo_caixa", request.getParameter("flu_cd_entidade"));
					}
				}

				if(request.getParameter("flu_ds_forma_pagamento") != null){
					if (request.getParameter("flu_ds_forma_pagamento").equalsIgnoreCase("-1")){
						request.getSession().setAttribute("forma_pagamento_fluxo_caixa", null);

					}else{
						request.getSession().setAttribute("forma_pagamento_fluxo_caixa", request.getParameter("flu_ds_forma_pagamento"));
						forma_pagamento = request.getParameter("flu_ds_forma_pagamento");
					}
				}

				if(request.getParameter("flu_dt_cadastro") != null){

					//marca data escolhida para a tela de listagem de fluxo de caixa
					request.getSession().setAttribute("data_escolhida_fluxo_caixa1", request.getParameter("flu_dt_cadastro"));
					data_inicio = request.getParameter("flu_dt_cadastro");

				}else{
					request.getSession().setAttribute("data_escolhida_fluxo_caixa1", null);


				}


				if(request.getParameter("flu_dt_cadastro2") != null){

					//marca data escolhida para a tela de listagem de fluxo de caixa
					request.getSession().setAttribute("data_escolhida_fluxo_caixa2", request.getParameter("flu_dt_cadastro2"));
					data_fim = request.getParameter("flu_dt_cadastro2");


				}else{
					request.getSession().setAttribute("data_escolhida_fluxo_caixa2", null);

				}


			};





			//busca os nomes das formas de pagamento para o combobox
			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			FormaPagamento formaPagamento = new FormaPagamento();
			ArrayList<FormaPagamento> listNomesFormaPagamento;
			String forma_pagamento_fluxo_caixa;

			//se for null nao foi escolhido nada ainda
			if(request.getSession().getAttribute("forma_pagamento_fluxo_caixa") == null){
				request.getSession().setAttribute("forma_pagamento_fluxo_caixa", "-1");
				forma_pagamento_fluxo_caixa = "-1";
			}else{				
				//uma action trigger no change do combobox de entidade no fluxo de caixa adicionou esse atributo a sessao
				forma_pagamento_fluxo_caixa = request.getSession().getAttribute("forma_pagamento_fluxo_caixa").toString();
				formaPagamento = (FormaPagamento) fopDAO.carregaEntidade(formaPagamento, Integer.parseInt(forma_pagamento_fluxo_caixa));
				if (formaPagamento != null){
					request.getSession().setAttribute("forma_pagamento_fluxo_caixa_msg", formaPagamento.getFop_ds_forma_pagamento());
				}else{
					request.getSession().setAttribute("forma_pagamento_fluxo_caixa_msg", null);
				};
			}


			listNomesFormaPagamento = fopDAO.buscaTodasListagemObjetos("CX");
			request.getSession().setAttribute("arrayFormaPagamentoFluxoCaixa",listNomesFormaPagamento);





			//busca lista de entidades para filtrar fluxo
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			Entidade auxEntidade = new Entidade();
			String entidade;


			//se for null nao foi escolhido nada ainda
			if(request.getSession().getAttribute("entidade_fluxo_caixa") == null){
				request.getSession().setAttribute("entidade_fluxo_caixa", "-1");
				entidade = "-1";
			}else{
				//uma action trigger no change do combobox de entidade no fluxo de caixa adicionou esse atributo a sessao
				entidade = request.getSession().getAttribute("entidade_fluxo_caixa").toString();
				auxEntidade = (Entidade) entDAO.carregaEntidade(auxEntidade, Integer.parseInt(entidade));
				if (auxEntidade != null){
					request.getSession().setAttribute("entidade_fluxo_caixa_msg", auxEntidade.getEnt_nm_entidade());
				}else{
					request.getSession().setAttribute("entidade_fluxo_caixa_msg", null);
				};
			}

			ArrayList<Entidade> listEntidades = entDAO.buscaTodasEntidades(u.getUsu_cd_usuario().toString());

			request.getSession().setAttribute("arrayEntidadesFluxoCaixa", listEntidades);				





			FluxoCaixaDAO fluDAO = new FluxoCaixaDAO(u.getUsu_ds_subdominio());
			ArrayList<FluxoCaixa> listFluxoCaixa ;
			//se for igual a '' eh porque eh o primeiro acesso...
			if ((!data_inicio.equalsIgnoreCase("")) && (!data_fim.equalsIgnoreCase(""))){
				listFluxoCaixa = fluDAO.buscaTodasListagemObjetosPeriodo(entidade, forma_pagamento, data_inicio, data_fim, descricao);
			}else{
				listFluxoCaixa = null;
			};

			ArrayList<FluxoCaixa> listEntrada = new ArrayList<>();
			ArrayList<FluxoCaixa> listSaida = new ArrayList<>();

			if((listFluxoCaixa != null)  && (listFluxoCaixa.size() != 0)){
				int i = 0;
				while (i < listFluxoCaixa.size()){

					//popula fluxo de entradas
					if(listFluxoCaixa.get(i).getFlu_fl_tipo().equalsIgnoreCase("ENTRADA") ){
						listEntrada.add(listFluxoCaixa.get(i));
					}

					//popula fluxo de saidas
					if(listFluxoCaixa.get(i).getFlu_fl_tipo().equalsIgnoreCase("SAIDA") ){
						listSaida.add(listFluxoCaixa.get(i));
					}

					i++;

				}

				request.getSession().setAttribute("listEntrada", listEntrada);
				request.getSession().setAttribute("listSaida", listSaida);
			}else{
				request.getSession().setAttribute("listEntrada", null);
				request.getSession().setAttribute("listSaida", null);
			}

			response.sendRedirect("listagemFluxoCaixaPeriodo.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Fluxo de Caixa...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}

	}

}
