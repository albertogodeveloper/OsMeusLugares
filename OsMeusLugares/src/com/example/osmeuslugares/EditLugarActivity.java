package com.example.osmeuslugares;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
	private MenuItem btnEliminar;
	CategoriasAdapter categoriasAdapter;
	private boolean add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);
		// Botón eliminar para usar visibilidad,si va ser para crear un lugar no
		// se muestra el boton.
		
		// btnEliminar = (Button) findViewById(R.id.btnEliminar);---------------------------------------------------------------------------------
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
//			btnEliminar = (MenuItem) findViewById(R.id.eliminar_edLugar);
			btnEliminar.setVisible(true);
			Toast.makeText(getBaseContext(),
					"EDITAR " + extras.getString(Lugar.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			lugarEdit.setBundle(extras);
			
		}

		// Establecer valores desde lugarEdit a widget edición
		establecerValoresEditar();
	}

	private void crearLugarEnBd() {
		db.createLugar(getLugarDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private void actualizarLugarEnBd() {
		db.updateLugar(getLugarEdit());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private void eliminarLugarEnBd() {
		db.deleteLugar(lugarEdit);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
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

	private Lugar getLugarEdit() {
		lugarEdit.setNombre(editTextNombre.getText().toString());
		int position = spinnerCategoria.getSelectedItemPosition();
		lugarEdit.setCategoria((Categoria) categoriasAdapter.getItem(position));
		lugarEdit.setCiudad(editTextCiudad.getText().toString());
		lugarEdit.setDireccion(editTextDireccion.getText().toString());
		lugarEdit.setTelefono(editTextTelefono.getText().toString());
		lugarEdit.setUrl(editTextUrl.getText().toString());
		lugarEdit.setComentario(editTextComentario.getText().toString());
		return lugarEdit;
	}

	private void establecerValoresEditar() {

		editTextNombre.setText(lugarEdit.getNombre());

		int position = 0;
		if (!add) {
			position = categoriasAdapter.getPositionById(lugarEdit.getCategoria().getId());
		}
		spinnerCategoria.setSelection(position);
		editTextCiudad.setText(lugarEdit.getCiudad());
		editTextDireccion.setText(lugarEdit.getDireccion());
		editTextTelefono.setText(lugarEdit.getTelefono());
		editTextUrl.setText(lugarEdit.getUrl());
		editTextComentario.setText(lugarEdit.getComentario());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.guardar_edLugar: {
			if (comprobarCatSeleccionada()) {
				if (add) {
					crearLugarEnBd();
				} else {
					actualizarLugarEnBd();
				}
				finish();	
			}else{
				alertaSimple("Tipo Lugar", "Debe de seleccionar una categoria para poder continuar");
			}
			
			break;
		}
		case R.id.eliminar_edLugar: {
			eliminarLugarEnBd();
			finish();
			break;
		}
		case R.id.cerrar_edLugar: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private Boolean comprobarCatSeleccionada() {
		int position = spinnerCategoria.getSelectedItemPosition();
		if (position == 0) {
			return false;
		}else{
			return true;
		}
	}
	
	
	private void alertaSimple(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setMessage(mensaje);
		builder.setPositiveButton("OK", null);
		builder.create();
		builder.show();
	}
}
