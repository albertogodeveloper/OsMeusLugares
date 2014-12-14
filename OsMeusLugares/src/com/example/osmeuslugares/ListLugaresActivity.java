package com.example.osmeuslugares;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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

	/**
	 * Atributos.
	 */
	private LugaresDb db;
	private ListLugaresAdapter listLugaresAdapter;
	Bundle extras = new Bundle();
	Sonidos sonidos;
	
	/**
	 * Se crea un adaptador para la lista y se le asigna. Se registra el menú
	 * contextual.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);

		db = new LugaresDb(this);
		listLugaresAdapter = new ListLugaresAdapter(this);
		setListAdapter(listLugaresAdapter);
		registerForContextMenu(super.getListView());
		leerPreferenciaInfo();
		sonidos = new Sonidos(this);
	}

	/**
	 * Obtiene el elemento clickeado y lo guarda en un bundle, lanza editLugar
	 * con el bundle.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		sonidos.playSonido1();
		Lugar itemLugar = (Lugar) getListAdapter().getItem(position);
		Bundle extras = itemLugar.getBundle();
		extras.putBoolean("add", false);// Para que editLugar sepa que es una
										// edición.
		lanzarEditLugar(extras);
	}

	/**
	 * Lanza editLugar con el bundle.
	 * 
	 * @param extras
	 */
	private void lanzarEditLugar(Bundle extras) {
		Intent i = new Intent(this, EditLugarActivity.class);
		i.putExtras(extras);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_lugares, menu);
		return true;
	}

	/**
	 * Barra de menu.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add_lugar:
			extras.clear();// Limpia el bundle.
			extras.putBoolean("add", true);// Para que editLugar sepa que es una
											// creación.
			lanzarEditLugar(extras);
			return true;

		case R.id.mi_localizacion:
			lanzarCoordenadas();
			return true;

		case R.id.cerrar:
			finish();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Menú contextual.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_lugares_contextual, menu);
	}

	/**
	 * Seleccionado en el menú contextual...
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		// Obtiene el lugar seleccionado:
		Lugar lugar = (Lugar) listLugaresAdapter.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.ir_web_lugar:
			if (lugar.getUrl().isEmpty() || lugar.getUrl() == "") {
				Toast.makeText(getBaseContext(), "No hay dirección",
						Toast.LENGTH_SHORT).show();
			} else {
				lanzarWeb(lugar);
			}
			return true;

		case R.id.ir_marcar_telefono_lugar:
			lanzarMarcarTelefono(lugar.getTelefono());
			return true;

		case R.id.ir_enviar_por_email:
			lanzarEmail(lugar);
			return true;

		case R.id.ir_edit_lugar:
			// Paso el lugar seleccionado a un bundle:
			Bundle extras = lugar.getBundle();
			// Le paso un false para que sepa que es para editar y no crear:
			extras.putBoolean("add", false);
			// Lanzo editar lugar con el elemento en un bundle:
			lanzarEditLugar(extras);
			// Muestro un toast con el nombre del elemento a editar:
			Toast.makeText(getBaseContext(), "Editar: " + lugar.getNombre(),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.ir_delete_lugar:
			eliminarLugarEnBd(lugar);
			Toast.makeText(getBaseContext(), "Eliminar: " + lugar.getNombre(),
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Elimina un lugar de la bd y reinicia la actividad.
	 * 
	 * @param lugar
	 */
	private void eliminarLugarEnBd(Lugar lugar) {
		db.deleteLugar(lugar);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
		onRestart();
	}

	/**
	 * Muestra las coordenadas...
	 */
	private void lanzarCoordenadas() {// No funciona...
		CoordenadasGPS coordenadasGPS;
		Location localizacion;
		try {
			coordenadasGPS = new CoordenadasGPS(this);
			localizacion = coordenadasGPS.getLocalizacion();
			Toast.makeText(getBaseContext(),
					"Coordenadas acutales: " + localizacion.toString(),
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Para enviar por correo información de un lugar.
	 * 
	 * @param lugar
	 */
	private void lanzarEmail(Lugar lugar) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		String[] to = { "lag@fernandowirtz.com" };
		String subject = "Lugar " + lugar.getNombre();
		String body = lugar.toString();
		i.putExtra(Intent.EXTRA_EMAIL, to);
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT, body);
		startActivity(i);
	}

	/**
	 * Para marcar el nº de telf.
	 * 
	 * @param telefono
	 */
	private void lanzarMarcarTelefono(String telefono) {
		if (!telefono.isEmpty()) {
			Intent i = new Intent(Intent.ACTION_DIAL);
			i.setData(Uri.parse("tel:" + telefono));
			startActivity(i);
		}
	}

	/**
	 * Para lanzar en navegador con la web del lugar.
	 * 
	 * @param lugar
	 */
	private void lanzarWeb(Lugar lugar) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("http://" + lugar.getUrl()));
		this.startActivity(i);
	}

	/**
	 * Lee la preferencia de InfoAmpliada
	 */
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

	/**
	 * Devuelve el valor de la pref. info Ampliada
	 * 
	 * @return
	 */
	public boolean getPreferenciaVerInfoAmpliada() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("ver_info_ampliada", false);
	}

	/**
	 * Al volver a la actividad recarga la lista.
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		listLugaresAdapter.actualizarDesdeDb();
		listLugaresAdapter.notifyDataSetChanged();
	}
}
