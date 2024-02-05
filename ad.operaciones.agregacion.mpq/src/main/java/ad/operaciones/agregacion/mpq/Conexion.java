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

	// Creacion de credenciales para la conexion
	private static MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE_NAME,
			PASSWORD.toCharArray());
	// Configuracion del servidor y del cliente
	private static ServerAddress serverAddress = new ServerAddress(HOST, PORT);
	private static MongoClientSettings settings = MongoClientSettings.builder().credential(credential)
			.applyToClusterSettings(builder -> builder.hosts(Arrays.asList(serverAddress))).build();
	// Creacion del cliente MongoDB
	private static MongoClient mongoClient = MongoClients.create(settings);
	// Acceso a la base de datos y la coleccion
	private static MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
	private static MongoCollection<Document> collectionFacturasClientes = database.getCollection(COLLECTION_NAME);

	static Scanner sc = new Scanner(System.in);

	// Funcion para mostrar todas las facturas.
	public static void mostrarFacturas() {
		System.out.println("\tMostramos todas las faturas");

		FindIterable<Document> cursor = collectionFacturasClientes.find();

		try (MongoCursor<Document> cursorIterator = cursor.iterator()) {
			while (cursorIterator.hasNext()) {
				Document document = cursorIterator.next();
				//Aqui llamamos a la funcion para mostrar los datos de cada factura.
				mostrarInformacionFactura(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcion que muestra la informacion de las facturas.
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

				System.out.println("  Artículo: " + articulo + " - Precio Unitario: " + precioUnitario + " - Cantidad: "
						+ cantidad + "\n");

			}
		} else {
			System.out.println(
					"Estructura del documento no válida, no se encontró la información de las líneas de factura.");
		}
	}

	// Funcion que muestra los datos de una factura.
	public static void mostrarUnaFactura() {
		System.out.println("\tMostrar información de una Factura\n");
		System.out.println("\tIngrese el número de factura que desea mostrar (formato: '001'): ");
		String numeroFactura = sc.nextLine();

		// Filtrar la factura con el numero proporcionado
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
						System.out.println("  Artículo: " + linea.getString("articulo") + " - Precio Unitario: "
								+ linea.getInteger("precioUnitario") + " - Cantidad: " + linea.getInteger("cantidad"));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error al mostrar la factura: " + e.getMessage());
		}
	}

	// OPERACIONES DE AGREGACION.

	// Funcion que muestra la cantidad de facturas de un cliente.
	public static void cantidadFacturasCliente() {
		System.out.println("\tCantidad de Facturas asociadas a un Cliente\n");
		System.out.println("\tIngrese el nombre del cliente (formato: 'Cliente A'): ");
		String nombreCliente = sc.nextLine();

		// Agregacion para contar las facturas del cliente.
		List<Bson> pipeline = Arrays.asList(Aggregates.match(Filters.eq("cliente", nombreCliente)),
				Aggregates.group("$cliente", Accumulators.sum("numeroFactura", 1)));

		// Ejecutar la agregación
		Document result = collectionFacturasClientes.aggregate(pipeline).first();

		if (result != null && result.containsKey("numeroFactura")) {
			int numFacturas = result.getInteger("numeroFactura");
			System.out.println("El cliente '" + nombreCliente + "' tiene " + numFacturas + " facturas.\n");
		} else {
			System.out.println("No se encontraron facturas para el cliente con nombre: " + nombreCliente);
		}
	}

	// Funcion que obtiene el importe total de una factura especifica.
	public static void importeTotalFactura() {
		System.out.println("\tImporte Total de una Factura\n");
		System.out.println("\tIngrese el número de la Factura (formato: '001'): ");
		String numeroFactura = sc.nextLine();

		// Agregación para calcular el importe total de la factura especifica
		List<Bson> pipeline = Arrays.asList(Aggregates.match(Filters.eq("numeroFactura", numeroFactura)),
				Aggregates.unwind("$lineasFactura"),
				Aggregates.group(null,
						Accumulators.sum("importeTotal", new Document("$sum", Arrays.asList(new Document("$multiply",
								Arrays.asList("$lineasFactura.precioUnitario", "$lineasFactura.cantidad")))))));

		// Ejecutar la agregacion
		Document result = collectionFacturasClientes.aggregate(pipeline).first();

		if (result != null && result.containsKey("importeTotal")) {
			int importeTotal = result.getInteger("importeTotal");
			System.out.println("\tEl importe total de la factura numero: " + numeroFactura + " es de un total de: "
					+ importeTotal + "€\n");
		} else {
			System.out.println("No existe una factura con numero: " + numeroFactura);
		}
	}

	// Funcion que muestra el total de ventas asociadas a un cliente.
	public static void totalVentasCliente() {
		System.out.println("\tTotal de ventas asignadas a un Cliente\n");
		System.out.println("\tIngrese el nombre del Cliente (formato: 'Cliente A'): ");
		String nombreCliente = sc.nextLine();

		// Agregacion para calcular el total de ventas de un cliente especificado.
		List<Bson> pipeline = Arrays.asList(Aggregates.match(Filters.eq("cliente", nombreCliente)),
				Aggregates.unwind("$lineasFactura"),
				Aggregates.group(null,
						Accumulators.sum("totalVentas",
								new Document("$sum",
										Arrays.asList(
												new Document("$multiply",
														Arrays.asList("$lineasFactura.precioUnitario",
																"$lineasFactura.cantidad")),
												new Document("$toInt", "$lineasFactura.cantidad"))))));

		// Si queremos coger decimales de esta operacion, debemos cambiar el "$toInt" a
		// "$toDouble"
		// Sirve tanto para esta funcion como para cualquier otra, tambien tenemos que
		// cambiar el tipo de dato que muestra por consola
		// En la variable "totalVentas".

		// Ejecutamos la agregación
		Document result = collectionFacturasClientes.aggregate(pipeline).first();

		if (result != null && result.containsKey("totalVentas")) {
			int totalVentas = result.getInteger("totalVentas");
			System.out.println("El total de ventas asociadas a " + nombreCliente + " es: $" + totalVentas + "\n");
		} else {
			System.out.println("No se encontró el cliente con nombre: " + nombreCliente);
		}
	}

	// Funcion que muestra los 3 productos mas vendidos.
	public static void productosMasVendidos() {
		System.out.println("\tListado con los 3 productos mas vendidos de nuestra base de datos:\n");

		// Agregación para calcular los productos mas vendidos
		List<Bson> pipeline = Arrays.asList(Aggregates.unwind("$lineasFactura"),
				Aggregates.group("$lineasFactura.articulo",
						Accumulators.sum("totalVendido", "$lineasFactura.cantidad")),
				Aggregates.sort(Sorts.descending("totalVendido")), Aggregates.limit(3));
		// Ejecutar la agregacion
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
