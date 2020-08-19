package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SituacaoPropostaDAO;
import entidades.SituacaoProposta;
import entidades.Usuario;

public class CadastrarSituacaoPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			Integer resp;
			SituacaoProposta situacaoProposta = new SituacaoProposta();
			SituacaoPropostaDAO situacaoPropostaDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());

			situacaoProposta.setSip_ds_situacao_proposta(request.getParameter("SIP_DS_SITUACAO_PROPOSTA"));

			resp = situacaoPropostaDAO.inserirRegistro(situacaoProposta);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar situação... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroSituacaoPropostaAction carregaCadastroSituacaoProposta = new CarregaCadastroSituacaoPropostaAction();
				carregaCadastroSituacaoProposta.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar situação... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
