package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaPagarDAO;
import entidades.ContaPagar;
import entidades.Usuario;

public class ConfirmarEditarContaPagarAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			int nr_parcela = 0;
			ContaPagarDAO contaPagarDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());
			ContaPagar contaPagar = (ContaPagar) request.getSession().getAttribute("contaPagar"); 
			
			contaPagar.setCop_ds_fornecedor(request.getParameter("COP_DS_FORNECEDOR"));
			contaPagar.setCop_nr_documento(request.getParameter("COP_NR_DOCUMENTO"));
			contaPagar.setCop_ds_tipo_conta(request.getParameter("COP_DS_TIPO_CONTA"));
			contaPagar.setCop_ds_conta_pagar(request.getParameter("COP_DS_CONTA_PAGAR"));
			contaPagar.setCop_dt_vencimento( contaPagarDAO.dataConverteDataBrasilMysqlData( request.getParameter("COP_DT_VENCIMENTO"), "/", "-") );
			contaPagar.setCop_vl_conta_pagar(	contaPagarDAO.realConverteStringToDouble(request.getParameter("COP_VL_CONTA_PAGAR"))	);
			
			if(request.getParameter("COP_NR_PARCELA") != null) nr_parcela = Integer.parseInt(request.getParameter("COP_NR_PARCELA"));
			contaPagar.setCop_nr_parcela(nr_parcela);	
			
			contaPagar.setCop_fl_pago(request.getParameter("COP_FL_PAGO"));

			if (contaPagarDAO.updateEntidade(contaPagar)){
				ListagemContasPagarAction listagemContasPagar = new ListagemContasPagarAction();
				listagemContasPagar.process(request, response);
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
