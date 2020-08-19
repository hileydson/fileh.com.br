package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entidades.TipoConta;

public class TipoContaDAO extends MetodosComumDAO {
	
	
	
	public TipoContaDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	public ArrayList<String> buscaTipoContasListagemNomes(Integer cd_usuario_logado){
		ArrayList<String> arrayTipoConta = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT TCO_DS_TIPO_CONTA  "+
				" FROM TIPO_CONTA "+
				" WHERE TCO_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by TCO_DS_TIPO_CONTA order by TCO_DS_TIPO_CONTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayTipoConta.add(listaResult[i].toString());
			}

			return arrayTipoConta;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<String> buscaTipoContasListagemNomesContasPagar(Integer cd_usuario_logado){
		ArrayList<String> arrayTipoConta = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT COP_DS_TIPO_CONTA  "+
				" FROM CONTA_PAGAR "+
				" WHERE COP_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by COP_DS_TIPO_CONTA order by COP_DS_TIPO_CONTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayTipoConta.add(listaResult[i].toString());
			}

			return arrayTipoConta;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<String> buscaTipoContasListagemNomesContasReceber(Integer cd_usuario_logado){
		ArrayList<String> arrayTipoConta = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT COR_DS_TIPO_CONTA  "+
				" FROM CONTA_RECEBER "+
				" WHERE COR_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by COR_DS_TIPO_CONTA order by COR_DS_TIPO_CONTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayTipoConta.add(listaResult[i].toString());
			}

			return arrayTipoConta;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<TipoConta> consultaTipoContaBackup(){
		ArrayList<TipoConta> arrayTipoConta = new ArrayList<>();
		TipoConta auxTipoConta = new TipoConta();
		Object[] row;
		String sql;

		sql = 	" SELECT TCO_CD_TIPO_CONTA, TCO_DS_TIPO_CONTA "+
				" FROM TIPO_CONTA  ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){

				auxTipoConta = new TipoConta();
				
				row = q.get(i);
				
				auxTipoConta = (TipoConta) sg.carregaEntidade(auxTipoConta, Integer.parseInt(row[0].toString()));

				arrayTipoConta.add(auxTipoConta);
			}

			return arrayTipoConta;
		}catch(Exception e){
			return null;
		}
	};
	
	public ArrayList<TipoConta> buscaTodosTipoConta(String cd_usuario){
		ArrayList<TipoConta> arrayTipoConta = new ArrayList<>();
		TipoConta auxTipoConta = new TipoConta();
		Object[] row;
		String sql;

		sql = 	" SELECT TCO_CD_TIPO_CONTA, TCO_DS_TIPO_CONTA "+
				" FROM TIPO_CONTA "+
				" WHERE TCO_CD_USUARIO =  "+cd_usuario+" ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxTipoConta = new TipoConta();

				row = q.get(i);
				auxTipoConta.setTco_cd_tipo_conta(Integer.parseInt(row[0].toString()));
				auxTipoConta.setTco_ds_tipo_conta(row[1].toString());

				arrayTipoConta.add(auxTipoConta);
			}

			return arrayTipoConta;
		}catch(Exception e){
			return null;
		}
	};
	
	
}
