package com.example.osmeuslugares;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ListLugares extends ListActivity {

	private ListLugaresAdapter listLugaresAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);

		listLugaresAdapter = new ListLugaresAdapter(this);
		listLugaresAdapter.abrir();
		setListAdapter(listLugaresAdapter);
	}

	public void onButtonClickAñadir(View v) {

		Intent i = new Intent(this, EditLugarActivity.class);
		i.putExtra("anadir", true);
		startActivity(i);
	}
	public void onButtonClickEditar(View v) {

		Intent i = new Intent(this, EditLugarActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_lugares, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
