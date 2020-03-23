package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PropostaComercialDAO;
import entidades.PropostaComercial;
import entidades.Usuario;

public class ConfirmaEditarPropostaComercialAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			PropostaComercial proposta_editando;
			
			
			proposta_editando = (PropostaComercial) request.getSession().getAttribute("proposta_editando");
			
			String name_obs_proposta 	= request.getParameter("PRC_DS_OBS_edt");
			String name_vl_desconto 	= request.getParameter("PRC_VL_DESCONTO_edt");
			String name_vl_frete 		= request.getParameter("PRC_VL_FRETE_edt");
			String name_dt_prevista 	= request.getParameter("PRC_DT_PREVISTA_edt2");
			String name_dt_cadastro 	= request.getParameter("PRC_DT_CADASTRO_2");
			String name_ds_situacao 	= request.getParameter("situacaoProposta_edt");
			String name_ds_formaPagamento 	= request.getParameter("formaPagamento_edt");
			
			name_vl_desconto = name_vl_desconto.replace(".", "").replace(".", "").replace(".", "").replace(".", "").replace(",", ".");
			name_vl_frete = name_vl_frete.replace(".", "").replace(".", "").replace(".", "").replace(".", "").replace(",", ".");
			
			proposta_editando.setPrc_ds_obs(name_obs_proposta);
			proposta_editando.setPrc_vl_desconto(Double.parseDouble(name_vl_desconto));
			proposta_editando.setPrc_vl_frete(Double.parseDouble(name_vl_frete));
			proposta_editando.setPrc_ds_situacao(name_ds_situacao);
			proposta_editando.setPrc_ds_forma_pagamento(name_ds_formaPagamento);
			
			if(!name_dt_prevista.equalsIgnoreCase("")){
				name_dt_prevista = propostaDAO.dataConverteDataBrasilMysqlData(name_dt_prevista, "/", "-");
				proposta_editando.setPrc_dt_prevista(name_dt_prevista);
			}
			
			//reajusta data para update
			name_dt_cadastro = propostaDAO.dataConverteDataBrasilMysqlData(name_dt_cadastro, "/", "-");
			proposta_editando.setPrc_dt_cadastro(name_dt_cadastro);
			

			if (propostaDAO.updateEntidade(proposta_editando) == true){
				
				propostaDAO.carregaDadosPropostaComercial(request, response);
				
				request.getSession().setAttribute("proposta_comercial_criada","S");
				
				
				//carrega o frame de listagem de clientes e produtos
				propostaDAO.carregaFramesPropostaComercial(request, response);
				
				response.sendRedirect("cadastroPropostaComercial.jsp");

			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao atualizar dados da proposta, favor verificar se a proposta existe, caso contrário entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao atualizar dados da proposta, favor verificar se a proposta existe, caso contrário entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
