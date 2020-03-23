package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FornecedorDAO;
import entidades.Fornecedor;
import entidades.Usuario;

public class CarregaCadastroFornecedorAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());

			ArrayList<Fornecedor> listFornecedor = forDAO.buscaTodosFornecedores(u.getUsu_cd_usuario());

			request.getSession().setAttribute("arrayFornecedor", listFornecedor);

			response.sendRedirect("cadastroFornecedor.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao acessar fornecedores cadastradas...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
