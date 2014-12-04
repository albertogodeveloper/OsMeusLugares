package com.example.osmeuslugares;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	LugaresDb lugaresDb;
	MediaPlayer musica;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			lugaresDb = new LugaresDb(getBaseContext());
		} catch (Exception e) {
			Log.e(getClass().toString(), e.getMessage());
		}

		Toast.makeText(getBaseContext(), "Base de datos preparada",
				Toast.LENGTH_LONG).show();
		
		this.musica= MediaPlayer.create(this, R.raw.musica_fondo);
		
	}

	private void leerPreferenciaMusica() {
		/* Leer preferencia de música */
		boolean reproducirMusica = getPreferenciaMusica();
		if (reproducirMusica) {
			Toast.makeText(getBaseContext(), "Música ON", Toast.LENGTH_LONG)
					.show();
			this.musica= MediaPlayer.create(this, R.raw.musica_fondo);
			musica.start();
		} else {
			Toast.makeText(getBaseContext(), "Musica OFF", Toast.LENGTH_LONG)
					.show();
			musica.stop();
		}
	}

	public boolean getPreferenciaMusica() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("musica", false);

	}

	public void onButtonClickLugar(View v) {

		Intent i = new Intent(this, ListLugaresActivity.class);
		startActivity(i);
	}
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		leerPreferenciaMusica();
		super.onStart();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		musica.stop();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.acerca_de: {
			Toast.makeText(this, "AcerDe", Toast.LENGTH_SHORT).show();
			lanzarAcercaDe();
			break;
		}
		case R.id.listLugares: {
			lanzarListadoLugares();
			break;
		}case R.id.listCategorias: {
			lanzarListadoCategorias();
			break;
		}		
		case R.id.action_settings: {
			lanzarPrefencias();
			break;
		}
		case R.id.salir: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	

	private void lanzarPrefencias() {
		Intent i = new Intent(this, PreferenciasActivity.class);
		startActivity(i);

	}

	private void lanzarListadoLugares() {
		Intent i = new Intent(this, ListLugaresActivity.class);
		startActivity(i);

	}
	private void lanzarListadoCategorias() {
		Intent i = new Intent(this, ListCategoriasActivity.class);
		startActivity(i);		
	}

	private void lanzarAcercaDe() {
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);

	}
}
