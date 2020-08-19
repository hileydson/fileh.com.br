package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PropostaComercialDAO;
import entidades.PropostaComercial;
import entidades.SubUsuario;
import entidades.Usuario;

public class CadastrarPropostaComercialAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			SubUsuario SubU = (SubUsuario) request.getSession().getAttribute("subusuario");

			
			//cadastra uma nova proposta comercial
			PropostaComercial novaProposta = new PropostaComercial();
			PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			
			novaProposta.setPrc_cd_usuario(u.getUsu_cd_usuario());
			novaProposta.setPrc_nm_atendente(SubU.getSbu_nm_subusuario());
			novaProposta.setPrc_vl_desconto(0.0);
			novaProposta.setPrc_vl_frete(0.0);
			int codigoNovaProposta = propostaDAO.inserirRegistro(novaProposta);
			novaProposta = (PropostaComercial) propostaDAO.carregaEntidade(novaProposta, codigoNovaProposta);
			
			//reajusta a data para mostrar ao usuario
			novaProposta.setPrc_dt_cadastro(propostaDAO.dataConverteDataBrasilMysqlData(novaProposta.getPrc_dt_cadastro(), "-", "/"));
			if (novaProposta.getPrc_dt_prevista() != null) 	novaProposta.setPrc_dt_prevista(propostaDAO.dataConverteDataBrasilMysqlData(novaProposta.getPrc_dt_prevista(), "-", "/"));
			
			
			request.getSession().setAttribute("proposta_comercial_criada","S");
			
			
			
			//carrega dados de uma proposta nova
			request.getSession().setAttribute("proposta_editando",novaProposta);
			request.getSession().setAttribute("proposta_itens",null);
			request.getSession().setAttribute("proposta_cliente",null);
			
			request.getSession().setAttribute("proposta_editando_mostrar_codigo", codigoNovaProposta);
			
			
			//carrega o frame de listagem de clientes e produtos
			propostaDAO.carregaFramesPropostaComercial(request, response);
			
			propostaDAO.carregaDadosPropostaComercial(request, response);
			
			response.sendRedirect("cadastroPropostaComercial.jsp");
			
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar proposta comercial...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
