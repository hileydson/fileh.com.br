package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.Usuario;

public class CadastrarProdutoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			Produto p = new Produto();
			ProdutoDAO pDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			

			p.setPrd_ds_produto(request.getParameter("PRD_DS_PRODUTO"));
			p.setPrd_ds_unidade(request.getParameter("PRD_DS_UNIDADE"));
			p.setPrd_nr_estoque(Integer.parseInt( request.getParameter("PRD_NR_ESTOQUE")) );
			
			p.setPrd_vl_preco(pDAO.realConverteStringToDouble(request.getParameter("PRD_VL_PRECO")));			
			
			p.setPrd_cd_usuario(u.getUsu_cd_usuario());

			resp = pDAO.inserirRegistro(p);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar produto... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{				
				request.getSession().setAttribute("msg_cadastro",	"Dados :<br><br>"+
						"Descrição: "+p.getPrd_ds_produto()+"<br>"+
						"UN: "+p.getPrd_ds_unidade()+"<br>"+
						"Estoque: "+p.getPrd_nr_estoque()+"<br>"+
						"Preço: "+p.getPrd_vl_preco()+"<br>"+
						"<br><br>Cadastro efetuado com sucesso!");
				
				CarregaCadastroProdutoAction cadastroProduto = new CarregaCadastroProdutoAction();
				cadastroProduto.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar produto... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
