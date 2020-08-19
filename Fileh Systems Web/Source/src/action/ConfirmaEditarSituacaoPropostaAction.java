package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SituacaoPropostaDAO;
import entidades.SituacaoProposta;
import entidades.Usuario;

public class ConfirmaEditarSituacaoPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			SituacaoPropostaDAO situacaoPropostaDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());
			SituacaoProposta situacaoProposta = (SituacaoProposta) request.getSession().getAttribute("situacaoProposta"); 
			
			situacaoProposta.setSip_ds_situacao_proposta(request.getParameter("SIP_DS_SITUACAO_PROPOSTA"));

			if (situacaoPropostaDAO.updateEntidade(situacaoProposta) == true){
				CarregaCadastroSituacaoPropostaAction cadastroSituacaoProposta = new CarregaCadastroSituacaoPropostaAction();
				cadastroSituacaoProposta.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se a Situação existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se a Situação existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
