package objetos;

import java.io.Serializable;

import accesoBD.EnumMarcas;

public class Produccion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3826454513362811984L;
	private int id;
	private EnumMarcas marca;
	private String fabrica;
	private int unidades;

	public Produccion(int id, EnumMarcas marca, String fabrica, int unidades) {
		super();
		this.id = id;
		this.marca = marca;
		this.fabrica = fabrica;
		this.unidades = unidades;
	}

	public Produccion(int id, String marca, String fabrica, int unidades) {
		super();
		this.id = id;
		this.marca = EnumMarcas.valueOf(marca);
		this.fabrica = fabrica;
		this.unidades = unidades;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return marca.toString();
	}

	public void setMarca(String marca) {
		this.marca = EnumMarcas.valueOf(marca);
	}

	public String getFabrica() {
		return fabrica;
	}

	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public String toString() {
		return "Id: " + this.id + " marca: " + this.marca + " fabrica: " + this.fabrica + " unidades: " + this.unidades;
	}

}
