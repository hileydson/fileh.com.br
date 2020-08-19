package dao;

import java.util.ArrayList;
import java.util.List;
import entidades.Cliente;

public class ClienteDAO extends MetodosComumDAO {



	public ClienteDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	public ArrayList<Cliente> consultaClientes(String nome_cliente, String cod_cliente, String ds_entidade, String cd_usuario){
		ArrayList<Cliente> arrayClientes = new ArrayList<>();
		Cliente auxCliente = new Cliente();
		Object[] row;
		String sql;


		if((!nome_cliente.equalsIgnoreCase("")) || ((!cod_cliente.equalsIgnoreCase("0")) && (!cod_cliente.equalsIgnoreCase(""))) || (!ds_entidade.equalsIgnoreCase("-1"))){

			sql = 	" SELECT CLI_CD_CLIENTE, CLI_CD_USUARIO, CLI_DS_ENTIDADE, CLI_DS_LOGRADOURO, CLI_DS_REFERENCIA, CLI_DS_UF, CLI_NM_CLIENTE, CLI_NR_CPF, CLI_NR_TEL, CLI_DS_BAIRRO "+
					" FROM CLIENTE "+
					" WHERE CLI_CD_USUARIO =  "+cd_usuario+" ";

			if(!nome_cliente.equalsIgnoreCase(""))
				sql += " AND CLI_NM_CLIENTE LIKE '%"+nome_cliente+"%'    "; 

			if((!cod_cliente.equalsIgnoreCase("0")) && (!cod_cliente.equalsIgnoreCase("")))
				sql += " AND CLI_CD_CLIENTE = "+cod_cliente+"    "; 

			if(!ds_entidade.equalsIgnoreCase("") && !ds_entidade.equalsIgnoreCase("-1"))
				sql += " AND CLI_DS_ENTIDADE = '"+ds_entidade+"'    "; 		

		}else{
			sql = 	" SELECT CLI_CD_CLIENTE, CLI_CD_USUARIO, CLI_DS_ENTIDADE, CLI_DS_LOGRADOURO, CLI_DS_REFERENCIA, CLI_DS_UF, CLI_NM_CLIENTE, CLI_NR_CPF, CLI_NR_TEL, CLI_DS_BAIRRO "+
					" FROM CLIENTE "+
					" WHERE CLI_CD_USUARIO =  "+cd_usuario+" "+
					" ORDER BY CLI_NM_CLIENTE LIMIT 40 ; ";
		}


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxCliente = new Cliente();

				row = q.get(i);
				auxCliente.setCli_cd_cliente(Integer.parseInt(row[0].toString()));
				auxCliente.setCli_cd_usuario(Integer.parseInt(row[1].toString()));
				auxCliente.setCli_ds_entidade(row[2].toString());

				if(row[3] == null) row[3] = "";				
				auxCliente.setCli_ds_logradouro(row[3].toString());

				if(row[4] == null) row[4] = "";	
				auxCliente.setCli_ds_referencia(row[4].toString());

				auxCliente.setCli_ds_uf(row[5].toString());

				auxCliente.setCli_nm_cliente(row[6].toString());

				if(row[7] == null) row[7] = "";	
				auxCliente.setCli_nr_cpf(row[7].toString());

				if(row[8] == null) row[8] = "";	
				auxCliente.setCli_nr_tel(row[8].toString());

				if(row[9] == null) row[9] = "";	
				auxCliente.setCli_ds_bairro(row[9].toString());

				arrayClientes.add(auxCliente);
			}

			return arrayClientes;
		}catch(Exception e){
			return null;
		}
	};


	public ArrayList<Cliente> consultaClientesBackup(){
		ArrayList<Cliente> arrayClientes = new ArrayList<>();
		Cliente auxCliente = new Cliente();
		Object[] row;
		String sql;


		sql = 	" SELECT CLI_CD_CLIENTE, CLI_CD_USUARIO, CLI_DS_ENTIDADE, CLI_DS_LOGRADOURO, CLI_DS_REFERENCIA, CLI_DS_UF, CLI_NM_CLIENTE, CLI_NR_CPF, CLI_NR_TEL, CLI_DS_BAIRRO "+
				" FROM CLIENTE ;";

		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxCliente = new Cliente();

				row = q.get(i);
				auxCliente.setCli_cd_cliente(Integer.parseInt(row[0].toString()));
				auxCliente.setCli_cd_usuario(Integer.parseInt(row[1].toString()));
				auxCliente.setCli_ds_entidade(row[2].toString());

				if(row[3] == null) row[3] = "";				
				auxCliente.setCli_ds_logradouro(row[3].toString());

				if(row[4] == null) row[4] = "";	
				auxCliente.setCli_ds_referencia(row[4].toString());

				auxCliente.setCli_ds_uf(row[5].toString());

				auxCliente.setCli_nm_cliente(row[6].toString());

				if(row[7] == null) row[7] = "";	
				auxCliente.setCli_nr_cpf(row[7].toString());

				if(row[8] == null) row[8] = "";	
				auxCliente.setCli_nr_tel(row[8].toString());

				if(row[9] == null) row[9] = "";	
				auxCliente.setCli_ds_bairro(row[9].toString());

				arrayClientes.add(auxCliente);
			}

			return arrayClientes;
		}catch(Exception e){
			return null;
		}
	};


}
