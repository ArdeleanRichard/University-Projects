package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

	public static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		try {
			
			SessionFactory sessionFactoryLocal = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			return sessionFactoryLocal;
		} 
		catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
	}
	
	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		
		return sessionFactory;
	}
}
