package com.example.osmeuslugares;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class CategoriasDb extends SQLiteOpenHelper {
	// variables estáticas
	private static String LOGTAG = "CategoirasDb";// mandar a registro de log

	private SQLiteDatabase db;// creamos esto para no llamarlo en insertar
								// lugares prueba

	private static String nombre = "LugaresDb";// nombre que le damos a la base
												// de datos
	private static CursorFactory factory = null;
	private static int version = 1;

	public static final int C_ID = 0;
	public static final int C_NOMBRE = 1;
	public static final int C_CATEGORIA = 2;
	public static final int C_DIRECCION = 3;
	public static final int C_CIUDAD = 4;
	public static final int C_TELEFONO = 5;
	public static final int C_URL = 6;
	public static final int C_COMENTARIO = 7;

	public CategoriasDb(Context context) {
		super(context, nombre, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	// on create solo se ejecuta cuando la base de datos no existe si se mete
	// más inserciones
	// tenemos que borrar y ejecutar otra vez. Hacer método a parte para poner
	// más datos en bd
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		this.db = db;

		// lanzar creación de la tabla
		Log.i(CategoriasDb.LOGTAG, "Creando base de datos....");
		// creamos tabla lugar
		String sql = "CREATE TABLE Categoria("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT NOT NULL);";

		// Log.i(LugaresDb.LOGTAG,sql);

		db.execSQL(sql);// ejecutamos el sql
		sql = "CREATE UNIQUE INDEX nombre ON Categoria(nombre ASC)";// crear
																	// indice
																	// por
																	// nombre
		db.execSQL(sql);

		// Log.i(LugaresDb.LOGTAG, "Base de datos creada");

		// datos de prueba
		insertarDatosPrueba();

	}

	private void insertarDatosPrueba() {

		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Playas')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Restaurantes')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Hoteles')");
		db.execSQL("INSERT INTO Categoria(nombre) " + "VALUES('Otros')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public Vector<Categoria> cargarDesdeBD() {
		Vector<Categoria> resultado = new Vector<Categoria>();// crear vector
																// vacío
		SQLiteDatabase db = this.getReadableDatabase();// abrir acceso a base
														// dde datos en modo
														// lectura
		Cursor cursor = db.rawQuery("SELECT * FROM Categoira ORDER BY nombre",
				null);// crear query a base de datos
		// Se podrá usar query() en vez de rawQuery
		// join para recoger nombre de categoria, hacer tabla de categoria antes
		while (cursor.moveToNext()) {
			Categoria categoria = new Categoria();
			categoria.setId(cursor.getLong(C_ID));
			categoria.setNombre(cursor.getString(C_NOMBRE));
			resultado.add(categoria);
		}
		return resultado;
	}
}
