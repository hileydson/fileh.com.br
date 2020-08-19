package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TipoContaDAO;
import entidades.TipoConta;
import entidades.Usuario;

public class ApagarTipoContaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_tipo_conta = request.getParameter("name_tipo_conta_hidden");
			int id_tipo_conta_integer = Integer.parseInt(id_tipo_conta);


			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());
			TipoConta tipoConta = new TipoConta();


			tipoConta = (TipoConta) tipoContaDAO.carregaEntidade(tipoConta, id_tipo_conta_integer );

			

			if (tipoContaDAO.deleteEntidade(tipoConta) == true){
				CarregaCadastroTipoContaAction cadastroTipoConta = new CarregaCadastroTipoContaAction();				
				cadastroTipoConta.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar Tipo Conta, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar Tipo Conta, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
