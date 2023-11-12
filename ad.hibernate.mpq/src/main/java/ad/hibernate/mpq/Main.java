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
		Configuration configuracion = new Configuration();
		configuracion.configure("hibernate.cfg.xml");

		// Creación de la sesión entre JAVA e Hibernate
		SessionFactory sessionFactory = configuracion.buildSessionFactory();
		Session session = sessionFactory.openSession();

		// Comienzo de la transacción SQL
		Transaction transaction = session.beginTransaction();

		//consultar(session);
		//insertarUsuario(session, null, null, null);
		
		
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
	
	public static boolean insertarUsuario(Session session, String nombreLogin, String contrasena, String nombreCompleto) {
        try {
            // Creación de un nuevo usuario
            Usuarios nuevoUsuario = new Usuarios();
            nuevoUsuario.setNombrelogin(nombreLogin);
            nuevoUsuario.setContrasena(contrasena);
            nuevoUsuario.setNombreCompleto(nombreCompleto);

            // Intenta guardar el nuevo usuario en la base de datos
            session.save(nuevoUsuario);

            return true; // Operación exitosa
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Operación fallida
        }
    }

}
