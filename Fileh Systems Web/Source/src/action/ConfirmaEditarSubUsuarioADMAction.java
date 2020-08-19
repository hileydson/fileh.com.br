package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubUsuarioDAO;
import entidades.SubUsuario;
import entidades.Usuario;

public class ConfirmaEditarSubUsuarioADMAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			SubUsuarioDAO subUsuarioDAO = new SubUsuarioDAO(u.getUsu_ds_subdominio());
			SubUsuario subUsuario = new SubUsuario(); 
			
			String str_cd_subusuario = request.getParameter("sbu_cd_subusuario");
			
			subUsuario = (SubUsuario) subUsuarioDAO.carregaEntidade(subUsuario, Integer.parseInt(str_cd_subusuario) );
			
			
			subUsuario.setSbu_ds_login(request.getParameter("sbu_ds_login_edt"));
			subUsuario.setSbu_nm_subusuario(request.getParameter("sbu_nm_subusuario_edt"));
			subUsuario.setSbu_fl_adm(request.getParameter("sbu_fl_adm_edt"));
			subUsuario.setSbu_fl_modulo_caixa(request.getParameter("sbu_fl_caixa_edt"));
			subUsuario.setSbu_fl_modulo_cliente(request.getParameter("sbu_fl_cliente_edt"));
			subUsuario.setSbu_fl_modulo_financeiro(request.getParameter("sbu_fl_financeiro_edt"));
			subUsuario.setSbu_fl_modulo_venda(request.getParameter("sbu_fl_vendas_edt"));

			
			if (subUsuarioDAO.updateEntidade(subUsuario) == true){
								
				CarregaCadastroSubUsuarioAction carregaCadastroSubUsuario = new CarregaCadastroSubUsuarioAction();				
				carregaCadastroSubUsuario.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do usuário, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do usuário, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
