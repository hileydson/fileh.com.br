package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class ConfirmarEditarProdutoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			Produto produto = (Produto) request.getSession().getAttribute("produto"); 
			
			produto.setPrd_ds_produto(request.getParameter("PRD_DS_PRODUTO"));
			produto.setPrd_ds_unidade(request.getParameter("PRD_DS_UNIDADE"));
			produto.setPrd_nr_estoque(Integer.parseInt( request.getParameter("PRD_NR_ESTOQUE")) );
			
			String s = request.getParameter("PRD_VL_PRECO");
			s = s.replace(".", "").replace(".", "").replace(".", "").replace(".", "");
			s = s.replace(",", ".");
			Double d = Double.parseDouble( s );
			produto.setPrd_vl_preco(d);

			if (proDAO.updateEntidade(produto) == true){
				request.getSession().setAttribute("ConfirmarEditarApagarProdutoServlet", "considerarAtributoSessao");
				ListagemProdutosAction listagemProduto = new ListagemProdutosAction();
				listagemProduto.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do produto, favor verificar se o produto existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados do produto, favor verificar se o produto existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
