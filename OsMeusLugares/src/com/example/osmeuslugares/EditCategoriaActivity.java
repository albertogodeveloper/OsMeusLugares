package com.example.osmeuslugares;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EditCategoriaActivity extends Activity {

	private Categoria categoriaNueva;
	private Categoria categoriaEdit;
	private LugaresDb db = new LugaresDb(this);
	private Bundle extras;
	private TextView editTextNombre;
	private Spinner spinnerIcono;

	private boolean add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_categoria);

		// Nombre
		editTextNombre = (TextView) findViewById(R.id.editNombreCat);
		// Categoria
		spinnerIcono = (Spinner) findViewById(R.id.spinnerIcono);

		categoriaEdit = new Categoria();

		extras = new Bundle();
		extras = getIntent().getExtras();
		add = extras.getBoolean("add");

		if (add) {
			Toast.makeText(getBaseContext(), "CREAR NUEVA CATEGORIA",
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getBaseContext(),
					"EDITAR " + extras.getString(Categoria.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			categoriaEdit.setBundle(extras);

		}

		// Establecer valores desde categoriaEdit a widget edición
		establecerValoresEditar();
	}

	private void establecerValoresEditar() {

		editTextNombre.setText(categoriaEdit.getNombre());

		int position = 0;
		if (!add) {

			position = obtenerPosicionIcono(categoriaEdit.getIcono());
		}
		spinnerIcono.setSelection(position);

	}

	private int obtenerPosicionIcono(String icono) {
		SpinnerAdapter sa = this.spinnerIcono.getAdapter();
		String txt;
		int pos = -1;
		for (int i = 0; i < sa.getCount(); i++) {
			txt = (String) sa.getItem(i);
			if (txt.equals(icono)) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_categoria, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.guardar_edCat: {
			if (comprobarIconoSeleccionado()) {
				if (add) {
					crearCategoriaEnBd();
				} else {
					actualizarCategoriaEnBd();
				}
				finish();
			} else {
				alertaSimple("Icono",
						"Debe de seleccionar un icono para esa categoria");
			}

			break;
		}
		case R.id.eliminar_edCat: {
			eliminarCategoriaEnBd();
			finish();
			break;
		}
		case R.id.cerrar_edCat: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private Categoria getCategoriaDesdeCampos() {

		categoriaNueva = new Categoria();
		categoriaNueva.setNombre(editTextNombre.getText().toString());
		categoriaNueva.setIcono(spinnerIcono.getSelectedItem().toString());

		return categoriaNueva;
	}

	private Categoria getCategoriaEdit() {
		categoriaEdit.setNombre(editTextNombre.getText().toString());
		categoriaEdit.setIcono(spinnerIcono.getSelectedItem().toString());
		return categoriaEdit;
	}

	private void crearCategoriaEnBd() {
		db.createCategoria(getCategoriaDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private void actualizarCategoriaEnBd() {
		db.updateCategoria(getCategoriaEdit());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private void eliminarCategoriaEnBd() {
		db.deleteCategoria(categoriaEdit);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private Boolean comprobarIconoSeleccionado() {
		int position = spinnerIcono.getSelectedItemPosition();
		if (position == 0) {
			return false;
		} else {
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
