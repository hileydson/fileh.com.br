package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.UsuarioDAO;
import entidades.Usuario;

public class RestritoConfirmaEditarUsuarioADMAction implements ActionProcess{
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = new Usuario(); 
			
			String str_cd_subusuario = request.getParameter("usu_cd_usuario");
			
			usuario = (Usuario) usuarioDAO.carregaEntidade(usuario, Integer.parseInt(str_cd_subusuario) );
			
			
			usuario.setUsu_nm_usuario(request.getParameter("usu_nm_usuario_edt"));
			usuario.setUsu_ds_fantasia(request.getParameter("usu_ds_fantasia_edt"));
			usuario.setUsu_ds_logradouro(request.getParameter("usu_ds_logradouro_edt"));
			usuario.setUsu_ds_numero(request.getParameter("usu_ds_Numero_edt"));
			usuario.setUsu_ds_bairro(request.getParameter("usu_ds_bairro_edt"));
			usuario.setUsu_ds_cidade(request.getParameter("usu_ds_cidade_edt"));
			usuario.setUsu_ds_uf(request.getParameter("usu_ds_uf_edt"));
			usuario.setUsu_ds_tel(request.getParameter("usu_ds_tel_edt"));
			usuario.setUsu_ds_subdominio(request.getParameter("usu_ds_subdominio_edt"));
			usuario.setUsu_ds_email(request.getParameter("usu_ds_email_edt"));
			usuario.setUsu_nr_cnpj(request.getParameter("usu_nr_cnpj_edt"));
			usuario.setUsu_cd_plano(Integer.parseInt(request.getParameter("usu_cd_plano_edt")) );

			
			if (usuarioDAO.updateEntidade(usuario) == true){
								
				RestritoCarregarCadastroUsuarioAction carregaCadastroUsuarioServlet = new RestritoCarregarCadastroUsuarioAction();				
				carregaCadastroUsuarioServlet.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do usuario, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao editar usuario...");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
