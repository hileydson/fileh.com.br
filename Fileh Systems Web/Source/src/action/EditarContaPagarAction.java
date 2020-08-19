package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaPagarDAO;
import dao.FornecedorDAO;
import dao.TipoContaDAO;
import entidades.ContaPagar;
import entidades.Usuario;

public class EditarContaPagarAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			

			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			//carrega os combobox
			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());
			

			ArrayList <String> arrayFornecedor = forDAO.buscaFornecedoresListagemNomes(u.getUsu_cd_usuario());
			ArrayList <String> arrayTipoConta = tipoContaDAO.buscaTipoContasListagemNomes(u.getUsu_cd_usuario());
			
			request.getSession().setAttribute("arrayFornecedor", arrayFornecedor);
			request.getSession().setAttribute("arrayTipoConta", arrayTipoConta);
			
			
			ContaPagarDAO contaPagarDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());
			ContaPagar contaPagar = new ContaPagar();
			
			String id_conta = request.getParameter("conta_hidden");
			
			contaPagar = (ContaPagar) contaPagarDAO.carregaEntidade(contaPagar, Integer.parseInt(id_conta) );
			
			if(contaPagar.getCop_ds_fornecedor().equalsIgnoreCase("null")) contaPagar.setCop_ds_fornecedor("");
			if(contaPagar.getCop_nr_documento().equalsIgnoreCase("null")) contaPagar.setCop_nr_documento("");
			if(contaPagar.getCop_ds_tipo_conta().equalsIgnoreCase("null")) contaPagar.setCop_ds_tipo_conta("");
			if(contaPagar.getCop_ds_conta_pagar().equalsIgnoreCase("null")) contaPagar.setCop_ds_conta_pagar("");				
			if(contaPagar.getCop_fl_pago().equalsIgnoreCase("null")) contaPagar.setCop_fl_pago("");
			
			if(contaPagar.getCop_dt_vencimento().equalsIgnoreCase("null")){ 
				contaPagar.setCop_dt_vencimento("");
			}else{
				contaPagar.setCop_dt_vencimento(contaPagarDAO.dataConverteDataBrasilMysqlData(contaPagar.getCop_dt_vencimento(), "-", "/") );
			};
			

			request.getSession().setAttribute("contaPagar", contaPagar);
			
			response.sendRedirect("editarContaPagar.jsp");
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
