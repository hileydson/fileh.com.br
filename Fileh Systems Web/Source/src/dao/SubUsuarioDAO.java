package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.SubUsuario;



public class SubUsuarioDAO  extends MetodosComumDAO{
	
	public SubUsuarioDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<SubUsuario> buscaTodosSubUsuarios(Integer cd_usuario){
		ArrayList<SubUsuario> arraySubUsuario = new ArrayList<>();
		SubUsuario auxSubUsuario = new SubUsuario();
		Object[] row;
		String sql;

		sql = 	" SELECT SBU_CD_SUBUSUARIO, SBU_DS_LOGIN, SBU_NM_SUBUSUARIO, SBU_DS_MSG_FOOTER, SBU_FL_ADM, SBU_FL_MODULO_CAIXA, SBU_FL_MODULO_CLIENTE, SBU_FL_MODULO_FINANCEIRO, "+
				" SBU_FL_MODULO_VENDA  "+
				" FROM SUBUSUARIO "+
				" WHERE SBU_CD_USUARIO =  "+cd_usuario+" ; ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxSubUsuario = new SubUsuario();

				row = q.get(i);
				
				auxSubUsuario.setSbu_cd_subusuario(Integer.parseInt(row[0].toString()));
				auxSubUsuario.setSbu_ds_login(row[1].toString());
				auxSubUsuario.setSbu_nm_subusuario(row[2].toString());
				
				if(row[3] == null) row[3] = "";
				auxSubUsuario.setSbu_ds_msg_footer(row[3].toString());
				
				auxSubUsuario.setSbu_fl_adm(row[4].toString());
				auxSubUsuario.setSbu_fl_modulo_caixa(row[5].toString());
				auxSubUsuario.setSbu_fl_modulo_cliente(row[6].toString());
				auxSubUsuario.setSbu_fl_modulo_financeiro(row[7].toString());
				auxSubUsuario.setSbu_fl_modulo_venda(row[8].toString());
				

				arraySubUsuario.add(auxSubUsuario);
			}

			return arraySubUsuario;
		}catch(Exception e){
			return null;
		}
	};

}
