package ad.hibernate.mpq;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

import org.hibernate.*;

public class Main {

	public static void main(String[] args) {

		// Carga del fichero de configuración
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		// Creación de la sesión entre JAVA e Hibernate
		SessionFactory sessionFactory = cfg.buildSessionFactory();
		Session session = sessionFactory.openSession();

		// Comienzo de la transacción SQL
		Transaction transaction = session.beginTransaction();

		//consultar(session);
		
		
		
		
		transaction.commit();
		session.close();
	}

	public static void consultar(Session session) {
		Scanner sc = new Scanner(System.in);

		System.out.println();
		String sql = "FROM usuarios ";
		Query<Usuarios> query = session.createQuery(sql);

		List<Usuarios> usuarios = query.list();

		for (Usuarios user : usuarios) {
			System.out.println("pepe");
			System.out.println(user.getNombrelogin());
		}

	}

}
