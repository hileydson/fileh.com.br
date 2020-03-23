package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FormaPagamentoDAO;
import entidades.FormaPagamento;
import entidades.Usuario;

public class ConfirmaEditarFormaPagamentoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			FormaPagamento formaPagamento = (FormaPagamento) request.getSession().getAttribute("formaPagamento"); 
			
			formaPagamento.setFop_ds_forma_pagamento(request.getParameter("FOP_DS_FORMA_PAGAMENTO"));

			if (fopDAO.updateEntidade(formaPagamento) == true){
				//garante que vem da tela de cadastro do caixa...
				if(formaPagamento.getFop_fl_tipo().equalsIgnoreCase("CX")){request.getSession().setAttribute("formaPagamentoCaixaMenu", "S");};
				
				CarregaCadastroFormaPagamentoAction cadastroFormaPagamento = new CarregaCadastroFormaPagamentoAction();
				cadastroFormaPagamento.process(request, response);
			}else{
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se a Forma de Pagamento ainda existe, caso contrário entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor verificar se a Forma de Pagamento ainda existe, caso contrário entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
