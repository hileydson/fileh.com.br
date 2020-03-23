package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

	//conexao PRINCIPAL
	//-------------------------------------------------------------------------------------
	private static final SessionFactory sessionFactory;

	static {
		try {
			Configuration cfg_PRINCIPAL = new AnnotationConfiguration();
			cfg_PRINCIPAL.configure("/hibernate.cfg.xml");
			
			sessionFactory = cfg_PRINCIPAL.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("FALHA NA CRIACAO DA SESSION FACTORY!" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	//-------------------------------------------------------------------------------------




	//conexao SERDO
	//-------------------------------------------------------------------------------------
	private static final SessionFactory sessionFactory_SERDO;

	static {
		try {



			Configuration cfg_SERDO = new AnnotationConfiguration();
			cfg_SERDO.configure("/hibernate_serdo.cfg.xml");

			sessionFactory_SERDO = cfg_SERDO.buildSessionFactory();;

		} catch (Throwable ex) {
			System.err.println("FALHA NA CRIACAO DA SESSION FACTORY!" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory_SERDO() {
		return sessionFactory_SERDO;
	}
	//-------------------------------------------------------------------------------------


	//conexao MARINHO
	//-------------------------------------------------------------------------------------
	private static final SessionFactory sessionFactory_MARINHO;

	static {
		try {



			Configuration cfg_MARINHO = new AnnotationConfiguration();
			cfg_MARINHO.configure("/hibernate_marinho.cfg.xml");

			sessionFactory_MARINHO = cfg_MARINHO.buildSessionFactory();;

		} catch (Throwable ex) {
			System.err.println("FALHA NA CRIACAO DA SESSION FACTORY!" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory_MARINHO() {
		return sessionFactory_MARINHO;
	}
	//-------------------------------------------------------------------------------------
	
	
	
	//conexao HILEYDSON
	//-------------------------------------------------------------------------------------
	private static final SessionFactory sessionFactory_HILEYDSON;

	static {
		try {



			Configuration cfg_HILEYDSON = new AnnotationConfiguration();
			cfg_HILEYDSON.configure("/hibernate_hileydson.cfg.xml");

			sessionFactory_HILEYDSON = cfg_HILEYDSON.buildSessionFactory();;

		} catch (Throwable ex) {
			System.err.println("FALHA NA CRIACAO DA SESSION FACTORY!" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory_HILEYDSON() {
		return sessionFactory_HILEYDSON;
	}
	//-------------------------------------------------------------------------------------

}
