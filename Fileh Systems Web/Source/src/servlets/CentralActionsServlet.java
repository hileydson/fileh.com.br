package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.*;

/**
 * Servlet implementation class CentralActionsServlet
 */
public class CentralActionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CentralActionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//busca qual action sera executada...
		String path = request.getServletPath();
		path = path.substring(1,path.indexOf("."));
		
		
		//redireciona a classe respectiva da action...
		//------------------------------------------------------------------------------
		
		//Clientes
		if (path.equalsIgnoreCase("ApagarCliente"))			
			new ApagarClienteAction().process(request, response);
		
		if (path.equalsIgnoreCase("ListagemClientes"))			
			new ListagemClientesAction().process(request, response);

		if (path.equalsIgnoreCase("CadastrarCliente"))			
			new CadastrarClienteAction().process(request, response);
		
		if (path.equalsIgnoreCase("CarregaCadastroCliente"))			
			new CarregaCadastroClienteAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ConfirmaEditarCliente"))			
			new ConfirmaEditarClienteAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarCliente"))			
			new EditarClienteAction().process(request, response);		
		
		//------------------------------------------------------------------------------
		
		//Contas a pagar
		if (path.equalsIgnoreCase("MarcarContasPagar"))			
			new MarcarContasPagarAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ApagarContaPagar"))			
			new ApagarContaPagarAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarContaPagar"))			
			new CadastrarContaPagarAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroContaPagar"))			
			new CarregaCadastroContaPagarAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmarEditarContaPagar"))			
			new ConfirmarEditarContaPagarAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarContaPagar"))			
			new EditarContaPagarAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ListagemContasPagar"))			
			new ListagemContasPagarAction().process(request, response);	
		
		//------------------------------------------------------------------------------
		
		//Contas a receber
		if (path.equalsIgnoreCase("MarcarContasReceber"))			
			new MarcarContasReceberAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ApagarContaReceber"))			
			new ApagarContaReceberAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarContaReceber"))			
			new CadastrarContaReceberAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroContaReceber"))			
			new CarregaCadastroContaReceberAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmarEditarContaReceber"))			
			new ConfirmarEditarContaReceberAction().process(request, response);	
		
		if (path.equalsIgnoreCase("EditarContaReceber"))			
			new EditarContaReceberAction().process(request, response);
		
		if (path.equalsIgnoreCase("ListagemContasReceber"))			
			new ListagemContasReceberAction().process(request, response);	
		
		//------------------------------------------------------------------------------
		
		//Entidade
		if (path.equalsIgnoreCase("ApagarEntidade"))			
			new ApagarEntidadeAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarEntidade"))			
			new CadastrarEntidadeAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroEntidade"))			
			new CarregaCadastroEntidadeAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarEntidade"))			
			new ConfirmaEditarEntidadeAction().process(request, response);	

		if (path.equalsIgnoreCase("EditarEntidade"))			
			new EditarEntidadeAction().process(request, response);		
		
		//------------------------------------------------------------------------------
		
		//Fornecedor
		if (path.equalsIgnoreCase("ApagarFornecedor"))			
			new ApagarFornecedorAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarFornecedor"))			
			new CadastrarFornecedorAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroFornecedor"))			
			new CarregaCadastroFornecedorAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarFornecedor"))			
			new ConfirmaEditarFornecedorAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarFornecedor"))			
			new EditarFornecedorAction().process(request, response);		
		
		//------------------------------------------------------------------------------
		
		//Proposta
		if (path.equalsIgnoreCase("ApagarItemProposta"))			
			new ApagarItemPropostaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ApagarPropostaComercial"))			
			new ApagarPropostaComercialAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ApagarSituacaoProposta"))			
			new ApagarSituacaoPropostaAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ApagarFormaPagamento"))			
			new ApagarFormaPagamentoAction().process(request, response);	
		
		if (path.equalsIgnoreCase("CadastrarSituacaoProposta"))			
			new CadastrarSituacaoPropostaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarFormaPagamento"))			
			new CadastrarFormaPagamentoAction().process(request, response);	
		
		if (path.equalsIgnoreCase("CadastrarPropostaComercial"))			
			new CadastrarPropostaComercialAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroSituacaoProposta"))			
			new CarregaCadastroSituacaoPropostaAction().process(request, response);
		
		if (path.equalsIgnoreCase("CarregaCadastroFormaPagamento"))			
			new CarregaCadastroFormaPagamentoAction().process(request, response);	
		
		if (path.equalsIgnoreCase("CarregaCadastroPropostaComercial"))			
			new CarregaCadastroPropostaComercialAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroPropostaComercialLimpa"))			
			new CarregaCadastroPropostaComercialLimpaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarItemProposta"))			
			new ConfirmaEditarItemPropostaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarPropostaComercial"))			
			new ConfirmaEditarPropostaComercialAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarSituacaoProposta"))			
			new ConfirmaEditarSituacaoPropostaAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ConfirmaEditarFormaPagamento"))			
			new ConfirmaEditarFormaPagamentoAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ConfirmarInclusaoClienteProposta"))			
			new ConfirmarInclusaoClientePropostaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmarInclusaoProdutoProposta"))			
			new ConfirmarInclusaoProdutoPropostaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarPropostaComercial"))			
			new EditarPropostaComercialAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarSituacaoProposta"))			
			new EditarSituacaoPropostaAction().process(request, response);
		
		if (path.equalsIgnoreCase("EditarFormaPagamento"))			
			new EditarFormaPagamentoAction().process(request, response);	
		
		if (path.equalsIgnoreCase("ListagemPropostasCliente"))			
			new ListagemPropostasClienteAction().process(request, response);
		
		//------------------------------------------------------------------------------
		
		//Produto
		if (path.equalsIgnoreCase("ApagarProduto"))			
			new ApagarProdutoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarProduto"))			
			new CadastrarProdutoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroProduto"))			
			new CarregaCadastroProdutoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmarEditarProduto"))			
			new ConfirmarEditarProdutoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarProduto"))			
			new EditarProdutoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ListagemProdutosPreco"))			
			new ListagemProdutosPrecoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ListagemProdutos"))			
			new ListagemProdutosAction().process(request, response);	
		
		//------------------------------------------------------------------------------
		
		//Usuario
		if (path.equalsIgnoreCase("ApagarSubUsuarioADM"))			
			new ApagarSubUsuarioADMAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarSubUsuarioADM"))			
			new CadastrarSubUsuarioADMAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroSubUsuario"))			
			new CarregaCadastroSubUsuarioAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarSubUsuarioADM"))			
			new ConfirmaEditarSubUsuarioADMAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarSubUsuario"))			
			new ConfirmaEditarSubUsuarioAction().process(request, response);		
		
		if (path.equalsIgnoreCase("LoginUsuario"))			
			new LoginUsuarioAction().process(request, response);		
		
		if (path.equalsIgnoreCase("Logoff"))			
			new LogoffAction().process(request, response);	
		
		if (path.equalsIgnoreCase("CarregaDadosUsuario"))			
			new CarregaDadosUsuarioAction().process(request, response);
		//------------------------------------------------------------------------------
		
		//Tipo conta
		if (path.equalsIgnoreCase("ApagarTipoConta"))			
			new ApagarTipoContaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CadastrarTipoConta"))			
			new CadastrarTipoContaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("CarregaCadastroTipoConta"))			
			new CarregaCadastroTipoContaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarTipoConta"))			
			new ConfirmaEditarTipoContaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("EditarTipoConta"))			
			new EditarTipoContaAction().process(request, response);		
				
		
		//------------------------------------------------------------------------------
	
		
		//Fluxo de Caixa
		if (path.equalsIgnoreCase("CarregaCadastroFluxoCaixa"))			
			new CarregaCadastroFluxoCaixaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("ConfirmaEditarFluxoCaixa"))			
			new ConfirmaEditarFluxoCaixaAction().process(request, response);	
		
		if (path.equalsIgnoreCase("CadastrarFluxoCaixa"))			
			new CadastrarFluxoCaixaAction().process(request, response);
		
		if (path.equalsIgnoreCase("ApagarFluxoCaixa"))			
			new ApagarFluxoCaixaAction().process(request, response);
		
		if (path.equalsIgnoreCase("ListagemFluxoCaixa"))			
			new ListagemFluxoCaixaAction().process(request, response);
	
		if (path.equalsIgnoreCase("ListagemFluxoCaixaPeriodo"))			
			new ListagemFluxoCaixaPeriodoAction().process(request, response);
		
		if (path.equalsIgnoreCase("CarregaCadastroFormaPagamentoCaixa"))			
			new CarregaCadastroFormaPagamentoCaixaAction().process(request, response);
		//------------------------------------------------------------------------------
		
		
		//Backup e restauracao do sistema
		if (path.equalsIgnoreCase("GeralBackupSistema"))			
			new GeralBackupSistemaAction().process(request, response);		
		
		if (path.equalsIgnoreCase("GeralRestaurarSistema"))			
			new GeralRestaurarSistemaAction().process(request, response);	
		
		if (path.equalsIgnoreCase("GeralDownloadCompleteBackup"))			
			new GeralDownloadCompleteBackupAction().process(request, response);	
		//------------------------------------------------------------------------------
		
		
		//Paginas RESTRITAS
		if (path.equalsIgnoreCase("RestritoAutenticarRestrito"))			
			new RestritoAutenticarRestritoAction().process(request, response);		
		
		if (path.equalsIgnoreCase("RestritoConfirmaEditarUsuarioADM"))			
			new RestritoConfirmaEditarUsuarioADMAction().process(request, response);	
		
		if (path.equalsIgnoreCase("RestritoApagarUsuarioADM"))			
			new RestritoApagarUsuarioADMAction().process(request, response);
		
		if (path.equalsIgnoreCase("RestritoCadastrarUsuarioADM"))			
			new RestritoCadastrarUsuarioADMAction().process(request, response);	
		
		if (path.equalsIgnoreCase("RestritoCarregarCadastroUsuario"))			
			new RestritoCarregarCadastroUsuarioAction().process(request, response);
			
		if (path.equalsIgnoreCase("RestritoLogoffRestrito"))			
			new RestritoLogoffRestritoAction().process(request, response);	
		//------------------------------------------------------------------------------
		
		
	}

}
