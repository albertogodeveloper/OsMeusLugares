package com.example.osmeuslugares;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class RecursoIcono {

	// Para iconos:
	private Activity activity;
	private Resources res;
	private TypedArray drawableIconosLugares;
	private List<String> valoresIconosLugares;

	public RecursoIcono(Activity activity) {
		super();
		this.activity = activity;
		cargarIconos(activity);
	}

	// PARA ICONOS:----------------------------------------------------------
	private void cargarIconos(Activity activity) {
		// Cargar recursos iconos
		res = activity.getResources();
		drawableIconosLugares = res.obtainTypedArray(R.array.iconos_lugares);
		valoresIconosLugares = (List<String>) Arrays.asList(res
				.getStringArray(R.array.valores_iconos_lugares));
	}

	public Drawable obtenerDrawableIcon(String icon) {
		res = activity.getResources();
		// Obtener la posición de icon en el array.
		int posicion = valoresIconosLugares.indexOf(icon);

		Log.i(this.getClass().toString(), "POSICION DEL ICONO EN EL ARRAY = "
				+ posicion);
		// -1 si no existe lo ponemos a 0 (icono ND: No Definido)
		if (posicion == -1) {
			posicion = 0;
		}
		return drawableIconosLugares.getDrawable(posicion);
	}
	
}
