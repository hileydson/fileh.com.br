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

public class CarregaCadastroFluxoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			
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

			ArrayList<FluxoCaixa> listFluxoCaixa = fluDAO.buscaTodasListagemObjetos(entidade);

			ArrayList<FluxoCaixa> listEntrada = new ArrayList<>();
			ArrayList<FluxoCaixa> listSaida = new ArrayList<>();

			if((listFluxoCaixa != null)  && (listFluxoCaixa.size() != 0)){
				int i = 0;
				while (i < listFluxoCaixa.size()){
					
					//popula fluxo de entradas
					if(listFluxoCaixa.get(i).getFlu_fl_tipo().equalsIgnoreCase("EN") ){
						listEntrada.add(listFluxoCaixa.get(i));
					}
					
					//popula fluxo de saidas
					if(listFluxoCaixa.get(i).getFlu_fl_tipo().equalsIgnoreCase("SA") ){
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
			
			
			
			//busca data atual do servidor para as datas que serão inseridas, se retornal null, não será possível entrar no cadastro de fluxo de caixa
			String dataAtualServidor = fluDAO.consultaDataAtualServidor();
			if (dataAtualServidor != null){
				request.getSession().setAttribute("dataAtualServidor", dataAtualServidor);	
				response.sendRedirect("cadastroFluxoCaixa.jsp");
			}else{
				request.getSession().setAttribute("dataAtualServidor", "--/--/----");	
				request.getSession().setAttribute("msg_erro", "Erro ao buscar data atual do servidor... Favor entrar em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}
			
			
			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Fluxo de Caixa...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
