package com.example.osmeuslugares;

import java.util.Vector;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LugaresDb extends SQLiteOpenHelper {

	/**
	 * Atributos:
	 */
	private SQLiteDatabase db;
	private static String nombre = "lugares.db";
	private static CursorFactory factory = null;
	private static int version = 5;
	private static String sql;

	/**
	 * Constructor que recibe el contexto como parámetro.
	 * 
	 * @param context
	 */
	public LugaresDb(Context context) {
		super(context, nombre, factory, version);
	}

	/**
	 * Se crea la base...
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			crearTablaLugar(db);

			crearTablaCategoria(db);

			insertarCategoriasPrueba();

			insertarLugaresPrueba();

		} catch (SQLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}
	}

	/**
	 * Se ejecuta cuando se actualiza la app y la base ya está creada.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("INFO", "Base de datos: onUpgrade" + oldVersion + "->"
				+ newVersion);
		if (newVersion > oldVersion) {
			try {
				db.execSQL("DROP TABLE IF EXISTS lugar");
				db.execSQL("DROP INDEX IF EXISTS idx_lug_nombre");
				db.execSQL("DROP TABLE IF EXISTS categoria");
				db.execSQL("DROP INDEX IF EXISTS idx_cat_nombre");
			} catch (Exception e) {
				Log.e(this.getClass().toString(), e.getMessage());
			}
			onCreate(db);

			Log.i(this.getClass().toString(),
					"Base de datos actualizada. versión " + newVersion);
		}
	}

	/**
	 * Crea la tabla Lugar y un Indice por nombre del lugar.
	 * 
	 * @param db
	 */
	private void crearTablaLugar(SQLiteDatabase db) {
		sql = "CREATE TABLE lugar("
				+ "lug_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "lug_nombre TEXT NOT NULL, "
				+ "lug_categoria_id INTEGER NOT NULL," + "lug_direccion TEXT,"
				+ "lug_ciudad TEXT," + "lug_telefono TEXT, " + "lug_url TEXT,"
				+ "lug_comentario TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_lug_nombre ON Lugar(lug_nombre ASC)";
		db.execSQL(sql);
	}

	/**
	 * Crea la tabla categoría y un Indice por nombre de la categoría.
	 * 
	 * @param db
	 */
	private void crearTablaCategoria(SQLiteDatabase db) {
		sql = "CREATE TABLE Categoria("
				+ "cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat_nombre TEXT NOT NULL, " + "cat_icono TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_cat_nombre ON Categoria(cat_nombre ASC)";
		db.execSQL(sql);
	}

	/**
	 * Inserta categorías de prueba en la base de datos.
	 */
	private void insertarCategoriasPrueba() {
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Playas','icono_playa')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Restaurantes','icono_restaurante')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Hoteles','icono_hotel')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Otros','icono_otros')");
	}

	/**
	 * Inserta Lugares de prueba en la base de datos.
	 */
	private void insertarLugaresPrueba() {
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia de Riazor',1, 'Riazor','A Coruña','981000000','www.praiariazor.com','Playa con mucho oleaje.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia do Orzan',1, 'Orzan','A Coruña','981000000','www.praiaorzan.com','Preciosas vistas a las discotecas...')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Mesón Suso',2, 'Av de Madrid,6','Lugo','981000000','www.mesonsuso.com','Un buen sitio para comer carne a la brasa.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Hotel Paraiso',3, 'Av Buenos Aires,10','Vigo','986123456','www.hparaiso.com','Un lugar perfecto para dormir una siesta.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Club El Descanso',4, 'Carretera de Orense, s/n','Ourense','988000000','www.clubdescanso.com','Un lugar muy acojedor...')");
		Log.i("INFO", "Registros de prueba insertados");
	}

	/**
	 * Devuelve un Vector los lugares que hay en la base de datos.
	 * 
	 * @return
	 */
	public Vector<Lugar> cargarLugaresDesdeBD() {
		Vector<Lugar> vectorLugares = new Vector<Lugar>();
		SQLiteDatabase db = this.getReadableDatabase();// Abre la bd en modo
														// lectura.
		Cursor cursor = db.rawQuery("SELECT Lugar.*, cat_nombre, cat_icono "
				+ "FROM Lugar join Categoria on lug_categoria_id = cat_id",
				null);
		// Recorremos el cursor para extraer los Lugares uno a uno y guardarlos
		// en el vector:
		while (cursor.moveToNext()) {
			Lugar lugar = new Lugar();
			lugar.setId(cursor.getLong(0));
			lugar.setNombre(cursor.getString(cursor
					.getColumnIndex(Lugar.C_NOMBRE)));
			int idCategoria = cursor.getInt(cursor
					.getColumnIndex(Lugar.C_CATEGORIA_ID));
			String nombreCategoria = cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE));
			String iconoCategoria = cursor.getString(cursor
					.getColumnIndex(Categoria.C_ICONO));

			lugar.setCategoria(new Categoria(idCategoria, nombreCategoria,
					iconoCategoria));
			lugar.setDireccion(cursor.getString(cursor
					.getColumnIndex(Lugar.C_DIRECCION)));
			lugar.setCiudad(cursor.getString(cursor
					.getColumnIndex(Lugar.C_CIUDAD)));
			lugar.setTelefono(cursor.getString(cursor
					.getColumnIndex(Lugar.C_TELEFONO)));
			lugar.setUrl(cursor.getString(cursor.getColumnIndex(Lugar.C_URL)));
			lugar.setComentario(cursor.getString(cursor
					.getColumnIndex(Lugar.C_COMENTARIO)));

			vectorLugares.add(lugar);
		}
		return vectorLugares;
	}

	/**
	 * Recibe un booleano para saber si la carga de categorías la hace el
	 * spinner y asi incluir el Seleccionar... Devuelve un Vector con las
	 * categorias que hay en la bd.
	 * 
	 * @param opcSeleccionar
	 * @return
	 */
	public Vector<Categoria> cargarCategoriasDesdeBD(boolean opcSeleccionar) {
		Vector<Categoria> resultado = new Vector<Categoria>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM Categoria ORDER By cat_nombre", null);
		if (opcSeleccionar) {
			// Como es para un spinner incluir una primera opción por defecto
			resultado.add(new Categoria(0, "Seleccionar...", "icono_nd"));
		}

		// Recorremos el cursor para extraer las categorías una a una y
		// guardarlas en el vector:
		while (cursor.moveToNext()) {
			Categoria categoria = new Categoria();
			categoria
					.setId(cursor.getInt(cursor.getColumnIndex(Categoria.C_ID)));
			categoria.setNombre(cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE)));
			categoria.setIcono(cursor.getString(cursor
					.getColumnIndex(Categoria.C_ICONO)));
			resultado.add(categoria);
		}
		return resultado;
	}

	// --------------------Para Lugares------------------------------

	/**
	 * Recibe un lugar y lo inserta en la bd.
	 * 
	 * @param newLugar
	 */
	public void createLugar(Lugar newLugar) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Insertamos el registro en la base de datos.
		db.insert("Lugar", null, newLugar.getContentValues());
		Log.i(this.getClass().toString(), "Agregado un nuevo Lugar: "
				+ newLugar.toString());
	}

	/**
	 * Recibe un lugar y lo actualiza en la bd.
	 * 
	 * @param lugarEdit
	 */
	public void updateLugar(Lugar lugarEdit) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Actualizamos el registro en la base de datos.
		db.update("Lugar", lugarEdit.getContentValues(),
				"lug_id=" + lugarEdit.getId(), null);
		Log.i(this.getClass().toString(),
				"Lugar Actualizado: " + lugarEdit.toString());
	}

	/**
	 * Recibe un lugar y lo elimina de la bd.
	 * 
	 * @param lugarDel
	 */
	public void deleteLugar(Lugar lugarDel) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Eliminamos el registro de la base de datos.
		db.delete("Lugar", "lug_id=" + lugarDel.getId(), null);
		Log.i(this.getClass().toString(),
				"Lugar Eliminado: " + lugarDel.toString());
	}

	// --------------------Para categorias------------------------------

	/**
	 * Recibe una categoría y la inserta en la bd.
	 * 
	 * @param newCategoria
	 */
	public void createCategoria(Categoria newCategoria) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Insertamos el registro en la base de datos.
		db.insert("Categoria", null, newCategoria.getContentValues());
		Log.i(this.getClass().toString(), "Agregada una nueva categoria: "
				+ newCategoria.toString());
	}

	/**
	 * Recibe una categoría y la actualiza en la bd.
	 * 
	 * @param categoriaEdit
	 */
	public void updateCategoria(Categoria categoriaEdit) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Actualizamos el registro en la base de datos.
		db.update("Categoria", categoriaEdit.getContentValues(), "cat_id="
				+ categoriaEdit.getId(), null);
		Log.i(this.getClass().toString(), "Actualizando categoria con id="
				+ categoriaEdit.getId());
	}

	/**
	 * Recibe una categoría y la elimina de la bd.
	 * 
	 * @param categoriaDel
	 */
	public void deleteCategoria(Categoria categoriaDel) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Eliminamos el registro de la base de datos.
		db.delete("Categoria", "cat_id=" + categoriaDel.getId(), null);
		Log.i(this.getClass().toString(), "Categoria Eliminada: "
				+ categoriaDel.toString());
	}

}
