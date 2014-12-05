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

public class ListCategoriasAdapter extends BaseAdapter {

	private final Activity activity;
	private Vector<Categoria> lista;
	private LugaresDb lugaresDb;
	private RecursoIcono recursoIcono;

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
		this.recursoIcono = new RecursoIcono(activity);
	}

	public void cargarDatosDesdeBd() throws SQLException {
		this.lista = lugaresDb.cargarCategoriasDesdeBD(false);
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
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista_categorias, null,
				true);

		cargaDatos(position, view);
		return view;
	}

	private void cargaDatos(int position, View view) {
		TextView textViewTitulo = (TextView) view
				.findViewById(R.id.tvTituloCat);
		TextView txtNombre = (TextView) view.findViewById(R.id.tvNombreCat);
		TextView txtIcono = (TextView) view.findViewById(R.id.tvIconoCat);

		Categoria categoria = (Categoria) lista.elementAt(position);
		int id = categoria.getId();
		ImageView imgViewIcono = (ImageView) view.findViewById(R.id.iconoCat);
		// Log.i(this.getClass().toString(),"ICONO OBTENIDO DE LA CATEGORIA= "+categoria.getIcono());
		Drawable icon = recursoIcono.obtenerDrawableIcon(categoria.getIcono());
		imgViewIcono.setImageDrawable(icon);

		textViewTitulo.setText(categoria.getNombre());
		txtNombre.setText(categoria.getNombre());
		txtIcono.setText(categoria.getIcono());

	}

	public int getPositionById(int id) {
		// Buscar en lista
		Categoria buscar = new Categoria();
		buscar.setId(id);
		return lista.indexOf(buscar);
	}
}
