package com.example.osmeuslugares;

import java.util.Vector;
import android.app.Activity;
import android.database.SQLException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriasAdapter extends BaseAdapter {

	/**
	 * Atributos.
	 */
	private final Activity activity;
	private Vector<Categoria> lista;
	private LugaresDb lugaresDb;

/**
 * Constructor que recibe el activity y carga las categorías en un vector.
 * @param activity
 */
	public CategoriasAdapter(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Categoria>();
		cargarDatosDesdeBd();
	}

	/**
	 * Carga las categorías en un vector.
	 * @throws SQLException
	 */
	public void cargarDatosDesdeBd() throws SQLException {
		lugaresDb = new LugaresDb(activity);
		//Le pasa true para que aparezca el Seleccionar... en el Spinner.
		this.lista = lugaresDb.cargarCategoriasDesdeBD(true);
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		Categoria categoria = (Categoria) getItem(position);
		return categoria.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Categoria categoria = (Categoria) lista.elementAt(position);
		TextView text = new TextView(activity);
		text.setText(categoria.getNombre());
		return text;

	}

	public int getPositionById(int id) {
		// Buscar en lista
		Categoria buscar = new Categoria();
		buscar.setId(id);
		return lista.indexOf(buscar);
	}
}