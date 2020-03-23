package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FormaPagamentoDAO;
import entidades.FormaPagamento;
import entidades.Usuario;

public class EditarFormaPagamentoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			String id_string = request.getParameter("name_forma_pagamento_hidden");
			int id_integer = Integer.parseInt(id_string);

			FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			FormaPagamento fop = new FormaPagamento();

			fop = (FormaPagamento) fopDAO.carregaEntidade(fop, id_integer );

			if (fop == null){
				response.sendRedirect("processoErro.jsp");
			}else{
				request.getSession().setAttribute("formaPagamento", fop);
				response.sendRedirect("editarFormaPagamentoProposta.jsp");
			}

			
			
		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
