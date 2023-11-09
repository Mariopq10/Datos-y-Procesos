package ad.hibernate.mpq;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String coduser;

	@Column(name = "nombrelogin")
	private String nombrelogin;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "nombrecompleto")
	private String nombreCompleto;

	public Usuarios(String coduser, String nombrelogin, String contrasena, String nombreCompleto) {
		super();
		this.coduser = coduser;
		this.nombrelogin = nombrelogin;
		this.contrasena = contrasena;
		this.nombreCompleto = nombreCompleto;
	}

	public Usuarios() {

	}

	public String getCoduser() {
		return coduser;
	}

	public void setCoduser(String coduser) {
		this.coduser = coduser;
	}

	public String getNombrelogin() {
		return nombrelogin;
	}

	public void setNombrelogin(String nombrelogin) {
		this.nombrelogin = nombrelogin;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
