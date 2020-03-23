package hibernate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import dao.UsuarioDAO;

import entidades.SubUsuario;
import entidades.Usuario;
import hibernate.ConexaoHibernate;

public class ServicosGeral {

	public Session session;
	public ConexaoHibernate con;
	public String empresa = null;

	public ServicosGeral(String empresa_parametro){
		setVars(empresa_parametro);
	}
	
	public void setVars(String empresa_parametro){
		empresa = empresa_parametro;
		con = new ConexaoHibernate(empresa_parametro);
		session = con.configureSession(empresa_parametro);
	}

	public Integer inserirRegistro(Object obj){
		Integer resp ;
		session = con.configureSession(empresa);

		try{
			session.beginTransaction();
			resp = (Integer) session.save(obj);
			session.getTransaction().commit();
			return resp;
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
			}
		}
	}	

	public List<Object[]> consultaGeral(String sql){
		session = con.configureSession(empresa);
		try{
			session.beginTransaction();
			List<Object[]> q = session.createSQLQuery(sql).list(); 			
			session.getTransaction().commit();

			return q;
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
				//session.close();
			}
		}
	}

	public Object carregaEntidade(Object o ,int id ){
		session = con.configureSession(empresa);
		//session.getSessionFactory().openSession();

		Object result;
		try{	
			session.beginTransaction();
			result = session.get(o.getClass(), id);
			session.getTransaction().commit();

			return result;
		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
			}
		}
	}

	public boolean updateEntidade(Object o ){
		session = con.configureSession(empresa);
		//session.getSessionFactory().openSession();

		try{	
			session.beginTransaction();
			session.update(o);
			session.getTransaction().commit();

			return true;
		}catch(Exception e){
			session.getTransaction().rollback();
			return false;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
			}
		}
	}

	public boolean deleteEntidade(Object o ){
		session = con.configureSession(empresa);
		//session.getSessionFactory().openSession();

		try{	
			session.beginTransaction();
			session.delete(o);
			session.getTransaction().commit();

			return true;
		}catch(Exception e){
			session.getTransaction().rollback();
			return false;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
			}
		}
	}

	public List<Object[]> validaLoginUsuario(HttpServletRequest request,Usuario usu, SubUsuario sub){

		session = con.configureSession(empresa);
		//session.getSessionFactory().openSession();
		/*
		String sql = 	" SELECT SBU_CD_USUARIO, SBU_CD_SUBUSUARIO FROM SUBUSUARIO  "+
						" WHERE SBU_DS_LOGIN 			= '"+sub.getSbu_ds_login()+"' "+
						" AND SBU_SH_SUBUSUARIO 	= '"+sub.getSbu_sh_subusuario()+"' ";	
		 */



		String sql = 	" SELECT USU_CD_USUARIO, USU_DS_SUBDOMINIO FROM USUARIO WHERE USU_DS_EMAIL 			= '"+usu.getUsu_ds_email()+"' ";	

		List<Object[]> q = null;
		try{	
			session.beginTransaction();
			q = session.createSQLQuery(sql).list(); 			
			session.getTransaction().commit();

		}catch(Exception e){
			session.getTransaction().rollback();
			return null;
		}
		
		
		

		if (q != null){
			
			//carrega a empresa logada
			Object[] row = q.get(0);
			
			//carrega usuario na sessao ANTES DE MUDAR A BASE DE CONEXAO
			usu = (Usuario) carregaEntidade(usu, Integer.parseInt(row[0].toString()) );
			request.getSession().setAttribute("usuario", usu);
			
			//passa o usu_ds_dominio para MUDAR A BASE DE CONEXAO
			setVars(usu.getUsu_ds_subdominio());

			sql = 	" SELECT SBU_CD_USUARIO, SBU_CD_SUBUSUARIO FROM SUBUSUARIO  "+
					" WHERE SBU_DS_LOGIN 			= '"+sub.getSbu_ds_login()+"' "+
					" AND SBU_SH_SUBUSUARIO 	= '"+sub.getSbu_sh_subusuario()+"' ";	

			try{	
				session.beginTransaction();
				q = session.createSQLQuery(sql).list(); 			
				session.getTransaction().commit();

				return  q;
			}catch(Exception e){
				session.getTransaction().rollback();
				return null;
			}
		}else{
			return null;
		}
		
		

	}




	public boolean executaSqlGeral(String sql){

		session = con.configureSession(empresa);
		//session.getSessionFactory().openSession();

		try{	

			session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate(); 			
			session.getTransaction().commit();

			return  true;
		}catch(Exception e){
			return false;
		}finally{
			if (session != null){
				//session.getSessionFactory().close();
			}
		}



	}

}
