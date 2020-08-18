package com.nptec.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.nptec.mvp.R;
import com.nptec.mvp.dao.DownloadDados;
import com.nptec.mvp.dao.HttpService;
import com.nptec.mvp.dao.Utils;
import com.nptec.mvp.model.Sensores;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ListaDeSensoresActivity extends AppCompatActivity {
    ProgressDialog load;
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
                    try {
                        sensores = new HttpService().execute("view-last-data").get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent vaiParaMediaActivity = new Intent(ListaDeSensoresActivity.this, MediaActivity.class);
                    vaiParaMediaActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                    startActivity(vaiParaMediaActivity);

                }else if (sensorClidado.equals("Média")){
                    Log.i("Sensor clicado:", sensorClidado);
                    try {
                        sensores = new HttpService().execute("view-all-average").get();
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


                    try {
                        sensores = new HttpService().execute("view-all-standard-deviation").get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent vaiParaMediaActivity = new Intent(ListaDeSensoresActivity.this, MediaActivity.class);
                    vaiParaMediaActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                    startActivity(vaiParaMediaActivity);
                }

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

    class HttpService extends AsyncTask<String, Void, Sensores> {

    /*
    * url_base = victortirano.pythonanywhere.com
    média aritmética = url_base/view-all-average
    desvio padrão = url_base/view-all-standard-deviation
    último dado = url_base/view-last-data
    todos os dados = url_base/view-all-data
    * */

        @Override
        protected void onPreExecute(){
            Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " +  Thread.currentThread().getName());
            load = ProgressDialog.show(ListaDeSensoresActivity.this, "Por favor Aguarde ...","Baixando Imagem ...");
        }

        @Override
        protected Sensores doInBackground(String... params) {
            StringBuilder resposta = new StringBuilder();
            URL url = null;
            try {
                if(params.length > 0){
                    url = new URL("http://victortirano.pythonanywhere.com/" + params[0]);
                }else{
                    url = new URL("http://victortirano.pythonanywhere.com/" + params.toString());
                }


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                //connection.setReadTimeout(5000);
                //connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Sensores sensores = new Utils().extraiSensor(resposta.toString());
            //Sensores sensores = new Gson().fromJson(resposta.toString(), Sensores.class);
            Log.i("Resposta:", sensores.toString());

            //List<Sensores> produtos = new Gson().fromJson(resposta.toString(), new TypeToken<List<Sensores>>(){}.getType());

            return sensores;
        }

        @Override
        protected void onPostExecute(Sensores sensores) {
            super.onPostExecute(sensores);
            Log.i("AsyncTask", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());
            load.dismiss();
        }
    }
}

