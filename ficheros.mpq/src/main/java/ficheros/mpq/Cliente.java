package ficheros.mpq;

public class Cliente {
	private String nombre;
	private String apellido;
	private byte edad;
	
	public Cliente(String nombre, String apellido, byte edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
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

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public byte getEdad() {
		return edad;
	}

	public void setEdad(byte edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return ""+nombre +" " + apellido + " " + edad + " años\n";
	}
	
	
	
	
}
