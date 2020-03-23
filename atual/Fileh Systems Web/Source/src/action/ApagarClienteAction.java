package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import entidades.Cliente;
import entidades.Usuario;

public class ApagarClienteAction implements ActionProcess{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub

		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_cliente = request.getParameter("name_cliente_hidden");
			int id_cliente_integer = Integer.parseInt(id_cliente);


			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			Cliente cliente = new Cliente();


			cliente = (Cliente) cliDAO.carregaEntidade(cliente, id_cliente_integer );

			

			if (cliDAO.deleteEntidade(cliente) == true){
				ListagemClientesAction listagemClientes = new ListagemClientesAction();				
				listagemClientes.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar cliente, possivelmente existe uma proposta <br> "+
																"em aberta deste cliente, favor verificar, caso contrário <br>"+
																"entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao deletar cliente, possivelmente existe uma proposta <br> "+
															"em aberta deste cliente, favor verificar, caso contrário <br>"+
															"entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
		
	}

}
