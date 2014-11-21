package com.example.osmeuslugares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditLugarActivity extends Activity {

	private Lugar lugarNuevo;
	private Lugar lugarEdit;
	private LugaresDb db = new LugaresDb(this);
	private Spinner spinnerCategoria;
	private TextView editTextNombre;
	private TextView editTextCiudad;
	private TextView editTextDireccion;
	private TextView editTextTelefono;
	private TextView editTextUrl;
	private TextView editTextComentario;

	CategoriasAdapter categoriasAdapter;
	private boolean add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);

		// Nombre
		editTextNombre = (TextView) findViewById(R.id.editNombre);
		// Categoria
		spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
		categoriasAdapter = new CategoriasAdapter(this);
		spinnerCategoria.setAdapter(categoriasAdapter);
		// Ciudad
		editTextCiudad = (TextView) findViewById(R.id.editCiudad);
		// Direccion
		editTextDireccion = (TextView) findViewById(R.id.editDireccion);
		// Telefono
		editTextTelefono = (TextView) findViewById(R.id.editTelefono);
		// Url
		editTextUrl = (TextView) findViewById(R.id.editUrl);
		// Comentario
		editTextComentario = (TextView) findViewById(R.id.editComentario);

		lugarEdit = new Lugar();
		Bundle extras = new Bundle();
		extras = getIntent().getExtras();
		add = extras.getBoolean("add");
		if (add) {
			Toast.makeText(getBaseContext(), "CREAR NUEVO LUGAR",
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getBaseContext(), extras.getString(Lugar.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			lugarEdit.setBundle(extras);
		}

		// Establecer valores desde lugarEdit a widget edición
		establecerValoresEditar();

	}

	public void onButtonClickGuardarNuevo(View v) {
		if (add) {
			crearLugarEnBd();
			lanzarListadoLugares();
			finish();

		} else {
			actualizarLugarEnBd();
			lanzarListadoLugares();
			finish();
		}
	}

	private void crearLugarEnBd() {
		db.createLugar(getLugarDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}
	private void actualizarLugarEnBd(){
		
	    int id=(db.buscarIdEnLugares(lugarEdit.getNombre()));
		db.updateLugar(id,getLugarDesdeCampos());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private Lugar getLugarDesdeCampos() {
		lugarNuevo = new Lugar();
		lugarNuevo.setNombre(editTextNombre.getText().toString());
		int position = spinnerCategoria.getSelectedItemPosition();
		lugarNuevo.setCategoria((Categoria) categoriasAdapter.getItem(position));
		lugarNuevo.setCiudad(editTextCiudad.getText().toString());
		lugarNuevo.setDireccion(editTextDireccion.getText().toString());
		lugarNuevo.setTelefono(editTextTelefono.getText().toString());
		lugarNuevo.setUrl(editTextUrl.getText().toString());
		lugarNuevo.setComentario(editTextComentario.getText().toString());
		return lugarNuevo;
	}

	private void establecerValoresEditar() {

		editTextNombre.setText(lugarEdit.getNombre());

		int position = 0;
		if (!add) {
			position = categoriasAdapter.getPositionById(lugarEdit
					.getCategoria().getId());
		}
		spinnerCategoria.setSelection(position);
		editTextCiudad.setText(lugarEdit.getCiudad());
		editTextDireccion.setText(lugarEdit.getDireccion());
		editTextTelefono.setText(lugarEdit.getTelefono());
		editTextUrl.setText(lugarEdit.getUrl());
		editTextComentario.setText(lugarEdit.getComentario());
	}

	private void lanzarListadoLugares() {
		Intent i = new Intent(this, ListLugares.class);
		startActivity(i);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
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
