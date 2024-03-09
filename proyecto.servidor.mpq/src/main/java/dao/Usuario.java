package dao;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")

	public class Usuario {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_usuario")
		private int idUsuario;

		@Column(name = "password")
		private String password;

		@Column(name = "nombre")
		private String nombre;

		@Column(name = "apellido")
		private String apellido;


		// Constructor con todos los campos
		public Usuario(int idUsuario, String nombre, String pass, String apellido) {
			this.idUsuario = idUsuario;
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
			return idUsuario;
		}

		public void setIdUsuario(int idUsuario) {
			this.idUsuario = idUsuario;
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

