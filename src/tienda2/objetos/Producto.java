package objetos;

import java.io.Serializable;

import excepciones.NoHayUnidadesException;

public class Producto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -91715111385150929L;
	private int codigo, precio, cantidad;
	private String nombre;

	public Producto(int codigo, int precio, int cantidad, String nombre) {
		super();
		this.codigo = codigo;
		this.precio = precio;
		this.cantidad = cantidad;
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", precio=" + precio + ", cantidad=" + cantidad + ", nombre=" + nombre
				+ "]\n";
	}

	public void aniadirCant(int cantidad) {
		this.cantidad += cantidad;
	}

	public void disminuirCantidad(int cantidad) throws NoHayUnidadesException {
		if (this.cantidad - cantidad < 0)
			throw new NoHayUnidadesException("No hay suficientes unidades");

		this.cantidad -= cantidad;
	}

}
