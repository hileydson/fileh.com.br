package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.ContaPagar;

public class ContaPagarDAO  extends MetodosComumDAO{


	
	public ContaPagarDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}



	public boolean marcarContasFlPago(int cd_usuario, String fl_marcar, String contas){
		String sql;

		try{

			sql = "UPDATE CONTA_PAGAR SET COP_FL_PAGO = '"+fl_marcar+"' WHERE COP_CD_USUARIO = "+cd_usuario+" AND COP_CD_CONTA_PAGAR IN ("+contas+");";

			return super.executaSqlGeral(sql);

		}catch(Exception e){
			return false;
		}
	};
	
	
	
	public boolean consultaDuplicidadeConta(String fornecedor, String tipoConta, String documento){
		String sql;


			sql = 	" SELECT COP_CD_CONTA_PAGAR, COP_DS_FORNECEDOR  "+
					" FROM CONTA_PAGAR  "+
					" WHERE COP_DS_FORNECEDOR = '"+fornecedor+"' "+
					" AND COP_DS_TIPO_CONTA = '"+tipoConta+"' "+
					" AND COP_NR_DOCUMENTO = '"+documento+"' ";

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



	public ArrayList<ContaPagar> consultaContasPagarBackup(){
		ArrayList<ContaPagar> arrayContasPagar = new ArrayList<>();
		ContaPagar auxContaPagar = new ContaPagar();
		Object[] row;
		String sql;


			sql = 	" SELECT COP_CD_CONTA_PAGAR, COP_DS_FORNECEDOR  "+
					" FROM CONTA_PAGAR  ";

		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxContaPagar = new ContaPagar();

				row = q.get(i);

				auxContaPagar = (ContaPagar) sg.carregaEntidade(auxContaPagar, Integer.parseInt(row[0].toString()));


				arrayContasPagar.add(auxContaPagar);
			}

			return arrayContasPagar;
		}catch(Exception e){
			return null;
		}
	};


	public ArrayList<ContaPagar> consultaContasPagar(Integer cd_usuario, String situacao, String data_inicio, String data_fim, String nr_doc, int codigo_conta, 
			String fornecedor, String tipoConta){
		ArrayList<ContaPagar> arrayContasPagar = new ArrayList<>();
		ContaPagar auxContaPagar = new ContaPagar();
		Object[] row;
		String sql;


		if( (!situacao.equalsIgnoreCase("T")) || ((!data_inicio.equalsIgnoreCase("")) && (!data_fim.equalsIgnoreCase(""))) || (!nr_doc.equalsIgnoreCase(""))
				|| (codigo_conta != 0 ) || (!fornecedor.equalsIgnoreCase("-1")) || (!tipoConta.equalsIgnoreCase("-1")) ){

			sql = 	" SELECT COP_CD_CONTA_PAGAR, COP_DS_FORNECEDOR, COP_NR_DOCUMENTO, COP_DS_CONTA_PAGAR, COP_DT_VENCIMENTO, COP_VL_CONTA_PAGAR, COP_NR_PARCELA, COP_FL_PAGO, COP_DS_TIPO_CONTA  "+
					" FROM CONTA_PAGAR "+
					" WHERE COP_CD_USUARIO =  "+cd_usuario+" ";

			if(!situacao.equalsIgnoreCase("T"))
				sql += " AND COP_FL_PAGO = '"+situacao+"'   "; 

			if((!data_inicio.equalsIgnoreCase("")) && (!data_fim.equalsIgnoreCase("")))
				sql += " AND COP_DT_VENCIMENTO BETWEEN  STR_TO_DATE('"+data_inicio+"', '%d/%m/%Y') AND STR_TO_DATE('"+data_fim+"', '%d/%m/%Y')   "; 

			if(!nr_doc.equalsIgnoreCase(""))
				sql += " AND COP_NR_DOCUMENTO LIKE '%"+nr_doc+"%'    "; 

			if(codigo_conta != 0 )
				sql += " AND COP_CD_CONTA_PAGAR = "+codigo_conta+"    "; 

			if( (!fornecedor.equalsIgnoreCase("")) && (!fornecedor.equalsIgnoreCase("-1")) )
				sql += " AND COP_DS_FORNECEDOR = '"+fornecedor+"'    ";			

			if( (!tipoConta.equalsIgnoreCase("")) && (!tipoConta.equalsIgnoreCase("-1")) )
				sql += " AND COP_DS_TIPO_CONTA = '"+tipoConta+"'    ";

			sql += " ORDER BY COP_DT_VENCIMENTO, COP_DS_FORNECEDOR ; ";
		}else{
			sql = 	" SELECT COP_CD_CONTA_PAGAR, COP_DS_FORNECEDOR, COP_NR_DOCUMENTO, COP_DS_CONTA_PAGAR, COP_DT_VENCIMENTO, COP_VL_CONTA_PAGAR, COP_NR_PARCELA, COP_FL_PAGO, COP_DS_TIPO_CONTA  "+
					" FROM CONTA_PAGAR "+
					" WHERE COP_CD_USUARIO =  "+cd_usuario+
					" ORDER BY COP_DT_VENCIMENTO, COP_DS_FORNECEDOR LIMIT 40 ; ";
		}


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxContaPagar = new ContaPagar();

				row = q.get(i);
				auxContaPagar.setCop_cd_conta_pagar(Integer.parseInt(row[0].toString()));//COP_CD_CONTA_PAGAR

				auxContaPagar.setCop_ds_fornecedor(row[1].toString());//COP_DS_FORNECEDOR

				if(row[2] == null) row[2] = "";	
				auxContaPagar.setCop_nr_documento(row[2].toString());//COP_NR_DOCUMENTO

				if(row[3] == null) row[3] = "";				
				auxContaPagar.setCop_ds_conta_pagar(row[3].toString());//COP_DS_CONTA_PAGAR

				if(row[4] == null) row[4] = "";;
				auxContaPagar.setCop_dt_vencimento( this.dataConverteDataBrasilMysqlData( row[4].toString(), "-", "/") );//COP_DT_VENCIMENTO

				auxContaPagar.setCop_vl_conta_pagar(Double.parseDouble(row[5].toString()));//COP_VL_CONTA_PAGAR

				auxContaPagar.setCop_nr_parcela(Integer.parseInt(row[6].toString()));//COP_NR_PARCELA

				auxContaPagar.setCop_fl_pago(row[7].toString());//COP_FL_PAGO 

				auxContaPagar.setCop_ds_tipo_conta(row[8].toString());//COP_DS_TIPO_CONTA 


				arrayContasPagar.add(auxContaPagar);
			}

			return arrayContasPagar;
		}catch(Exception e){
			return null;
		}
	};


}
