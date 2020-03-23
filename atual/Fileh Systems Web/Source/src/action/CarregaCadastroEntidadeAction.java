package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import entidades.Entidade;
import entidades.Usuario;

public class CarregaCadastroEntidadeAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			/*----busca grupo de entidades cadastradas -----*/
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());

			ArrayList<Entidade> listEntidades = entDAO.buscaTodasEntidades(u.getUsu_cd_usuario().toString());

			request.getSession().setAttribute("arrayEntidades", listEntidades);

			response.sendRedirect("cadastroEntidade.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao acessar entidades cadastradas...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
