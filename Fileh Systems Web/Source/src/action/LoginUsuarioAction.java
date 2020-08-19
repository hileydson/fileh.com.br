package action;

import hibernate.ServicosGeral;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.SubUsuario;
import entidades.Usuario;

public class LoginUsuarioAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			ServicosGeral sg = new ServicosGeral(null);	

			Usuario usuario = new Usuario();
			usuario.setUsu_ds_email(request.getParameter("input_text_email_usuario"));

			SubUsuario subusuario = new SubUsuario();
			subusuario.setSbu_ds_login(request.getParameter("input_text_login_usuario"));
			subusuario.setSbu_sh_subusuario(request.getParameter("input_text_senha_usuario"));

			List<Object[]> listResult = sg.validaLoginUsuario(request, usuario,subusuario);
			
			if(listResult.size() == 0){ 
				
				request.getSession().setAttribute("usuario", "erro");
				request.getSession().setAttribute("subusuario", "erro");
				response.sendRedirect("index.jsp");
			
			}else{
			
				//CARREGAR MODULOS, ID E USUARIO LOGADO
				int id;
				
				Object[] row = listResult.get(0);

				
				//carrega usuario
				//Object cd_usuario = row[0];
				
				//id = Integer.parseInt(cd_usuario.toString());
				
				//usuario = (Usuario) sg.carregaEntidade(usuario, id);
				
				
				//carrega subusuario
				Object cd_subusuario = row[1];
				
				id = Integer.parseInt(cd_subusuario.toString());

				subusuario = (SubUsuario) sg.carregaEntidade(subusuario, id);

				
				//request.getSession().setAttribute("usuario", usuario);
				request.getSession().setAttribute("subusuario", subusuario);
				
				response.sendRedirect("usuarioLogadoInicial.jsp"); 		
				
				
			}

		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
