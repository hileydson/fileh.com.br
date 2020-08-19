package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.FluxoCaixa;
import entidades.FormaPagamento;

public class FluxoCaixaDAO extends MetodosComumDAO {
	
	

	public FluxoCaixaDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Traz os objetos manipulados por periodo.. nao eh o objeto original.. o objeto correto.. eh um objeto totalmente manipulado para mostrar o resultado agrupado algumas colunas
	 * @param entidade
	 * @param forma_pagamento_saida
	 * @param data_inicio
	 * @param data_fim
	 * @return
	 */
	public ArrayList<FluxoCaixa> buscaTodasListagemObjetosPeriodo( String entidade, String forma_pagamento_saida, String data_inicio, String data_fim, String descricao){
		ArrayList<FluxoCaixa> arrayFluxoCaixa = new ArrayList<>();
		FluxoCaixa auxFluxoCaixa; 
		Object[] row;
		String sql;
		String sql_condicao = " ";
		
		
		if(!forma_pagamento_saida.equalsIgnoreCase("")){
			FormaPagamento pag = new FormaPagamento();
			pag = (FormaPagamento) sg.carregaEntidade(pag, Integer.parseInt(forma_pagamento_saida));
			
			sql_condicao += " AND FLU_DS_FORMA_PAGAMENTO = '"+pag.getFop_ds_forma_pagamento()+"' ";
		};
		
		
		
		if(!descricao.equalsIgnoreCase("")){			
			sql_condicao += " AND FLU_DS_FLUXO_CAIXA = '"+descricao+"' ";
		};
		
		


		sql = 	" SELECT FLU_DT_CADASTRO,  "+
				" CASE FLU_FL_TIPO  WHEN 'EN' THEN 'ENTRADA' WHEN 'SA' THEN 'SAIDA' END FLU_FL_TIPO,  "+
				" FLU_DS_FORMA_PAGAMENTO, SUM(FLU_VL_FLUXO_CAIXA) FLU_VL_FLUXO_CAIXA, FLU_DS_FLUXO_CAIXA "+
				" FROM FLUXO_CAIXA "+
				" WHERE FLU_CD_ENTIDADE = "+entidade+
				" AND FLU_DT_CADASTRO BETWEEN STR_TO_DATE('"+data_inicio+"','%d/%m/%Y')  AND STR_TO_DATE('"+data_fim+"','%d/%m/%Y') "+
				sql_condicao+
				" GROUP BY  FLU_DT_CADASTRO, FLU_FL_TIPO, FLU_DS_FORMA_PAGAMENTO  "+
				" order by FLU_DT_CADASTRO ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			if(q != null){
				for(int i = 0; i<q.size();i++){
					row = q.get(i);

					auxFluxoCaixa = new FluxoCaixa();

					//valores agrupados
					auxFluxoCaixa.setFlu_dt_cadastro(row[0].toString());
					auxFluxoCaixa.setFlu_fl_tipo(row[1].toString());
					
					if(row[2] == null){
						auxFluxoCaixa.setFlu_ds_forma_pagamento("");
					}else{
						auxFluxoCaixa.setFlu_ds_forma_pagamento(row[2].toString());
					};
					
					auxFluxoCaixa.setFlu_vl_fluxo_caixa(Double.parseDouble(row[3].toString()));
					
					
					if(row[4] == null){
						auxFluxoCaixa.setFlu_ds_fluxo_caixa("");
					}else{
						auxFluxoCaixa.setFlu_ds_fluxo_caixa(row[4].toString());
					};

					arrayFluxoCaixa.add(auxFluxoCaixa);

				}
			}else{
				return null;
			}

