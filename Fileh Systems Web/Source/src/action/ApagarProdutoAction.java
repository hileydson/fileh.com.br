package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class ApagarProdutoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_produto = request.getParameter("name_produto_hidden");
			int id_produto_int = Integer.parseInt(id_produto);


			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			Produto produto = new Produto();


			produto = (Produto) proDAO.carregaEntidade(produto, id_produto_int );

			

			if (proDAO.deleteEntidade(produto) == true){
				request.getSession().setAttribute("ConfirmarEditarApagarProdutoServlet", "considerarAtributoSessao");
				ListagemProdutosAction listagemProdutos = new ListagemProdutosAction();				
				listagemProdutos.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar produto, entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao deletar produto, entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}		
		
		
		
	}

}
