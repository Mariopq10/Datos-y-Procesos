package psp.mongoseguro.mpq;

import java.util.Scanner;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Main {

		public static void main(String[] args) {
			Scanner scanner = new Scanner(System.in);
			
	        System.out.print("Ingrese el nombre de usuario: ");
	        String username = scanner.nextLine();
	        
	        System.out.print("Ingrese la contraseña: ");
	        String password = scanner.nextLine();

	        // Construye la cadena de conexión con el nombre de usuario y la contraseña
	        String connectionString = "mongodb://" + username + ":" + password + "@143.47.54.181:27017/?tls=true&tlsInsecure=true";
			// Conéctate al servidor MongoDB
	        try (MongoClient mongoClient = MongoClients.create(
	                MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build())) {

	            // Conexión exitosa
	            System.out.println("Conexión exitosa a MongoDB");

	            // Puedes realizar operaciones en la base de datos a partir de aquí
	            MongoDatabase database = mongoClient.getDatabase("facturas");
	            System.out.println("Conectado a la base de datos: " + database.getName());

	            // Realiza operaciones en la base de datos...

	        } catch (Exception e) {
	            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
	        } finally {
	            // Cierra el scanner
	            scanner.close();
	        }
	    }

	}