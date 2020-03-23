package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PropostaComercialDAO;
import entidades.PropostaComercial;
import entidades.Usuario;

public class ConfirmarInclusaoClientePropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			PropostaComercial proposta_editando;
			
			String id_cliente = request.getParameter("cliente_hidden");
			
			if (id_cliente == null){ 
				id_cliente = "";
				request.getSession().setAttribute("id_cliente_escolhido", id_cliente);
				
				request.getSession().setAttribute("msg_erro", "Erro ao incluir cliente na proposta comercial...entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
				return;
			};

			request.getSession().setAttribute("id_cliente_escolhido", id_cliente);

			
			//recupera e atribui cliente a proposta
			proposta_editando = (PropostaComercial) request.getSession().getAttribute("proposta_editando");
			proposta_editando.setPrc_cd_cliente(Integer.parseInt(id_cliente));
			
			//reajusta data para update
			proposta_editando.setPrc_dt_cadastro(propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_cadastro(), "/", "-"));
			if(proposta_editando.getPrc_dt_prevista() != null) proposta_editando.setPrc_dt_prevista( propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_prevista(), "/", "-") );
			
			
			
			if (propostaDAO.updateEntidade(proposta_editando) != true){
				request.getSession().setAttribute("msg_erro", "Erro ao incluir cliente na proposta comercial...entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");	
				return;
			};
			
			
			//reajusta data para mostrar
			proposta_editando.setPrc_dt_cadastro(propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_cadastro(), "-", "/"));
			
			//para que não tenha nenhuma msg ao carregar a pagina
			request.getSession().setAttribute("proposta_comercial_criada","S");
			

			//carrega proposta novamente
			propostaDAO.carregaDadosPropostaComercial(request, response);			
			
			//carrega o frame de listagem de clientes e produtos
			propostaDAO.carregaFramesPropostaComercial(request, response);
			
			response.sendRedirect("refreshParentFromFrame.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao incluir cliente na proposta comercial...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}

	}

}
