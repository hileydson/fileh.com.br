package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.EntidadeDAO;
import entidades.Cliente;
import entidades.Usuario;

public class ListagemClientesAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			
			/*----busca clientes consulta-----*/
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			ArrayList<Cliente> listClientes;
			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());

			String msgEntidade = " ";
			String entidade = "";
			String nome = "";
			String cd_cliente = "";

			
			//busca filtros como parametros do form, vai encontrar somente se vinher do formulario de listagem de clientes
			if (request.getParameter("entidade") != null) request.getSession().setAttribute("entidade_listagem",request.getParameter("entidade")); 
			if (request.getParameter("nome") != null) request.getSession().setAttribute("nome",request.getParameter("nome") );
			if (request.getParameter("cd_cliente") != null) request.getSession().setAttribute("cd_cliente", request.getParameter("cd_cliente"));

			
			//busca filtros já inseridos como atributos da sessão, já foi filtrado algo antes...
			if (request.getSession().getAttribute("entidade_listagem") == null) request.getSession().setAttribute("entidade_listagem","");
			if (request.getSession().getAttribute("nome") == null) request.getSession().setAttribute("nome","");
			if (request.getSession().getAttribute("cd_cliente") == null) request.getSession().setAttribute("cd_cliente","");
			

			entidade = request.getSession().getAttribute("entidade_listagem").toString();
			nome = request.getSession().getAttribute("nome").toString();
			cd_cliente = request.getSession().getAttribute("cd_cliente").toString();

			if((entidade.equalsIgnoreCase("")) || (entidade.equalsIgnoreCase("-1"))	){
				entidade = "-1";
				msgEntidade = "Escolha uma entidade...";
			}else{
				msgEntidade = entidade;
			};
			

			request.getSession().setAttribute("entidade_listagem", entidade);
			request.getSession().setAttribute("msgEntidade", msgEntidade);
			request.getSession().setAttribute("nome", nome);
			request.getSession().setAttribute("cd_cliente", cd_cliente);

			
			try{			
				//retira os possiveis zeros do inicio do numero
				int aux_cd_cliente = Integer.parseInt(cd_cliente);
				cd_cliente = aux_cd_cliente + "";
			}catch(Exception e){}
		
			
			listClientes = cliDAO.consultaClientes(nome, cd_cliente, entidade, u.getUsu_cd_usuario().toString());

			request.getSession().setAttribute("arrayClientes", listClientes);
			
			/*----busca grupo de entidades cadastradas para filtrar-----*/
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listEntidades = entDAO.buscaEntidadesListagemNomes( u.getUsu_cd_usuario());
			
			request.getSession().setAttribute("arrayEntidades", listEntidades);

			//não redireciona a pagina caso for para carregar algum frame de listagem de clientes
			if (request.getSession().getAttribute("carregarFrameClientes") == null) {
				
				//caso for o filtro de algum frame de listagem de clientes apenas da um refresh na pagina
				if (request.getParameter("fl_frame") == null){
					response.sendRedirect("listagemClientes.jsp");
				}else{
					response.sendRedirect("incluirClientePropostaFrame.jsp");
				}
				
			};
			


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar clientes...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
		
	}

}
