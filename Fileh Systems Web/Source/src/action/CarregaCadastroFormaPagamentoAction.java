package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FormaPagamentoDAO;
import entidades.FormaPagamento;
import entidades.Usuario;

public class CarregaCadastroFormaPagamentoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			/*----busca grupo de entidades cadastradas para filtrar-----*/
			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());


			
			
			//redireciona, se nao vim do menu caixa nao estah com o atribudo na sessao, vai estar null...
			if(request.getSession().getAttribute("formaPagamentoCaixaMenu") == null){
				ArrayList<FormaPagamento> listFormaPagamento = fopDAO.buscaTodasListagemObjetos("PR");

				request.getSession().setAttribute("arrayFormaPagamento", listFormaPagamento);
				
				response.sendRedirect("cadastroFormaPagamento.jsp");
			}else{
				ArrayList<FormaPagamento> listFormaPagamento = fopDAO.buscaTodasListagemObjetos("CX");

				request.getSession().setAttribute("arrayFormaPagamento", listFormaPagamento);
				
				request.getSession().setAttribute("formaPagamentoCaixaMenu",null);
				response.sendRedirect("cadastroFormaPagamentoCaixa.jsp");
			};

			


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Forma de Pagamento...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
