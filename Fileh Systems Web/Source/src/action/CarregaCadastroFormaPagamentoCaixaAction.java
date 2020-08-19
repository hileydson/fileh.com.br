package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarregaCadastroFormaPagamentoCaixaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute("formaPagamentoCaixaMenu", "S");
		
		CarregaCadastroFormaPagamentoAction carregaFormaPagamento = new CarregaCadastroFormaPagamentoAction();
		carregaFormaPagamento.process(request, response);

	}

}
