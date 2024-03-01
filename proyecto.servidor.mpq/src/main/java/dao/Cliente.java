package dao;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

	public class Usuario {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_cliente")
		private int idCliente;

		@Column(name = "password")
		private String password;

		@Column(name = "nombre")
		private String nombre;

		@Column(name = "apellido")
		private String apellido;


		// Constructor con todos los campos
		public Usuario(int idUsuario, String nombre, String pass, String apellido) {
			this.idCliente = idUsuario;
			this.nombre = nombre;
			this.password = pass;
			this.apellido = apellido;
		}

		// Constructor sin el campo id
		public Usuario(String nombre, String apellido) {
			this.nombre = nombre;
			this.apellido = apellido;
		}

		// Getters y setters
		public int getIdUsuario() {
			return idCliente;
		}

		public void setIdUsuario(int idUsuario) {
			this.idCliente = idUsuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido1(String apellido) {
			this.apellido = apellido;
		}


	}
}
