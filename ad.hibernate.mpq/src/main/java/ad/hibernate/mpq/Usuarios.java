package ad.hibernate.mpq;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long coduser;

	@Column(name = "nombrelogin")
	private String nombreLogin;

	@Column(name = "contrasena")
	private String contrasena;

	@Column(name = "nombrecompleto")
	private String nombreCompleto;



	public Long getCoduser() {
		return coduser;
	}
	public void setCoduser(Long coduser) {
		this.coduser = coduser;
	}
	public String getNombreLogin() {
		return nombreLogin;
	}
	public void setNombreLogin(String nombreLogin) {
		this.nombreLogin = nombreLogin;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String password) {
		this.contrasena = password;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
