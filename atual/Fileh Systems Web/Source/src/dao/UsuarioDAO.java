package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;



public class UsuarioDAO  extends MetodosComumDAO{

	public UsuarioDAO() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Usuario> buscaTodosUsuarios(){
		ArrayList<Usuario> arrayUsuario = new ArrayList<>();
		Usuario auxUsuario = new Usuario();
		Object[] row;
		String sql;

		sql = 	" SELECT USU_CD_USUARIO, USU_NM_USUARIO"+
				" FROM USUARIO ";


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxUsuario = new Usuario();

				row = q.get(i);
								
				auxUsuario = (Usuario) sg.carregaEntidade(auxUsuario, Integer.parseInt(row[0].toString()) );
						

				arrayUsuario.add(auxUsuario);
			}

			return arrayUsuario;
		}catch(Exception e){
			return null;
		}
	};
	
	public String carregaContadorSistema(String userDominio) {
		// TODO Auto-generated method stub
		
		
		String resultado = "0";
		String sql;
		sql = 	"SELECT  ( "+
				" (SELECT COUNT(*) FROM CLIENTE)+ "+
				" (SELECT COUNT(*) FROM CONTA_PAGAR)+ "+
				" (SELECT COUNT(*) FROM CONTA_RECEBER)+ "+
				" (SELECT COUNT(*) FROM ENTIDADE)+ "+
				" (SELECT COUNT(*) FROM FLUXO_CAIXA)+ "+
				" (SELECT COUNT(*) FROM FORMA_PAGAMENTO)+ "+
				" (SELECT COUNT(*) FROM FORNECEDOR)+ "+
				" (SELECT COUNT(*) FROM ITEM_PROPOSTA)+ "+
				" (SELECT COUNT(*) FROM PRODUTO)+ "+
				" (SELECT COUNT(*) FROM PROPOSTA_COMERCIAL)+ "+
				" (SELECT COUNT(*) FROM SITUACAO_PROPOSTA)+ "+
				" (SELECT COUNT(*) FROM SUBUSUARIO)+ "+
				" (SELECT COUNT(*) FROM TIPO_CONTA) "+
				" ) , 1 FROM DUAL ; ";

		
		Object[] row;
		
		try{
			sg.setVars(userDominio);

			List<Object[]> q = sg.consultaGeral(sql);

			for(int i = 0; i<q.size();i++){

				row = q.get(i);
				
				resultado = row[0].toString();

			}

			return resultado;
		}catch(Exception e){
			return null;
		}

	}



}
