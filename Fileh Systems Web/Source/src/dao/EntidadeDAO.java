package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entidades.Entidade;

public class EntidadeDAO  extends MetodosComumDAO{
	
	
	
	public EntidadeDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Busca todos os nomes de entidades que já possuem clientes relacionados.
	 * @param cd_usuario_logado
	 * @return
	 */
	public ArrayList<String> buscaEntidadesListagemNomes(Integer cd_usuario_logado){
		ArrayList<String> arrayEntidades = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT CLI_DS_ENTIDADE  "+
				" FROM CLIENTE "+
				" WHERE CLI_CD_USUARIO =  "+cd_usuario_logado+" "+
				" group by CLI_DS_ENTIDADE order by CLI_DS_ENTIDADE ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayEntidades.add(listaResult[i].toString());
			}

			return arrayEntidades;
		}catch(Exception e){
			return null;
		}
	};
	
	/**
	 * Busca todos os nomes de entidades independente de relacionamento com outras tabelas. 
	 * @param cd_usuario_logado
	 * @return
	 */
	public ArrayList<String> buscaTodasEntidadesListagemNomes(Integer cd_usuario_logado){
		ArrayList<String> arrayEntidades = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT ENT_NM_ENTIDADE  "+
				" FROM ENTIDADE "+
				" WHERE ENT_CD_USUARIO =  "+cd_usuario_logado+" ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arrayEntidades.add(listaResult[i].toString());
			}

			return arrayEntidades;
		}catch(Exception e){
			return null;
		}
	};
	
	
	/**
	 * Buscam todas as entidades como objetos independente de relacionamento com outras tabelas.
	 * @param cd_usuario
	 * @return
	 */
	public ArrayList<Entidade> buscaTodasEntidades(String cd_usuario){
		ArrayList<Entidade> arrayEntidade = new ArrayList<>();
		Entidade auxEntidade = new Entidade();
		Object[] row;
		String sql;

		sql = 	" SELECT ENT_CD_ENTIDADE, ENT_NM_ENTIDADE "+
				" FROM ENTIDADE "+
				" WHERE ENT_CD_USUARIO =  "+cd_usuario+" ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxEntidade = new Entidade();

				row = q.get(i);
				auxEntidade.setEnt_cd_entidade(Integer.parseInt(row[0].toString()));
				auxEntidade.setEnt_nm_entidade(row[1].toString());

				arrayEntidade.add(auxEntidade);
			}

			return arrayEntidade;
		}catch(Exception e){
			return null;
		}
	};
	
	
	
	
	public ArrayList<Entidade> consultaEntidadesBackup(){
		ArrayList<Entidade> arrayEntidade = new ArrayList<>();
		Entidade auxEntidade = new Entidade();
		Object[] row;
		String sql;

		sql = 	" SELECT ENT_CD_ENTIDADE, ENT_NM_ENTIDADE "+
				" FROM ENTIDADE  ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxEntidade = new Entidade();

				row = q.get(i);
				
				auxEntidade = (Entidade) sg.carregaEntidade(auxEntidade, Integer.parseInt(row[0].toString()));

				arrayEntidade.add(auxEntidade);
			}

			return arrayEntidade;
		}catch(Exception e){
			return null;
		}
	};

}
