package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FornecedorDAO;
import entidades.Fornecedor;
import entidades.Usuario;

public class CadastrarFornecedorAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			Fornecedor f = new Fornecedor();
			FornecedorDAO fDAO = new FornecedorDAO(u.getUsu_ds_subdominio());

			f.setFor_ds_fornecedor(request.getParameter("FOR_DS_FORNECEDOR"));
			f.setFor_nr_cnpj(request.getParameter("FOR_NR_CNPJ"));
			f.setFor_ds_logradouro(request.getParameter("FOR_DS_LOGRADOURO"));
			f.setFor_ds_bairro(request.getParameter("FOR_DS_BAIRRO"));
			f.setFor_ds_cidade(request.getParameter("FOR_DS_CIDADE"));
			f.setFor_nr_cep(request.getParameter("FOR_NR_CEP"));
			f.setFor_ds_contato(request.getParameter("FOR_DS_CONTATO"));
			f.setFor_nr_insc_estadual(request.getParameter("FOR_NR_INSC_ESTADUAL"));
			f.setFor_nr_insc_municipal(request.getParameter("FOR_NR_INSC_MUNICIPAL"));
			f.setFor_cd_usuario(u.getUsu_cd_usuario());

			resp = fDAO.inserirRegistro(f);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar fornecedor... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroFornecedorAction carregaCadastroFornecedor = new CarregaCadastroFornecedorAction();
				carregaCadastroFornecedor.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar fornecedor... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
		
	}

}
