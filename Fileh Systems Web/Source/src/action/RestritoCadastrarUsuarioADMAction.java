package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import entidades.Usuario;

public class RestritoCadastrarUsuarioADMAction implements ActionProcess{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			
			
			Integer resp;
			
			Usuario usuario = new Usuario();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			usuario.setUsu_nm_usuario(request.getParameter("usu_nm_usuario"));
			usuario.setUsu_ds_fantasia(request.getParameter("usu_ds_fantasia"));
			usuario.setUsu_ds_logradouro(request.getParameter("usu_ds_logradouro"));
			usuario.setUsu_ds_numero(request.getParameter("usu_ds_Numero"));
			usuario.setUsu_ds_bairro(request.getParameter("usu_ds_bairro"));
			usuario.setUsu_ds_cidade(request.getParameter("usu_ds_cidade"));
			usuario.setUsu_ds_uf(request.getParameter("usu_ds_uf"));
			usuario.setUsu_ds_tel(request.getParameter("usu_ds_tel"));
			usuario.setUsu_ds_subdominio(request.getParameter("usu_ds_subdominio"));
			usuario.setUsu_ds_email(request.getParameter("usu_ds_email"));
			usuario.setUsu_nr_cnpj(request.getParameter("usu_nr_cnpj"));
			usuario.setUsu_cd_plano(Integer.parseInt(request.getParameter("usu_cd_plano")) );
			
			resp = usuarioDAO.inserirRegistro(usuario);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar usuario... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				RestritoCarregarCadastroUsuarioAction carregaCadastroUsuarioServlet = new RestritoCarregarCadastroUsuarioAction();
				carregaCadastroUsuarioServlet.process(request, response);
			};
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar usuario...");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
