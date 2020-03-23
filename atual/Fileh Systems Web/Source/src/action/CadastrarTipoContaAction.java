package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TipoContaDAO;
import entidades.TipoConta;
import entidades.Usuario;

public class CadastrarTipoContaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			TipoConta tipoConta = new TipoConta();
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());

			tipoConta.setTco_ds_tipo_conta(request.getParameter("TCO_DS_TIPO_CONTA"));
			tipoConta.setTco_cd_usuario(u.getUsu_cd_usuario());

			resp = tipoContaDAO.inserirRegistro(tipoConta);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar tipo conta... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro", "Cadastro efetuado com sucesso!");
				CarregaCadastroTipoContaAction carregaCadastroTipoConta = new CarregaCadastroTipoContaAction();
				carregaCadastroTipoConta.process(request, response);
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar tipo conta... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
