package com.example.osmeuslugares;

import android.content.ContentValues;
import android.os.Bundle;

public class Lugar {
	private Long id;
	private String nombre;// obligatorio
	private Categoria categoria;// obligatorio
	private String direccion;
	private String ciudad;
	private String url;
	private String telefono;
	private String comentario;

	/* Mapeo BBDD */
	// Campos Base de Datos Tabla Lugar
	public static final String C_ID = "lug_id";
	public static final String C_NOMBRE = "lug_nombre";
	public static final String C_CATEGORIA_ID = "lug_categoria_id";
	public static final String C_DIRECCION = "lug_direccion";
	public static final String C_CIUDAD = "lug_ciudad";
	public static final String C_TELEFONO = "lug_telefono";
	public static final String C_URL = "lug_url";
	public static final String C_COMENTARIO = "lug_comentario";

	/**
	 * @param nombre
	 * @param tipoLugar
	 */
	public Lugar() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
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
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Lugar [id=" + id + ", nombre=" + nombre + ", categoria="
				+ categoria.toString() + ", direccion=" + direccion
				+ ", ciudad=" + ciudad + ", url=" + url + ", telefono="
				+ telefono + ", comentario=" + comentario + "]";
	}

	ContentValues getContentValues() {
		ContentValues reg = new ContentValues();
		reg.put(C_NOMBRE, nombre);
		reg.put(C_CATEGORIA_ID, categoria.getId());
		reg.put(Categoria.C_NOMBRE, categoria.getNombre());
		reg.put(C_DIRECCION, direccion);
		reg.put(C_CIUDAD, ciudad);
		reg.put(C_URL, url);
		reg.put(C_TELEFONO, telefono);
		reg.put(C_COMENTARIO, comentario);
		return reg;
	}

	Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putString(C_NOMBRE, nombre);
		bundle.putLong(C_CATEGORIA_ID, categoria.getId());
		bundle.putString(Categoria.C_NOMBRE, categoria.getNombre());
		bundle.putString(C_DIRECCION, direccion);
		bundle.putString(C_CIUDAD, ciudad);
		bundle.putString(C_URL, url);
		bundle.putString(C_TELEFONO, telefono);
		bundle.putString(C_COMENTARIO, comentario);
		return bundle;
	}

	void setBundle(Bundle bundle) {

		nombre = bundle.getString(C_NOMBRE);
		categoria = new Categoria(bundle.getLong(C_CATEGORIA_ID),
				bundle.getString(Categoria.C_NOMBRE));
		direccion = bundle.getString(C_DIRECCION);
		ciudad = bundle.getString(C_CIUDAD);
		telefono = bundle.getString(C_TELEFONO);
		comentario = bundle.getString(C_COMENTARIO);
	}
}
