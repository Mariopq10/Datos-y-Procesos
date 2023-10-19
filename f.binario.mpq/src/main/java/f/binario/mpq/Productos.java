package f.binario.mpq;

import java.io.Serializable;

public class Productos implements Serializable  {
	private String articulo;
	private float precio;
	private int existencias;

	public Productos(String articulo, float precio, int existencias) {
		super();
		this.articulo = articulo;
		this.precio = precio;
		this.existencias = existencias;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	@Override
	public String toString() {
		return "Articulo=" + articulo + ", precio=" + precio + ", existencias=" + existencias;
	}

}
