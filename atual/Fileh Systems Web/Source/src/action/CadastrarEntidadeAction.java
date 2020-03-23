package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import entidades.Entidade;
import entidades.Usuario;

public class CadastrarEntidadeAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			Entidade e = new Entidade();
			EntidadeDAO eDAO = new EntidadeDAO(u.getUsu_ds_subdominio());

			e.setEnt_nm_entidade(request.getParameter("ENT_NM_ENTIDADE"));
			e.setEnt_cd_usuario(u.getUsu_cd_usuario());

			resp = eDAO.inserirRegistro(e);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar entidade... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroEntidadeAction carregaCadastroEndidade = new CarregaCadastroEntidadeAction();
				carregaCadastroEndidade.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar entidade... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
