package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entidades.Fornecedor;

public class FornecedorDAO extends MetodosComumDAO {
	
	
	
	public FornecedorDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	public ArrayList<String> buscaFornecedoresListagemNomes(Integer cd_usuario_logado){
		ArrayList<String> arrayFornecedor = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT FOR_DS_FORNECEDOR  "+
				" FROM FORNECEDOR "+
				" WHERE FOR_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by FOR_DS_FORNECEDOR order by FOR_DS_FORNECEDOR ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayFornecedor.add(listaResult[i].toString());
			}

			return arrayFornecedor;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<String> buscaFornecedoresListagemNomesContasPagar(Integer cd_usuario_logado){
		ArrayList<String> arrayFornecedor = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT COP_DS_FORNECEDOR  "+
				" FROM CONTA_PAGAR "+
				" WHERE COP_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by COP_DS_FORNECEDOR order by COP_DS_FORNECEDOR ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayFornecedor.add(listaResult[i].toString());
			}

			return arrayFornecedor;
		}catch(Exception e){
			return null;
		}
	};
	
	public ArrayList<String> buscaFornecedoresListagemNomesContasReceber(Integer cd_usuario_logado){
		ArrayList<String> arrayFornecedor = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT COR_DS_FORNECEDOR  "+
				" FROM CONTA_RECEBER "+
				" WHERE COR_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by COR_DS_FORNECEDOR order by COR_DS_FORNECEDOR ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayFornecedor.add(listaResult[i].toString());
			}

			return arrayFornecedor;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<Fornecedor> buscaTodosFornecedores(Integer cd_usuario){
		ArrayList<Fornecedor> arrayFornecedor = new ArrayList<>();
		Fornecedor auxFornecedor = new Fornecedor();
		Object[] row;
		String sql;

		sql = 	" SELECT FOR_CD_FORNECEDOR, FOR_DS_FORNECEDOR "+
				" FROM FORNECEDOR "+
				" WHERE FOR_CD_USUARIO =  "+cd_usuario+" ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				

				row = q.get(i);
				auxFornecedor = (Fornecedor) carregaEntidade(auxFornecedor, Integer.parseInt(row[0].toString()));

				
				arrayFornecedor.add(auxFornecedor);
			}

			return arrayFornecedor;
		}catch(Exception e){
			return null;
		}
	};
	
	
	
	public ArrayList<Fornecedor> consultaFornecedoresBackup(){
		ArrayList<Fornecedor> arrayFornecedor = new ArrayList<>();
		Fornecedor auxFornecedor = new Fornecedor();
		Object[] row;
		String sql;

		sql = 	" SELECT FOR_CD_FORNECEDOR, FOR_DS_FORNECEDOR "+
				" FROM FORNECEDOR ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				

				row = q.get(i);
				auxFornecedor = (Fornecedor) carregaEntidade(auxFornecedor, Integer.parseInt(row[0].toString()));

				
				arrayFornecedor.add(auxFornecedor);
			}

			return arrayFornecedor;
		}catch(Exception e){
			return null;
		}
	};
	
	
}
