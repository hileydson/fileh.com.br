package action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MetodosComumDAO;
import entidades.Usuario;

public class GeralRestaurarSistemaAction implements ActionProcess {
	Usuario u = null;

	//label para divisao de tabelas
	String divide_arquivo_tabelas = "----------DIVISOR----------";

	private String nome_file_caminho_name = "";
	private int qtd_Registros = 0;
	private int qtd_RegistrosRestaurados = 0;
	private String restaurado = "";

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub

		u = (Usuario) request.getSession().getAttribute("usuario");
		
		//upload o arquivo para o servidor
		GeralUploadDownloadFileAction upload = new GeralUploadDownloadFileAction();
		upload.process(request, response);
		nome_file_caminho_name = upload.upload_file_caminho_name;

		//processamento do arquivo
		if(restaurarArquivo() == true){

			request.getSession().setAttribute("msg_restaurar", "<br>Dados da restauração  <br><br><br>Restaurado : "+restaurado+" <br><br>"+
					"Quantidade Total de registros no arquivo: "+qtd_Registros+"<br><br>"+
					"Quantidade Total de registros restaurados: "+qtd_RegistrosRestaurados+"<br><br>");
		}else{
			request.getSession().setAttribute("msg_restaurar", "<br>Não foi possivel restaurar o arquivo corretamente... <br><br><br>Favor entrar em contato com suporte!"+
					"<br><br>Restaurado : "+restaurado+" <br><br>"+
					"Quantidade Total de registros no arquivo: "+qtd_Registros+"<br><br>"+
					"Quantidade Total de registros restaurados: "+qtd_RegistrosRestaurados+"<br><br>");
		};


		//deleta o arquivo do servidor
		File fileDelete = new File(nome_file_caminho_name);
		fileDelete.delete();

