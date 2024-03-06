package dao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private int idProducto;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "cantidad")
	private int cantidad;

	/**
	 * 
	 * @param idComida
	 * @param nombre
	 * @param cantidad
	 */
	public Producto(int idProducto, String nombre, int cantidad) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	/**
	 * 
	 * @param nombre
	 * @param cantidad
	 */
	public Producto(String nombre, int cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	// Getters y Setters
	public int getIdComida() {
		return idProducto;
	}

	public void setIdComida(int idComida) {
		this.idProducto = idComida;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
