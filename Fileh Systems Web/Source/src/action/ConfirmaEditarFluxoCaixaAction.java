package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmaEditarFluxoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			
			if( (request.getParameter("flu_cd_entidade") != null) || (request.getParameter("flu_ds_forma_pagamento") != null) || (request.getParameter("flu_dt_cadastro") != null) ){
				if(request.getParameter("flu_cd_entidade") != null){
					if (request.getParameter("flu_cd_entidade").equalsIgnoreCase("-1")){
						request.getSession().setAttribute("entidade_fluxo_caixa", null);
	
					}else{
						request.getSession().setAttribute("entidade_fluxo_caixa", request.getParameter("flu_cd_entidade"));
					}
				}
				
				if(request.getParameter("flu_ds_forma_pagamento") != null){
					if (request.getParameter("flu_ds_forma_pagamento").equalsIgnoreCase("-1")){
						request.getSession().setAttribute("forma_pagamento_fluxo_caixa", null);
	
					}else{
						request.getSession().setAttribute("forma_pagamento_fluxo_caixa", request.getParameter("flu_ds_forma_pagamento"));
					}
				}
				
				if(request.getParameter("flu_dt_cadastro") != null){
					
					//marca data escolhida para a tela de listagem de fluxo de caixa
					request.getSession().setAttribute("data_escolhida_fluxo_caixa", request.getParameter("flu_dt_cadastro"));
					
					ListagemFluxoCaixaAction listagemFluxoCaixaAction = new ListagemFluxoCaixaAction();
					listagemFluxoCaixaAction.process(request, response);
				}else{
					request.getSession().setAttribute("data_escolhida_fluxo_caixa", null);
					
					CarregaCadastroFluxoCaixaAction fluxoCadastro = new CarregaCadastroFluxoCaixaAction();
					fluxoCadastro.process(request, response);
				}
				
				
				

	
			}else{
				request.getSession().setAttribute("entidade_fluxo_caixa", null);
				request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor entre em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}
			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao atualizar dados, favor entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
