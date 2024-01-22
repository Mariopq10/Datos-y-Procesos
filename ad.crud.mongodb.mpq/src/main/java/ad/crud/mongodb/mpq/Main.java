package ad.crud.mongodb.mpq;

import java.util.Arrays;
import java.util.List;
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
		String connectionString = "mongodb://" + username + ":" + password + "@143.47.54.181:27017";
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
				Funciones.mostrarMenu();
				String opcion = sc.nextLine();
				String titulo;
				String banda;
				String ano;
				List<String> estilosMusicales;
				List<String> mejoresTemas;

				switch (Integer.parseInt(opcion)) {
				case 1:
					// Operación de Creación (Alta)
					System.out.println("Opción 1 seleccionada: Crear Disco");

					System.out.println("Introduce titulo del disco: ");
					titulo = sc.nextLine();

					System.out.println("Introduce nombre de banda: ");
					banda = sc.nextLine();
					System.out.println("Introduce el año: ");
					ano = sc.nextLine();

					// Necesitamos crear una lista, al llamar a la funcion Arrays.asList() hacemos
					// que la lista se castee a un Array de String
					System.out.println("Introduce el estilo musical: ");
					estilosMusicales = Arrays.asList(sc.nextLine().split((", ")));
					System.out.println("Introduce el mejor tema de la banda: ");
					mejoresTemas = Arrays.asList(sc.nextLine().split((", ")));
					Funciones.crearDisco(discos, titulo, banda, Integer.parseInt(ano), estilosMusicales, mejoresTemas);
					break;
				case 2:
					// Consultar disco por titulo.
					System.out.println(
							"Opción 2 seleccionada: Consultar Disco\nIntroduce el titulo que quieres consultar:");
					String disco = sc.nextLine();
					// Llama al método consultarDisco().
					Funciones.consultarDisco(discos, disco);
					break;
				case 3:
					// Consultamos discos del mismo estilo
					System.out.println(
							"Opción 3 seleccionada: Consultar Grupo de Discos por estilo.\nCual es el estilo que desea buscar:");
					String estilo = sc.nextLine();
					// Llama al metodo consultarGruposDiscos.
					Funciones.consultarGrupoDiscos(discos, estilo);
					break;
				case 4:
					// Operación de Actualización (Actualizar un campo)
					System.out.println(
							"Opción 4 seleccionada: Actualizar Campo de Disco.\nCual es el disco que desea modificar:");
					String tituloModificar = sc.nextLine();
					System.out.println("Cual es el campo que desea modificar y su antiguo valor?");
					String campoModificado = sc.nextLine();
					String antValor = sc.nextLine();
					System.out.println("Introduce el nuevo valor del campo " + campoModificado + " :");
					String newValor = sc.nextLine();

					// Llama al método actualizarCampoDisco()
					Funciones.actualizarCampoDisco(discos, tituloModificar, campoModificado, antValor, newValor);
					break;
				case 5:
					// Operación de Eliminación (Eliminar un disco)
					System.out.println("Opción 5 seleccionada: Eliminar Disco\nIntroduce el titulo del disco a eliminar:");
					String tituloEliminar = sc.nextLine();
					// Llama al método eliminarDisco()
					Funciones.eliminarDisco(discos, tituloEliminar);
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
