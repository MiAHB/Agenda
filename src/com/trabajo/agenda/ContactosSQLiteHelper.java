package com.trabajo.agenda;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactosSQLiteHelper extends SQLiteOpenHelper{
	
	public static final String ID_FILA = "_id";
	public static final String ID_NOMBRE = "nombre";
	public static final String ID_APELLIDOS = "apellidos";
	
	private static final String TABLA_NOMBRE = "contactos";
	private static final String BASEDATOS_NOMBRE = "bdContactos.db";
	private static final int BASEDATOS_VERSION = 1;
	private SQLiteDatabase baseDatos;
	
	public ContactosSQLiteHelper(Context context){
		this(context, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
	}
	
	public ContactosSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	//C�digo necesario para crear nuestra base de datos 
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlCreate = "CREATE TABLE " + TABLA_NOMBRE +"("
				+ ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ID_NOMBRE + " TEXT NOT NULL, "
				+ ID_APELLIDOS + " TEXT NOT NULL);";
		db.execSQL(sqlCreate);
		
	}

	//C�digo necesario para actualizar la estructura de nuestra base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente la opci�n de
        //      eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este m�todo deber�a ser m�s elaborado.
 
        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRE);
        //Se crea la nueva versi�n de la tabla
        onCreate(db);
	}
	
	public void abrir(){
		baseDatos = getWritableDatabase();		
	}
	
	public void cerrar(){
		close();
	}
	
	public void insertarContacto(String nombre, String apellidos){
		String sql = "INSERT INTO " + TABLA_NOMBRE +  "(" + ID_NOMBRE + ", " + ID_APELLIDOS + ") VALUES ('" + nombre + "', '" + apellidos + "')";
		baseDatos.execSQL(sql);	
	}
	
	public Cursor obtenerContactos(){
		String sql = "SELECT * FROM " + TABLA_NOMBRE + ";";
		return baseDatos.rawQuery(sql, null);
		
	}
	
	public void eliminarContacto(int id){
		String sql = "DELETE FROM " + TABLA_NOMBRE + " WHERE " + ID_FILA + " = '" + id + "'";
		baseDatos.execSQL(sql);
		
	}

	public Contacto getContacto(int id){
		String sql = "SELECT * FROM " + TABLA_NOMBRE + " WHERE " + ID_FILA + " = '" + id + "'";
		Cursor cursor = baseDatos.rawQuery(sql, null);
		cursor.moveToFirst();
		Contacto contacto = new Contacto(cursor.getString(1), cursor.getString(2));
		
		return contacto;
	}
		
	
	/*public Cursor buscarContactoPorId(int num){
		
	}*/
	
	

}
