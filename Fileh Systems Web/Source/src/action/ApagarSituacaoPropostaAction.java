package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SituacaoPropostaDAO;
import entidades.SituacaoProposta;
import entidades.Usuario;

public class ApagarSituacaoPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_situacao_proposta = request.getParameter("name_situacao_proposta_hidden");
			int id_situacao_proposta_integer = Integer.parseInt(id_situacao_proposta);


			SituacaoPropostaDAO situacaoPropostaDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());
			SituacaoProposta situacaoProposta = new SituacaoProposta();


			situacaoProposta = (SituacaoProposta) situacaoPropostaDAO.carregaEntidade(situacaoProposta, id_situacao_proposta_integer );

			

			if (situacaoPropostaDAO.deleteEntidade(situacaoProposta) == true){
				CarregaCadastroSituacaoPropostaAction cadastroSituacaoProposta = new CarregaCadastroSituacaoPropostaAction();				
				cadastroSituacaoProposta.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar a situação, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar situação, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
		
	}

}
