package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContaReceberDAO;
import entidades.ContaReceber;
import entidades.Usuario;

public class CadastrarContaReceberAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ContaReceber contaReceber = new ContaReceber();
			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());


			contaReceber.setCor_ds_fornecedor(request.getParameter("COR_DS_FORNECEDOR"));
			contaReceber.setCor_ds_tipo_conta(request.getParameter("COR_DS_TIPO_CONTA"));
			contaReceber.setCor_nr_documento(request.getParameter("COR_NR_DOCUMENTO"));
			contaReceber.setCor_ds_conta_receber(request.getParameter("COR_DS_CONTA_RECEBER"));
			contaReceber.setCor_dt_vencimento(contaReceberDAO.dataConverteDataBrasilMysqlData(request.getParameter("COR_DT_VENCIMENTO"), "/", "-"));
			contaReceber.setCor_vl_conta_receber(contaReceberDAO.realConverteStringToDouble(request.getParameter("COR_VL_CONTA_RECEBER")));	
			contaReceber.setCor_fl_parcelado(request.getParameter("COR_FL_PARCELADO"));
			contaReceber.setCor_fl_recebido("N");
			
			String nr_parcela = request.getParameter("COR_NR_PARCELA");
			if (nr_parcela == null) nr_parcela = "0";
			contaReceber.setCor_nr_parcela( Integer.parseInt(nr_parcela) );
			
			contaReceber.setCor_cd_usuario(u.getUsu_cd_usuario());
			
			
			
			
			
			//Antes de inserir, faz busca para ver duplicidade de contas, se houver retorna uma msg de duplicidade e nao permite cadastrar...
			if(contaReceberDAO.consultaDuplicidadeConta(request.getParameter("COR_DS_FORNECEDOR"), request.getParameter("COR_DS_TIPO_CONTA"), request.getParameter("COR_NR_DOCUMENTO"))){
			
				request.getSession().setAttribute("msg_cadastro",	"ATENÇÃO :<br><br>"+
						"NÃO FOI POSSÍVEL CADASTRAR A CONTA! <br><br>"+
						"Motivo : Conta já foi cadastrada! <br>"+
						"Dados : <br>"+
						"Fornecedor: "+contaReceber.getCor_ds_fornecedor()+"<br>"+
						"Tipo Conta: "+contaReceber.getCor_ds_tipo_conta()+"<br>"+
						"Documento: "+contaReceber.getCor_nr_documento()+"<br>"+

						"<br><br>CONTA NÃO CADASTRADA!!");
				
				CarregaCadastroContaPagarAction cadastroContasPagar = new CarregaCadastroContaPagarAction();
				cadastroContasPagar.process(request, response);
				
			}else{
				
				resp = contaReceberDAO.inserirRegistro(contaReceber);

				if (resp == null){
					request.getSession().setAttribute("msg_erro", "Erro ao cadastrar conta a receber... entre em contato com o suporte");
					response.sendRedirect("processoErroMsg.jsp");
				}else{
					request.getSession().setAttribute("msg_cadastro",	"Dados :<br><br>"+
							"Fornecedor: "+contaReceber.getCor_ds_fornecedor()+"<br>"+
							"Tipo Conta: "+contaReceber.getCor_ds_tipo_conta()+"<br>"+
							"Documento: "+contaReceber.getCor_nr_documento()+"<br>"+
							"Descrição: "+contaReceber.getCor_ds_conta_receber()+"<br>"+
							"Vencimento: "+request.getParameter("COR_DT_VENCIMENTO")+"<br>"+
							"Valor: "+contaReceber.getCor_vl_conta_receber().toString().replace(",","").replace(".", ",")+"<br>"+
							"Parcela: "+contaReceber.getCor_nr_parcela()+"<br>"+
							"<br><br>Cadastro efetuado com sucesso!");
					
					CarregaCadastroContaReceberAction cadastroContasReceber = new CarregaCadastroContaReceberAction();
					cadastroContasReceber.process(request, response);
				};

				
			}

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar conta a receber... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
