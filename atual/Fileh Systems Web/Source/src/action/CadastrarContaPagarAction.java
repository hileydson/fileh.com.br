package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaPagarDAO;
import entidades.ContaPagar;
import entidades.Usuario;

public class CadastrarContaPagarAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ContaPagar contaPagar = new ContaPagar();
			ContaPagarDAO contaPagarDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());


			contaPagar.setCop_ds_fornecedor(request.getParameter("COP_DS_FORNECEDOR"));
			contaPagar.setCop_ds_tipo_conta(request.getParameter("COP_DS_TIPO_CONTA"));
			contaPagar.setCop_nr_documento(request.getParameter("COP_NR_DOCUMENTO"));
			contaPagar.setCop_ds_conta_pagar(request.getParameter("COP_DS_CONTA_PAGAR"));
			contaPagar.setCop_dt_vencimento(contaPagarDAO.dataConverteDataBrasilMysqlData(request.getParameter("COP_DT_VENCIMENTO"), "/", "-"));
			contaPagar.setCop_vl_conta_pagar(contaPagarDAO.realConverteStringToDouble(request.getParameter("COP_VL_CONTA_PAGAR")));	
			contaPagar.setCop_fl_parcelado(request.getParameter("COP_FL_PARCELADO"));
			contaPagar.setCop_fl_pago("N");
			
			String nr_parcela = request.getParameter("COP_NR_PARCELA");
			if (nr_parcela == null) nr_parcela = "0";
			contaPagar.setCop_nr_parcela( Integer.parseInt(nr_parcela) );
			
			contaPagar.setCop_cd_usuario(u.getUsu_cd_usuario());
			
			
			
			
			
			//Antes de inserir, faz busca para ver duplicidade de contas, se houver retorna uma msg de duplicidade e nao permite cadastrar...
			if(contaPagarDAO.consultaDuplicidadeConta(request.getParameter("COP_DS_FORNECEDOR"), request.getParameter("COP_DS_TIPO_CONTA"), request.getParameter("COP_NR_DOCUMENTO"))){
				
				request.getSession().setAttribute("msg_cadastro",	"ATENÇÃO :<br><br>"+
						"NÃO FOI POSSÍVEL CADASTRAR A CONTA! <br><br>"+
						"Motivo : Conta já foi cadastrada! <br>"+
						"Dados : <br>"+
						"Fornecedor: "+contaPagar.getCop_ds_fornecedor()+"<br>"+
						"Tipo Conta: "+contaPagar.getCop_ds_tipo_conta()+"<br>"+
						"Documento: "+contaPagar.getCop_nr_documento()+"<br>"+

						"<br><br>CONTA NÃO CADASTRADA!!");
				
				CarregaCadastroContaPagarAction cadastroContasPagar = new CarregaCadastroContaPagarAction();
				cadastroContasPagar.process(request, response);
				
			}else{
				
				resp = contaPagarDAO.inserirRegistro(contaPagar);

				if (resp == null){
					request.getSession().setAttribute("msg_erro", "Erro ao cadastrar conta a pagar... entre em contato com o suporte");
					response.sendRedirect("processoErroMsg.jsp");
				}else{
					request.getSession().setAttribute("msg_cadastro",	"Dados :<br><br>"+
							"Fornecedor: "+contaPagar.getCop_ds_fornecedor()+"<br>"+
							"Tipo Conta: "+contaPagar.getCop_ds_tipo_conta()+"<br>"+
							"Documento: "+contaPagar.getCop_nr_documento()+"<br>"+
							"Descrição: "+contaPagar.getCop_ds_conta_pagar()+"<br>"+
							"Vencimento: "+request.getParameter("COP_DT_VENCIMENTO")+"<br>"+
							"Valor: "+contaPagar.getCop_vl_conta_pagar().toString().replace(",","").replace(".", ",")+"<br>"+
							"Parcela: "+contaPagar.getCop_nr_parcela()+"<br><br>"+
							"<br><br>Cadastro efetuado com sucesso!");
					
					CarregaCadastroContaPagarAction cadastroContasPagar = new CarregaCadastroContaPagarAction();
					cadastroContasPagar.process(request, response);
				};	
				
			}
			
			



		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar conta a pagar... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
