package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubUsuarioDAO;
import entidades.SubUsuario;
import entidades.Usuario;

public class CarregaCadastroSubUsuarioAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			SubUsuarioDAO subUsuarioDAO = new SubUsuarioDAO(u.getUsu_ds_subdominio());
			
			ArrayList<SubUsuario> arraySubUsuario = subUsuarioDAO.buscaTodosSubUsuarios(u.getUsu_cd_usuario());
			
			request.getSession().setAttribute("arraySubUsuario", arraySubUsuario);

			response.sendRedirect("cadastroSubUsuarioADM.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao acessar usuarios cadastrados...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
