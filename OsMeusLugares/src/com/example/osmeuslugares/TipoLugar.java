package com.example.osmeuslugares;

public class TipoLugar {

	private int id;
	private String tipo;

	public TipoLugar(int id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "TipoLugar [id=" + id + ", tipo=" + tipo + "]";
	}

}