			return arrayFluxoCaixa;
		}catch(Exception e){
			return null;
		}
	};

	public ArrayList<FluxoCaixa> buscaTodasListagemObjetos( String entidade){
		ArrayList<FluxoCaixa> arrayFluxoCaixa = new ArrayList<>();
		FluxoCaixa auxFluxoCaixa; 
		//Object[] listaResult;
		Object[] row;
		String sql;


		sql = 	" SELECT FLU_DS_FLUXO_CAIXA, FLU_CD_FLUXO_CAIXA "+
				" FROM FLUXO_CAIXA "+
				" WHERE FLU_CD_ENTIDADE = "+entidade+
				" AND DATE_FORMAT(FLU_DT_CADASTRO,'%d/%m/%Y') = DATE_FORMAT(NOW(),'%d/%m/%Y') "+
				" order by FLU_CD_FLUXO_CAIXA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			if(q != null){
				for(int i = 0; i<q.size();i++){
					row = q.get(i);

					auxFluxoCaixa = new FluxoCaixa();

					auxFluxoCaixa = (FluxoCaixa) sg.carregaEntidade(auxFluxoCaixa, Integer.parseInt(row[1].toString()));

					arrayFluxoCaixa.add(auxFluxoCaixa);

				}
			}else{
				return null;
			}

			return arrayFluxoCaixa;
		}catch(Exception e){
			return null;
		}
	};
	
	public ArrayList<FluxoCaixa> consultaFluxoCaixaBackup(){
		ArrayList<FluxoCaixa> arrayFluxoCaixa = new ArrayList<>();
		FluxoCaixa auxFluxoCaixa; 
		Object[] row;
		String sql;


		sql = 	" SELECT FLU_DS_FLUXO_CAIXA, FLU_CD_FLUXO_CAIXA "+
				" FROM FLUXO_CAIXA ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			if(q != null){
				for(int i = 0; i<q.size();i++){
					row = q.get(i);

					auxFluxoCaixa = new FluxoCaixa();

					auxFluxoCaixa = (FluxoCaixa) sg.carregaEntidade(auxFluxoCaixa, Integer.parseInt(row[1].toString()));

					arrayFluxoCaixa.add(auxFluxoCaixa);

				}
			}else{
				return null;
			}

			return arrayFluxoCaixa;
		}catch(Exception e){
			return null;
		}
	};
	
	
	
	
	
	public String consultaDataAtualServidor(){
		Object[] row;
		String sql;


		sql = 	" select  DATE_FORMAT(now(), '%d/%m/%Y'), 'TEMP'  from dual;  ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			if(q != null){
				if(q.size()>0){
					row = q.get(0);

					//DATA FORMATADA
					return row[0].toString();

				}
			}

		}catch(Exception e){
			return null;
		}
		
		return null;
	};
	
	
	
	
	/**
	 * A data tem que vim com o formato '%d/%m/%Y'
	 * @param entidade
	 * @param data
	 * @return
	 */
	public ArrayList<FluxoCaixa> buscaTodasListagemObjetos( String entidade, String data){
		ArrayList<FluxoCaixa> arrayFluxoCaixa = new ArrayList<>();
		FluxoCaixa auxFluxoCaixa; 
		//Object[] listaResult;
		Object[] row;
		String sql;


		sql = 	" SELECT FLU_DS_FLUXO_CAIXA, FLU_CD_FLUXO_CAIXA "+
				" FROM FLUXO_CAIXA "+
				" WHERE FLU_CD_ENTIDADE = "+entidade+
				" AND DATE_FORMAT(FLU_DT_CADASTRO,'%d/%m/%Y') = DATE_FORMAT(STR_TO_DATE("+data+",'%d/%m/%Y'),'%d/%m/%Y') "+
				" order by FLU_CD_FLUXO_CAIXA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			if(q != null){
				for(int i = 0; i<q.size();i++){
					row = q.get(i);

					auxFluxoCaixa = new FluxoCaixa();

					auxFluxoCaixa = (FluxoCaixa) sg.carregaEntidade(auxFluxoCaixa, Integer.parseInt(row[1].toString()));

					arrayFluxoCaixa.add(auxFluxoCaixa);

				}
			}else{
				return null;
			}

			return arrayFluxoCaixa;
		}catch(Exception e){
			return null;
		}
	};
	
	public String converteDataParaMostrar( String data){
		return dataConverteDataBrasilMysqlData(data, "-", "/");
	}
	
	public String converteDataParaGravar( String data){
		return dataConverteDataBrasilMysqlData(data, "/", "-");
	}

}
