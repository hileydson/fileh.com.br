package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaReceberDAO;
import entidades.ContaReceber;
import entidades.Usuario;

public class ConfirmarEditarContaReceberAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			int nr_parcela = 0;
			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());
			ContaReceber contaReceber = (ContaReceber) request.getSession().getAttribute("contaReceber"); 
			
			contaReceber.setCor_ds_fornecedor(request.getParameter("COR_DS_FORNECEDOR"));
			contaReceber.setCor_nr_documento(request.getParameter("COR_NR_DOCUMENTO"));
			contaReceber.setCor_ds_tipo_conta(request.getParameter("COR_DS_TIPO_CONTA"));
			contaReceber.setCor_ds_conta_receber(request.getParameter("COR_DS_CONTA_RECEBER"));
			contaReceber.setCor_dt_vencimento( contaReceberDAO.dataConverteDataBrasilMysqlData( request.getParameter("COR_DT_VENCIMENTO"), "/", "-") );
			contaReceber.setCor_vl_conta_receber(	contaReceberDAO.realConverteStringToDouble(request.getParameter("COR_VL_CONTA_RECEBER"))	);
			
			if(request.getParameter("COR_NR_PARCELA") != null) nr_parcela = Integer.parseInt(request.getParameter("COR_NR_PARCELA"));
			contaReceber.setCor_nr_parcela(nr_parcela);	
			
			contaReceber.setCor_fl_recebido(request.getParameter("COR_FL_RECEBIDO"));

			if (contaReceberDAO.updateEntidade(contaReceber)){
				ListagemContasReceberAction listagemContasReceber = new ListagemContasReceberAction();
				listagemContasReceber.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados da conta, favor verificar se a conta existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados da conta, favor verificar se a conta existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
