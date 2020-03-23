package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.ItemProposta;

public class ItemPropostaDAO extends MetodosComumDAO {

	
	public ItemPropostaDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	public ArrayList<ItemProposta> consultaItensPropostaComercialBackup(){
		ArrayList<ItemProposta> listItensPropostas = new ArrayList<>();
		ItemProposta itemProposta ;
		Object[] row;
		String sql;



		try{

			sql = 
					" SELECT IPC_CD_ITEM_PROPOSTA, IPC_NR_QUANTIDADE, IPC_DS_ITEM_PROPOSTA, IPC_VL_DESCONTO, IPC_VL_ITEM_PROPOSTA, IPC_DS_UNIDADE, IPC_CD_PROPOSTA_COMERCIAL "+
					" FROM ITEM_PROPOSTA 	";


			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				itemProposta = new ItemProposta();

				row = q.get(i);
				
				itemProposta.setIpc_cd_item_proposta(Integer.parseInt(row[0].toString()));
					
				itemProposta.setIpc_nr_quantidade(Double.parseDouble(row[1].toString()));
				
				if(row[2] == null) row[2] = "";
				itemProposta.setIpc_ds_item_proposta(row[2].toString());
		
				itemProposta.setIpc_vl_desconto(Double.parseDouble(row[3].toString()));
	
				itemProposta.setIpc_vl_item_proposta(Double.parseDouble(row[4].toString()));
	
				itemProposta.setIpc_ds_unidade(row[5].toString());
				
				itemProposta.setIpc_cd_proposta_comercial(Integer.parseInt(row[6].toString()));
				
				
				listItensPropostas.add(itemProposta);
			};
			
			if (q.size() == 0){
				return null;
			}else{
				return listItensPropostas;
			}

		}catch(Exception e){
			return null;
		}
	};
		
	
	public ArrayList<ItemProposta> buscaItensPropostaComercial(int id_proposta_comercial){
		ArrayList<ItemProposta> listItensPropostas = new ArrayList<>();
		ItemProposta itemProposta ;
		Object[] row;
		String sql;



		try{

			sql = 
					" SELECT IPC_CD_ITEM_PROPOSTA, IPC_NR_QUANTIDADE, IPC_DS_ITEM_PROPOSTA, IPC_VL_DESCONTO, IPC_VL_ITEM_PROPOSTA, IPC_DS_UNIDADE "+
					" FROM ITEM_PROPOSTA 	"+
					" WHERE IPC_CD_PROPOSTA_COMERCIAL = "+id_proposta_comercial+ "	";


			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				itemProposta = new ItemProposta();

				row = q.get(i);
				
				itemProposta.setIpc_cd_item_proposta(Integer.parseInt(row[0].toString()));
					
				itemProposta.setIpc_nr_quantidade(Double.parseDouble(row[1].toString()));
				
				if(row[2] == null) row[2] = "";
				itemProposta.setIpc_ds_item_proposta(row[2].toString());
		
				itemProposta.setIpc_vl_desconto(Double.parseDouble(row[3].toString()));
	
				itemProposta.setIpc_vl_item_proposta(Double.parseDouble(row[4].toString()));
	
				itemProposta.setIpc_ds_unidade(row[5].toString());
				
				itemProposta.setIpc_cd_proposta_comercial(id_proposta_comercial);
				
				
				listItensPropostas.add(itemProposta);
			};
			
			if (q.size() == 0){
				return null;
			}else{
				return listItensPropostas;
			}

		}catch(Exception e){
			return null;
		}
	};
	
	
	
	public boolean deletarTodosItensProposta(int id_proposta){
		String sql;
		
		try{
		
			sql = "DELETE FROM ITEM_PROPOSTA WHERE IPC_CD_PROPOSTA_COMERCIAL = "+id_proposta+" ;";

			return super.executaSqlGeral(sql);
			
		}catch(Exception e){
			return false;
		}
	};
}
