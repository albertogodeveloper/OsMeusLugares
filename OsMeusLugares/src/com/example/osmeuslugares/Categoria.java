package com.example.osmeuslugares;

import android.content.ContentValues;
import android.os.Bundle;

public class Categoria {
	private int id;
	private String nombre;
	private String icono;

	/* Mapeo BBDD */
	// Campos Base de Datos Tabla Lugar
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

	ContentValues getContentValues() {
		ContentValues reg = new ContentValues();
		reg.put(C_NOMBRE, nombre);
		reg.put(C_ICONO, icono);
		return reg;
	}

	Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(C_ID, id);
		bundle.putString(C_NOMBRE, nombre);
		bundle.putString(C_ICONO, icono);
		return bundle;
	}

	void setBundle(Bundle bundle) {
		id = bundle.getInt(C_ID);
		nombre = bundle.getString(C_NOMBRE);
		icono = bundle.getString(C_ICONO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", icono="
				+ icono + "]";
	}
	

}
