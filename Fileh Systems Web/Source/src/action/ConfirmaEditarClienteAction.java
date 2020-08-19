package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import entidades.Cliente;
import entidades.Usuario;

public class ConfirmaEditarClienteAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			Cliente cliente = (Cliente) request.getSession().getAttribute("cliente"); 
			
			cliente.setCli_ds_bairro(request.getParameter("cli_ds_bairro"));
			cliente.setCli_ds_entidade(request.getParameter("cli_ds_entidade"));
			cliente.setCli_ds_logradouro(request.getParameter("cli_ds_logradouro"));
			cliente.setCli_ds_referencia(request.getParameter("cli_ds_referencia"));
			cliente.setCli_ds_uf(request.getParameter("cli_ds_uf"));
			cliente.setCli_nm_cliente(request.getParameter("cli_nm_cliente"));
			cliente.setCli_nr_cpf(request.getParameter("cli_nr_cpf"));
			cliente.setCli_nr_tel(request.getParameter("cli_nr_tel"));

			if (cliDAO.updateEntidade(cliente)){
				ListagemClientesAction listagemCliente = new ListagemClientesAction();
				listagemCliente.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do cliente, favor verificar se o cliente existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do cliente, favor verificar se o cliente existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
