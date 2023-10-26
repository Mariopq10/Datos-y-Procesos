package examen.ad.mpq;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Hacemos la conexion a la base de datos de sqlite que hemos creado e
		// introducido en la carpeta del proyecto, no necesitamos usuario y contraseña
		// como en el ejercicio anterior.
		Scanner sc = new Scanner(System.in);
		LocalDateTime horarioActual = LocalDateTime.now();
		Timestamp fechaHora = Timestamp.valueOf(horarioActual);
		String nombreLogin;
		String nombreCompleto;
		String contrasena;

		try (Connection conexion = DriverManager.getConnection("jdbc:sqlite:./examen.db")) {
			// Realizamos la conexion con la base de datos
			Statement stmt = conexion.createStatement();
			conexion.setAutoCommit(false);

			// Ejecutamos la linea de mysql que crea la tabla incidencias, crea la tabla
			// solo si no esta creada.
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS usuarios (coduser int auto_increment , nombrelogin text primary key,contrasena text, nombrecompleto text);");

			// PreparedStatement la lineaInsert introduciendo datos manualmente.

			System.out.println("Que quieres hacer: \n1-Insertar usuario\n2-Logear usuario\n3-Borrar usuario");
			byte opcion = Byte.parseByte(sc.nextLine());
			switch (opcion) {
			case 1:
				System.out.println("Inserta el nick del cliente: ");
				nombreLogin = sc.nextLine();
				System.out.println("Inserta la contrasena: ");
				contrasena = sc.nextLine();
				System.out.println("Inserta el nombre completo: ");
				nombreCompleto = sc.nextLine();
				String lineaInsert = "INSERT INTO usuarios (nombrelogin,contrasena,nombrecompleto) VALUES (?,?,?)";

				try (PreparedStatement state = conexion.prepareStatement(lineaInsert)) {
					state.setString(1, nombreLogin);
					state.setString(2, contrasena);
					state.setString(3, nombreCompleto);
					state.executeUpdate();
					state.close();
					conexion.commit();
					System.out.println("Cliente creado con exito");
				} catch (Exception e) {
					System.err.println("Cliente existente, intente con otro.");
					conexion.rollback();
				}

			case 2:
				int codigoUsuario = 0;
				System.out.println("Inserta el nick del cliente: ");
				nombreLogin = sc.nextLine();
				System.out.println("Inserta la contrasena: ");
				contrasena = sc.nextLine();
				String lineaLogin = "SELECT contrasena,nombrelogin,coduser FROM usuarios";
				try (PreparedStatement state = conexion.prepareStatement(lineaLogin)) {
					ResultSet rs2 = stmt.executeQuery(lineaLogin);
					while (rs2.next())
						if (rs2.getString(1).equals(contrasena) && rs2.getString(2).equals(nombreLogin)) {
							codigoUsuario = rs2.getInt(3);
							System.out.println("Login con usuario : " + nombreLogin + " correcto.");
						}

				} catch (Exception e) {
					System.err.println("Error");
					conexion.rollback();
					e.printStackTrace();
				}
				// Aqui introducimos los datos del usuario logado en la tabla entradas
				String lineaUpdate = "INSERT INTO entradas (coduser,contador_entradas,hora_entrada) VALUES (?,?,?)";
				System.out.println(codigoUsuario);
				try (PreparedStatement state = conexion.prepareStatement(lineaUpdate)) {
					state.setInt(1, codigoUsuario);
					state.setInt(2, 1);
					state.setString(3, "hora");
					state.executeUpdate();
					state.close();
					conexion.commit();

				} catch (SQLException e) {
					System.err.println("Error");
					e.printStackTrace();
					conexion.rollback();
				}
			case 3:
				// Aqui borraremos el usuario.
				int codigoUsuarioBorrar;
				System.out.println("Inserta el nick del cliente a borrar: ");
				String nombreBorrado = sc.nextLine();
				String linea = "SELECT nombrelogin,coduser FROM usuarios";
				try (PreparedStatement state = conexion.prepareStatement(linea)) {
					ResultSet rs2 = stmt.executeQuery(linea);
					while (rs2.next())
						if (rs2.getString(1).equals(nombreBorrado)) {
							codigoUsuarioBorrar = rs2.getInt(2);
							System.out.println("Se borrara al usuario con nombre : " + nombreBorrado);
							String lineaBorrado = "DELETE FROM usuarios WHERE coduser = " + codigoUsuarioBorrar + ";";
							// Aqui borramos el usuario de la tabla usuarios cogiendo el codigo de usuario
							// de la sentencia anterior.
							PreparedStatement state2 = conexion.prepareStatement(lineaBorrado);
							state2.executeUpdate();
							state2.close();
							conexion.commit();
							System.out.println(codigoUsuarioBorrar);

						}

				} catch (Exception e) {
					System.err.println("Error");
					conexion.rollback();
					e.printStackTrace();
				}
			}
			conexion.close();
			conexion.commit();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