		response.sendRedirect("geral_Restaurar.jsp");

	}



	private boolean restaurarArquivo(){

		ArrayList<String> fileArray = new ArrayList<String>();
		ArrayList<String> fileArray1 = new ArrayList<String>();
		ArrayList<String> fileArray2 = new ArrayList<String>();
		String nome_tabela = "";
		String[] nome_colunas = null;
		String sCurrentLine;
		BufferedReader br = null;
		boolean booleanProcessado = false;

		try {

			//br = new BufferedReader(new FileReader(nome_file_caminho_name));
			
			File fileDir = new File(nome_file_caminho_name);
			//resolve problema de arquivo UTF-8 
			br = new BufferedReader(
			   new InputStreamReader(
	                      new FileInputStream(fileDir), "UTF-8"));

			//primeira linha pega a tabela
			if ((sCurrentLine = br.readLine()) != null) {
				nome_tabela = sCurrentLine;
				restaurado += nome_tabela;

				//pega colunas
				if ((sCurrentLine = br.readLine()) != null) {
					nome_colunas = sCurrentLine.split(";"); 

					//carrega lista de arquivos para restaurar
					while ((sCurrentLine = br.readLine()) != null) {
						qtd_Registros++;
						fileArray.add(sCurrentLine);
					}



					if (nome_tabela.equalsIgnoreCase("PRODUTO")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "PRD_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("FLUXO_CAIXA")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "FLU_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("FORNECEDOR")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "FOR_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("TIPO_CONTA")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "TCO_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("CONTA_PAGAR")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "COP_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("CONTA_RECEBER")){
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "COR_", nome_colunas, fileArray, false );
						booleanProcessado = true;
					};

					if (nome_tabela.equalsIgnoreCase("CLIENTE")){
						boolean proximaTabela = false;
						String[] colunasFileArray2 = null;
						int i = 0;

						while(i < fileArray.size()){

							if(proximaTabela == false){

								if(!fileArray.get(i).equalsIgnoreCase(divide_arquivo_tabelas)){
									//tabela de clientes
									fileArray1.add(fileArray.get(i));
									//System.out.println(fileArray.get(i));
								}else{
									proximaTabela = true;
									i++;
									i++;
									//pega as colunas
									colunasFileArray2 = fileArray.get(i).split(";"); 
									//reajusta contagem pois 3 linhas sao divisor, tabela, colunas
									qtd_Registros += -3;
								}

							}else{

								//tabela de entidades
								fileArray2.add(fileArray.get(i));

							}

							i++;
						}
						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao(nome_tabela, "CLI_", nome_colunas, fileArray1, false );
						executarRestauracao("ENTIDADE", "ENT_", colunasFileArray2, fileArray2 , false);
						booleanProcessado = true;
					};


					if (nome_tabela.equalsIgnoreCase("PROPOSTA_COMERCIAL")){
						boolean proximaTabela1_forma_pagamento = false;
						boolean proximaTabela2_itens = false;
						boolean proximaTabela3_situacao = false;

						String[] colunasFileArray1_forma_pagamento = null;
						String[] colunasFileArray2_itens = null;
						String[] colunasFileArray3_situacao = null;


						ArrayList<String> fileArray_proposta = new ArrayList<String>();
						ArrayList<String> fileArray1_forma_pagamento = new ArrayList<String>();
						ArrayList<String> fileArray2_itens = new ArrayList<String>();
						ArrayList<String> fileArray3_situacao = new ArrayList<String>();

						int i = 0;

						while(i < fileArray.size()){

							if(proximaTabela1_forma_pagamento == false){

								if(!fileArray.get(i).equalsIgnoreCase(divide_arquivo_tabelas)){
									//tabela de proposta
									fileArray_proposta.add(fileArray.get(i));
									//System.out.println(fileArray.get(i));
								}else{
									proximaTabela1_forma_pagamento = true;
									i++;
									i++;
									//pega as colunas da proxima tabela
									colunasFileArray1_forma_pagamento = fileArray.get(i).split(";"); 
									//reajusta contagem pois 3 linhas sao divisor, tabela, colunas
									qtd_Registros += -3;
								}

							}else{

								if(proximaTabela2_itens == false){

									if(!fileArray.get(i).equalsIgnoreCase(divide_arquivo_tabelas)){
										//tabela de forma pagamento
										fileArray1_forma_pagamento.add(fileArray.get(i));
										//System.out.println(fileArray.get(i));
									}else{
										proximaTabela2_itens = true;
										i++;
										i++;
										//pega as colunas da proxima tabela
										colunasFileArray2_itens = fileArray.get(i).split(";"); 
										//reajusta contagem pois 3 linhas sao divisor, tabela, colunas
										qtd_Registros += -3;
									}

								}else{

									if(proximaTabela3_situacao == false){

										if(!fileArray.get(i).equalsIgnoreCase(divide_arquivo_tabelas)){
											//tabela de itens
											fileArray2_itens.add(fileArray.get(i));
											//System.out.println(fileArray.get(i));
										}else{
											proximaTabela3_situacao = true;
											i++;
											i++;
											//pega as colunas da proxima tabela
											colunasFileArray3_situacao = fileArray.get(i).split(";"); 
											//reajusta contagem pois 3 linhas sao divisor, tabela, colunas
											qtd_Registros += -3;
										}

									}else{

										//tabela de situacao
										fileArray3_situacao.add(fileArray.get(i));
										//System.out.println(fileArray.get(i));

									}

								}

							}

							i++;
						}


						//deleta todos os dados da tabela e recarrega ela
						executarRestauracao("ITEM_PROPOSTA", "IPC_", colunasFileArray2_itens, fileArray2_itens, false);
						executarRestauracao(nome_tabela, "PRC_", nome_colunas, fileArray_proposta, true );
						
						executarRestauracao("FORMA_PAGAMENTO", "FOP_", colunasFileArray1_forma_pagamento, fileArray1_forma_pagamento, false);
						executarRestauracao("SITUACAO_PROPOSTA", "SIP_", colunasFileArray3_situacao, fileArray3_situacao, false);
						
						booleanProcessado = true;
					};




				}
			}

			return booleanProcessado;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}




	private void executarRestauracao(String tabela, String prefixo, String[] colunas, ArrayList<String> arrayRegistros, boolean modificar_barra_n){
		MetodosComumDAO metDAO = new MetodosComumDAO(u.getUsu_ds_subdominio());

		//deletar tabela produtos
		metDAO.deleteTable(tabela);

		//inserir registro
		int i = 0;
		while(i<arrayRegistros.size()){
			if( metDAO.inserirRegistroSqlDinamico(tabela, prefixo, colunas, arrayRegistros.get(i).split(";"), modificar_barra_n) == true ){
				qtd_RegistrosRestaurados++;
			};

			i++;
		}

	}



}
