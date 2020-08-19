package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import entidades.Entidade;
import entidades.Usuario;

public class ConfirmaEditarEntidadeAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			Entidade entidade = (Entidade) request.getSession().getAttribute("entidade"); 
			
			entidade.setEnt_nm_entidade(request.getParameter("ENT_NM_ENTIDADE"));

			if (entDAO.updateEntidade(entidade) == true){
				CarregaCadastroEntidadeAction cadastroEntidade = new CarregaCadastroEntidadeAction();
				cadastroEntidade.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o fornecedor existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o fornecedor existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
