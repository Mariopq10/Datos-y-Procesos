package mongoDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoConexion {

	private static Scanner input = new Scanner(System.in);
	
	// Configuración de constantes para la conexión a MongoDB
    private static final String DATABASE_NAME = "FacturasClientes";
    private static final String COLLECTION_NAME = "FacturasClientes";
    private static final String HOST = "143.47.63.59"; 
    private static final int PORT = 27017; 
    private static final String USERNAME = "zeba";
    private static final String PASSWORD = "zeba"; 
    
    // Creación de credenciales para la conexión
    private static MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE_NAME, PASSWORD.toCharArray());
    // Configuración del servidor y del cliente
    private static ServerAddress serverAddress = new ServerAddress(HOST, PORT);
    private static MongoClientSettings settings = MongoClientSettings.builder()
            .credential(credential)
            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(serverAddress)))
            .build();
    // Creación del cliente MongoDB
    private static MongoClient mongoClient = MongoClients.create(settings);
    // Acceso a la base de datos y la colección
    private static MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
    private static MongoCollection<Document> collectionFacturasClientes = database.getCollection(COLLECTION_NAME);

	public static void main(String[] args) {

		// mongoClient.listDatabaseNames().forEach(System.out::println); // Listar todas las bases de datos

		// Opciones del menu
		int opcion = -1;
		do {
			try {
				opcion = menu();
				switch (opcion) {
				// Consultar todos los clientes
				case 1:
					mostrarClientes();
					break;
				// Consultar un cliente
				case 2:
					mostrarUnCliente();
					break;
				// Cantidad de facturas de un cliente
				case 3:
					cantidadFacturasCliente();					
					break;
				// Importe total de una factura
				case 4:
					importeTotalFactura();
					break;
				// Total de ventas a un cliente
				case 5:
					totalVentasCliente();
					break;
				// Productos más vendidos
				case 6:
					productosMasVendidos();
					break;
				case 0:
					System.out.println("Ha salido del programa.");
					break;
				default:
					System.err.println("Opcion incorrecta, seleccione una opcion [1 al 6, 0 para salir].\n");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\n");
			}
		} while (opcion != 0);

	}

	// Funcion menu()
	public static int menu() {
		Scanner input = new Scanner(System.in);
		int opcion = -1;
		System.out.println("[Agregación con MongoDB]");
		System.out.println("1. Consultar todos los clientes"
						+ "\n2. Consultar un cliente" 
						+ "\n         [Operaciones de agregación]:" 
						+ "\n3. Cantidad de facturas de un cliente"
						+ "\n4. Importe total de una factura"
						+ "\n5. Total de ventas a un cliente"
						+ "\n6. Productos más vendidos"
						+ "\n0. Salir");
		try {
			opcion = input.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("Debe ingresar un número.");
		}
		
		return opcion;
	}

	// Método para mostrar todos los clientes
	public static void mostrarClientes() {
	    System.out.println("[Mostrar todos los Clientes]");

	    FindIterable<Document> cursor = collectionFacturasClientes.find();

	    try (MongoCursor<Document> cursorIterator = cursor.iterator()) {
	        Set<String> clientesMostrados = new HashSet<>(); // Para rastrear los clientes ya mostrados
	        while (cursorIterator.hasNext()) {
	            Document document = cursorIterator.next();	            
	            mostrarInformacionClientes(document, clientesMostrados);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	// Método para mostrar la información de los clientes en un documento
	private static void mostrarInformacionClientes(Document document, Set<String> clientesMostrados) {
	    if (document.containsKey("clientes") && document.get("clientes") instanceof List) {
	        List<Document> clientes = (List<Document>) document.get("clientes");

	        for (Document cliente : clientes) {
	            String idCliente = cliente.getString("id_cliente");
	            // Mostrar información del cliente solo si aún no ha sido mostrado
	            if (!clientesMostrados.contains(idCliente)) {
	                System.out.println("ID Cliente: " + idCliente);
	                System.out.println("Nombre: " + cliente.getString("nombre"));
	                System.out.println(); // Separador entre clientes
	                clientesMostrados.add(idCliente); // Registrar que el cliente ha sido mostrado
	            }
	        }
	    } else {
	        System.out.println("Estructura del documento no válida, no se encontró la información de los clientes.");
	    }
	}
	
	// Método para mostrar un cliente específico
	public static void mostrarUnCliente() {
	    System.out.println("[Mostrar un Cliente]");
	    System.out.println("Ingrese el id del cliente que desea mostrar (formato: 'C1001'): ");
	    String idCliente = input.nextLine();

	    // Filtrar el cliente con el ID proporcionado
	    Document clientFilter = new Document("clientes.id_cliente", idCliente);
	    Document projection = new Document("clientes.$", 1);

	    FindIterable<Document> cursor = collectionFacturasClientes.find(clientFilter).projection(projection);

	    try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
	        while (cursorIterator.hasNext()) {
	            Document document = cursorIterator.next();
	            if (document.containsKey("clientes") && document.get("clientes") instanceof List) {
	                List<Document> clientes = (List<Document>) document.get("clientes");
	                for (Document cliente : clientes) {
	                    System.out.println("ID Cliente: " + cliente.getString("id_cliente"));
	                    System.out.println("Nombre: " + cliente.getString("nombre"));
	                    System.out.println("Dirección: " + cliente.getString("direccion"));
	                    System.out.println("Teléfono: " + cliente.getString("telefono"));
	                }
	            }
	        }
	        System.out.println();
	    } catch (Exception e) {
	        System.out.println("Error al mostrar el cliente: " + e.getMessage());
	    }
	}

	// Método para mostrar la cantidad de facturas de un cliente específico
	public static void cantidadFacturasCliente() {
	    System.out.println("[Cantidad de Facturas de un Cliente]");
	    System.out.println("Ingrese el ID del Cliente (formato: 'C1001'): ");
	    String idCliente = input.nextLine();

	    // Agregación para contar las facturas del cliente específico
	    List<Bson> pipeline = Arrays.asList(
	            Aggregates.match(Filters.eq("facturas.id_cliente", idCliente)),
	            Aggregates.unwind("$facturas"),
	            Aggregates.match(Filters.eq("facturas.id_cliente", idCliente)),
	            Aggregates.group("$facturas.id_cliente", Accumulators.sum("numFacturas", 1))
	    );

	    // Ejecutar la agregación
	    Document result = collectionFacturasClientes.aggregate(pipeline).first();

	    if (result != null && result.containsKey("numFacturas")) {
	        int numFacturas = result.getInteger("numFacturas");
	        System.out.println("El cliente '" + idCliente + "' tiene " + numFacturas + " facturas.\n");
	    } else {
	        System.out.println("No se encontraron facturas para el cliente con ID: " + idCliente);
	    }
	}

	// Método para obtener el importe total de una factura específica
	public static void importeTotalFactura() {
	    System.out.println("[Importe Total de una Factura]");
	    System.out.println("Ingrese el ID de la Factura (formato: 'F001'): ");
	    String idFactura = input.nextLine();

	    // Agregación para calcular el importe total de la factura específica
	    List<Bson> pipeline = Arrays.asList(
	            Aggregates.match(Filters.eq("facturas.id_factura", idFactura)),
	            Aggregates.unwind("$facturas"),
	            Aggregates.match(Filters.eq("facturas.id_factura", idFactura)),
	            Aggregates.unwind("$facturas.lineas_factura"),
	            Aggregates.group(null, Accumulators.sum("importeTotal", new Document("$sum",
	                    Arrays.asList(
	                            new Document("$toDouble", "$facturas.lineas_factura.precio_unitario"),
	                            new Document("$toDouble", "$facturas.lineas_factura.cantidad")
	                    )
	            ))));

	    // Ejecutar la agregación
	    Document result = collectionFacturasClientes.aggregate(pipeline).first();

	    if (result != null && result.containsKey("importeTotal")) {
	        double importeTotal = result.getDouble("importeTotal");
	        System.out.println("El importe total de la factura " + idFactura + " es: $" + importeTotal + "\n");
	    } else {
	        System.out.println("No se encontró la factura con ID: " + idFactura);
	    }
	}

	// Método para mostrar el total de ventas a un cliente específico
	public static void totalVentasCliente() {
	    System.out.println("[Total de Ventas a un Cliente]");
	    System.out.println("Ingrese el ID del Cliente (formato: 'C1001'): ");
	    String idCliente = input.nextLine();

	    // Agregación para calcular el total de ventas al cliente específico
	    List<Bson> pipeline = Arrays.asList(
	            Aggregates.match(Filters.eq("clientes.id_cliente", idCliente)),
	            Aggregates.unwind("$facturas"),
	            Aggregates.match(Filters.eq("facturas.id_cliente", idCliente)),
	            Aggregates.unwind("$facturas.lineas_factura"),
	            Aggregates.group(null, Accumulators.sum("totalVentas", new Document("$sum",
	                    Arrays.asList(
	                            new Document("$toDouble", "$facturas.lineas_factura.precio_unitario"),
	                            new Document("$toDouble", "$facturas.lineas_factura.cantidad")
	                    )
	            ))));

	    // Ejecutar la agregación
	    Document result = collectionFacturasClientes.aggregate(pipeline).first();

	    if (result != null && result.containsKey("totalVentas")) {
	        double totalVentas = result.getDouble("totalVentas");
	        System.out.println("El total de ventas al cliente " + idCliente + " es: $" + totalVentas + "\n");
	    } else {
	        System.out.println("No se encontró el cliente con ID: " + idCliente);
	    }
	}

	// Método para mostrar los productos más vendidos
	public static void productosMasVendidos() {
	    System.out.println("[Productos Más Vendidos]");

	    // Agregación para calcular los productos más vendidos
	    List<Bson> pipeline = Arrays.asList(
	            Aggregates.unwind("$facturas"),
	            Aggregates.unwind("$facturas.lineas_factura"),
	            Aggregates.group("$facturas.lineas_factura.articulo",
	                    Accumulators.sum("totalVendido", "$facturas.lineas_factura.cantidad")),
	            Aggregates.sort(Sorts.descending("totalVendido")),
	            Aggregates.limit(3) // sólo muestra los 3 más vendidos
	    );

	    // Ejecutar la agregación
	    AggregateIterable<Document> results = collectionFacturasClientes.aggregate(pipeline);

	    // Mostrar los resultados
	    for (Document result : results) {
	        String articulo = result.getString("_id");
	        int totalVendido = result.getInteger("totalVendido");
	        System.out.println("Producto: " + articulo + " - Cantidad Total Vendida: " + totalVendido);
	    }
	    System.out.println();
	}
}