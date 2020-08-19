package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SituacaoPropostaDAO;
import entidades.SituacaoProposta;
import entidades.Usuario;

public class CarregaCadastroSituacaoPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			/*----busca grupo de entidades cadastradas para filtrar-----*/
			SituacaoPropostaDAO situacaoPropostaDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());

			ArrayList<SituacaoProposta> listSituacaoProposta = situacaoPropostaDAO.buscaTodasSitucaoPropostaListagemObjetos();

			request.getSession().setAttribute("arraySituacaoProposta", listSituacaoProposta);

			response.sendRedirect("cadastroSituacaoProposta.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Situações de Propostas...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
