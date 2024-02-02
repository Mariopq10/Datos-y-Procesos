package ad.operaciones.agregacion.mpq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Funciones {

	public static void displayMenu() throws IOException {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Elige una opcion:");
			System.out.println("\t1. Consultar todos los clientes" + "\n\t2. Consultar un cliente\n"
					+ "\n         [Operaciones de agregación]:\n"
					+ "\n\t3. Cantidad de facturas de un cliente" + "\n\t4. Importe total de una factura"
					+ "\n\t5. Total de ventas a un cliente" + "\n\t6. Productos más vendidos" + "\n\t0. Salir");

			int opcion = sc.nextInt();
			seleccion(opcion);
		}
	}

	public static void seleccion(int opcion) throws IOException {
		Scanner sc = new Scanner(System.in);
		switch (opcion) {
		case 1:
			Conexion.mostrarFacturas();
			break;
		// Consultar un cliente
		case 2:
			Conexion.mostrarUnaFactura();
			break;
		// Cantidad de facturas de un cliente
		case 3:
			Conexion.cantidadFacturasCliente();
			break;
		// Importe total de una factura
		case 4:
			Conexion.importeTotalFactura();
			break;
		// Total de ventas a un cliente
		case 5:
			Conexion.totalVentasCliente();
			break;
		// Productos más vendidos
		case 6:
			Conexion.productosMasVendidos();
			break;
		case 0:
			System.out.println("Ha salido del programa.");
			System.exit(0);
			break;
		default:
			System.out.println("Elige una opcion correcta.");
		}
	}

}
