package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubUsuarioDAO;
import entidades.SubUsuario;
import entidades.Usuario;

public class ConfirmaEditarSubUsuarioAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			SubUsuarioDAO subUsuarioDAO = new SubUsuarioDAO(u.getUsu_ds_subdominio());
			SubUsuario subUsuario = (SubUsuario) request.getSession().getAttribute("subusuario"); 
			
			subUsuario = (SubUsuario) subUsuarioDAO.carregaEntidade(subUsuario, subUsuario.getSbu_cd_subusuario());
			
			
			subUsuario.setSbu_nm_subusuario(request.getParameter("SBU_NM_SUBUSUARIO"));
			subUsuario.setSbu_ds_login(request.getParameter("SBU_DS_LOGIN"));
			subUsuario.setSbu_ds_msg_footer(request.getParameter("SBU_DS_MSG_FOOTER"));
			subUsuario.setSbu_sh_subusuario(request.getParameter("SBU_SH_SUBUSUARIO"));

			
			if (subUsuarioDAO.updateEntidade(subUsuario) == true){
				request.getSession().setAttribute("subusuario", subUsuario);
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				
				response.sendRedirect("editarSubUsuario.jsp");
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do usuario, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do usuario, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
