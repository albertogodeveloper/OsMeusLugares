package com.example.osmeuslugares;

import android.content.ContentValues;
import android.os.Bundle;

public class Categoria {
	private int id;
	private String nombre;
	private String icono;

	/* Mapeo BBDD */
	// Campos Base de Datos Tabla Categoria
	public static final String C_ID = "cat_id";
	public static final String C_NOMBRE = "cat_nombre";
	public static final String C_ICONO = "cat_icono";

	/**
	 * Constructores
	 */
	public Categoria() {
		super();
	}

	public Categoria(int id, String nombre, String icono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.icono = icono;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono
	 *            the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}

	/**
	 * Esta colección es de tipo diccionario, donde almacenaremos parejas de
	 * clave-valor, donde la clave será el nombre de cada campo y el valor será
	 * el dato correspondiente a insertar en dicho campo.
	 * 
	 * @return
	 */
	ContentValues getContentValues() {
		ContentValues reg = new ContentValues();
		reg.put(C_NOMBRE, nombre);
		reg.put(C_ICONO, icono);
		return reg;
	}

	/**
	 * Contiene una lista de pares clave-valor con toda la información a pasar
	 * entre las actividades.
	 * 
	 * @return
	 */
	Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(C_ID, id);
		bundle.putString(C_NOMBRE, nombre);
		bundle.putString(C_ICONO, icono);
		return bundle;
	}

	/**
	 * Crea una lista de pares clave-valor con toda la información a pasar entre
	 * las actividades.
	 * 
	 * @return
	 */
	void setBundle(Bundle bundle) {
		id = bundle.getInt(C_ID);
		nombre = bundle.getString(C_NOMBRE);
		icono = bundle.getString(C_ICONO);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Categoria) {
			Categoria tmpCategoria = (Categoria) o;
			if (getId() == tmpCategoria.getId()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", icono="
				+ icono + "]";
	}
}
