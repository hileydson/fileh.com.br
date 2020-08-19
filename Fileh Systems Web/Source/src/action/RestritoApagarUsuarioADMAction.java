package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import entidades.Usuario;

public class RestritoApagarUsuarioADMAction implements ActionProcess{

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			
			String id_subusuario = request.getParameter("name_usuario_hidden");
			int id_subusuario_integer = Integer.parseInt(id_subusuario);


			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = new Usuario();

			usuario = (Usuario) usuarioDAO.carregaEntidade(usuario, id_subusuario_integer );


			if (usuarioDAO.deleteEntidade(usuario) == true){
				RestritoCarregarCadastroUsuarioAction carregaCadastroUsuarioServlet = new RestritoCarregarCadastroUsuarioAction();				
				carregaCadastroUsuarioServlet.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar usuário, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao apagar usuario...");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
