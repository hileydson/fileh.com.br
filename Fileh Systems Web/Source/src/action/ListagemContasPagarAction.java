package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaPagarDAO;
import dao.FornecedorDAO;
import dao.TipoContaDAO;
import entidades.ContaPagar;
import entidades.Usuario;

public class ListagemContasPagarAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{

			Usuario u = (Usuario) request.getSession().getAttribute("usuario");

			ArrayList<ContaPagar> arrayContasPagar;
			ContaPagarDAO contaPagarDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());
			
			String fl_situacao = "T";
			

			String msgFornecedor = " ";
			String msgFornecedorValue = "-1";
			String msgTipoConta = " ";
			String msgTipoContaValue = "-1";

			String filtroDataInicio = "";
			String filtroDataFim = "";
			String fornecedor = "";
			String tipoConta = "";
			String doc = "";
			String fl_situacao_pago = "";
			String fl_situacao_naoPago = "";
			String fl_situacao_todas = "";
			int codigo_conta = 0;
			

			
			//busca filtros como parametros do form, vai encontrar somente se vinher do formulario de listagem de contas
			if (request.getParameter("filtroDataInicio") != null) request.getSession().setAttribute("filtroDataInicio",request.getParameter("filtroDataInicio")); 
			if (request.getParameter("filtroDataFim") != null) request.getSession().setAttribute("filtroDataFim",request.getParameter("filtroDataFim") );
			if (request.getParameter("fornecedor") != null) request.getSession().setAttribute("fornecedor_listagem", request.getParameter("fornecedor"));
			if (request.getParameter("tipoConta") != null) request.getSession().setAttribute("tipoConta_listagem", request.getParameter("tipoConta"));
			if (request.getParameter("doc") != null) request.getSession().setAttribute("doc", request.getParameter("doc"));
			if (request.getParameter("fl_situacao") != null) request.getSession().setAttribute("fl_situacao", request.getParameter("fl_situacao"));
			if (request.getParameter("codigo_conta") != null) request.getSession().setAttribute("codigo_conta", request.getParameter("codigo_conta"));

			
			
			//busca filtros já inseridos como atributos da sessão, já foi filtrado algo antes...
			if (request.getSession().getAttribute("filtroDataInicio") == null) request.getSession().setAttribute("filtroDataInicio","");
			if (request.getSession().getAttribute("filtroDataFim") == null) request.getSession().setAttribute("filtroDataFim","");
			if (request.getSession().getAttribute("fornecedor_listagem") == null) request.getSession().setAttribute("fornecedor_listagem","");
			if (request.getSession().getAttribute("tipoConta_listagem") == null) request.getSession().setAttribute("tipoConta_listagem","");
			if (request.getSession().getAttribute("doc") == null) request.getSession().setAttribute("doc","");
			if (request.getSession().getAttribute("fl_situacao") == null) request.getSession().setAttribute("fl_situacao","T");
			if (request.getSession().getAttribute("codigo_conta") == null) request.getSession().setAttribute("codigo_conta","0");

			filtroDataInicio = request.getSession().getAttribute("filtroDataInicio").toString();
			filtroDataFim = request.getSession().getAttribute("filtroDataFim").toString();
			fornecedor = request.getSession().getAttribute("fornecedor_listagem").toString();
			tipoConta = request.getSession().getAttribute("tipoConta_listagem").toString();
			doc = request.getSession().getAttribute("doc").toString();
			
			
			if (Integer.parseInt(request.getSession().getAttribute("codigo_conta").toString()) <= 0){
				codigo_conta = 0;
			}else{
				codigo_conta = Integer.parseInt(request.getSession().getAttribute("codigo_conta").toString());
			}
			

			if(fornecedor.equalsIgnoreCase("") || fornecedor.equalsIgnoreCase("-1")){
				msgFornecedor = "Escolha um fornecedor...";
				msgFornecedorValue = "-1";
			}else{
				msgFornecedor = fornecedor;
				msgFornecedorValue = fornecedor;
			};

			if(tipoConta.equalsIgnoreCase("") || tipoConta.equalsIgnoreCase("-1")){
				msgTipoConta = "Escolha o tipo da conta...";
				msgTipoContaValue = "-1";
			}else{
				msgTipoConta = tipoConta;
				msgTipoContaValue = tipoConta;
			};
			
			
			fl_situacao = request.getSession().getAttribute("fl_situacao").toString();
			if (fl_situacao.equalsIgnoreCase("T")){
				fl_situacao_todas = "checked=\"checked\"";
				fl_situacao_pago = "";
				fl_situacao_naoPago = "";
			};

			if (fl_situacao.equalsIgnoreCase("S")){
				fl_situacao_pago = "checked=\"checked\"";
				fl_situacao_todas = "";
				fl_situacao_naoPago = "";	
			}

			if (fl_situacao.equalsIgnoreCase("N")){
				fl_situacao_naoPago = "checked=\"checked\"";
				fl_situacao_pago = "";
				fl_situacao_todas = "";	
			}	


			request.getSession().setAttribute("fl_situacao_todas", fl_situacao_todas);
			request.getSession().setAttribute("fl_situacao_pago", fl_situacao_pago);
			request.getSession().setAttribute("fl_situacao_naoPago", fl_situacao_naoPago);
			request.getSession().setAttribute("filtroDataInicio", filtroDataInicio);
			request.getSession().setAttribute("filtroDataFim", filtroDataFim);
			request.getSession().setAttribute("doc", doc);
			request.getSession().setAttribute("codigo_conta", codigo_conta);
			request.getSession().setAttribute("msgFornecedor", msgFornecedor);
			request.getSession().setAttribute("msgTipoConta", msgTipoConta);
			request.getSession().setAttribute("msgFornecedorValue", msgFornecedorValue);
			request.getSession().setAttribute("msgTipoContaValue", msgTipoContaValue);



			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			TipoContaDAO tipoContaDAO = new TipoContaDAO(u.getUsu_ds_subdominio());

			ArrayList <String> arrayFornecedor = forDAO.buscaFornecedoresListagemNomesContasPagar(u.getUsu_cd_usuario());
			ArrayList <String> arrayTipoConta = tipoContaDAO.buscaTipoContasListagemNomesContasPagar(u.getUsu_cd_usuario());

			request.getSession().setAttribute("arrayFornecedor", arrayFornecedor);
			request.getSession().setAttribute("arrayTipoConta", arrayTipoConta);
			
			
			arrayContasPagar = contaPagarDAO.consultaContasPagar(u.getUsu_cd_usuario(), fl_situacao, filtroDataInicio, filtroDataFim, doc, codigo_conta, msgFornecedorValue, msgTipoContaValue);
			
			request.getSession().setAttribute("arrayContasPagar", arrayContasPagar);
			
			response.sendRedirect("listagemContasPagar.jsp");



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao listar contas a pagar...entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
