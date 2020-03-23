package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PropostaComercialDAO;
import entidades.PropostaComercial;
import entidades.Usuario;

public class EditarPropostaComercialAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			PropostaComercial proposta_editando = new PropostaComercial();
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio()); 
			
			//carrega proposta
			int id_proposta = Integer.parseInt(	request.getParameter("id_name_proposta_hidden")	);
			proposta_editando = (PropostaComercial) propostaDAO.carregaEntidade(proposta_editando, id_proposta);
			
			
			//reajusta a data para mostrar ao usuario
			proposta_editando.setPrc_dt_cadastro(propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_cadastro(), "-", "/"));
			if (proposta_editando.getPrc_dt_prevista() != null) 	proposta_editando.setPrc_dt_prevista(propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_prevista(), "-", "/"));
			
			
			//carrega dados da proposta escolhida
			request.getSession().setAttribute("proposta_editando",proposta_editando);	
			request.getSession().setAttribute("proposta_editando_mostrar_codigo", proposta_editando.getPrc_cd_proposta_comercial());
			propostaDAO.carregaDadosPropostaComercial(request, response);			
		
			
			//marcar como proposta criada para que seja carregada os dados no cadastro
			request.getSession().setAttribute("proposta_comercial_criada","S");
			
			//carrega o frame de listagem de clientes e produtos
			propostaDAO.carregaFramesPropostaComercial(request, response);
			
			
			response.sendRedirect("cadastroPropostaComercial.jsp");
						
			
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao carregar cadastro de proposta comercial...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
