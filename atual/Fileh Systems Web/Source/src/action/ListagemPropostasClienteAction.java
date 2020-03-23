package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.EntidadeDAO;
import dao.PropostaComercialDAO;
import dao.SituacaoPropostaDAO;
import entidades.Cliente;
import entidades.PropostaComercial;
import entidades.Usuario;

public class ListagemPropostasClienteAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			String codigo, codigo_cliente, dataPrevista, dataCadastro, situacaoProposta, check_situacao_null;
			boolean bCheck_situacao_null = false;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			String id_cliente = request.getParameter("name_cliente_hidden");
			Cliente cli = null;
			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			
			
			
			
			//---------- Filtro Entidade ----------------
			
			String msgEntidade = " ";
			String entidade = "";
			if (request.getParameter("entidade") != null) request.getSession().setAttribute("entidade_listagem",request.getParameter("entidade")); 
			if (request.getSession().getAttribute("entidade_listagem") == null) request.getSession().setAttribute("entidade_listagem","");
			entidade = request.getSession().getAttribute("entidade_listagem").toString();
			
			if((entidade.equalsIgnoreCase("")) || (entidade.equalsIgnoreCase("-1"))	){
				entidade = "-1";
				msgEntidade = "Escolha uma entidade...";
			}else{
				msgEntidade = entidade;
			};
			
			request.getSession().setAttribute("entidade_listagem", entidade);
			request.getSession().setAttribute("msgEntidade", msgEntidade);
			
			/*----busca grupo de entidades cadastradas para filtrar-----*/
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listEntidades = entDAO.buscaEntidadesListagemNomes( u.getUsu_cd_usuario());
			
			request.getSession().setAttribute("arrayEntidades", listEntidades);
			
			//---------- -------------- ----------------
			
			
			if (id_cliente == null){
				id_cliente = "";
				request.getSession().setAttribute("obj_cliente_proposta", null);	
			}else{
				cli = (Cliente) cliDAO.carregaEntidade(new Cliente(), Integer.parseInt(id_cliente));
				request.getSession().setAttribute("obj_cliente_proposta", cli);				
			};
			
			
			if(cli != null){
				codigo_cliente = cli.getCli_cd_cliente() + "";
			}else{
				if (request.getParameter("codigo_cliente") != null) {
					codigo_cliente = request.getParameter("codigo_cliente");
				}else{
					if(request.getSession().getAttribute("codigo_cliente") != null){
						codigo_cliente = request.getSession().getAttribute("codigo_cliente").toString();
					}else{
						codigo_cliente = "";
					}
				}
			}
			
			
			
			if (request.getParameter("codigo") != null) {
				codigo = request.getParameter("codigo");
			}else{
				if(request.getSession().getAttribute("codigo") != null){
					codigo = request.getSession().getAttribute("codigo").toString();
				}else{
					codigo = "";
				}
			}
			
			
			if (request.getParameter("dataPrevista") != null) {
				dataPrevista = request.getParameter("dataPrevista");
			}else{
				if(request.getSession().getAttribute("dataPrevista") != null){
					dataPrevista = request.getSession().getAttribute("dataPrevista").toString();
				}else{
					dataPrevista = "";
				}
			}
			
			if (request.getParameter("dataCadastro") != null) {
				dataCadastro = request.getParameter("dataCadastro");
			}else{
				if(request.getSession().getAttribute("dataCadastro") != null){
					dataCadastro = request.getSession().getAttribute("dataCadastro").toString();
				}else{
					dataCadastro = "";
				}
			}
			
			if (request.getParameter("situacaoProposta") != null) {
				situacaoProposta = request.getParameter("situacaoProposta");
			}else{
				if(request.getSession().getAttribute("situacaoProposta") != null){
					situacaoProposta = request.getSession().getAttribute("situacaoProposta").toString();
				}else{
					situacaoProposta = "";
				}
			}
			
			
			
			if (request.getParameter("check_situacao_null") != null) {
				check_situacao_null = request.getParameter("check_situacao_null");
				if(!check_situacao_null.equalsIgnoreCase("")){
					check_situacao_null = " checked=\"checked\" ";
					bCheck_situacao_null = true;
				}else{
					check_situacao_null = "";
				}
			}else{
				
				if(request.getSession().getAttribute("check_situacao_null") != null){
					check_situacao_null = request.getSession().getAttribute("check_situacao_null").toString();
					
					if(!check_situacao_null.equalsIgnoreCase("")){
						bCheck_situacao_null = true;
					}else{
						bCheck_situacao_null = false;
					}
					
				}else{
					check_situacao_null = "";
				}
				
			}					
			
			
			if(codigo == null) codigo = "";
			if(codigo.equalsIgnoreCase("0")) codigo = "";
			if(codigo_cliente.equalsIgnoreCase("0")) codigo_cliente = "";
						
			request.getSession().setAttribute("dataPrevista",dataPrevista );
			request.getSession().setAttribute("dataCadastro",dataCadastro );
			request.getSession().setAttribute("situacaoProposta", situacaoProposta );
			request.getSession().setAttribute("codigo", codigo);
			request.getSession().setAttribute("check_situacao_null", check_situacao_null);
			request.getSession().setAttribute("codigo_cliente", codigo_cliente);


			if (request.getSession().getAttribute("id_cliente_proposta") == null) request.getSession().setAttribute("id_cliente_proposta","");

			
			//busca a listagem de propostas comerciais
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			ArrayList<PropostaComercial> listPropostas;
			listPropostas = propostaDAO.consultaListagemPropostaCliente(id_cliente,request.getSession().getAttribute("codigo_cliente").toString(), 
																		request.getSession().getAttribute("codigo").toString(), 
																		request.getSession().getAttribute("dataCadastro").toString(),
																		request.getSession().getAttribute("dataPrevista").toString(),
																		request.getSession().getAttribute("situacaoProposta").toString(),
																		entidade,
																		bCheck_situacao_null,
																		u.getUsu_cd_usuario());
			
			
			if (listPropostas == null){
				request.getSession().setAttribute("listPropostas","");
				request.getSession().setAttribute("listTotalProposta","");
				request.getSession().setAttribute("listClientesNames","");
				request.getSession().setAttribute("total_propostas","");
			}else{
				request.getSession().setAttribute("listPropostas",listPropostas);
				request.getSession().setAttribute("listTotalProposta",propostaDAO.getListTotalPropostaConsultaListagem());
				request.getSession().setAttribute("listClientesNames",propostaDAO.getListClienteNamesConsultaListagem());	
			}
			
			
			
			
			//busca os nomes das situacoes para o combobox
			SituacaoPropostaDAO sitDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listNomesSituacao;
			listNomesSituacao = sitDAO.buscaTodosAtribuidosSitucaoPropostaListagemNomes();
			if(listNomesSituacao == null){
				request.getSession().setAttribute("listNomesSituacao","");
			}else{
				request.getSession().setAttribute("listNomesSituacao",listNomesSituacao);
			}

			
			//busca as entidades para o combobox
			



			response.sendRedirect("listagemPropostaComercial.jsp");
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
