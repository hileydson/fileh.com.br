package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.EntidadeDAO;
import entidades.Cliente;
import entidades.Usuario;

public class EditarClienteAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			Cliente cliente = new Cliente();
			EntidadeDAO entidadeDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			ArrayList<String> arrayEntidadesEdit;
			
			
			String id_cliente = request.getParameter("name_cliente_hidden");			
			cliente = (Cliente) cliDAO.carregaEntidade(cliente, Integer.parseInt(id_cliente) );
			
			
			arrayEntidadesEdit = entidadeDAO.buscaTodasEntidadesListagemNomes(u.getUsu_cd_usuario());			
			
			
			if(cliente.getCli_ds_bairro().equalsIgnoreCase("null")) cliente.setCli_ds_bairro("");
			if(cliente.getCli_ds_entidade().equalsIgnoreCase("null")) cliente.setCli_ds_entidade("");
			if(cliente.getCli_ds_logradouro().equalsIgnoreCase("null")) cliente.setCli_ds_logradouro("");
			if(cliente.getCli_ds_referencia().equalsIgnoreCase("null")) cliente.setCli_ds_referencia("");
			if(cliente.getCli_ds_uf().equalsIgnoreCase("null")) cliente.setCli_ds_uf("");
			if(cliente.getCli_nm_cliente().equalsIgnoreCase("null")) cliente.setCli_nm_cliente("");
			if(cliente.getCli_nr_cpf().equalsIgnoreCase("null")) cliente.setCli_nr_cpf("");
			if(cliente.getCli_nr_tel().equalsIgnoreCase("null")) cliente.setCli_nr_tel("");
			
			

			request.getSession().setAttribute("cliente", cliente);
			request.getSession().setAttribute("arrayEntidadesEdit", arrayEntidadesEdit);
			
			response.sendRedirect("editarCliente.jsp");
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
		
		
	}

}
