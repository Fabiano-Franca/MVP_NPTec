package com.nptec.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nptec.mvp.R;
import com.nptec.mvp.dao.DownloadDados;
import com.nptec.mvp.dao.HttpService;
import com.nptec.mvp.model.Sensores;

import java.sql.Array;
import java.util.concurrent.ExecutionException;

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

                String sensorClidado = (String) adapterView.getItemAtPosition(i);
                Sensores sensores = null;
                if (sensorClidado.equals("Status do Tanque")){
                    Log.i("Sensor clicado:", sensorClidado);
                }else if (sensorClidado.equals("Média")){
                    Log.i("Sensor clicado:", sensorClidado);
                    try {
                        sensores = new HttpService().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent vaiParaMediaActivity = new Intent(ListaDeSensoresActivity.this, MediaActivity.class);
                    vaiParaMediaActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                    startActivity(vaiParaMediaActivity);
                }else if (sensorClidado.equals("Desvio Padrão")){
                    Log.i("Sensor clicado:", sensorClidado);
                }
                //Abrir tela de status de cada sensor
                /*TESTE JSON
                try {
                    new HttpService().execute().get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    private void configuraAdapter(ListView listaDeSensores) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
        String[] listaDeSensoresString = {"Status do Tanque", "Média","Desvio Padrão"};
        adapter.addAll( listaDeSensoresString );
        listaDeSensores.setAdapter(adapter);
    }
}