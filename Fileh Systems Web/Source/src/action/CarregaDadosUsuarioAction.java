package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PlanoDAO;
import dao.UsuarioDAO;
import entidades.Usuario;
import action.ActionProcess;

public class CarregaDadosUsuarioAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			
			//carrega contador de uso do sistema...
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			UsuarioDAO usuDAO = new UsuarioDAO();
			PlanoDAO plaDAO = new PlanoDAO();
			
			request.getSession().setAttribute("contadorUsoSistema", usuDAO.carregaContadorSistema(u.getUsu_ds_subdominio()));
			request.getSession().setAttribute("planoUsuario", plaDAO.buscaPlanoUsuario(u.getUsu_cd_plano()));
			
			response.sendRedirect("geral_Dados_Gerais.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao carregar dados do usuario...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
