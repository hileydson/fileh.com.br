package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemPropostaDAO;
import dao.PropostaComercialDAO;
import entidades.PropostaComercial;
import entidades.Usuario;

public class ApagarPropostaComercialAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			int id_proposta = Integer.parseInt(request.getParameter("id_name_proposta_hidden"));

			ItemPropostaDAO itemPropostaDAO = new ItemPropostaDAO(u.getUsu_ds_subdominio());
			PropostaComercialDAO propostaComercialDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			PropostaComercial propostaComercial = new PropostaComercial();


			propostaComercial = (PropostaComercial) propostaComercialDAO.carregaEntidade(propostaComercial, id_proposta );

			
			if (itemPropostaDAO.deletarTodosItensProposta(id_proposta) == true){
				
				if (propostaComercialDAO.deleteEntidade(propostaComercial) == true){
					ListagemPropostasClienteAction listagemPropostasCliente = new ListagemPropostasClienteAction();				
					listagemPropostasCliente.process(request, response);
					
				}else{
					request.getSession().setAttribute("msg_erro", 	"Erro ao deletar proposta, favor entrar em contato com o suporte!");
					response.sendRedirect("processoErroMsg.jsp");
					
				}
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar proposta, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar proposta, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}	
	}

}
