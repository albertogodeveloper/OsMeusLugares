package com.example.osmeuslugares;

import java.util.Vector;

import android.app.Activity;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListLugaresAdapter extends BaseAdapter {

	private final Activity activity;
	private Vector<Lugar> lista;
	private LugaresDb lugaresDb;

	public ListLugaresAdapter(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Lugar>();

	}

	public void abrir() throws SQLException {
		lugaresDb = new LugaresDb(activity);
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

		TextView textViewTitulo = (TextView) view
				.findViewById(R.id.textViewTitulo);
		TextView textViewInfo = (TextView) view.findViewById(R.id.textViewInfo);
		Lugar lugar = (Lugar) lista.elementAt(position);
		textViewTitulo.setText(lugar.getNombre());
		textViewInfo.setText(lugar.toString());
		return view;
	}

}
