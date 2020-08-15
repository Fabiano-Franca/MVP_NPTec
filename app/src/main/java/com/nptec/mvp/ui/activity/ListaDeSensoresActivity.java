package com.nptec.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nptec.mvp.R;

import java.sql.Array;

public class ListaDeSensoresActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_sensores);

        configuraLista();

    }

    private void configuraLista() {
        ListView listaDeSensores = findViewById(R.id.listaSensores_listaDeSensores_listView);
        configuraAdapter(listaDeSensores);
        configuraListenerDeCliquePorItem(listaDeSensores);
        registerForContextMenu(listaDeSensores);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeSensores) {
        listaDeSensores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Sensor clicado:", (String) adapterView.getItemAtPosition(i));
                //Abrir tela de status de cada sensor
            }
        });
    }

    private void configuraAdapter(ListView listaDeSensores) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
        String[] listaDeSensoresString = {"Temperatura","PH", "RPM", "C1", "C2", "T1", "T2"};
        adapter.addAll( listaDeSensoresString );
        listaDeSensores.setAdapter(adapter);
    }
}