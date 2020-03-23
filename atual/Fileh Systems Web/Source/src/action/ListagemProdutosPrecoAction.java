package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class ListagemProdutosPrecoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{

			/*----busca Preços consulta-----*/
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			ArrayList<Produto> arrayProduto = null;
			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());

			String auxPesquisar = "";

			//busca a data da ultima atualização do modulo de vendas/produtos
			request.getSession().setAttribute("data_atualizacao", "   /   /  ");

			//coloca a action do pesquisar para quando pesquisar carregar a pagina correta 
			request.getSession().setAttribute("pesquisar_action", "ListagemProdutosPreco.action");


			auxPesquisar = request.getParameter("pesquisar");


			arrayProduto = proDAO.consultaProdutos(auxPesquisar, u.getUsu_cd_usuario() );
			request.getSession().setAttribute("arrayProdutos", arrayProduto);

			response.sendRedirect("listagemProdutosPreco.jsp");



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar pre�os...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
