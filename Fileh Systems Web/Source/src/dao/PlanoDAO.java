package dao;

import entidades.Plano;

public class PlanoDAO extends MetodosComumDAO {

	public PlanoDAO() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	public Plano buscaPlanoUsuario(Integer usu_cd_plano) {
		// TODO Auto-generated method stub
		Plano plano = new Plano();

		plano = (Plano) sg.carregaEntidade(plano, usu_cd_plano);
		
		return plano;
	}

}
