package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FornecedorDAO;
import entidades.Fornecedor;
import entidades.Usuario;

public class ApagarFornecedorAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_fornecedor = request.getParameter("name_fornecedor_hidden");
			int id_fornecedor_integer = Integer.parseInt(id_fornecedor);


			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			Fornecedor fornecedor = new Fornecedor();


			fornecedor = (Fornecedor) forDAO.carregaEntidade(fornecedor, id_fornecedor_integer );

			

			if (forDAO.deleteEntidade(fornecedor) == true){
				CarregaCadastroFornecedorAction cadastroFornecedor = new CarregaCadastroFornecedorAction();				
				cadastroFornecedor.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar fornecedor, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar fornecedor, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
