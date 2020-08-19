package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaPagarDAO;
import entidades.ContaPagar;
import entidades.Usuario;

public class ApagarContaPagarAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_conta = request.getParameter("conta_hidden");
			int id_conta_integer = Integer.parseInt(id_conta);


			ContaPagarDAO contaPagarDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());
			ContaPagar contaPagar = new ContaPagar();


			contaPagar = (ContaPagar) contaPagarDAO.carregaEntidade(contaPagar, id_conta_integer );
			

			if (contaPagarDAO.deleteEntidade(contaPagar) == true){
				ListagemContasPagarAction listagemContasPagar = new ListagemContasPagarAction();				
				listagemContasPagar.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar Conta, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar Conta, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}	
	}

}
