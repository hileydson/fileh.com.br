package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class ListagemProdutosAction implements ActionProcess {

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
			request.getSession().setAttribute("pesquisar_action", "ListagemProdutos.action");


			if(request.getSession().getAttribute("ConfirmarEditarApagarProduto.action") != null){
				//vem do servlet que edita o produto e o produto ja foi pesquisado
				if(request.getSession().getAttribute("voltar_editar_produto") != null){
					auxPesquisar = request.getSession().getAttribute("voltar_editar_produto").toString();
				};
				request.getSession().setAttribute("ConfirmarEditarApagarProduto.action", null);
			}else{
				auxPesquisar = request.getParameter("pesquisar");
			}


			//guarda o valor pesquisado para quando voltar da edição do produto
			request.getSession().setAttribute("voltar_editar_produto", auxPesquisar);


			arrayProduto = proDAO.consultaProdutos(auxPesquisar, u.getUsu_cd_usuario() );

			request.getSession().setAttribute("arrayProdutos", arrayProduto);

			//não redireciona a pagina caso for para carregar algum frame de listagem de produtos
			if (request.getSession().getAttribute("carregarFrameProdutos") == null) {

				//caso for o filtro de algum frame de listagem de produtos apenas da um refresh na pagina
				if (request.getParameter("fl_frame") == null){
					response.sendRedirect("listagemProdutos.jsp");
				}else{
					response.sendRedirect("incluirProdutoPropostaFrame.jsp");
				}

			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar produtos...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
