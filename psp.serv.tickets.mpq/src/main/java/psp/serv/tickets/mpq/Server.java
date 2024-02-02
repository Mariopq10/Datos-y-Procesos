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
	private static final String MONGO_DB_NAME = "ticket";
	private static final String MONGO_COLLECTION_NAME = "ticket";
	private Object lock = new Object();

	public Server(int puerto, int tickets) {
		this.puerto = puerto;
		this.tickets = tickets;
	}

	/**
	 * Funcion iniciarServidor() que realiza la conexion al servidor de mongoDB con los valores que escojamos en las variables estaticas que tenemos arriba.
	 */
	public void iniciarServidor() {
		try {
			// Establecemos el socket del servidor con el puerto especificado, en este caso nos conectamos a la base de datos local.
			ServerSocket serverSocket = new ServerSocket(this.puerto);

			try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
				MongoDatabase database = mongoClient.getDatabase(MONGO_DB_NAME);
				MongoCollection<Document> collection = database.getCollection(MONGO_COLLECTION_NAME);

				while (true) {
					// Esperar a que un cliente se conecte a nuestro servidor.
					Socket socket = serverSocket.accept();

					// Procesar la conexión en un hilo separado para manejar múltiples clientes simultaneos.
					new Thread(() -> procesarConexion(socket, collection)).start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 
	 * @param socket Recibe el socket de conexion al servidor.
	 * @param collection Recibe un MongoCollection<Document> que es la coleccion de la base de datos ticket.
	 * 
	 * Funcion procesarConexion que recibe un Socket de conexion y una coleccion, lee la autorizacion que recibe del cliente,
	 * Luego realiza las comprobaciones, si la cadena de string que recibe empieza por TK comienza el proceso.
	 * Se sincroniza el proceso, si el server tiene tickets disponibles, coge un ticket con un id que recoge de la funcion
	 * obtenerIdUnicoTicket() y modifica los valores de sus campos.
	 * En caso de no  tener tickets disponibles, muestra un mensaje que muestra que no quedan tickets.
	 */
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

	/**
	 * 
	 * @param collection La coleccion que recibe el servidor de la base de datos, en este caso ticket.
	 * @return Devuelve un int con el valor del id del ticket que va a usar luego la funcion procesarConexion y realizará acciones con ese ticket.
	 * Funcion obtenerIdUnicoTicket() que recoge un id de ticket que va a usar luego la funcion procesarConexion y realizará acciones con esa id del ticket,
	 * Modificando los valores dentro de la base de datos.
	 * 
	 */
	private int obtenerIdUnicoTicket(MongoCollection<Document> collection) {
	    Document result = collection.findOneAndUpdate(
	            Filters.and(
	                    Filters.eq("estado", "Abierto")
	            ),
	            Updates.combine(
	                    Updates.set("estado", "Cerrado")
	            ),
	            new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
	    );
	    //Si el resultado no es nulo, recoge ese valor.
	    if (result != null) {
	        return result.getInteger("id");
	    //En caso contrario muestra un mensaje en el cual muestra que no hay tickets disponibles.
	    } else {
	        // Manejar el caso cuando no hay más tickets disponibles
	        System.err.println("No hay más tickets disponibles.");
	        throw new RuntimeException("No hay más tickets disponibles.");
	    }
	    
	}
}
