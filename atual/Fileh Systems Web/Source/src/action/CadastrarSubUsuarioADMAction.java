package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubUsuarioDAO;
import entidades.SubUsuario;
import entidades.Usuario;

public class CadastrarSubUsuarioADMAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			SubUsuario subUsuario = new SubUsuario();
			SubUsuarioDAO subUsuarioDAO = new SubUsuarioDAO(u.getUsu_ds_subdominio());

			//senha padrão inicial
			subUsuario.setSbu_sh_subusuario("123");
			
			subUsuario.setSbu_cd_usuario(u.getUsu_cd_usuario());
			subUsuario.setSbu_ds_login(request.getParameter("sbu_ds_login"));
			subUsuario.setSbu_nm_subusuario(request.getParameter("sbu_nm_subusuario"));
			subUsuario.setSbu_fl_adm(request.getParameter("sbu_fl_adm"));
			subUsuario.setSbu_fl_modulo_caixa(request.getParameter("sbu_fl_caixa"));
			subUsuario.setSbu_fl_modulo_cliente(request.getParameter("sbu_fl_cliente"));
			subUsuario.setSbu_fl_modulo_financeiro(request.getParameter("sbu_fl_financeiro"));
			subUsuario.setSbu_fl_modulo_venda(request.getParameter("sbu_fl_vendas"));

			resp = subUsuarioDAO.inserirRegistro(subUsuario);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar usuário... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroSubUsuarioAction carregaCadastroSubUsuarioServlet = new CarregaCadastroSubUsuarioAction();
				carregaCadastroSubUsuarioServlet.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar usuário... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
