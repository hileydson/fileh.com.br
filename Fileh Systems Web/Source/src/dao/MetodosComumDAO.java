package dao;

import hibernate.ServicosGeral;

public class MetodosComumDAO {
	public ServicosGeral sg;

	public MetodosComumDAO(String empresa_subdominio){
		sg = new ServicosGeral(empresa_subdominio);
	}

	public Integer inserirRegistro(Object c){
		try{
			return sg.inserirRegistro(c);
		}catch(Exception e){
			return null;
		}
	}	

	public Object carregaEntidade(Object o,Integer id){
		try{
			return sg.carregaEntidade(o, id);
		}catch(Exception e){
			return null;
		}
	}	

	public boolean updateEntidade(Object o){
		try{
			return sg.updateEntidade(o); 
		}catch(Exception e){
			return false;
		}
	}	

	public boolean deleteEntidade(Object o){
		try{
			return sg.deleteEntidade(o); 
		}catch(Exception e){
			return false;
		}
	}	
	
	public boolean executaSqlGeral(String sql){
		try{
			return sg.executaSqlGeral(sql); 
		}catch(Exception e){
			return false;
		}
	}
	
	
	public boolean deleteTable(String tabela){
		try{
			return sg.executaSqlGeral("DELETE FROM "+tabela+" ; "); 
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Inseri registro para qualquer tabela dinamicamente, necessario o prefixo separado do restante do nome da coluna, ex: coluna PRD_CD_PRODUTO -> 'PRD_'(prefixo) + 'CD_PRODUTO'(coluna)
	 * @param tabela
	 * @param prefixo
	 * @param colunas
	 * @param dados_registro
	 * @return
	 */
	public boolean inserirRegistroSqlDinamico(String tabela, String prefixo, String[] colunas, String[] dados_registro, boolean modificar_barra_n_r){
		String sql = " ";
		String colunas_str = "";
		String dados_str = "";
		String quote = "'";
		int i = 0;
		
		i = 0;
		while(i<colunas.length){
			if(i != (colunas.length -1)){
				colunas_str += prefixo+colunas[i] + " , ";
			}else{
				colunas_str += prefixo+colunas[i] + " ";
			}
			i++;
		}
		
		i = 0;
		while(i<dados_registro.length){
			if(i != (dados_registro.length -1)){
				if(dados_registro[i].equalsIgnoreCase("null")){
					dados_str += dados_registro[i] + " , ";
				}else{
					dados_str += quote+dados_registro[i]+quote + " , ";
				}
			}else{
				if(dados_registro[i].equalsIgnoreCase("null")){
					dados_str += dados_registro[i] + " ";
				}else{
					dados_str += quote+dados_registro[i]+quote + " ";
				}
			}
			i++;
		}
		
		if(modificar_barra_n_r == true){
			dados_str = dados_str.replaceAll("/n", "\n");
			dados_str = dados_str.replaceAll("/r", "\r");
		}
		
		
		sql = "INSERT INTO "+tabela+" ( "+colunas_str+" ) VALUES ( "+dados_str+" ) ; ";
		
		
		//System.out.println(sql);
		
		try{
			boolean execution = sg.executaSqlGeral(sql); 
			/*if (execution == false){
				System.out.print(dados_str);
			}*/
			
			return execution;
		}catch(Exception e){
			System.out.println("Execao ao restaurar : "+sql);
			return false;
		}
	}
	
	

	/**
	 *  Converte um valor string para real no banco de dados, ex: 12.050,00 --> 12050.00 
	 *  
	 *  Em caso de exeção é retornado uma exceção.
	 *
	 * @param sValor  valor a ser convertido para real
	 * @return Double
	 */
	public Double realConverteStringToDouble(String sValor){
		sValor = sValor.replace(".", "").replace(".", "").replace(".", "").replace(".", "");
		sValor = sValor.replace(",", ".");
		Double d = Double.parseDouble( sValor );
		return d;
	}
	
	
	/**
	 *  Converte uma data string padrão MySql para Padrão data do Brasil ou vice-versa, Ex: 20/01/2014 --> 2014/01/20
	 *  
	 *  Em caso de exeção é retornado uma exceção.
	 *
	 * @param sData  data a ser convertido para mysql antes de inserção ou update
	 * @param sDivisorData  divisor do formato da data a ser convertido para mysql antes de inserção ou update
	 * @return String
	 */
	public String dataConverteDataBrasilMysqlData(String sData, String sDivisorData, String sNovoDivisorData){
		if(!sData.equalsIgnoreCase("")){
			String [] sDataSplit = sData.split(sDivisorData);
			String sDataAux = sDataSplit[2].split(" ")[0]+sNovoDivisorData+sDataSplit[1].split(" ")[0]+sNovoDivisorData+sDataSplit[0].split(" ")[0];
			return sDataAux;
		}else{
			return "";
		}
		
	}



}
