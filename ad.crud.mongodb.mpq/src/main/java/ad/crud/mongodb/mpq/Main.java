package ad.crud.mongodb.mpq;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Ingrese el nombre de usuario: ");
		String username = sc.nextLine();

		System.out.print("Ingrese la contraseña: ");
		String password = sc.nextLine();

		// Construye la cadena de conexión con el nombre de usuario y la contraseña
		String connectionString = "mongodb://" + username + ":" + password + "@localhost:27017";
		// Conéctate al servidor MongoDB
		try (MongoClient mongoClient = MongoClients.create(
				MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build())) {

			// Conexión exitosa
			System.out.println("Conexión exitosa a MongoDB");
			System.out.println("\nListado de bases de datos\n");
			mongoClient.listDatabaseNames().forEach(System.out::println);
			// Puedes realizar operaciones en la base de datos a partir de aquí
			
			MongoDatabase database = mongoClient.getDatabase("musica");
			MongoCollection<Document> discos = database.getCollection("discos");
			database.getCollection("musica");
			System.out.println("Conectado a la base de datos: " + database.getName());

			boolean con = false;
			while (true) {
				System.out.println("\n*** Menú CRUD para MongoDB ***");
				System.out.println("1. Crear Disco");
				System.out.println("2. Consultar Disco");
				System.out.println("3. Consultar Grupo de Discos");
				System.out.println("4. Actualizar Campo de Disco");
				System.out.println("5. Eliminar Disco");
				System.out.println("6. Operación Adicional 1");
				System.out.println("7. Operación Adicional 2");
				System.out.println("0. Salir");
				System.out.print("Seleccione una opción: ");

				int opcion = sc.nextInt();
				String titulo ;
				String banda ;
				int ano;
				String estilosMusicales;
				String mejoresTemas;
				
				switch (opcion) {
				case 1:
					// Operación de Creación (Alta)
					System.out.println("Opción 1 seleccionada: Crear Disco");
					System.out.println("Introduce titulo del disco: ");
					titulo = sc.nextLine();
					System.out.println("Introduce nombre de banda: ");
					banda = sc.nextLine();
					System.out.println("Introduce el año: ");
					ano = sc.nextInt();
					System.out.println("Introduce el estilo musical: ");
					estilosMusicales = sc.nextLine();
					System.out.println("Introduce el mejor tema de la banda: ");
					mejoresTemas = sc.nextLine();
					Funciones.crearDisco(discos, titulo, banda, ano,estilosMusicales , mejoresTemas);
					break;
				case 2:
					// Operación de Lectura (Consulta de un solo disco)
					System.out.println("Opción 2 seleccionada: Consultar Disco");
					// Llama al método consultarDisco()
					break;
				case 3:
					// Operación de Lectura (Consulta de un grupo de discos)
					System.out.println("Opción 3 seleccionada: Consultar Grupo de Discos");
					// Llama al método consultarGrupoDiscos()
					break;
				case 4:
					// Operación de Actualización (Actualizar un campo)
					System.out.println("Opción 4 seleccionada: Actualizar Campo de Disco");
					// Llama al método actualizarCampoDisco()
					break;
				case 5:
					// Operación de Eliminación (Eliminar un disco)
					System.out.println("Opción 5 seleccionada: Eliminar Disco");
					// Llama al método eliminarDisco()
					break;
				case 6:
					// Operación Adicional 1
					System.out.println("Opción 6 seleccionada: Operación Adicional 1");
					// Llama a la primera operación adicional
					break;
				case 7:
					// Operación Adicional 2
					System.out.println("Opción 7 seleccionada: Operación Adicional 2");
					// Llama a la segunda operación adicional
					break;
				case 0:
					// Salir del programa
					System.out.println("¡Hasta luego!");
					System.exit(0);
				default:
					// Opción no válida
					System.out.println("Opción no válida. Por favor, elija una opción válida.");
				}
			}

		} catch (Exception e) {
			System.err.println("Error al conectar a MongoDB: " + e.getMessage());
		} finally {
			// Cierra el scanner
			sc.close();
		}
	}

}
