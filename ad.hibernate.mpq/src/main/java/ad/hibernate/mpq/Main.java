package ad.hibernate.mpq;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

public class Main {

	public static void main(String[] args) {
		/*
		 * Configuration configuration = new
		 * Configuration().configure("/hibernate.cfg.xml");
		 * 
		 * SessionFactory factory = HibernateUtil.getSessionFactory(); Session session =
		 * factory.openSession(); Transaction transaction = session.beginTransaction();
		 */

		Session sesion = HibernateUtil.getSessionFactory().openSession();

		String sql = "SELECT VERSION()";
		String resultado = (String) sesion.createNativeQuery(sql).getSingleResult();
		System.out.println("La versión que estás usando es: " + resultado);

		HibernateUtil.getSessionFactory().close();

	}

}
