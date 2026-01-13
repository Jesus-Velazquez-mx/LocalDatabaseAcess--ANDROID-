package com.example.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class AsistenteDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public AsistenteDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int siguienteId() {
        int folio = 1;
        Cursor cursor = null;

        try {
            String SELECT = "SELECT MAX(" + DatabaseHelper.COLUMN_CODIGO + ") FROM " + DatabaseHelper.TABLE_NAME;
            cursor = database.rawQuery(SELECT, null);

            if (cursor != null && cursor.moveToFirst()) {
                folio = cursor.isNull(0) ? 1 : cursor.getInt(0) + 1;  // Si MAX es NULL, inicia desde 1
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return folio;
    }


    public long insertarAsistente(int codigo, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroControl, String email, int activo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CODIGO, codigo);
        values.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(DatabaseHelper.COLUMN_APELLIDO_PATERNO, apellidoPaterno);
        values.put(DatabaseHelper.COLUMN_APELLIDO_MATERNO, apellidoMaterno);
        values.put(DatabaseHelper.COLUMN_NUMERO_CONTROL, numeroControl);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_ACTIVO, activo);
        return database.insert(DatabaseHelper.TABLE_NAME, null, values);
    }


    public long actualizarAsistente(int codigo, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroControl, String email, int activo) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(DatabaseHelper.COLUMN_APELLIDO_PATERNO, apellidoPaterno);
        values.put(DatabaseHelper.COLUMN_APELLIDO_MATERNO, apellidoMaterno);
        values.put(DatabaseHelper.COLUMN_NUMERO_CONTROL, numeroControl);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_ACTIVO, activo);
        String[] whereArgs = new String[]{String.valueOf(codigo)};
        return database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_CODIGO + "=?", whereArgs);
    }

        public long eliminarAsistente(int codigo) {
            String[] whereArgs = new String[]{String.valueOf(codigo)};
            return database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_CODIGO + "=?", whereArgs);
        }

    public Asistente getAsistente(int codigo) {
        Asistente asistente = new Asistente();
        String [] whereArgs = new String[] {String.valueOf(codigo)};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                new String[]{
                        DatabaseHelper.COLUMN_CODIGO,
                        DatabaseHelper.COLUMN_NOMBRE,
                        DatabaseHelper.COLUMN_APELLIDO_PATERNO,
                        DatabaseHelper.COLUMN_APELLIDO_MATERNO,
                        DatabaseHelper.COLUMN_NUMERO_CONTROL,
                        DatabaseHelper.COLUMN_EMAIL,
                        DatabaseHelper.COLUMN_ACTIVO

                },
                DatabaseHelper.COLUMN_CODIGO + "=?", whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE));
            @SuppressLint("Range") String apellidoPaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_PATERNO));
            @SuppressLint("Range") String apellidoMaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_MATERNO));
            @SuppressLint("Range") String numeroControl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMERO_CONTROL));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
            @SuppressLint("Range") int activo = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVO));

            asistente.setCodigo(codigo);
            asistente.setNombre(nombre);
            asistente.setApellidoPaterno(apellidoPaterno);
            asistente.setApellidoMaterno(apellidoMaterno);
            asistente.setNumeroControl(numeroControl);
            asistente.setEmail(email);
            asistente.setActivo(activo);
        }
        cursor.close();
        return asistente;
    }

    public Asistente getAsistente(String Nombre) {
        Asistente asistente = new Asistente();
        String [] whereArgs = new String[] {Nombre};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                new String[]{
                        DatabaseHelper.COLUMN_CODIGO,
                        DatabaseHelper.COLUMN_NOMBRE,
                        DatabaseHelper.COLUMN_APELLIDO_PATERNO,
                        DatabaseHelper.COLUMN_APELLIDO_MATERNO,
                        DatabaseHelper.COLUMN_NUMERO_CONTROL,
                        DatabaseHelper.COLUMN_EMAIL,
                        DatabaseHelper.COLUMN_ACTIVO

                },
                DatabaseHelper.COLUMN_CODIGO + "=?", whereArgs, null, null, null);


        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int codigo = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CODIGO));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE));
            @SuppressLint("Range") String apellidoPaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_PATERNO));
            @SuppressLint("Range") String apellidoMaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_MATERNO));
            @SuppressLint("Range") String numeroControl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMERO_CONTROL));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
            @SuppressLint("Range") int activo = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVO));

            asistente.setCodigo(codigo);
            asistente.setNombre(nombre);
            asistente.setApellidoPaterno(apellidoPaterno);
            asistente.setApellidoMaterno(apellidoMaterno);
            asistente.setNumeroControl(numeroControl);
            asistente.setEmail(email);
        }
        cursor.close();
        return asistente;
    }

    public long borradoLogico(int codigo) {
        Asistente asistente = getAsistente(codigo);
        if (asistente != null) {
            int nuevoActivo = (asistente.getActivo() == 1) ? 0 : 1;

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ACTIVO, nuevoActivo);

            String[] whereArgs = new String[]{String.valueOf(codigo)};
            return database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_CODIGO + "=?", whereArgs);
        }
        return -1;
    }


    public List<String> obtenerAsistentes() {
        List<String> asistentes = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                new String[]{
                        DatabaseHelper.COLUMN_CODIGO,
                        DatabaseHelper.COLUMN_NOMBRE,
                        DatabaseHelper.COLUMN_APELLIDO_PATERNO,
                        DatabaseHelper.COLUMN_APELLIDO_MATERNO,
                        DatabaseHelper.COLUMN_NUMERO_CONTROL,
                        DatabaseHelper.COLUMN_EMAIL,
                        DatabaseHelper.COLUMN_ACTIVO

                },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String codigo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CODIGO));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                asistentes.add(nombre + " - " + email);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return asistentes;
    }

    public List<Asistente> getAsistentes() {
        List<Asistente> asistentes = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME,
                new String[]{
                        DatabaseHelper.COLUMN_CODIGO,
                        DatabaseHelper.COLUMN_NOMBRE,
                        DatabaseHelper.COLUMN_APELLIDO_PATERNO,
                        DatabaseHelper.COLUMN_APELLIDO_MATERNO,
                        DatabaseHelper.COLUMN_NUMERO_CONTROL,
                        DatabaseHelper.COLUMN_EMAIL,
                        DatabaseHelper.COLUMN_ACTIVO

                },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int codigo = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CODIGO));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE));
                @SuppressLint("Range") String apellidoPaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_PATERNO));
                @SuppressLint("Range") String apellidoMaterno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_APELLIDO_MATERNO));
                @SuppressLint("Range") String numeroControl = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NUMERO_CONTROL));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                @SuppressLint("Range") int activo = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ACTIVO));

                asistentes.add(new Asistente(codigo, nombre, apellidoPaterno, apellidoMaterno, numeroControl, email, activo));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return asistentes;
    }
}
