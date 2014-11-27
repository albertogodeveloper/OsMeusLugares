package com.example.osmeuslugares;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	LugaresDb lugaresDb;

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

		leerPreferenciaMusica();
	}

	private void leerPreferenciaMusica() {
		/* Leer preferencia de música */
		boolean reproducirMusica = getPreferenciaMusica();
		if (reproducirMusica) {
			Toast.makeText(getBaseContext(), "Música ON", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(getBaseContext(), "Musica OFF", Toast.LENGTH_LONG)
					.show();
		}
	}

	public boolean getPreferenciaMusica() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("musica", false);

	}

	public void onButtonClickLugar(View v) {

		Intent i = new Intent(this, ListLugares.class);
		startActivity(i);
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
		Intent i = new Intent(this, ListLugares.class);
		startActivity(i);

	}

	private void lanzarAcercaDe() {
		Intent i = new Intent(this, AcercaDeActivity.class);
		startActivity(i);

	}
}
