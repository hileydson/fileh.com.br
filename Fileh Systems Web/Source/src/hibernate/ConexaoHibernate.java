package hibernate;


import hibernate.HibernateSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public class ConexaoHibernate {
	
	private Session session;
	
	public ConexaoHibernate(String empresa){
		if((session == null) || (session.isConnected() == false)){
			session = configureSession( empresa);
		}
	}
	
	public Session configureSession(String empresa) throws HibernateException {	 
		
		if (empresa == null){
			return HibernateSession.getSessionFactory().getCurrentSession();
		}
		
		
		
		if(empresa.equalsIgnoreCase("serdo")){
			return HibernateSession.getSessionFactory_SERDO().getCurrentSession();
		}
		
		if(empresa.equalsIgnoreCase("marinho")){
			return HibernateSession.getSessionFactory_MARINHO().getCurrentSession();
		}
		
		if(empresa.equalsIgnoreCase("hileydson")){
			return HibernateSession.getSessionFactory_HILEYDSON().getCurrentSession();
		}	
		
		
		return null;
	    
	}
	
}
