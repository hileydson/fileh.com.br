package action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import dao.ClienteDAO;
import dao.ContaPagarDAO;
import dao.ContaReceberDAO;
import dao.EntidadeDAO;
import dao.FluxoCaixaDAO;
import dao.FormaPagamentoDAO;
import dao.FornecedorDAO;
import dao.ItemPropostaDAO;
import dao.ProdutoDAO;
import dao.PropostaComercialDAO;
import dao.SituacaoPropostaDAO;
import dao.TipoContaDAO;
import entidades.Cliente;
import entidades.ContaPagar;
import entidades.ContaReceber;
import entidades.Entidade;
import entidades.FluxoCaixa;
import entidades.FormaPagamento;
import entidades.Fornecedor;
import entidades.ItemProposta;
import entidades.Produto;
import entidades.PropostaComercial;
import entidades.SituacaoProposta;
import entidades.TipoConta;
import entidades.Usuario;

public class GeralBackupSistemaAction implements ActionProcess {
	

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub		
		Usuario u = (Usuario) request.getSession().getAttribute("usuario");

		//OBS: Todos os caracteres ';' devem ser substituidos por ' ', por ser um separador dentro do arquivo a ser gerado.
		
		
		//descobrir qual opcao foi escolhida...
		String nome_backup = request.getParameter("radio_backup");
		int i = 0;
		String fileName = "" ;
		String backups_tables = "" ;
		String qtdRegistros = "";
		
		//label para divisao de tabelas
		String divide_arquivo_tabelas = "----------DIVISOR----------\n";


