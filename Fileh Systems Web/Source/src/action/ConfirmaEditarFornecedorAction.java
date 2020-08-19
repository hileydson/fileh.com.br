package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FornecedorDAO;
import entidades.Fornecedor;
import entidades.Usuario;

public class ConfirmaEditarFornecedorAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			Fornecedor fornecedor = (Fornecedor) request.getSession().getAttribute("fornecedor"); 
			
			fornecedor.setFor_ds_fornecedor(request.getParameter("FOR_DS_FORNECEDOR"));
			fornecedor.setFor_nr_cnpj(request.getParameter("FOR_NR_CNPJ"));
			fornecedor.setFor_ds_logradouro(request.getParameter("FOR_DS_LOGRADOURO"));
			fornecedor.setFor_ds_bairro(request.getParameter("FOR_DS_BAIRRO"));
			fornecedor.setFor_ds_cidade(request.getParameter("FOR_DS_CIDADE"));
			fornecedor.setFor_nr_cep(request.getParameter("FOR_NR_CEP"));
			fornecedor.setFor_ds_contato(request.getParameter("FOR_DS_CONTATO"));
			fornecedor.setFor_nr_insc_estadual(request.getParameter("FOR_NR_INSC_ESTADUAL"));
			fornecedor.setFor_nr_insc_municipal(request.getParameter("FOR_NR_INSC_MUNICIPAL"));
			
			if (forDAO.updateEntidade(fornecedor) == true){
				CarregaCadastroFornecedorAction cadastroFornecedor = new CarregaCadastroFornecedorAction();
				cadastroFornecedor.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o fornecedor existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o fornecedor existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
