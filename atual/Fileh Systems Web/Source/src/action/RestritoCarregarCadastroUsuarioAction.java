package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import entidades.Usuario;

public class RestritoCarregarCadastroUsuarioAction implements ActionProcess{
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		try{

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			
			ArrayList<Usuario> arrayUsuario = usuarioDAO.buscaTodosUsuarios();
			
			request.getSession().setAttribute("arrayUsuario", arrayUsuario);

			response.sendRedirect("restritoCadastroUsuarioADM.jsp");

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao acessar usuarios cadastrados...");
			response.sendRedirect("processoErroMsg.jsp");
		}
		
	}
}
