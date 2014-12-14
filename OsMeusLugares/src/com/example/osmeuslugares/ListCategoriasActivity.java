package com.example.osmeuslugares;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListCategoriasActivity extends ListActivity {

	/**
	 * Atributos.
	 */
	private ListCategoriasAdapter listCategoriasAdapter;
	private LugaresDb db;
	Bundle extras;

	/**
	 * Asigna el adapter de categorías. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_categorias);
		registerForContextMenu(super.getListView());
		db = new LugaresDb(this);
		extras = new Bundle();
		listCategoriasAdapter = new ListCategoriasAdapter(this);
		this.setListAdapter(listCategoriasAdapter);
	}

	/**
	 * Obtiene el elemento pulsado y entra en modo edición.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Categoria categoria = (Categoria) listCategoriasAdapter.getItem(position);
		Bundle extras = categoria.getBundle();
		extras.putBoolean("add", false);
		lanzarEditCategoria(extras);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_categorias, menu);
		return true;
	}

	/**
	 * Menú con el botón añadir y cerrar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add_categoria: {
			extras.clear();
			extras.putBoolean("add", true);
			lanzarEditCategoria(extras);
			return true;
		}
		case R.id.cerrar_list_cat: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Crea el menú contextual.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_categorias_contextual, menu);
	}
/**
 * Lo que se seleccione en el menú contextual...
 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		// Obtiene la categoria seleccionada:
		Categoria categoria = (Categoria) listCategoriasAdapter
				.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.edit_categoria:
			// Paso el lugar seleccionado a un bundle:
			Bundle extras = categoria.getBundle();
			// Le paso un false para que sepa que es para editar y no crear:
			extras.putBoolean("add", false);
			// Lanzo editar categoria con el elemento en un bundle:
			lanzarEditCategoria(extras);
			// Muestro un toast con el nombre del elemento a editar:
			Toast.makeText(getBaseContext(),
					"Editar: " + categoria.getNombre(), Toast.LENGTH_SHORT)
					.show();
			return true;

		case R.id.delete_categoria:
			eliminarCategoriaEnBd(categoria);
			Toast.makeText(getBaseContext(),
					"Eliminar: " + categoria.getNombre(), Toast.LENGTH_SHORT)
					.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Elimina una categoría de la bd.
	 * @param categoria
	 */
	private void eliminarCategoriaEnBd(Categoria categoria) {
		db.deleteCategoria(categoria);
		Toast.makeText(getBaseContext(), "ELIMINADA CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
		onRestart();
	}

	/**
	 * Lanza editarCategoriaActivity
	 * @param extras
	 */
	private void lanzarEditCategoria(Bundle extras) {
		Intent i = new Intent(this, EditCategoriaActivity.class);
		i.putExtras(extras);
		startActivity(i);

	}

	/**
	 * Recarga la lista
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		listCategoriasAdapter.cargarDatosDesdeBd();
		listCategoriasAdapter.notifyDataSetChanged();
	}

}
