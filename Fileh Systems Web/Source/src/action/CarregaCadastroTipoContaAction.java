package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TipoContaDAO;
import entidades.TipoConta;
import entidades.Usuario;

public class CarregaCadastroTipoContaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			/*----busca grupo de entidades cadastradas para filtrar-----*/
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());

			ArrayList<TipoConta> listTipoConta = tipoContaDAO.buscaTodosTipoConta(u.getUsu_cd_usuario().toString());

			request.getSession().setAttribute("arrayTipoConta", listTipoConta);

			response.sendRedirect("cadastroTipoConta.jsp");


		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar Tipo Contas...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
