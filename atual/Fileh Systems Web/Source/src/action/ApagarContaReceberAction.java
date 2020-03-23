package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaReceberDAO;
import entidades.ContaReceber;
import entidades.Usuario;

public class ApagarContaReceberAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_conta = request.getParameter("conta_hidden");
			int id_conta_integer = Integer.parseInt(id_conta);


			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());
			ContaReceber contaReceber = new ContaReceber();


			contaReceber = (ContaReceber) contaReceberDAO.carregaEntidade(contaReceber, id_conta_integer );
			

			if (contaReceberDAO.deleteEntidade(contaReceber) == true){
				ListagemContasReceberAction listagemContasReceber = new ListagemContasReceberAction();				
				listagemContasReceber.process(request, response);
				
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
