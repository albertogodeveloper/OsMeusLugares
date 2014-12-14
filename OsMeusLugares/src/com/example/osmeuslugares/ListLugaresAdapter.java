package com.example.osmeuslugares;

import java.util.Vector;
import android.app.Activity;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListLugaresAdapter extends BaseAdapter {

	/**
	 * Atributos.
	 */
	private final Activity activity;
	private Vector<Lugar> lista;
	private LugaresDb lugaresDb;
	private RecursoIcono recursoIcono;

	/**
	 * Constructor que recibe el activity y carga la lista adapter de la bd.
	 * 
	 * @param activity
	 */
	public ListLugaresAdapter(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Lugar>();
		lugaresDb = new LugaresDb(activity);
		actualizarDesdeDb();
		this.recursoIcono = new RecursoIcono(activity);
	}

	/**
	 * Carga los lugares en un vector.
	 * 
	 * @throws SQLException
	 */
	public void actualizarDesdeDb() throws SQLException {
		this.lista = lugaresDb.cargarLugaresDesdeBD();
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
		return position;
	}

	/**
	 * Carga los datos en el layout.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista, null, true);
		// Carga los datos en el elemento_lista.xml:
		cargaDatos(position, view);
		return view;
	}

	/**
	 * Carga los datos de cada elemento y los asigna a las cajas txt del
	 * elemento_lista.xml
	 * 
	 * @param position
	 * @param view
	 */
	private void cargaDatos(int position, View view) {
		// Identificadores:
		TextView textViewTitulo = (TextView) view
				.findViewById(R.id.textViewTitulo);
		TextView txtNombre = (TextView) view.findViewById(R.id.tvNombre);
		TextView txtLugar = (TextView) view.findViewById(R.id.tvTipo);
		TextView txtDireccion = (TextView) view.findViewById(R.id.tvDireccion);
		TextView txtCiudad = (TextView) view.findViewById(R.id.tvCiudad);
		TextView txtUrl = (TextView) view.findViewById(R.id.tvUrl);
		TextView txtTelf = (TextView) view.findViewById(R.id.tvTelefono);
		TextView txtComent = (TextView) view.findViewById(R.id.tvComentario);

		// Obtiene el lugar de la lista:
		Lugar lugar = (Lugar) lista.elementAt(position);

		// Obtiene el icono de la categoria de ese lugar:
		ImageView imgViewIcono = (ImageView) view.findViewById(R.id.icono);
		Log.i(this.getClass().toString(), "ICONO OBTENIDO DE LA CATEGORIA= "
				+ lugar.getCategoria().getIcono());
		Drawable icon = recursoIcono.obtenerDrawableIcon(lugar.getCategoria()
				.getIcono());

		// Asigna los datos a las cajas de texto:
		imgViewIcono.setImageDrawable(icon);
		textViewTitulo.setText(lugar.getNombre());
		txtNombre.setText(lugar.getNombre());
		txtLugar.setText(lugar.getCategoria().getNombre());
		txtDireccion.setText(lugar.getDireccion());
		txtCiudad.setText(lugar.getCiudad());
		txtUrl.setText(lugar.getUrl());
		txtTelf.setText(lugar.getTelefono());
		txtComent.setText(lugar.getComentario());
	}
	
}