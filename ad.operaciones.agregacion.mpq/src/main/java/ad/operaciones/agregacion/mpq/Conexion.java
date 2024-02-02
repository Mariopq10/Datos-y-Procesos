package ad.operaciones.agregacion.mpq;

import java.util.Arrays;
import java.util.HashSet;
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
import com.mongodb.client.model.Sorts;

public class Conexion {

	private static final String DATABASE_NAME = "facturas";
	private static final String COLLECTION_NAME = "facturas";
	private static final String HOST = "143.47.54.181";
	private static final int PORT = 27017;
	private static final String USERNAME = "mariofacturas";
	private static final String PASSWORD = "mariofacturas";

	// Creación de credenciales para la conexión
	private static MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE_NAME,
			PASSWORD.toCharArray());
	// Configuración del servidor y del cliente
	private static ServerAddress serverAddress = new ServerAddress(HOST, PORT);
	private static MongoClientSettings settings = MongoClientSettings.builder().credential(credential)
			.applyToClusterSettings(builder -> builder.hosts(Arrays.asList(serverAddress))).build();
	// Creación del cliente MongoDB
	private static MongoClient mongoClient = MongoClients.create(settings);
	// Acceso a la base de datos y la colección
	private static MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
	private static MongoCollection<Document> collectionFacturasClientes = database.getCollection(COLLECTION_NAME);

	static Scanner sc = new Scanner(System.in);

	// Método para mostrar todos los clientes
	public static void mostrarFacturas() {
		System.out.println("Mostramos todas las faturas");

		FindIterable<Document> cursor = collectionFacturasClientes.find();

		try (MongoCursor<Document> cursorIterator = cursor.iterator()) {
			while (cursorIterator.hasNext()) {
				Document document = cursorIterator.next();
				mostrarInformacionFactura(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para mostrar la información de los clientes en un documento
	private static void mostrarInformacionFactura(Document document) {
		String numeroFactura = document.getString("numeroFactura");
		String fecha = document.getString("fecha");
		String cliente = document.getString("cliente");

		System.out.println("Número de Factura: " + numeroFactura);
		System.out.println("Fecha: " + fecha);
		System.out.println("Cliente: " + cliente);

		if (document.containsKey("lineasFactura") && document.get("lineasFactura") instanceof List) {
			List<Document> lineasFactura = (List<Document>) document.get("lineasFactura");

			for (Document linea : lineasFactura) {
				String articulo = linea.getString("articulo");
				int precioUnitario = linea.getInteger("precioUnitario");
				int cantidad = linea.getInteger("cantidad");

				System.out.println("  Artículo: " + articulo);
				System.out.println("  Precio Unitario: " + precioUnitario);
				System.out.println("  Cantidad: " + cantidad);
				System.out.println(); // Agregar espacio entre las líneas de factura
			}
		} else {
			System.out.println(
					"Estructura del documento no válida, no se encontró la información de las líneas de factura.");
		}
	}

	// Método para mostrar un cliente específico
	public static void mostrarUnaFactura() {
		System.out.println("Mostrar información de una Factura\n");
		System.out.println("Ingrese el número de factura que desea mostrar (formato: '001'): ");
		String numeroFactura = sc.nextLine();

		// Filtrar la factura con el número proporcionado
		Document facturaFilter = new Document("numeroFactura", numeroFactura);

		FindIterable<Document> cursor = collectionFacturasClientes.find(facturaFilter);

		try (final MongoCursor<Document> cursorIterator = cursor.iterator()) {
			while (cursorIterator.hasNext()) {
				Document document = cursorIterator.next();

				if (document.containsKey("lineasFactura") && document.get("lineasFactura") instanceof List) {
					List<Document> lineasFactura = (List<Document>) document.get("lineasFactura");

					System.out.println("Cliente: " + document.getString("cliente"));
					System.out.println("Numero de factura: " + document.getString("numeroFactura"));
					System.out.println("Fecha: " + document.getString("fecha"));

					System.out.println("Lineas de factura:");

					for (Document linea : lineasFactura) {
						System.out.println("  Articulo: " + linea.getString("articulo"));
						System.out.println("  Precio Unitario: " + linea.getInteger("precioUnitario"));
						System.out.println("  Cantidad: " + linea.getInteger("cantidad"));
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error al mostrar la factura: " + e.getMessage());
		}
	}

	// OPERACIONES DE AGREGACION.

	// Método para mostrar la cantidad de facturas de un cliente específico
	public static void cantidadFacturasCliente() {
		System.out.println("Cantidad de Facturas asociadas a un Cliente\n");
		System.out.println("Ingrese el nombre del cliente (formato: 'Cliente A'): ");
		String nombreCliente = sc.nextLine();

		// Agregación para contar las facturas del cliente específico
		List<Bson> pipeline = Arrays.asList(Aggregates.match(Filters.eq("cliente", nombreCliente)),
				Aggregates.unwind("$facturas"), Aggregates.match(Filters.eq("facturas.cliente", nombreCliente)),
				Aggregates.group("$facturas.cliente", Accumulators.sum("numeroFactura", 1)));

		// Ejecutar la agregación
		Document result = collectionFacturasClientes.aggregate(pipeline).first();

		if (result != null && result.containsKey("numeroFactura")) {
			int numFacturas = result.getInteger("numeroFactura");
			System.out.println("El cliente '" + nombreCliente + "' tiene " + numFacturas + " facturas.\n");
		} else {
			System.out.println("No se encontraron facturas para el cliente con nombre: " + nombreCliente);
		}
	}

	// Método para obtener el importe total de una factura específica
	public static void importeTotalFactura() {
		System.out.println("Importe Total de una Factura\n");
		System.out.println("Ingrese el número de la Factura (formato: '001'): ");
		String numeroFactura = sc.nextLine();

		// Agregación para calcular el importe total de la factura específica
		List<Bson> pipeline = Arrays.asList(Aggregates.match(Filters.eq("numeroFactura", numeroFactura)),
				Aggregates.unwind("$lineasFactura"),
				Aggregates.group(null,
						Accumulators.sum("importeTotal", new Document("$sum", Arrays.asList(new Document("$multiply",
								Arrays.asList("$lineasFactura.precioUnitario", "$lineasFactura.cantidad")))))));

		// Ejecutar la agregación
		Document result = collectionFacturasClientes.aggregate(pipeline).first();

		if (result != null && result.containsKey("importeTotal")) {
			int importeTotal = result.getInteger("importeTotal");
			System.out.println("\tEl importe total de la factura numero: " + numeroFactura + " es de un total de: "
					+ importeTotal + "€\n");
		} else {
			System.out.println("No existe una factura con numero: " + numeroFactura);
		}
	}

	// Método para mostrar el total de ventas a un cliente específico
	public static void totalVentasCliente() {
		System.out.println("[Total de Ventas a un Cliente]");
		System.out.println("Ingrese el ID del Cliente (formato: 'C1001'): ");
		String idCliente = sc.nextLine();

		// Agregación para calcular el total de ventas al cliente específico
		List<Bson> pipeline = Arrays
				.asList(Aggregates.match(Filters.eq("clientes.id_cliente", idCliente)), Aggregates.unwind("$facturas"),
						Aggregates.match(Filters.eq("facturas.id_cliente", idCliente)),
						Aggregates.unwind("$facturas.lineas_factura"),
						Aggregates.group(null,
								Accumulators.sum("totalVentas",
										new Document("$sum", Arrays.asList(
												new Document("$toDouble", "$facturas.lineas_factura.precio_unitario"),
												new Document("$toDouble", "$facturas.lineas_factura.cantidad"))))));

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
		List<Bson> pipeline = Arrays.asList(Aggregates.unwind("$facturas"),
				Aggregates.unwind("$facturas.lineas_factura"),
				Aggregates.group("$facturas.lineas_factura.articulo",
						Accumulators.sum("totalVendido", "$facturas.lineas_factura.cantidad")),
				Aggregates.sort(Sorts.descending("totalVendido")), Aggregates.limit(3) // sólo muestra los 3 más
																						// vendidos
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
