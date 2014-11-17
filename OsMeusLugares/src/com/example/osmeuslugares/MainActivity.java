package com.example.osmeuslugares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		switch (item.getItemId()) {
		case R.id.acerca_de: {
			Toast.makeText(this, "AcerDe", Toast.LENGTH_SHORT).show();
			// lanzar acercade();
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
		if (id == R.id.action_settings) {
			return true;
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
}
