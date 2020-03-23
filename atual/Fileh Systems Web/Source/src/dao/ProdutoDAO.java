package dao;

import java.util.ArrayList;
import java.util.List;

import entidades.Produto;

public class ProdutoDAO extends MetodosComumDAO {
	
	
	public ProdutoDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Busca pelo codigo ou descrição do produto.
	 * @param cd_ou_ds_produto
	 * @param cd_usuario
	 * @return
	 */
	public ArrayList<Produto> consultaProdutos(String cd_ou_ds_produto, int cd_usuario){
		ArrayList<Produto> arrayProdutos = new ArrayList<>();
		Produto auxProduto = new Produto();
		Object[] row;
		String sql;



		sql = 	" SELECT PRD_CD_PRODUTO, PRD_DS_PRODUTO, PRD_VL_PRECO, PRD_DS_UNIDADE, PRD_NR_ESTOQUE "+
				" from PRODUTO  "+
				" WHERE PRD_CD_USUARIO = "+cd_usuario+"   "+
				" ORDER BY PRD_DS_PRODUTO LIMIT 40 ; ";





		if (	(	(cd_ou_ds_produto != null) && (!cd_ou_ds_produto.equalsIgnoreCase("")) )  ){

			sql = 	" SELECT PRD_CD_PRODUTO, PRD_DS_PRODUTO, PRD_VL_PRECO, PRD_DS_UNIDADE, PRD_NR_ESTOQUE "+
					" from PRODUTO "+
					" WHERE PRD_CD_USUARIO =  "+cd_usuario+" ";


			sql += " AND ( PRD_CD_PRODUTO = '"+cd_ou_ds_produto+"'  OR  PRD_DS_PRODUTO LIKE '%"+cd_ou_ds_produto+"%'  )   "; 
			
			sql += "ORDER BY PRD_DS_PRODUTO";

		}


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxProduto = new Produto();

				row = q.get(i);
				auxProduto.setPrd_cd_produto( Integer.parseInt(row[0].toString()) );//PRD_CD_PRODUTO
				auxProduto.setPrd_ds_produto( row[1].toString() );//PRD_DS_PRODUTO
				auxProduto.setPrd_vl_preco( Double.parseDouble(row[2].toString()) );//PRD_VL_PRECO

				if(row[3] == null) row[3] = "";				
				auxProduto.setPrd_ds_unidade( row[3].toString() );//PRD_DS_UNIDADE

				if(row[4] == null) row[4] = "";	
				auxProduto.setPrd_nr_estoque( Integer.parseInt(row[4].toString()) );//PRD_NR_ESTOQUE


				arrayProdutos.add(auxProduto);
			}

			return arrayProdutos;
		}catch(Exception e){
			return null;
		}
	};



	/**
	 * Busca pelo código ou descrição do produto separadamente.
	 * @param ds_produto
	 * @param cd_produto
	 * @param cd_usuario
	 * @return
	 */
	public ArrayList<Produto> consultaProdutos(String ds_produto, String cd_produto, int cd_usuario){
		ArrayList<Produto> arrayProdutos = new ArrayList<>();
		Produto auxProduto = new Produto();
		Object[] row;
		String sql;



		sql = 	" SELECT PRD_CD_PRODUTO, PRD_DS_PRODUTO, PRD_VL_PRECO, PRD_DS_UNIDADE, PRD_NR_ESTOQUE "+
				" from PRODUTO  "+
				" WHERE PRD_CD_USUARIO = "+cd_usuario+"   "+
				" ORDER BY PRD_DS_PRODUTO LIMIT 40 ; ";





		if (	(	(ds_produto != null) && (!ds_produto.equalsIgnoreCase("")) ) || ((cd_produto != null) &&	(!cd_produto.equalsIgnoreCase(""))	)  ){

			sql = 	" SELECT PRD_CD_PRODUTO, PRD_DS_PRODUTO, PRD_VL_PRECO, PRD_DS_UNIDADE, PRD_NR_ESTOQUE "+
					" from PRODUTO "+
					" WHERE PRD_CD_USUARIO =  "+cd_usuario+" ";

			if(	(ds_produto != null) && (!ds_produto.equalsIgnoreCase("")) )
				sql += " AND PRD_DS_PRODUTO LIKE '%"+ds_produto+"%'    "; 

			if((cd_produto != null) &&	(!cd_produto.equalsIgnoreCase(""))	)
				sql += " AND PRD_CD_PRODUTO = "+cd_produto+"    "; 




			sql += " AND ( PRD_CD_PRODUTO = "+cd_produto+"  OR  PRD_DS_PRODUTO LIKE '%"+ds_produto+"%'     "; 

		}


		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxProduto = new Produto();

				row = q.get(i);
				auxProduto.setPrd_cd_produto( Integer.parseInt(row[0].toString()) );//PRD_CD_PRODUTO
				auxProduto.setPrd_ds_produto( row[1].toString() );//PRD_DS_PRODUTO
				auxProduto.setPrd_vl_preco( Double.parseDouble(row[2].toString()) );//PRD_VL_PRECO

				if(row[3] == null) row[3] = "";				
				auxProduto.setPrd_ds_unidade( row[3].toString() );//PRD_DS_UNIDADE

				if(row[4] == null) row[4] = "";	
				auxProduto.setPrd_nr_estoque( Integer.parseInt(row[4].toString()) );//PRD_NR_ESTOQUE


				arrayProdutos.add(auxProduto);
			}

			return arrayProdutos;
		}catch(Exception e){
			return null;
		}
	};

	/**
	 * Busca todos os produtos cadastrados para backup
	 * @param ds_produto
	 * @param cd_produto
	 * @param cd_usuario
	 * @return
	 */
	public ArrayList<Produto> consultaProdutosBackup(){
		ArrayList<Produto> arrayProdutos = new ArrayList<>();
		Produto auxProduto = new Produto();
		Object[] row;
		String sql;



		sql = 	" SELECT PRD_CD_PRODUTO, PRD_DS_PRODUTO, PRD_VL_PRECO, PRD_DS_UNIDADE, PRD_NR_ESTOQUE, PRD_CD_USUARIO "+
				" from PRODUTO  ";



		try{

			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				auxProduto = new Produto();

				row = q.get(i);
				auxProduto.setPrd_cd_produto( Integer.parseInt(row[0].toString()) );//PRD_CD_PRODUTO
				auxProduto.setPrd_ds_produto( row[1].toString() );//PRD_DS_PRODUTO
				auxProduto.setPrd_vl_preco( Double.parseDouble(row[2].toString()) );//PRD_VL_PRECO

				if(row[3] == null) row[3] = "";				
				auxProduto.setPrd_ds_unidade( row[3].toString() );//PRD_DS_UNIDADE

				if(row[4] == null) row[4] = "";	
				auxProduto.setPrd_nr_estoque( Integer.parseInt(row[4].toString()) );//PRD_NR_ESTOQUE

				if(row[5] == null) row[5] = "";	
				auxProduto.setPrd_cd_usuario( Integer.parseInt(row[5].toString()) );
				
				arrayProdutos.add(auxProduto);
			}

			return arrayProdutos;
		}catch(Exception e){
			return null;
		}
	};
	
}
