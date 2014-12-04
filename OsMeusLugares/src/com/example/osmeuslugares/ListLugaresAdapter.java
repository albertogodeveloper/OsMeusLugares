package com.example.osmeuslugares;

import java.util.Vector;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListLugaresAdapter extends BaseAdapter {

	private final Activity activity;
	private Vector<Lugar> lista;
	private LugaresDb lugaresDb;

	public ListLugaresAdapter(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Lugar>();
		lugaresDb = new LugaresDb(activity);
		actualizarDesdeDb();
	}

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista, null, true);

		cargaDatos(position, view);
		return view;
	}

	private void cargaDatos(int position, View view) {
		TextView textViewTitulo = (TextView) view.findViewById(R.id.textViewTitulo);
		TextView txtNombre = (TextView) view.findViewById(R.id.tvNombre);
		TextView txtLugar = (TextView) view.findViewById(R.id.tvTipo);
		TextView txtDireccion = (TextView) view.findViewById(R.id.tvDireccion);
		TextView txtCiudad = (TextView) view.findViewById(R.id.tvCiudad);
		TextView txtUrl = (TextView) view.findViewById(R.id.tvUrl);
		TextView txtTelf = (TextView) view.findViewById(R.id.tvTelefono);
		TextView txtComent = (TextView) view.findViewById(R.id.tvComentario);

		Lugar lugar = (Lugar) lista.elementAt(position);
		int id = lugar.getCategoria().getId();
		ImageView icono = (ImageView) view.findViewById(R.id.icono);
		
		//Iconos del xml...............................
		lugar.getCategoria().getIcono();
		
		switch (id) {
		case 1:
			icono.setImageResource(R.drawable.ic_playas);
			break;
		case 2:
			icono.setImageResource(R.drawable.ic_restaurantes);
			break;
		case 3:
			icono.setImageResource(R.drawable.ic_hoteles);
			break;
		default:
			icono.setImageResource(R.drawable.ic_otros);
			break;
		}

		textViewTitulo.setText(lugar.getNombre());
		txtNombre.setText(lugar.getNombre());
		txtLugar.setText(lugar.getCategoria().getNombre());
		txtDireccion.setText(lugar.getDireccion());
		txtCiudad.setText(lugar.getCiudad());
		txtUrl.setText(lugar.getUrl());
		txtTelf.setText(lugar.getTelefono());
		txtComent.setText(lugar.getComentario());
	}
	
	private Drawable obtenerDrawableIcon(String icon) {
		Resources res = activity.getResources();
		TypedArray iconosLugares = res.obtainTypedArray(R.array.iconos_lugares);

		TypedArray nombresIconos = res.obtainTypedArray(R.array.lista_tipos);
		//Obtener la posición de icon en el array.
		return iconosLugares.getDrawable(0);
	}
}