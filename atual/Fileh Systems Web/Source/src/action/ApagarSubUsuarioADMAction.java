package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubUsuarioDAO;
import entidades.SubUsuario;
import entidades.Usuario;

public class ApagarSubUsuarioADMAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_subusuario = request.getParameter("name_subusuario_hidden");
			int id_subusuario_integer = Integer.parseInt(id_subusuario);


			SubUsuarioDAO subUsuarioDAO = new SubUsuarioDAO(u.getUsu_ds_subdominio());
			SubUsuario subUsuario = new SubUsuario();


			subUsuario = (SubUsuario) subUsuarioDAO.carregaEntidade(subUsuario, id_subusuario_integer );

			

			if (subUsuarioDAO.deleteEntidade(subUsuario) == true){
				CarregaCadastroSubUsuarioAction carregaCadastroSubUsuarioServlet = new CarregaCadastroSubUsuarioAction();				
				carregaCadastroSubUsuarioServlet.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar usuário, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar usuário, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
