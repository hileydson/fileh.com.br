package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PropostaComercialDAO;
import entidades.Usuario;

public class CarregaCadastroPropostaComercialAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{			
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			
			propostaDAO.carregaDadosPropostaComercial(request, response);
			
			request.getSession().setAttribute("proposta_comercial_criada","N");
			
			
			//carrega o frame de listagem de clientes e produtos
			propostaDAO.carregaFramesPropostaComercial(request, response);
			
			response.sendRedirect("cadastroPropostaComercial.jsp");
						
			
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao carregar cadastro de proposta comercial...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
