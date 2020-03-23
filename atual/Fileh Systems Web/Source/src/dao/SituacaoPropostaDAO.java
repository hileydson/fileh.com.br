package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entidades.SituacaoProposta;


public class SituacaoPropostaDAO extends MetodosComumDAO {
	
	public SituacaoPropostaDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> buscaTodosAtribuidosSitucaoPropostaListagemNomes(){
		ArrayList<String> arraySituacaoProposta = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT PRC_DS_SITUACAO "+
				" FROM PROPOSTA_COMERCIAL "+
				" WHERE PRC_DS_SITUACAO IS NOT NULL "+
				" AND PRC_DS_SITUACAO != '' "+
				" group by PRC_DS_SITUACAO order by PRC_DS_SITUACAO ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				
				if(listaResult[i] != null){
					arraySituacaoProposta.add(listaResult[i].toString());
				};
				
			}

			return arraySituacaoProposta;
		}catch(Exception e){
			return null;
		}
	};
	
	public ArrayList<String> buscaTodosTabelaSitucaoPropostaListagemNomes(){
		ArrayList<String> arraySituacaoProposta = new ArrayList<>();
		Object[] listaResult;
		String sql;


		sql = 	" SELECT SIP_DS_SITUACAO_PROPOSTA  "+
				" FROM SITUACAO_PROPOSTA "+
				" group by SIP_DS_SITUACAO_PROPOSTA order by SIP_DS_SITUACAO_PROPOSTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			listaResult = Arrays.asList(q).get(0).toArray();
			
			for(int i = 0; i<listaResult.length;i++){
				arraySituacaoProposta.add(listaResult[i].toString());
			}

			return arraySituacaoProposta;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<SituacaoProposta> consultaSitucaoPropostaBackup(){
		ArrayList<SituacaoProposta> arraySituacaoProposta = new ArrayList<>();
		SituacaoProposta auxSituacaoProposta; 
		Object[] row;
		String sql;


		sql = 	" SELECT SIP_DS_SITUACAO_PROPOSTA, SIP_CD_SITUACAO_PROPOSTA  "+
				" FROM SITUACAO_PROPOSTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			for(int i = 0; i<q.size();i++){
				
				auxSituacaoProposta = new SituacaoProposta();
				
				row = q.get(i);
				
				auxSituacaoProposta = (SituacaoProposta) sg.carregaEntidade(auxSituacaoProposta, Integer.parseInt(row[1].toString()));
				
				arraySituacaoProposta.add(auxSituacaoProposta);
				
			}

			return arraySituacaoProposta;
		}catch(Exception e){
			return null;
		}
	};
	

	public ArrayList<SituacaoProposta> buscaTodasSitucaoPropostaListagemObjetos(){
		ArrayList<SituacaoProposta> arraySituacaoProposta = new ArrayList<>();
		SituacaoProposta auxSituacaoProposta; 
		//Object[] listaResult;
		Object[] row;
		String sql;


		sql = 	" SELECT SIP_DS_SITUACAO_PROPOSTA, SIP_CD_SITUACAO_PROPOSTA  "+
				" FROM SITUACAO_PROPOSTA "+
				" order by SIP_CD_SITUACAO_PROPOSTA ; ";

		try{

			List<Object[]> q = super.sg.consultaGeral(sql);

			//listaResult = Arrays.asList(q).get(0).toArray();
			
			//for(int i = 0; i<listaResult.length;i++){
			for(int i = 0; i<q.size();i++){
				row = q.get(i);
				
				auxSituacaoProposta = new SituacaoProposta();
				auxSituacaoProposta.setSip_ds_situacao_proposta(row[0].toString());
				auxSituacaoProposta.setSip_cd_situacao_proposta(Integer.parseInt(row[1].toString()));
				
				arraySituacaoProposta.add(auxSituacaoProposta);
				
			}

			return arraySituacaoProposta;
		}catch(Exception e){
			return null;
		}
	};


}
