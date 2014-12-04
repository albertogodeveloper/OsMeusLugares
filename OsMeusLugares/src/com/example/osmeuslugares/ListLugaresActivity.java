package com.example.osmeuslugares;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListLugaresActivity extends ListActivity {

	private ListLugaresAdapter listLugaresAdapter;
	Bundle extras = new Bundle();
	private LugaresDb db = new LugaresDb(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);
		registerForContextMenu(super.getListView());

		listLugaresAdapter = new ListLugaresAdapter(this);
		setListAdapter(listLugaresAdapter);

		leerPreferenciaInfo();
	}

	private void leerPreferenciaInfo() {
		/* Leer preferencia de info */
		boolean infoAmpliada = getPreferenciaVerInfoAmpliada();
		if (infoAmpliada) {
			Toast.makeText(getBaseContext(), "Info Ampliada ON",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getBaseContext(), "Info Ampliada OFF",
					Toast.LENGTH_LONG).show();
		}
	}

	public boolean getPreferenciaVerInfoAmpliada() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("ver_info_ampliada", false);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Lugar itemLugar = (Lugar) getListAdapter().getItem(position);
		Bundle extras = itemLugar.getBundle();
		extras.putBoolean("add", false);
		lanzarEditLugar(extras);
	}

	private void lanzarEditLugar(Bundle extras) {
		Intent i = new Intent(this, EditLugarActivity.class);
		i.putExtras(extras);
		startActivity(i);
		// startActivityForResult(i, 1234); //Preguntar a
		// Gabino..................................................................
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode == 1234 && resultCode == RESULT_OK) {
	// String resultado = data.getExtras().getString("resultado");
	// Toast.makeText(getBaseContext(), resultado, Toast.LENGTH_LONG)
	// .show();
	//
	// }
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_lugares, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add_lugar: {
			extras.clear();
			extras.putBoolean("add", true);
			lanzarEditLugar(extras);
			return true;
		}
		case R.id.cerrar: {
			finish();
			break;
		}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_lugares_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		 // Obtiene el lugar seleccionado:
		Lugar lugar = (Lugar) listLugaresAdapter.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.edit_lugar:
			// Paso el lugar seleccionado a un bundle:
			Bundle extras = lugar.getBundle();
			// Le paso un false para que sepa que es para editar y no crear:
			extras.putBoolean("add", false);
			// Lanzo editar lugar con el elemento en un bundle:
			lanzarEditLugar(extras);
			// Muestro un toast con el nombre del elemento a editar:
			Toast.makeText(getBaseContext(), "Editar: " + lugar.getNombre(),Toast.LENGTH_SHORT).show();
			return true;

		case R.id.delete_lugar:
			eliminarLugarEnBd(lugar);
			Toast.makeText(getBaseContext(), "Eliminar: " + lugar.getNombre(),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.web_lugar:
			lanzarWeb(lugar);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void lanzarWeb(Lugar lugar) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("http://"+lugar.getUrl()));
		this.startActivity(i);
		
	}

	private void eliminarLugarEnBd(Lugar lugar) {
		db.deleteLugar(lugar);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
		onRestart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		listLugaresAdapter.actualizarDesdeDb();
		listLugaresAdapter.notifyDataSetChanged();
	}

}
