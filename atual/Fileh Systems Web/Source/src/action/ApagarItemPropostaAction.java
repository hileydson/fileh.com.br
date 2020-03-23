package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemPropostaDAO;
import dao.PropostaComercialDAO;
import entidades.ItemProposta;
import entidades.Usuario;

public class ApagarItemPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ItemProposta itemProposta = new ItemProposta();
			ItemPropostaDAO itemPropostaDAO = new ItemPropostaDAO(u.getUsu_ds_subdominio());
			
			int id_item_remover = Integer.parseInt(request.getParameter("produto_rmv"));
			
			itemProposta = (ItemProposta) itemPropostaDAO.carregaEntidade(itemProposta, id_item_remover);

			if (itemPropostaDAO.deleteEntidade(itemProposta) == true){
				PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
				
				propostaDAO.carregaDadosPropostaComercial(request, response);
				
				request.getSession().setAttribute("proposta_comercial_criada","S");
				
				
				//carrega o frame de listagem de clientes e produtos
				propostaDAO.carregaFramesPropostaComercial(request, response);
				
				response.sendRedirect("cadastroPropostaComercial.jsp");

			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar item da proposta, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao deletar item da proposta, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}

	}

}