		//Produtos
		if (nome_backup.equalsIgnoreCase("Produtos")){
			fileName = "produtos.csv";
			ProdutoDAO proDAO = new ProdutoDAO(u.getUsu_ds_subdominio());
			ArrayList<Produto> array = proDAO.consultaProdutosBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Produtos";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("PRODUTO\n");
				w.write("DS_PRODUTO" +";"+ "DS_UNIDADE" +";"+
						"CD_PRODUTO" +";"+ "CD_USUARIO" +";"+ 
						"NR_ESTOQUE" +";"+ "VL_PRECO" +"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						w.write(array.get(i).getPrd_ds_produto().replace(";", " ") +";"+ array.get(i).getPrd_ds_unidade() +";"+
								array.get(i).getPrd_cd_produto() +";"+ array.get(i).getPrd_cd_usuario() +";"+ 
								array.get(i).getPrd_nr_estoque() +";"+ array.get(i).getPrd_vl_preco() +"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}


		//Fluxo de Caixa
		if (nome_backup.equalsIgnoreCase("Fluxo de Caixa")){
			fileName = "caixa.csv";
			FluxoCaixaDAO fluDAO = new FluxoCaixaDAO(u.getUsu_ds_subdominio());
			ArrayList<FluxoCaixa> array = fluDAO.consultaFluxoCaixaBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Fluxo de Caixa";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("FLUXO_CAIXA\n");
				w.write("DS_FLUXO_CAIXA" +";"+ "DS_FORMA_PAGAMENTO" +";"+
						"DT_CADASTRO"+";"+ "FL_TIPO"+";"+ 
						"CD_ENTIDADE"+";"+ "CD_FLUXO_CAIXA"+";"+
						"VL_FLUXO_CAIXA"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						if (array.get(i).getFlu_ds_fluxo_caixa() == null) array.get(i).setFlu_ds_fluxo_caixa("") ;

						w.write(array.get(i).getFlu_ds_fluxo_caixa().replace(";", " ") +";"+ array.get(i).getFlu_ds_forma_pagamento().replace(";", " ") +";"+
								array.get(i).getFlu_dt_cadastro()+";"+ array.get(i).getFlu_fl_tipo()+";"+ 
								array.get(i).getFlu_cd_entidade()+";"+ array.get(i).getFlu_cd_fluxo_caixa()+";"+
								array.get(i).getFlu_vl_fluxo_caixa()+"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}


		//Clientes
		if (nome_backup.equalsIgnoreCase("Clientes")){
			fileName = "clientes.csv";
			ClienteDAO cliDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			ArrayList<Cliente> array = cliDAO.consultaClientesBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Clientes";

			//backup das entidades
			EntidadeDAO entDAO = new EntidadeDAO(u.getUsu_ds_subdominio());
			ArrayList<Entidade> array2 = entDAO.consultaEntidadesBackup();
			qtdRegistros += " + "+array2.size()+"" ;
			backups_tables +=  " e Entidades";


			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				
				w.write("CLIENTE\n");
				w.write("DS_BAIRRO" +";"+ "DS_ENTIDADE"+";"+
						"DS_LOGRADOURO"+";"+ "DS_REFERENCIA"+";"+ 
						"DS_UF"+";"+ "NM_CLIENTE"+";"+
						"NR_CPF"+";"+ "NR_TEL"+";"+
						"CD_CLIENTE"+";"+ "CD_USUARIO"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){

						if (array.get(i).getCli_ds_bairro() == null) array.get(i).setCli_ds_bairro("") ;
						if (array.get(i).getCli_nr_cpf() == null) array.get(i).setCli_nr_cpf("") ;
						if (array.get(i).getCli_ds_logradouro() == null) array.get(i).setCli_ds_logradouro("") ;
						if (array.get(i).getCli_ds_referencia() == null) array.get(i).setCli_ds_referencia("") ;
						if (array.get(i).getCli_nr_tel() == null) array.get(i).setCli_nr_tel("") ;
						
						
						w.write(array.get(i).getCli_ds_bairro().replace(";", " ") +";"+ array.get(i).getCli_ds_entidade().replace(";", " ")+";"+
								array.get(i).getCli_ds_logradouro().replace(";", " ")+";"+ array.get(i).getCli_ds_referencia().replace(";", " ")+";"+ 
								array.get(i).getCli_ds_uf()+";"+ array.get(i).getCli_nm_cliente().replace(";", " ")+";"+
								array.get(i).getCli_nr_cpf().replace(";", " ")+";"+ array.get(i).getCli_nr_tel().replace(";", " ")+";"+
								array.get(i).getCli_cd_cliente()+";"+ array.get(i).getCli_cd_usuario()+"\n");
						i++;
					}
				}

				
				w.write(divide_arquivo_tabelas);
				w.write("ENTIDADE\n");
				w.write("NM_ENTIDADE"+";"+ "CD_ENTIDADE"+";"+
						"CD_USUARIO"+"\n");
				if (array2 != null){
					i = 0;
					while(i < array2.size()){

						w.write(array2.get(i).getEnt_nm_entidade().replace(";", " ") +";"+ array2.get(i).getEnt_cd_entidade()+";"+
								array2.get(i).getEnt_cd_usuario()+"\n");
						i++;
					}
				}


				w.close();
				
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}


		//Propostas Comercias
		if (nome_backup.equalsIgnoreCase("Propostas Comercias")){
			fileName = "propostas.csv";
			String obs_proposta = ""; 
			PropostaComercialDAO propostaAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
			ArrayList<PropostaComercial> array = propostaAO.consultaPropostaClienteBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Propostas Comerciais";
			
			
			//backup forma de pagamento
			FormaPagamentoDAO forDAO = new FormaPagamentoDAO(u.getUsu_ds_subdominio());
			ArrayList<FormaPagamento> array2 = forDAO.consultaFormaPagamentoBackup();
			qtdRegistros += " + "+array2.size()+"" ;
			backups_tables +=  ", Formas de Pagamento/Saída";
			
			
			
			//backup situacao proposta
			ItemPropostaDAO intemDAO = new ItemPropostaDAO(u.getUsu_ds_subdominio());
			ArrayList<ItemProposta> array3 = intemDAO.consultaItensPropostaComercialBackup();
			qtdRegistros += " + "+array3.size()+"" ;
			backups_tables +=  ", Itens Propostas";
			
			
			//backup itens proposta
			SituacaoPropostaDAO situacaoPropostaDAO = new SituacaoPropostaDAO(u.getUsu_ds_subdominio());
			ArrayList<SituacaoProposta> array4 = situacaoPropostaDAO.consultaSitucaoPropostaBackup();
			qtdRegistros += " + "+array4.size()+"" ;
			backups_tables +=  " e Situação Proposta";
			
			
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);

				w.write("PROPOSTA_COMERCIAL\n");
				w.write("DS_FORMA_PAGAMENTO" +";"+ "DS_OBS"+";"+
						"DS_SITUACAO"+";"+ "DT_CADASTRO"+";"+ 
						"DT_PREVISTA"+";"+ "NM_ATENDENTE"+";"+
						"CD_CLIENTE"+";"+ "CD_PROPOSTA_COMERCIAL"+";"+
						"CD_USUARIO"+";"+ "VL_DESCONTO"+";"+
						"VL_FRETE"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						//trata os \n para quando for importado nao afetar..., no restaurar, passar o parametro para modificar novamente
						if (array.get(i).getPrc_ds_obs() == null){
							obs_proposta = "";
						}else{
							obs_proposta = array.get(i).getPrc_ds_obs();
							obs_proposta = obs_proposta.replaceAll("\n", "/n");
							obs_proposta = obs_proposta.replaceAll("\r", "/r");
						}
						
						if (array.get(i).getPrc_ds_forma_pagamento() == null) array.get(i).setPrc_ds_forma_pagamento("") ;
						if (array.get(i).getPrc_ds_situacao() == null) array.get(i).setPrc_ds_situacao("") ;
						if (array.get(i).getPrc_nm_atendente() == null) array.get(i).setPrc_nm_atendente("") ;
						
						
						w.write(array.get(i).getPrc_ds_forma_pagamento().replace(";", " ") +";"+ obs_proposta.replace(";", " ") +";"+
								array.get(i).getPrc_ds_situacao().replace(";", " ")+";"+ array.get(i).getPrc_dt_cadastro()+";"+ 
								array.get(i).getPrc_dt_prevista()+";"+ array.get(i).getPrc_nm_atendente().replace(";", " ")+";"+
								array.get(i).getPrc_cd_cliente()+";"+ array.get(i).getPrc_cd_proposta_comercial()+";"+
								array.get(i).getPrc_cd_usuario()+";"+ array.get(i).getPrc_vl_desconto()+";"+
								array.get(i).getPrc_vl_frete()+"\n");
						i++;
					}
				}
				
				
				w.write(divide_arquivo_tabelas);
				w.write("FORMA_PAGAMENTO\n");
				w.write("DS_FORMA_PAGAMENTO" +";"+ "FL_TIPO"+";"+
						"CD_FORMA_PAGAMENTO"+"\n");
				if (array2 != null){
					i = 0;
					while(i < array2.size()){

						if (array2.get(i).getFop_ds_forma_pagamento() == null) array2.get(i).setFop_ds_forma_pagamento("") ;
						
						w.write(array2.get(i).getFop_ds_forma_pagamento().replace(";", " ") +";"+ array2.get(i).getFop_fl_tipo()+";"+
								array2.get(i).getFop_cd_forma_pagamento()+"\n");
						i++;
					}
				}
				
				
				w.write(divide_arquivo_tabelas);
				w.write("ITEM_PROPOSTA\n");
				w.write("DS_ITEM_PROPOSTA" +";"+ "DS_UNIDADE"+";"+
						"CD_ITEM_PROPOSTA"+";"+ "CD_PROPOSTA_COMERCIAL"+";"+ 
						"NR_QUANTIDADE"+";"+ "VL_DESCONTO"+";"+
						"VL_ITEM_PROPOSTA"+"\n");
				if (array != null){
					i = 0;
					while(i < array3.size()){

						w.write(array3.get(i).getIpc_ds_item_proposta().replace(";", " ") +";"+ array3.get(i).getIpc_ds_unidade()+";"+
								array3.get(i).getIpc_cd_item_proposta()+";"+ array3.get(i).getIpc_cd_proposta_comercial()+";"+ 
								array3.get(i).getIpc_nr_quantidade()+";"+ array3.get(i).getIpc_vl_desconto()+";"+
								array3.get(i).getIpc_vl_item_proposta()+"\n");
						i++;
					}
				}
				
				
				w.write(divide_arquivo_tabelas);
				w.write("SITUACAO_PROPOSTA\n");
				w.write("DS_SITUACAO_PROPOSTA" +";"+ "CD_SITUACAO_PROPOSTA"+"\n");
				if (array4 != null){
					i = 0;
					while(i < array4.size()){

						w.write(array4.get(i).getSip_ds_situacao_proposta().replace(";", " ") +";"+ array4.get(i).getSip_cd_situacao_proposta()+"\n");
						i++;
					}
				}
				
				
				w.close();
				
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}



		//Fornecedores
		if (nome_backup.equalsIgnoreCase("Fornecedores")){
			fileName = "fornecedores.csv";
			FornecedorDAO forDAO = new FornecedorDAO(u.getUsu_ds_subdominio());
			ArrayList<Fornecedor> array = forDAO.consultaFornecedoresBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Fornecedores";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("FORNECEDOR\n");
				w.write("DS_BAIRRO" +";"+ "DS_CIDADE"+";"+
						"DS_FORNECEDOR"+";"+ "DS_LOGRADOURO"+";"+ 
						"NR_CEP"+";"+ "NR_CNPJ"+";"+
						"NR_INSC_ESTADUAL"+";"+ "NR_INSC_MUNICIPAL"+";"+
						"CD_FORNECEDOR"+";"+"DS_CONTATO"+";"+ "CD_USUARIO"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						if (array.get(i).getFor_ds_bairro() == null) array.get(i).setFor_ds_bairro("") ;
						if (array.get(i).getFor_ds_cidade() == null) array.get(i).setFor_ds_cidade("") ;
						if (array.get(i).getFor_ds_fornecedor() == null) array.get(i).setFor_ds_fornecedor("") ;
						if (array.get(i).getFor_ds_logradouro() == null) array.get(i).setFor_ds_logradouro("") ;
						if (array.get(i).getFor_nr_cep() == null) array.get(i).setFor_nr_cep("") ;
						if (array.get(i).getFor_nr_cnpj() == null) array.get(i).setFor_nr_cnpj("") ;
						if (array.get(i).getFor_nr_insc_estadual() == null) array.get(i).setFor_nr_insc_estadual("") ;
						if (array.get(i).getFor_nr_insc_municipal() == null) array.get(i).setFor_nr_insc_municipal("") ;
						if (array.get(i).getFor_nr_cnpj() == null) array.get(i).setFor_nr_cnpj("") ;
						if (array.get(i).getFor_ds_contato() == null) array.get(i).setFor_ds_contato("") ;

						w.write(array.get(i).getFor_ds_bairro().replace(";", " ") +";"+ array.get(i).getFor_ds_cidade().replace(";", " ")+";"+
								array.get(i).getFor_ds_fornecedor().replace(";", " ")+";"+ array.get(i).getFor_ds_logradouro().replace(";", " ")+";"+ 
								array.get(i).getFor_nr_cep().replace(";", " ")+";"+ array.get(i).getFor_nr_cnpj().replace(";", " ")+";"+
								array.get(i).getFor_nr_insc_estadual().replace(";", " ")+";"+ array.get(i).getFor_nr_insc_municipal().replace(";", " ")+";"+
								array.get(i).getFor_cd_fornecedor()+";"+array.get(i).getFor_ds_contato().replace(";", " ")+";"+ array.get(i).getFor_cd_usuario()+"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}
		
		
		
		//Tipo de Contas
		if (nome_backup.equalsIgnoreCase("Tipo de Contas")){
			fileName = "tipo_contas.csv";
			TipoContaDAO tipDAO = new TipoContaDAO(u.getUsu_ds_subdominio());
			ArrayList<TipoConta> array = tipDAO.consultaTipoContaBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Tipo de Contas";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("TIPO_CONTA\n");
				w.write("DS_TIPO_CONTA" +";"+ "CD_TIPO_CONTA"+";"+
						"CD_USUARIO"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){

						w.write(array.get(i).getTco_ds_tipo_conta().replace(";", " ") +";"+ array.get(i).getTco_cd_tipo_conta()+";"+
								array.get(i).getTco_cd_usuario()+"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}


		//Contas a Pagar
		if (nome_backup.equalsIgnoreCase("Contas a Pagar")){
			fileName = "conta_pagar.csv";
			ContaPagarDAO copDAO = new ContaPagarDAO(u.getUsu_ds_subdominio());
			ArrayList<ContaPagar> array = copDAO.consultaContasPagarBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Contas a Pagar";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("CONTA_PAGAR\n");
				w.write("DS_CONTA_PAGAR"+";"+ "DS_FORNECEDOR"+";"+
						"DS_TIPO_CONTA"+";"+ "DT_CADASTRO"+";"+ 
						"DT_VENCIMENTO"+";"+ "FL_PAGO"+";"+
						"FL_PARCELADO"+";"+ "NR_DOCUMENTO"+";"+
						"CD_CONTA_PAGAR"+";"+ "CD_USUARIO"+";"+
						"NR_PARCELA"+";"+ "VL_CONTA_PAGAR"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						if (array.get(i).getCop_nr_documento() == null) array.get(i).setCop_nr_documento("") ;

						w.write(array.get(i).getCop_ds_conta_pagar().replace(";", " ")+";"+ array.get(i).getCop_ds_fornecedor().replace(";", " ")+";"+
								array.get(i).getCop_ds_tipo_conta().replace(";", " ")+";"+ array.get(i).getCop_dt_cadastro()+";"+ 
								array.get(i).getCop_dt_vencimento()+";"+ array.get(i).getCop_fl_pago()+";"+
								array.get(i).getCop_fl_parcelado()+";"+ array.get(i).getCop_nr_documento().replace(";", " ")+";"+
								array.get(i).getCop_cd_conta_pagar()+";"+ array.get(i).getCop_cd_usuario()+";"+
								array.get(i).getCop_nr_parcela()+";"+ array.get(i).getCop_vl_conta_pagar()+"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}

		//Contas a Receber
		if (nome_backup.equalsIgnoreCase("Contas a Receber")){
			fileName = "conta_receber.csv";
			ContaReceberDAO contaReceberDAO = new ContaReceberDAO(u.getUsu_ds_subdominio());
			ArrayList<ContaReceber> array = contaReceberDAO.consultaContasReceberBackup();
			qtdRegistros = array.size()+"" ;
			backups_tables +=  "Contas a Receber";
			try {
				//criar o arquivo
				File statText = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
				FileOutputStream is = new FileOutputStream(statText);
				OutputStreamWriter osw = new OutputStreamWriter(is,"UTF8");    
				Writer w = new BufferedWriter(osw);
				w.write("CONTA_RECEBER\n");
				w.write("DS_CONTA_RECEBER" +";"+ "DS_FORNECEDOR"+";"+
						"DS_TIPO_CONTA"+";"+ "DT_CADASTRO"+";"+ 
						"DT_VENCIMENTO"+";"+ "FL_PARCELADO"+";"+
						"FL_RECEBIDO"+";"+ "NR_DOCUMENTO"+";"+
						"CD_CONTA_RECEBER"+";"+ "CD_USUARIO"+";"+
						"NR_PARCELA"+"\n");
				if (array != null){
					i = 0;
					while(i < array.size()){
						
						if (array.get(i).getCor_nr_documento() == null) array.get(i).setCor_nr_documento("") ;

						w.write(array.get(i).getCor_ds_conta_receber().replace(";", " ") +";"+ array.get(i).getCor_ds_fornecedor().replace(";", " ")+";"+
								array.get(i).getCor_ds_tipo_conta().replace(";", " ")+";"+ array.get(i).getCor_dt_cadastro()+";"+ 
								array.get(i).getCor_dt_vencimento()+";"+ array.get(i).getCor_fl_parcelado()+";"+
								array.get(i).getCor_fl_recebido()+";"+ array.get(i).getCor_nr_documento().replace(";", " ")+";"+
								array.get(i).getCor_cd_conta_receber()+";"+ array.get(i).getCor_cd_usuario()+";"+
								array.get(i).getCor_nr_parcela()+"\n");
						i++;
					}
					w.close();
				}
			} catch (IOException e) {
				System.err.println("Problem writing to the file");
			}
		}

		//-----------------------------------------------------------------------------------------------

		request.getSession().setAttribute("name_file_download", fileName);

		request.getSession().setAttribute("msg_backup", "<br><br>Backup realizado com sucesso! <br><br><br>"+
				"Backup : "+backups_tables+"<br><br>"+
				"Quantidade de registros : "+qtdRegistros);
		response.sendRedirect("geral_Backup.jsp");

	}

}
