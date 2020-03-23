package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarregaCadastroProdutoAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			response.sendRedirect("cadastroProduto.jsp");

		}catch(Exception e){
			response.sendRedirect("processoErro.jsp");
		}
	}

}
