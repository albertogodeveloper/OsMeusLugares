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

	private SQLiteDatabase db;
	private static String nombre = "lugares.db";
	private static CursorFactory factory = null;
	private static int version = 2;
	private static String sql;

	public LugaresDb(Context context) {
		super(context, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			crearTablaLugar(db);

			crearTablaCategoria(db);

			insertarLugaresPrueba();
		} catch (SQLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}
	}

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

	private void crearTablaCategoria(SQLiteDatabase db) {
		sql = "CREATE TABLE Categoria("
				+ "cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat_nombre TEXT NOT NULL);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_cat_nombre ON Categoria(cat_nombre ASC)";
		db.execSQL(sql);
	}

	private void insertarLugaresPrueba() {
		db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Playas')");
		db.execSQL("INSERT INTO Categoria(cat_nombre) "
				+ "VALUES('Restaurantes')");
		db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Hoteles')");
		db.execSQL("INSERT INTO Categoria(cat_nombre) " + "VALUES('Otros')");

		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia de Riazor',1, 'Riazor','A Coru–a','981000000','','')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Praia do Orzan',1, 'Orzan','A Coru–a','981000000','','')");
		Log.i("INFO", "Registros de prueba insertados");
	}

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
					"Base de datos actualizada. versión 2");
		}
	}

	public Vector<Lugar> cargarLugaresDesdeBD() {
		Vector<Lugar> resultado = new Vector<Lugar>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT Lugar.*, cat_nombre "
				+ "FROM Lugar join Categoria on lug_categoria_id = cat_id",
				null);
		// Se podría usar query() en vez de rawQuery
		// join para recoger nombre categoria, previamente crear tabla de
		// categorias
		while (cursor.moveToNext()) {
			Lugar lugar = new Lugar();
			lugar.setId(cursor.getLong(0));
			lugar.setNombre(cursor.getString(cursor
					.getColumnIndex(Lugar.C_NOMBRE)));
			int idCategoria = cursor.getInt(cursor
					.getColumnIndex(Lugar.C_CATEGORIA_ID));
			String nombreCategoria = cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE));
			lugar.setCategoria(new Categoria(idCategoria, nombreCategoria));
			lugar.setDireccion(cursor.getString(cursor
					.getColumnIndex(Lugar.C_DIRECCION)));
			lugar.setCiudad(cursor.getString(cursor
					.getColumnIndex(Lugar.C_CIUDAD)));
			lugar.setTelefono(cursor.getString(cursor
					.getColumnIndex(Lugar.C_TELEFONO)));
			lugar.setUrl(cursor.getString(cursor.getColumnIndex(Lugar.C_URL)));
			lugar.setComentario(cursor.getString(cursor
					.getColumnIndex(Lugar.C_COMENTARIO)));
			resultado.add(lugar);
		}
		return resultado;
	}

	public Vector<Categoria> cargarCategoriasDesdeBD() {
		Vector<Categoria> resultado = new Vector<Categoria>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM Categoria ORDER By cat_nombre", null);
		// Como es para un spinner incluir una primera opción por defecto
		resultado.add(new Categoria(0, "Seleccionar..."));
		while (cursor.moveToNext()) {
			Categoria categoria = new Categoria();
			categoria
					.setId(cursor.getInt(cursor.getColumnIndex(Categoria.C_ID)));
			categoria.setNombre(cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE)));
			resultado.add(categoria);
		}
		return resultado;
	}

	public void createLugar(Lugar newLugar) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Insertamos el registro en la base de datos
		db.insert("Lugar", null, newLugar.getContentValues());
	}

	public void updateLugar(Lugar lugarEdit) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Actualizamos el registro en la base de datos
		db.update("Lugar", lugarEdit.getContentValues(),
				"lug_id=" + lugarEdit.getId(), null);
	}

	public void deleteLugar(Lugar lugarDel) {
		SQLiteDatabase db = this.getWritableDatabase();
		// Eliminamos el registro de la base de datos.
		db.delete("Lugar", "lug_id=" + lugarDel.getId(), null);
	}
}
