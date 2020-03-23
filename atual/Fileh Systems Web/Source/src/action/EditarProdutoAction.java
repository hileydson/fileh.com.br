package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class EditarProdutoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			Produto produto = new Produto();
			
			String id_produto = request.getParameter("name_produto_hidden");
			int id_produto_integer = Integer.parseInt(id_produto);
			
			produto = (Produto) proDAO.carregaEntidade(produto, id_produto_integer );
						
			
			if (produto.getPrd_ds_produto().equalsIgnoreCase("null")) produto.setPrd_ds_produto("");
			if (produto.getPrd_ds_unidade().equalsIgnoreCase("null")) produto.setPrd_ds_unidade("");



			request.getSession().setAttribute("produto", produto);
			
			response.sendRedirect("editarProduto.jsp");
			
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
