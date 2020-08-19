package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaReceberDAO;
import entidades.Usuario;

public class MarcarContasReceberAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());

			String contas = request.getParameter("contas_selecionadas_hidden");
			String fl = request.getParameter("contas_selecionadas_fl_hidden");



			if (contaReceberDAO.marcarContasFlPago(u.getUsu_cd_usuario(), fl, contas) == true){
				ListagemContasReceberAction listagemContasReceber = new ListagemContasReceberAction();				
				listagemContasReceber.process(request, response);

			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao marcar Conta, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");

			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao marcar Conta, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
