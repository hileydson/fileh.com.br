package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FormaPagamentoDAO;
import entidades.FormaPagamento;
import entidades.Usuario;

public class CadastrarFormaPagamentoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			Integer resp;
			FormaPagamento formaPagamento = new FormaPagamento();
			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());

			formaPagamento.setFop_ds_forma_pagamento(request.getParameter("FOP_DS_FORMA_PAGAMENTO"));
			formaPagamento.setFop_fl_tipo(request.getParameter("FOP_FL_TIPO"));

			resp = fopDAO.inserirRegistro(formaPagamento);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar Forma de Pagamento... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				//garante que vem da tela de cadastro do caixa...
				if(formaPagamento.getFop_fl_tipo().equalsIgnoreCase("CX")){request.getSession().setAttribute("formaPagamentoCaixaMenu", "S");};
				
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroFormaPagamentoAction carregaCadastroFormaPagamento = new CarregaCadastroFormaPagamentoAction();
				carregaCadastroFormaPagamento.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar Forma de Pagamento... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
