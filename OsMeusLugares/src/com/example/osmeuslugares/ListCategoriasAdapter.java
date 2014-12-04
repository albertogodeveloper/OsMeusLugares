package com.example.osmeuslugares;

import java.util.Vector;

import android.app.Activity;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCategoriasAdapter extends BaseAdapter {

	private final Activity activity;
	private Vector<Categoria> lista;
	private LugaresDb lugaresDb;

	/**
	 * @param activity
	 * @param lista
	 */
	public ListCategoriasAdapter(Activity activity) {
		super();
		this.activity = activity;
		lugaresDb = new LugaresDb(activity);
		this.lista = new Vector<Categoria>();
		cargarDatosDesdeBd();
	}

	public void cargarDatosDesdeBd() throws SQLException{
		this.lista = lugaresDb.cargarCategoriasDesdeBD();
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
		Categoria categoria = (Categoria)getItem(position);
		return categoria.getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_categorias, null, true);

		cargaDatos(position, view);
		return view;
	}
	private void cargaDatos(int position, View view) {
		TextView textViewTitulo = (TextView) view.findViewById(R.id.tvTituloCat);
		TextView txtNombre = (TextView) view.findViewById(R.id.tvNombreCat);
		TextView txtIcono = (TextView) view.findViewById(R.id.tvIconoCat);

		Categoria categoria = (Categoria) lista.elementAt(position);
		int id = categoria.getId();
		ImageView icono = (ImageView) view.findViewById(R.id.iconoCat);
		
		//Iconos del xml...............................
		categoria.getIcono();
		
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

		textViewTitulo.setText(categoria.getNombre());
		txtNombre.setText(categoria.getNombre());
		txtIcono.setText(categoria.getIcono());

	}
	
	public int getPositionById(int id) {
		//Buscar en lista 
		Categoria buscar = new Categoria();
		buscar.setId(id);
		return lista.indexOf(buscar);
	}
}
