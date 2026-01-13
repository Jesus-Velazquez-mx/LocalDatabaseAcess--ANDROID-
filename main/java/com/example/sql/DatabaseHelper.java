package com.example.sql;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "asistencia.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "asistentes";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDO_PATERNO = "apellido_paterno";
    public static final String COLUMN_APELLIDO_MATERNO = "apellido_materno";
    public static final String COLUMN_NUMERO_CONTROL = "numero_control";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ACTIVO = "activo";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_CODIGO + " INT PRIMARY KEY, " +
                    COLUMN_NOMBRE + " TEXT(100), " +
                    COLUMN_APELLIDO_PATERNO + " TEXT(100), " +
                    COLUMN_APELLIDO_MATERNO + " TEXT(100), " +
                    COLUMN_NUMERO_CONTROL + " TEXT(100), " +
                    COLUMN_EMAIL + " TEXT(100), " +
                    COLUMN_ACTIVO + " INT);";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
