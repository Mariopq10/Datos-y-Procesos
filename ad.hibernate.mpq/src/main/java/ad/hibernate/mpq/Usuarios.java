package ad.hibernate.mpq;

import org.hibernate.annotations.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.*;


@Entity
@Table(appliesTo = "usuarios")
public class Usuarios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coduser")
	private String coduser;
	
	@Column(name ="nombrelogin")
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
