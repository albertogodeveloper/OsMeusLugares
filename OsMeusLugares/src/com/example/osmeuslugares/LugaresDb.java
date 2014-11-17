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

	private static String LOGTAG = "LugaresDb";
	private SQLiteDatabase db;
	private static String nombre = "lugares.db";
	private static CursorFactory factory = null;
	private static int version = 1;

	public static final int C_ID = 0;
	public static final int C_NOMBRE = 1;
	public static final int C_CATEGORIA_ID = 2;
	public static final int C_DIRECCION = 3;
	public static final int C_CIUDAD = 4;
	public static final int C_TELEFONO = 5;
	public static final int C_URL = 6;
	public static final int C_COMENTARIO = 7;
	public static final int C_CATEGORIA_NOMBRE = 8;

	public LugaresDb(Context context) {
		super(context, nombre, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
		try {
			String sql = "CREATE TABLE lugar("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "nombre TEXT NOT NULL, "
					+ "categoria_id INTEGER NOT NULL," + "direccion TEXT,"
					+ "ciudad TEXT," + "telefono TEXT, " + "url TEXT,"
					+ "comentario TEXT);";

			db.execSQL(sql);

			sql = "CREATE UNIQUE INDEX nombre_lugar ON Lugar(nombre ASC)";
			db.execSQL(sql);

			sql = "CREATE TABLE Categoria("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "nombre TEXT NOT NULL);";

			db.execSQL(sql);

			sql = "CREATE UNIQUE INDEX nombre_categoria ON Categoria(nombre ASC)";
			db.execSQL(sql);
			// Insertar datos de prueba
			insertarLugaresPrueba();
		} catch (SQLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}

	}

	private void insertarLugaresPrueba() {
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Playas')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Restaurantes')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Hoteles')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Otros')");

		db.execSQL("INSERT INTO Lugar(nombre, categoria_id, direccion, ciudad, telefono, url, comentario) "
				+ "VALUES('Playa Riazor',1, 'Riazor','A Coru–a','981000000','','')");
		db.execSQL("INSERT INTO Lugar(nombre, categoria_id, direccion, ciudad, telefono, url, comentario) "
				+ "VALUES('Playa Orzan',1, 'Orzan','A Coru–a','981000000','','')");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Vector<Lugar> cargarLugaresDesdeBD() {
		Vector<Lugar> resultado = new Vector<Lugar>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.rawQuery(
						"SELECT Lugar.*, Categoria.nombre "
								+ "FROM Lugar join Categoria on Lugar.categoria_id = Categoria._id",
						null);
		// Se podr’a usar query() en vez de rawQuery
		// join para recoger nombre categoria, previamente crear tabla de
		// categorias
		while (cursor.moveToNext()) {
			Lugar lugar = new Lugar();
			lugar.setId(cursor.getLong(C_ID));
			lugar.setNombre(cursor.getString(C_NOMBRE));
			Long idCategoria = cursor.getLong(C_CATEGORIA_ID);
			String nombreCategoria = cursor.getString(C_CATEGORIA_NOMBRE);
			lugar.setCategoria(new Categoria(idCategoria, nombreCategoria));
			lugar.setDireccion(cursor.getString(C_DIRECCION));
			lugar.setCiudad(cursor.getString(C_CIUDAD));
			lugar.setTelefono(cursor.getString(C_TELEFONO));
			lugar.setUrl(cursor.getString(C_URL));
			lugar.setComentario(cursor.getString(C_COMENTARIO));
			resultado.add(lugar);
		}
		return resultado;
	}

	public Vector<Categoria> cargarCategoriasDesdeBD() {
		Vector<Categoria> resultado = new Vector<Categoria>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM Categoria ORDER By nombre",
				null);
		while (cursor.moveToNext()) {
			Categoria categoria = new Categoria();
			categoria.setId(cursor.getLong(C_ID));
			categoria.setNombre(cursor.getString(C_NOMBRE));
			resultado.add(categoria);
		}
		return resultado;
	}

}
