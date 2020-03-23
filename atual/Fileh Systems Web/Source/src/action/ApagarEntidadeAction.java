package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EntidadeDAO;
import entidades.Entidade;
import entidades.Usuario;

public class ApagarEntidadeAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			String id_entidade = request.getParameter("name_entidade_hidden");
			int id_entidade_integer = Integer.parseInt(id_entidade);


			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			Entidade entidade = new Entidade();


			entidade = (Entidade) entDAO.carregaEntidade(entidade, id_entidade_integer );

			

			if (entDAO.deleteEntidade(entidade) == true){
				CarregaCadastroEntidadeAction cadastroEntidade = new CarregaCadastroEntidadeAction();				
				cadastroEntidade.process(request, response);
				
			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao deletar Entidade, favor verificar se há algum registro de fluxo de caixa com essa entidade antes de deletar, caso persista favor entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
				
			}

			

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao deletar Entidade, favor verificar se há algum registro de fluxo de caixa com essa entidade antes de deletar, caso persista favor entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
