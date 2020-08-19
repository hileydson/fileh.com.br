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
import entidades.Usuario;

public class ListagemFluxoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			
			//busca os nomes das formas de pagamento para o combobox
			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listNomesFormaPagamento;
			listNomesFormaPagamento = fopDAO.buscaTodosTabelaFormaPagamentoListagemNomes("CX");
			if(listNomesFormaPagamento == null){
				request.getSession().setAttribute("listNomesFormaPagamentoFluxoCaixa","");
			}else{
				request.getSession().setAttribute("listNomesFormaPagamentoFluxoCaixa",listNomesFormaPagamento);
			}
			
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
		
			
			//busca a modificacao da data
			String data = " (SELECT DATE_FORMAT(NOW(),'%d/%m/%Y') FROM DUAL) ";
			if(request.getSession().getAttribute("data_escolhida_fluxo_caixa") != null){
				
				data = " '"+request.getSession().getAttribute("data_escolhida_fluxo_caixa").toString()+"' ";

			}
			
			
			FluxoCaixaDAO fluDAO = new FluxoCaixaDAO(u.getUsu_ds_subdominio());

			ArrayList<FluxoCaixa> listFluxoCaixa = fluDAO.buscaTodasListagemObjetos(entidade, data);

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
			
			response.sendRedirect("listagemFluxoCaixa.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Fluxo de Caixa...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
