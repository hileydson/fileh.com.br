package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FornecedorDAO;
import dao.TipoContaDAO;
import entidades.Usuario;

public class CarregaCadastroContaReceberAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			
			
			/*----busca grupo de fornecedores cadastradas para escolha-----*/
			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listFornecedor = forDAO.buscaFornecedoresListagemNomes(u.getUsu_cd_usuario());
			request.getSession().setAttribute("arrayNomesFornecedor", listFornecedor);
			
			/*----busca grupo de Tipo Conta cadastradas para escolha-----*/
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());
			ArrayList<String> listTipoConta = tipoContaDAO.buscaTipoContasListagemNomes(u.getUsu_cd_usuario());
			request.getSession().setAttribute("arrayNomesTipoConta", listTipoConta);
			
			

			response.sendRedirect("cadastroContaReceber.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar carregar cadastro de contas a receber...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
