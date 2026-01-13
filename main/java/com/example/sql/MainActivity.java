package com.example.sql;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AsistenteDAO asistenteDAO;

    private EditText edtCodigo, edtNombre, edtApellidoPaterno, edtApellidoMaterno, edtNumeroControl, edtEmail, edtActivo;
    private Button btnInsertar, btnActualizar, btnEliminar, btnBorrarDesborrar, btnConsultar, btnMostrarRegistros, btnSalir;

    private boolean regNuevo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asistenteDAO = new AsistenteDAO(this);
        asistenteDAO.open();

        edtCodigo = findViewById(R.id.etCodigo);
        edtNombre = findViewById(R.id.etNombre);
        edtApellidoPaterno = findViewById(R.id.etApellidoPaterno);
        edtApellidoMaterno = findViewById(R.id.etApellidoMaterno);
        edtNumeroControl = findViewById(R.id.etNumControl);
        edtEmail = findViewById(R.id.etEmail);
        edtActivo = findViewById(R.id.etActivo);

        btnInsertar = findViewById(R.id.btnAgregar);
        btnActualizar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnDelFisico);
        btnBorrarDesborrar = findViewById(R.id.btnDelLogico);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnMostrarRegistros = findViewById(R.id.btnMostrarRegistros);

        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> {
            finish();
        });

        btnInsertar.setOnClickListener(v -> onInsertar());
        btnActualizar.setOnClickListener(v -> onActualizar());
        btnEliminar.setOnClickListener(v -> onEliminar());
        btnConsultar.setOnClickListener(v -> onConsultar());
        btnMostrarRegistros.setOnClickListener(v -> listarAsistentes());
        btnBorrarDesborrar.setOnClickListener(v -> onBorrarDesborrar());


        actualizaEstadoBotones("10011");
        edtActivo.setEnabled(false);

    }

    private void onInsertar() {
        if (btnInsertar.getText().equals("Nuevo")) {
            regNuevo = true;
            limpiarCampos();
            activarCampos(true);
            edtNombre.requestFocus();
            btnInsertar.setText("Guardar");
            btnActualizar.setText("Cancelar");
            actualizaEstadoBotones("11000");
            edtCodigo.setText(String.valueOf(asistenteDAO.siguienteId()));
            edtActivo.setText("1");
            edtActivo.setEnabled(false);
        } else {
            if (regNuevo) {
                try {
                    int codigo = Integer.parseInt(edtCodigo.getText().toString());
                    String nombre = edtNombre.getText().toString();
                    String apellidoPaterno = edtApellidoPaterno.getText().toString();
                    String apellidoMaterno = edtApellidoMaterno.getText().toString();
                    String numeroControl = edtNumeroControl.getText().toString();
                    String email = edtEmail.getText().toString();
                    int activo = Integer.parseInt(edtActivo.getText().toString());

                    long result = asistenteDAO.insertarAsistente(codigo, nombre, apellidoPaterno, apellidoMaterno, numeroControl, email, activo);

                    if (result != -1) {
                        Toast.makeText(this, "Asistente insertado con éxito.", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                        activarCampos(false);
                    } else {
                        Toast.makeText(this, "Error al insertar asistente.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Datos inválidos.", Toast.LENGTH_SHORT).show();
                }
            }

            btnInsertar.setText("Nuevo");
            btnActualizar.setText("Modificar");
            actualizaEstadoBotones("10011");
            limpiarCampos();
            regNuevo = false;
        }
    }


    private void onActualizar() {
        if (btnActualizar.getText().equals("Cancelar")) {
            limpiarCampos();
            activarCampos(false);
            btnInsertar.setText("Nuevo");
            btnActualizar.setText("Modificar");
            actualizaEstadoBotones("10011");
            regNuevo = false;
            edtActivo.setEnabled(false);
        } else if (btnActualizar.getText().equals("Guardar")) {
            try {
                int codigo = Integer.parseInt(edtCodigo.getText().toString());
                String nombre = edtNombre.getText().toString();
                String apellidoPaterno = edtApellidoPaterno.getText().toString();
                String apellidoMaterno = edtApellidoMaterno.getText().toString();
                String numeroControl = edtNumeroControl.getText().toString();
                String email = edtEmail.getText().toString();
                int activo = Integer.parseInt(edtActivo.getText().toString());

                long result = asistenteDAO.actualizarAsistente(codigo, nombre, apellidoPaterno, apellidoMaterno, numeroControl, email, activo);

                if (result > 0) {
                    Toast.makeText(this, "Asistente actualizado con éxito.", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    activarCampos(false);  // Deshabilitar los campos después de guardar
                } else {
                    Toast.makeText(this, "Error al actualizar asistente.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Datos inválidos.", Toast.LENGTH_SHORT).show();
            }

            btnActualizar.setText("Modificar");
            btnInsertar.setText("Nuevo");
            actualizaEstadoBotones("10011");
            regNuevo = false;
        } else {
            activarCampos(true);
            edtNombre.requestFocus();
            btnActualizar.setText("Guardar");
            btnInsertar.setText("Cancelar");
            actualizaEstadoBotones("11000");
        }
    }


    private void onEliminar() {
        try {
            int codigo = Integer.parseInt(edtCodigo.getText().toString());
            long result = asistenteDAO.eliminarAsistente(codigo);

            if (result > 0) {
                Toast.makeText(this, "Asistente eliminado con éxito.", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Asistente no encontrado.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos.", Toast.LENGTH_SHORT).show();
        }

        actualizaEstadoBotones("10011");
    }

    private void onBorrarDesborrar() {
        try {
            int codigo = Integer.parseInt(edtCodigo.getText().toString());

            long result = asistenteDAO.borradoLogico(codigo);

            if (result > 0) {
                Toast.makeText(this, "Asistente actualizado correctamente.", Toast.LENGTH_SHORT).show();

                Asistente asistente = asistenteDAO.getAsistente(codigo);
                edtActivo.setText(String.valueOf(asistente.getActivo()));
            } else {
                Toast.makeText(this, "Asistente no encontrado o error al actualizar.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos.", Toast.LENGTH_SHORT).show();
        }
    }


    private void onConsultar() {
        try {
            int codigo = Integer.parseInt(edtCodigo.getText().toString());
            Asistente asistente = asistenteDAO.getAsistente(codigo);

            if (asistente != null && asistente.getCodigo() != 0) {
                edtNombre.setText(asistente.getNombre());
                edtApellidoPaterno.setText(asistente.getApellidoPaterno());
                edtApellidoMaterno.setText(asistente.getApellidoMaterno());
                edtNumeroControl.setText(asistente.getNumeroControl());
                edtEmail.setText(asistente.getEmail());
                edtActivo.setText(String.valueOf(asistente.getActivo()));
                actualizaEstadoBotones("11111");
            } else {
                Toast.makeText(this, "Asistente no encontrado.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void listarAsistentes() {
        Intent intent = new Intent(MainActivity.this, MostrarRegistrosActivity.class);
        startActivity(intent);
    }

    private void limpiarCampos() {
        edtCodigo.setText("");
        edtNombre.setText("");
        edtApellidoPaterno.setText("");
        edtApellidoMaterno.setText("");
        edtNumeroControl.setText("");
        edtEmail.setText("");
        edtActivo.setText("");
    }

    private void activarCampos(boolean estado) {
        edtNombre.setEnabled(estado);
        edtApellidoPaterno.setEnabled(estado);
        edtApellidoMaterno.setEnabled(estado);
        edtNumeroControl.setEnabled(estado);
        edtEmail.setEnabled(estado);
    }

    private void actualizaEstadoBotones(String estados) {
        btnInsertar.setVisibility(estados.charAt(0) == '1' ? View.VISIBLE : View.GONE);
        btnActualizar.setVisibility(estados.charAt(1) == '1' ? View.VISIBLE : View.GONE);
        btnEliminar.setVisibility(estados.charAt(2) == '1' ? View.VISIBLE : View.GONE);
        btnBorrarDesborrar.setVisibility(estados.charAt(2) == '1' ? View.VISIBLE : View.GONE);
        btnConsultar.setVisibility(estados.charAt(3) == '1' ? View.VISIBLE : View.GONE);
        btnMostrarRegistros.setVisibility(estados.charAt(4) == '1' ? View.VISIBLE : View.GONE);

        if (estados.equals("10011")) {
            activarCampos(false);
            edtCodigo.setEnabled(true);  
        }
    }
}
