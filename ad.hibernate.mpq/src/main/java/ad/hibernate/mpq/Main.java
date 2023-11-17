package ad.hibernate.mpq;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;
import org.hibernate.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Carga del fichero de configuración
		Configuration configuracion = new Configuration();
		configuracion.configure("hibernate.cfg.xml");

		// Creación de la sesión entre JAVA e Hibernate
		SessionFactory sessionFactory = configuracion.buildSessionFactory();
		Session session = sessionFactory.openSession();

		// Comienzo de la transacción SQL
		// Transaction transaction = session.beginTransaction();

		// consultar(session);
		// insertarUsuario(session, null, null, null);
		byte opcion =0;
		boolean salir = false;
		do {
			System.out.println(
					"Que quieres hacer?\n1-Insertar dato\n2-Buscar dato\n3-Modificar dato\n4-Borrar dato\n5-Salir");
			
			opcion = sc.nextByte();
			switch (opcion) {
            case 1:
                insertar(session);
                break;
                
            case 2:
                Usuarios usuarios = consultar(session,Usuarios.class);
                if((Usuarios)usuarios==null){
                System.out.println("No se encuentra usuario");	
                }
                break;
                
            case 3:
            	update(session);
              
                break;
                
            case 4:
               delete(session);
                
                break;
                
            case 5:
                System.out.println("Has salido del programa");
                salir = true; // Sale del bucle y termina el programa
                break;
                
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
		} while (!salir);

		session.close();
	}

	public static <T> T consultar(Session s, Class<T> clase) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el nombrelogin para la busqueda: ");
		String nombreLogin = sc.nextLine();

		Usuarios ret = null;

		String hql = "FROM " + clase.getSimpleName();
		Query query = s.createQuery(hql);

		List<Usuarios> usuarios = query.list();

		for (Usuarios cliente : usuarios) {

			if (cliente.getNombreLogin().equals(nombreLogin)) {
				System.out.println("NombreLogin: " + cliente.getNombreLogin() + "\nPassword: " + cliente.getContrasena()
						+ "\nNombre Completo: " + cliente.getNombreCompleto());

				ret = cliente;

			}
		}
		return (T) ret;

	}

	public static void insertar(Session session) {

		session.beginTransaction();

		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce el nombrelogin, password y nombrecompleto");
		String nombreLogin = sc.nextLine();
		String contrasena = sc.nextLine();
		String nombreCompleto = sc.nextLine();

		Usuarios usuarios = new Usuarios();
		usuarios.setNombreLogin(nombreLogin);
		usuarios.setContrasena(contrasena);
		usuarios.setNombreCompleto(nombreCompleto);

		session.save(usuarios);
		session.getTransaction().commit();

	}

	public static void update(Session session) {

		session.beginTransaction();

		Scanner sc = new Scanner(System.in);

		Usuarios aux = consultar(session, Usuarios.class);
		if (aux != null) {
			System.out.println("Introduce el nuevo nombre de login");
			String nombreLoginNuevo = sc.nextLine();

			Usuarios usuarios = session.get(Usuarios.class, aux.getCoduser());

			usuarios.setNombreLogin(nombreLoginNuevo);

			session.getTransaction().commit();
		} else {

			System.out.println("No se ha podido modificar ningún valor");
		}

	}

	public static void delete(Session session) {

		session.beginTransaction();

		Object usuarios = consultar(session, Usuarios.class);

		if (usuarios != null) {

			session.delete(usuarios);

		} else {

			System.out.println("No se ha podido borrar - no se ha encontrado ninguna coincidencia");
		}

		session.getTransaction().commit();

	}

}
