package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FluxoCaixaDAO;
import dao.MetodosComumDAO;
import entidades.FluxoCaixa;
import entidades.Usuario;

public class CadastrarFluxoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			Integer resp;
			FluxoCaixa fluxoCaixa = new FluxoCaixa();
			FluxoCaixaDAO fluDAO = new FluxoCaixaDAO(u.getUsu_ds_subdominio());
			MetodosComumDAO sg = new MetodosComumDAO(u.getUsu_ds_subdominio());

			fluxoCaixa.setFlu_ds_fluxo_caixa(request.getParameter("flu_ds_fluxo_caixa"));
			fluxoCaixa.setFlu_cd_entidade( Integer.parseInt(request.getSession().getAttribute("entidade_fluxo_caixa").toString()));
			fluxoCaixa.setFlu_dt_cadastro(sg.dataConverteDataBrasilMysqlData(request.getParameter("flu_dt_cadastro"), "/", "-") );
			fluxoCaixa.setFlu_vl_fluxo_caixa( sg.realConverteStringToDouble(request.getParameter("flu_vl_fluxo_caixa")));
			fluxoCaixa.setFlu_ds_forma_pagamento(request.getParameter("flu_ds_forma_pagamento_hidden"));
			
			if(fluxoCaixa.getFlu_vl_fluxo_caixa() > 0){
				fluxoCaixa.setFlu_fl_tipo("EN");
			}else{
				fluxoCaixa.setFlu_fl_tipo("SA");
			}

			resp = fluDAO.inserirRegistro(fluxoCaixa);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar Fluxo de Caixa... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				CarregaCadastroFluxoCaixaAction carregaCadastroFluxoCaixa = new CarregaCadastroFluxoCaixaAction();
				carregaCadastroFluxoCaixa.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar Fluxo de Caixa... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
