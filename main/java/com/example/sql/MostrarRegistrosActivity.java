package com.example.sql;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MostrarRegistrosActivity extends AppCompatActivity {
    private Button btnRegresar;


    private ListView listViewRegistros;
    private AsistenteDAO asistenteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_registros);

        listViewRegistros = findViewById(R.id.listViewRegistros);

        btnRegresar = findViewById(R.id.btnRegresar);
        listViewRegistros = findViewById(R.id.listViewRegistros);

        btnRegresar.setOnClickListener(v -> {
            finish();
        });

        asistenteDAO = new AsistenteDAO(this);
        asistenteDAO.open();

        cargarRegistros();
    }

    private void cargarRegistros() {
        List<Asistente> asistentes = asistenteDAO.getAsistentes();

        String[] registros = new String[asistentes.size()];
        for (int i = 0; i < asistentes.size(); i++) {
            Asistente asistente = asistentes.get(i);
            registros[i] = asistente.getCodigo() + " - " + asistente.getNombre() + " " +
                    asistente.getApellidoPaterno() + " " + asistente.getApellidoMaterno() +
                    " - " + asistente.getNumeroControl() + " - " + asistente.getEmail() +
                    " - Activo: " + asistente.getActivo();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                registros
        );
        listViewRegistros.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asistenteDAO.close();
    }
}
