package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FluxoCaixaDAO;
import entidades.FluxoCaixa;
import entidades.Usuario;

public class ApagarFluxoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_string = request.getParameter("name_fluxo_caixa_hidden");
			int id_integer = Integer.parseInt(id_string);


			FluxoCaixaDAO fluDAO = new FluxoCaixaDAO(u.getUsu_ds_subdominio());
			FluxoCaixa fluxoCaixa = new FluxoCaixa();


			fluxoCaixa = (FluxoCaixa) fluDAO.carregaEntidade(fluxoCaixa, id_integer );

			

			if (fluDAO.deleteEntidade(fluxoCaixa) == true){
				CarregaCadastroFluxoCaixaAction cadastroFluxoCaixa = new CarregaCadastroFluxoCaixaAction();				
				cadastroFluxoCaixa.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar a Fluxo de Caixa, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar Fluxo de Caixa, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
