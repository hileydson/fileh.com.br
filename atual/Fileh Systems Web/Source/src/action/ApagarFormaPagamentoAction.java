package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FormaPagamentoDAO;
import entidades.FormaPagamento;
import entidades.Usuario;

public class ApagarFormaPagamentoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_string = request.getParameter("name_forma_pagamento_hidden");
			int id_integer = Integer.parseInt(id_string);


			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			FormaPagamento formaPagamento = new FormaPagamento();


			formaPagamento = (FormaPagamento) fopDAO.carregaEntidade(formaPagamento, id_integer );

			

			if (fopDAO.deleteEntidade(formaPagamento) == true){
				//garante que vem da tela de cadastro do caixa...
				if(formaPagamento.getFop_fl_tipo().equalsIgnoreCase("CX")){request.getSession().setAttribute("formaPagamentoCaixaMenu", "S");};
				
				CarregaCadastroFormaPagamentoAction cadastroFormaPagamento = new CarregaCadastroFormaPagamentoAction();				
				cadastroFormaPagamento.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar a Forma de Pagamento, favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao deletar Forma de Pagamento, favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
