package main;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;  

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		LocalDateTime horarioActual = LocalDateTime.now();  
		Timestamp fechaHora = Timestamp.valueOf(horarioActual);
		String descripcion;
		int nHoras, idCliente;
		 
			// Preinstalamos los drivers de conexion de la base de datos desde la página de
			// mysql e importamos el archivo.jar como libreria externa.
		try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.6.221:3306/mario", "mario", "12345")) {
				// Realizamos la conexion con la base de datos
				Statement stmt = con.createStatement(); 
			
			//Ejecutamos la linea de mysql que crea la tabla incidencias, crea la tabla solo si no esta creada.
			stmt.execute("CREATE TABLE IF NOT EXISTS incidencias (id int auto_increment primary key,id_cliente int,fechaHora datetime,descripcion text, nHoras int);");
			
			//Esta linea de aqui abajo relaciona id_cliente de la tabla incidencias con el id de la tabla clientes.
			stmt.execute("ALTER TABLE incidencias ADD constraint foreign key (id_cliente) references clientes(id)");
			//Aqui podemos introducir los datos por teclado.
			
			System.out.println("Inserta el id del cliente: ");
			idCliente = sc.nextInt();
			System.out.println("Inserta descripcion de incidencia: ");
			descripcion = sc.next();
			System.out.println("Inserta el número de horas: ");
			nHoras = sc.nextInt();
			
			//Creamos el String que contiene el insert y ejecutamos con un PreparedStatement la lineaInsert.
			
            String lineaInsert="INSERT INTO incidencias(id_cliente,fechaHora,descripcion,nHoras) VALUES (?,?,?,?)";
            try (PreparedStatement state=con.prepareStatement(lineaInsert)){
                state.setInt(1, idCliente);
                state.setTimestamp(2, fechaHora);
                state.setString(3, descripcion);
                state.setInt(4, nHoras);
                state.executeUpdate();
                state.close();
            }
            
            // Sentencia SELECT para coger todos los datos de incidencias y mostrarlas por consola.
            
			ResultSet rs2=stmt.executeQuery("select * from incidencias");
			while(rs2.next())  
				System.out.println(rs2.getInt(1)+" - "+rs2.getString(2)+" - "+rs2.getString(3)+" - "+rs2.getString(4)+" - "+rs2.getString(5));  
				con.close();  
			}catch(Exception e){ System.out.println(e);}  
			}  
			
	}

