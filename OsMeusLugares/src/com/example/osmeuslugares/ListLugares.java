package com.example.osmeuslugares;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ListLugares extends ListActivity {

	private ListLugaresAdapter listLugaresAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);

		listLugaresAdapter = new ListLugaresAdapter(this);
		setListAdapter(listLugaresAdapter);
	}

	public void imageButtonAddLugarOnClick(View v) {
		Bundle extras = new Bundle();
		extras.putBoolean("add", true);
		lanzarEditLugar(extras);
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

	@Override
	protected void onRestart() {
		super.onRestart();
		listLugaresAdapter.actualizarDesdeDb();
		listLugaresAdapter.notifyDataSetChanged();
	}

}
