package com.example.osmeuslugares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditLugarActivity extends Activity {

	/**
	 * Atributos.
	 */
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

	/**
	 * Asigna los ids de las cajasTxt al los atributos
	 */
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

		// Donde se guardará el lugar a editar:
		lugarEdit = new Lugar();
		// Carga el bundle del lugar a editar:
		Bundle extras = new Bundle();
		extras = getIntent().getExtras();
		add = extras.getBoolean("add");
		if (add) {
			Toast.makeText(getBaseContext(), "CREAR NUEVO LUGAR",
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getBaseContext(),
					"EDITAR " + extras.getString(Lugar.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			lugarEdit.setBundle(extras);

		}

		// Establecer valores desde lugarEdit a widget edición
		establecerValoresEditar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
		return true;
	}

	/**
	 * Menú con botón guardar, eliminar y cerrar.
	 */
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
			} else {
				alertaSimple("Tipo Lugar",
						"Debe de seleccionar una categoria para poder continuar");
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

	/**
	 * Comprueba que se ha seleccionado una categoría.
	 * 
	 * @return
	 */
	private Boolean comprobarCatSeleccionada() {
		int position = spinnerCategoria.getSelectedItemPosition();
		if (position == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Crea un lugar en la bd.
	 */
	private void crearLugarEnBd() {
		db.createLugar(getLugarDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Actualiza el lugar editado en la bd.
	 */
	private void actualizarLugarEnBd() {
		db.updateLugar(getLugarEdit());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Elimina el lugar de la bd.
	 */
	private void eliminarLugarEnBd() {
		db.deleteLugar(lugarEdit);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Crea el lugar y le da los valores introducidos en los campos.
	 * 
	 * @return
	 */
	private Lugar getLugarDesdeCampos() {

		lugarNuevo = new Lugar();
		lugarNuevo.setNombre(editTextNombre.getText().toString());
		int position = spinnerCategoria.getSelectedItemPosition();
		lugarNuevo
				.setCategoria((Categoria) categoriasAdapter.getItem(position));
		lugarNuevo.setCiudad(editTextCiudad.getText().toString());
		lugarNuevo.setDireccion(editTextDireccion.getText().toString());
		lugarNuevo.setTelefono(editTextTelefono.getText().toString());
		lugarNuevo.setUrl(editTextUrl.getText().toString());
		lugarNuevo.setComentario(editTextComentario.getText().toString());

		return lugarNuevo;
	}

	/**
	 * Le asigna los valores de los campos a el lugar editado.
	 * 
	 * @return
	 */
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

	/**
	 * Pone en las cajastxt los valores del lugar a editar.
	 */
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

	/**
	 * Lanza el ListCategoriaActivity.
	 * 
	 * @param v
	 */
	public void onButtonClickCategoria(View v) {
		lanzarListadoCategorias();
	}

	/**
	 * Lanza el listado de categorías.
	 */
	private void lanzarListadoCategorias() {
		Intent i = new Intent(this, ListCategoriasActivity.class);
		startActivity(i);
	}

	/**
	 * Muestra una alerta simple con el mensaje que le pasemos.
	 * 
	 * @param titulo
	 * @param mensaje
	 */
	private void alertaSimple(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setMessage(mensaje);
		builder.setPositiveButton("OK", null);
		builder.create();
		builder.show();
	}

	/**
	 * Recarga el spinner de categorias.
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		categoriasAdapter.cargarDatosDesdeBd();
		categoriasAdapter.notifyDataSetChanged();
	}
}