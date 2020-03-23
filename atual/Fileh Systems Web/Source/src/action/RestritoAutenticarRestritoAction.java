package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestritoAutenticarRestritoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub

		String idRestrito = request.getParameter("name_restrito");
		
		if (idRestrito.equalsIgnoreCase("123camila321")){
			request.getSession().setAttribute("restrito", "ok");
			response.sendRedirect("restritoLogado.jsp");
		}else{
			request.getSession().setAttribute("restrito", null);
			response.sendRedirect("restrito.jsp");
		}
		
		
	}

}
