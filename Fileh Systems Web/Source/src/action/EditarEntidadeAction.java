package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import entidades.Entidade;
import entidades.Usuario;

public class EditarEntidadeAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_entidade = request.getParameter("name_entidade_hidden");
			int id_entidade_integer = Integer.parseInt(id_entidade);

			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			Entidade entidade = new Entidade();

			entidade = (Entidade) entDAO.carregaEntidade(entidade, id_entidade_integer );
			
			if (entidade == null){
				response.sendRedirect("processoErro.jsp");
			}else{
				request.getSession().setAttribute("entidade", entidade);
				response.sendRedirect("editarEntidade.jsp");
			}


		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}

	}

}
