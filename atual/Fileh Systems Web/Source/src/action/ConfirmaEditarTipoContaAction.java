package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TipoContaDAO;
import entidades.TipoConta;
import entidades.Usuario;

public class ConfirmaEditarTipoContaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());
			TipoConta tipoConta = (TipoConta) request.getSession().getAttribute("tipoConta"); 
			
			tipoConta.setTco_ds_tipo_conta(request.getParameter("TCO_DS_TIPO_CONTA"));

			if (tipoContaDAO.updateEntidade(tipoConta) == true){
				CarregaCadastroTipoContaAction cadastroTipoConta = new CarregaCadastroTipoContaAction();
				cadastroTipoConta.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o tipo conta existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se o tipo conta existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
