package psp.serv.tickets.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

public class Server {

	private int puerto;
	private int tickets;
	private static final String MONGO_URI = "mongodb://localhost:27017";
	private static final String MONGO_DB_NAME = "tickets";
	private static final String MONGO_COLLECTION_NAME = "tickets";
	private Object lock = new Object();

	public Server(int puerto, int tickets) {
		this.puerto = puerto;
		this.tickets = tickets;
	}

	public void iniciarServidor() {
		try {
			// Establecer el socket del servidor con el puerto especificado
			ServerSocket serverSocket = new ServerSocket(this.puerto);

			try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
				MongoDatabase database = mongoClient.getDatabase(MONGO_DB_NAME);
				MongoCollection<Document> collection = database.getCollection(MONGO_COLLECTION_NAME);

				while (true) {
					// Esperar a que un cliente se conecte
					Socket socket = serverSocket.accept();

					// Procesar la conexión en un hilo separado para manejar múltiples clientes
					new Thread(() -> procesarConexion(socket, collection)).start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void procesarConexion(Socket socket, MongoCollection<Document> collection) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			String autorizacion = reader.readLine();
			int ticketId = obtenerIdUnicoTicket(collection);
			if (autorizacion != null && autorizacion.startsWith("TK")) {
				synchronized (lock) {
					if (this.tickets > 0) {
						collection.updateOne(Filters.eq("id", ticketId), Updates.set("nombre", autorizacion));
						collection.updateOne(Filters.eq("id", ticketId), Updates.set("estado", "Cerrado"));
						writer.write("Ticket concedido, quedan " + tickets + " restantes.\n");
						writer.flush();
						this.tickets--;
					} else {
						writer.write("No disponemos de tickets disponibles.\n");
						writer.flush();
					}
				}
			}

			reader.close();
			writer.close();
			socket.close();
		} catch (SocketException e) {
			// Manejar específicamente la excepción de conexión reseteada
			System.err.println("Conexión reseteada por el cliente.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al procesar la conexion" + e.getMessage());

		}

	}

	private int obtenerIdUnicoTicket(MongoCollection<Document> collection) {
	    Document result = collection.findOneAndUpdate(
	            Filters.and(
	                    Filters.eq("estado", "Abierto"),
	                    Filters.eq("en_proceso", false)
	            ),
	            Updates.combine(
	                    Updates.set("en_proceso", true),
	                    Updates.set("estado", "Cerrado")
	            ),
	            new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
	    );

	    if (result != null) {
	        return result.getInteger("id");
	    } else {
	        // Manejar el caso cuando no hay más tickets disponibles
	        System.err.println("No hay más tickets disponibles.");
	        throw new RuntimeException("No hay más tickets disponibles.");
	    }
	}
}
