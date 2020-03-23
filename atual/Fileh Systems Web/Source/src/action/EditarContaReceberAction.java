package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaReceberDAO;
import dao.FornecedorDAO;
import dao.TipoContaDAO;
import entidades.ContaReceber;
import entidades.Usuario;

public class EditarContaReceberAction implements ActionProcess {

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
			
			
			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());
			ContaReceber contaReceber = new ContaReceber();
			
			String id_conta = request.getParameter("conta_hidden");
			
			contaReceber = (ContaReceber) contaReceberDAO.carregaEntidade(contaReceber, Integer.parseInt(id_conta) );
			
			if(contaReceber.getCor_ds_fornecedor().equalsIgnoreCase("null")) contaReceber.setCor_ds_fornecedor("");
			if(contaReceber.getCor_nr_documento().equalsIgnoreCase("null")) contaReceber.setCor_nr_documento("");
			if(contaReceber.getCor_ds_tipo_conta().equalsIgnoreCase("null")) contaReceber.setCor_ds_tipo_conta("");
			if(contaReceber.getCor_ds_conta_receber().equalsIgnoreCase("null")) contaReceber.setCor_ds_conta_receber("");				
			if(contaReceber.getCor_fl_recebido().equalsIgnoreCase("null")) contaReceber.setCor_fl_recebido("");
			
			if(contaReceber.getCor_dt_vencimento().equalsIgnoreCase("null")){ 
				contaReceber.setCor_dt_vencimento("");
			}else{
				contaReceber.setCor_dt_vencimento(contaReceberDAO.dataConverteDataBrasilMysqlData(contaReceber.getCor_dt_vencimento(), "-", "/") );
			};
			

			request.getSession().setAttribute("contaReceber", contaReceber);
			
			response.sendRedirect("editarContaReceber.jsp");
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
		
	}

}
