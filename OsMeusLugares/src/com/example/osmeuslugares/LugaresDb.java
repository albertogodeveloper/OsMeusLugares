package com.example.osmeuslugares;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LugaresDb extends SQLiteOpenHelper {

	// variables estáticas
	private static String LOGTAG = "LugaresDb";// mandar a registro de log
	private SQLiteDatabase db;// creamos esto para no llamarlo en insertar
								// lugares prueba
	private static String nombre = "LugaresDb";// nombre que le damos a la base
												// de datos
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
	}

	@Override
	// on create solo se ejecuta cuando la base de datos no existe si se mete
	// más inserciones
	// tenemos que borrar y ejecutar otra vez. Hacer método a parte para poner
	// más datos en bd
	public void onCreate(SQLiteDatabase db) {
		this.db = db;

		// lanzar creación de la tabla
		Log.i(LugaresDb.LOGTAG, "Creando base de datos....");
		// creamos tabla lugar
		String sql = "CREATE TABLE Lugar("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT NOT NULL, "
				+ " categoria_id INTEGER NOT NULL, " + " direccion TEXT, "
				+ " ciudad TEXT, " + " telefono INTEGER, " + " url TEXT, "
				+ " comentario TEXT);";

		Log.i(LugaresDb.LOGTAG, sql);

		db.execSQL(sql);// ejecutamos el sql
		sql = "CREATE UNIQUE INDEX nombre ON lugar(nombre ASC)";// crear indice
																// por nombre
		db.execSQL(sql);

		Log.i(LugaresDb.LOGTAG, "Base de datos creada");

		// datos de prueba
		insertarDatosPrueba();

	}

	private void insertarDatosPrueba() {

		db.execSQL("INSERT INTO Lugar(nombre, categoria, direccion, ciudad, telefono, url, comentario) "
				+ "VALUES('Playa Riazor',1, 'Riazor','A Coru–a','981000000','','')");
		db.execSQL("INSERT INTO Lugar(nombre, categoria, direccion, ciudad, telefono, url, comentario) "
				+ "VALUES('Playa Orzan',1, 'Orzan','A Coru–a','981000000','','')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public Vector<Lugar> cargarListadoLugaresDesdeBD() {
		Vector<Lugar> resultado = new Vector<Lugar>();// crear vector vacío
		SQLiteDatabase db = this.getReadableDatabase();// abrir acceso a base de
														// datos en modo lectura
		Cursor cursor = db
				.rawQuery(
						"SELECT Lugar.*, Categoria.nombre "
								+ "FROM Lugar"
								+ " JOIN Categoria ON Lugar.categoria_id = Categoria._id",
						null);// crear query a base de datos
		// Se podrá usar query() en vez de rawQuery
		// join para recoger nombre de categoria, hacer tabla de categoria antes
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

}
