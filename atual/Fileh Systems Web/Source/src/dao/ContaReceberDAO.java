package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.ContaReceber;

public class ContaReceberDAO extends MetodosComumDAO {

	
	
	public ContaReceberDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	public boolean marcarContasFlPago(int cd_usuario, String fl_marcar, String contas){
		String sql;
		
		try{
		
			sql = "UPDATE CONTA_RECEBER SET COR_FL_RECEBIDO = '"+fl_marcar+"' WHERE COR_CD_USUARIO = "+cd_usuario+" AND COR_CD_CONTA_RECEBER IN ("+contas+");";

			return super.executaSqlGeral(sql);
			
		}catch(Exception e){
			return false;
		}
	};
	
	
	
	
	public boolean consultaDuplicidadeConta(String fornecedor, String tipoConta, String documento){
		String sql;


			sql = 	" SELECT COR_CD_CONTA_RECEBER, COR_DS_FORNECEDOR  "+
					" FROM CONTA_RECEBER  "+
					" WHERE COR_DS_FORNECEDOR = '"+fornecedor+"' "+
					" AND COR_DS_TIPO_CONTA = '"+tipoConta+"' "+
					" AND COR_NR_DOCUMENTO = '"+documento+"' ";

		try{

			List<Object[]> q = sg.consultaGeral(sql);


			//se retornar qualquer dado na consulta então existe duplicidade
			if(q.size()>0){
				return true;
			}else{
				return false;
			}

			
		}catch(Exception e){
			//retornando true em caso de exceção... nao permitirá o cadastro da conta...
			return true;
		}
	};	
	
	
	
	public ArrayList<ContaReceber> consultaContasReceberBackup(){
		ArrayList<ContaReceber> arrayContasReceber = new ArrayList<>();
		ContaReceber auxContaReceber = new ContaReceber();
		Object[] row;
		String sql;
		

			sql = 	" SELECT COR_CD_CONTA_RECEBER, COR_DS_FORNECEDOR  "+
					" FROM CONTA_RECEBER ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxContaReceber = new ContaReceber();

				row = q.get(i);
				
				auxContaReceber = (ContaReceber) sg.carregaEntidade(auxContaReceber, Integer.parseInt(row[0].toString()));


				arrayContasReceber.add(auxContaReceber);
			}

			return arrayContasReceber;
		}catch(Exception e){
			return null;
		}
	};
	
	
	public ArrayList<ContaReceber> consultaContasReceber(Integer cd_usuario, String situacao, String data_inicio, String data_fim, String nr_doc, int codigo_conta, 
													  String fornecedor, String tipoConta){
		ArrayList<ContaReceber> arrayContasReceber = new ArrayList<>();
		ContaReceber auxContaReceber = new ContaReceber();
		Object[] row;
		String sql;
		

		if( (!situacao.equalsIgnoreCase("T")) || ((!data_inicio.equalsIgnoreCase("")) && (!data_fim.equalsIgnoreCase(""))) || (!nr_doc.equalsIgnoreCase(""))
				|| (codigo_conta != 0 ) || (!fornecedor.equalsIgnoreCase("-1")) || (!tipoConta.equalsIgnoreCase("-1")) ){

			sql = 	" SELECT COR_CD_CONTA_RECEBER, COR_DS_FORNECEDOR, COR_NR_DOCUMENTO, COR_DS_CONTA_RECEBER, COR_DT_VENCIMENTO, COR_VL_CONTA_RECEBER, COR_NR_PARCELA, COR_FL_RECEBIDO, COR_DS_TIPO_CONTA  "+
					" FROM CONTA_RECEBER "+
					" WHERE COR_CD_USUARIO =  "+cd_usuario+" ";

			if(!situacao.equalsIgnoreCase("T"))
				sql += " AND COR_FL_RECEBIDO = '"+situacao+"'   "; 

			if((!data_inicio.equalsIgnoreCase("")) && (!data_fim.equalsIgnoreCase("")))
				sql += " AND COR_DT_VENCIMENTO BETWEEN  STR_TO_DATE('"+data_inicio+"', '%d/%m/%Y') AND STR_TO_DATE('"+data_fim+"', '%d/%m/%Y')   "; 

			if(!nr_doc.equalsIgnoreCase(""))
				sql += " AND COR_NR_DOCUMENTO LIKE '%"+nr_doc+"%'    "; 

			if(codigo_conta != 0 )
				sql += " AND COR_CD_CONTA_RECEBER = "+codigo_conta+"    "; 

			if( (!fornecedor.equalsIgnoreCase("")) && (!fornecedor.equalsIgnoreCase("-1")) )
				sql += " AND COR_DS_FORNECEDOR = '"+fornecedor+"'    ";			

			if( (!tipoConta.equalsIgnoreCase("")) && (!tipoConta.equalsIgnoreCase("-1")) )
				sql += " AND COR_DS_TIPO_CONTA = '"+tipoConta+"'    ";
			
			sql += " ORDER BY COR_DT_VENCIMENTO, COR_DS_FORNECEDOR ; ";
		}else{
			sql = 	" SELECT COR_CD_CONTA_RECEBER, COR_DS_FORNECEDOR, COR_NR_DOCUMENTO, COR_DS_CONTA_RECEBER, COR_DT_VENCIMENTO, COR_VL_CONTA_RECEBER, COR_NR_PARCELA, COR_FL_RECEBIDO, COR_DS_TIPO_CONTA  "+
					" FROM CONTA_RECEBER "+
					" WHERE COR_CD_USUARIO =  "+cd_usuario+ 
					" ORDER BY COR_DT_VENCIMENTO, COR_DS_FORNECEDOR LIMIT 40 ; ";
		}


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxContaReceber = new ContaReceber();

				row = q.get(i);
				auxContaReceber.setCor_cd_conta_receber(Integer.parseInt(row[0].toString()));//COR_CD_CONTA_RECEBER
				
				auxContaReceber.setCor_ds_fornecedor(row[1].toString());//COR_DS_FORNECEDOR
				
				if(row[2] == null) row[2] = "";	
				auxContaReceber.setCor_nr_documento(row[2].toString());//COR_NR_DOCUMENTO

				if(row[3] == null) row[3] = "";				
				auxContaReceber.setCor_ds_conta_receber(row[3].toString());//COR_DS_CONTA_RECEBER
	
				if(row[4] == null) row[4] = "";;
				auxContaReceber.setCor_dt_vencimento( this.dataConverteDataBrasilMysqlData( row[4].toString(), "-", "/") );//COR_DT_VENCIMENTO

				auxContaReceber.setCor_vl_conta_receber(Double.parseDouble(row[5].toString()));//COR_VL_CONTA_RECEBER

				auxContaReceber.setCor_nr_parcela(Integer.parseInt(row[6].toString()));//COR_NR_PARCELA
	
				auxContaReceber.setCor_fl_recebido(row[7].toString());//COR_FL_RECEBIDO 
				
				auxContaReceber.setCor_ds_tipo_conta(row[8].toString());//COR_DS_TIPO_CONTA 


				arrayContasReceber.add(auxContaReceber);
			}

			return arrayContasReceber;
		}catch(Exception e){
			return null;
		}
	};
}
