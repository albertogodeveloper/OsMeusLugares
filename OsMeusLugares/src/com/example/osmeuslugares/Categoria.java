package com.example.osmeuslugares;

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

}
