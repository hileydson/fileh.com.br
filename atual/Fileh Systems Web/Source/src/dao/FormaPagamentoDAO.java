package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import entidades.FormaPagamento;



public class FormaPagamentoDAO extends MetodosComumDAO {
	
	
	
	public FormaPagamentoDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> buscaTodosAtribuidosFormaPagamentoListagemNomesProposta(){
		ArrayList<String> array = new ArrayList<>();
		Object[] listaResult;
		String sql;


		//busca todas atribuidas que nao foram atribuidas as propostas 
		sql = 	" SELECT PRC_DS_FORMA_PAGAMENTO "+
				" FROM PROPOSTA_COMERCIAL "+
				" AND PRC_DS_FORMA_PAGAMENTO IS NOT NULL "+
				" AND PRC_DS_FORMA_PAGAMENTO != '' "+
				" group by PRC_DS_FORMA_PAGAMENTO order by PRC_DS_FORMA_PAGAMENTO ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				
				if(listaResult[i] != null){
					array.add(listaResult[i].toString());
				};
				
			}

			return array;
		}catch(Exception e){
			return null;
		}
	};
	
	public ArrayList<String> buscaTodosTabelaFormaPagamentoListagemNomes(String fl_tipo){
		ArrayList<String> array = new ArrayList<>();
		Object[] listaResult;
		String sql;
		

		sql = 	" SELECT FOP_DS_FORMA_PAGAMENTO  "+
				" FROM FORMA_PAGAMENTO "+
				" WHERE FOP_FL_TIPO = '"+fl_tipo+"' "+
				" group by FOP_DS_FORMA_PAGAMENTO order by FOP_DS_FORMA_PAGAMENTO ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				array.add(listaResult[i].toString());
			}

			return array;
		}catch(Exception e){
			return null;
		}
	};

	public ArrayList<FormaPagamento> buscaTodasListagemObjetos(String fl_tipo){
		ArrayList<FormaPagamento> arrayFormaPagamento = new ArrayList<>();
		FormaPagamento auxFormaPagamento; 
		Object[] row;
		String sql;

		
		sql = 	" SELECT FOP_DS_FORMA_PAGAMENTO, FOP_CD_FORMA_PAGAMENTO  "+
				" FROM FORMA_PAGAMENTO "+
				" WHERE FOP_FL_TIPO = '"+fl_tipo+"' "+
				" order by FOP_CD_FORMA_PAGAMENTO ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			for(int i = 0; i<q.size();i++){
				row = q.get(i);
				
				auxFormaPagamento = new FormaPagamento();
				auxFormaPagamento.setFop_ds_forma_pagamento(row[0].toString());
				auxFormaPagamento.setFop_cd_forma_pagamento(Integer.parseInt(row[1].toString()));
				
				arrayFormaPagamento.add(auxFormaPagamento);
				
			}

			return arrayFormaPagamento;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<FormaPagamento> consultaFormaPagamentoBackup(){
		ArrayList<FormaPagamento> arrayFormaPagamento = new ArrayList<>();
		FormaPagamento auxFormaPagamento; 
		Object[] row;
		String sql;

		
		sql = 	" SELECT FOP_DS_FORMA_PAGAMENTO, FOP_CD_FORMA_PAGAMENTO  "+
				" FROM FORMA_PAGAMENTO ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			for(int i = 0; i<q.size();i++){
				
				auxFormaPagamento = new FormaPagamento();
				
				row = q.get(i);
				
				auxFormaPagamento = (FormaPagamento) sg.carregaEntidade(auxFormaPagamento, Integer.parseInt(row[1].toString()));
				
				arrayFormaPagamento.add(auxFormaPagamento);
				
			}

			return arrayFormaPagamento;
		}catch(Exception e){
			return null;
		}
	};

}
