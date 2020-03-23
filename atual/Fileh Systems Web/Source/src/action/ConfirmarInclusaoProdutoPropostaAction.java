package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemPropostaDAO;
import dao.ProdutoDAO;
import dao.PropostaComercialDAO;
import entidades.ItemProposta;
import entidades.Produto;
import entidades.PropostaComercial;
import entidades.Usuario;

public class ConfirmarInclusaoProdutoPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub

		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			Produto produto = new Produto();
			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			
			ItemProposta item = new ItemProposta();
			ItemPropostaDAO itemDAO = new ItemPropostaDAO(u.getUsu_ds_subdominio());
			
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			PropostaComercial proposta_editando;
			
			
			String id_produto = request.getParameter("produto_hidden");
			String proposta_qtd = request.getParameter("qtd_produto_hidden");
			
			
			proposta_editando = (PropostaComercial) request.getSession().getAttribute("proposta_editando");
			
			
			//recupera o produto e cria um novo item para a proposta que está sendo editada
			produto = (Produto) proDAO.carregaEntidade(produto, Integer.parseInt(id_produto));
			item.setIpc_cd_proposta_comercial(proposta_editando.getPrc_cd_proposta_comercial());
			item.setIpc_ds_item_proposta(produto.getPrd_ds_produto());
			item.setIpc_ds_unidade(produto.getPrd_ds_unidade());
			item.setIpc_nr_quantidade(Double.parseDouble(proposta_qtd));
			item.setIpc_vl_desconto(0.0);
			item.setIpc_vl_item_proposta(produto.getPrd_vl_preco());
			
			if( itemDAO.inserirRegistro(item) == null){
				request.getSession().setAttribute("msg_erro", "Erro ao incluir novo item na proposta comercial...entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");	
				return;
			}
			
			
			//para que não tenha nenhuma msg ao carregar a pagina
			request.getSession().setAttribute("proposta_comercial_criada","S");
			
			//carrega proposta novamente
			propostaDAO.carregaDadosPropostaComercial(request, response);			
			
			//carrega o frame de listagem de clientes e produtos
			propostaDAO.carregaFramesPropostaComercial(request, response);
			
			response.sendRedirect("refreshParentFromFrame.jsp");
			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao incluir cliente na proposta comercial...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}	
	}

}
