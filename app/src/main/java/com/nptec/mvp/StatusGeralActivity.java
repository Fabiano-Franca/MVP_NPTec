package com.nptec.mvp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nptec.mvp.dao.Utils;
import com.nptec.mvp.model.Sensores;
import com.nptec.mvp.ui.activity.ListaDeSensoresActivity;
import com.nptec.mvp.ui.activity.MediaActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class StatusGeralActivity extends AppCompatActivity {

    SeekBar temperaturaSeekBar;
    TextView valorTemperatura;
    TextView statusTemperatura;
    SeekBar phSeekBar;
    TextView valorPh;
    TextView statusPh;
    Sensores sensores;
    ProgressDialog load;
    private ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_geral);
        inicializaCampos();
        valoresIniciais();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void valoresIniciais() {
        try {
            sensores = new HttpService().execute("view-last-data").get();
            alteraView(sensores);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void alteraView(Sensores sensores) {

        double valorTemperaturaDouble = Double.parseDouble(sensores.getTemp());
        temperaturaSeekBar.setProgress((int) valorTemperaturaDouble);
        valorTemperatura.setText(sensores.getTemp());
        if (valorTemperaturaDouble > 28 && valorTemperaturaDouble < 31){
            statusTemperatura.setText("BOM");
            temperaturaSeekBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }else if((valorTemperaturaDouble >= 25 && valorTemperaturaDouble <= 28) || (valorTemperaturaDouble >= 31 && valorTemperaturaDouble <= 33) ){
            statusTemperatura.setText("ATENÇÃO");
            temperaturaSeekBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }else if ((valorTemperaturaDouble < 25) || (valorTemperaturaDouble > 33) ){
            statusTemperatura.setText("PERIGO");
            temperaturaSeekBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        double valorpHDouble = Double.parseDouble(sensores.getPh());
        phSeekBar.setProgress((int) valorpHDouble);
        valorPh.setText(sensores.getPh());
        if (valorpHDouble > 7.5 && valorpHDouble < 8.5){
            statusPh.setText("BOM");
            phSeekBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }else if((valorpHDouble >= 6.5 && valorpHDouble <= 7.5) || (valorpHDouble >= 8.5 && valorpHDouble <= 9) ){
            statusPh.setText("ATENÇÃO");
            phSeekBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }else if ((valorpHDouble < 6.5) || (valorpHDouble > 9) ){
            statusPh.setText("PERIGO");
            phSeekBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    private void inicializaCampos() {
        temperaturaSeekBar = findViewById(R.id.temperatura_mediaG_seekBar);
        valorTemperatura = findViewById(R.id.valorTemperatura_mediaG_textView);
        statusTemperatura = findViewById(R.id.statusTemperatura_mediaG_textView);
        phSeekBar = findViewById(R.id.ph_mediaG_seekBar);
        valorPh = findViewById(R.id.valorPh_mediaG_textView);
        statusPh = findViewById(R.id.statusPh_mediaG_textView);
        configuraLista();
    }

    private void configuraLista() {
        ListView listaDeSensores = findViewById(R.id.listaSensores_mediaG_listView);
        configuraAdapter(listaDeSensores);
        configuraListenerDeCliquePorItem(listaDeSensores);
        registerForContextMenu(listaDeSensores);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configuraListenerDeCliquePorItem(ListView listaDeSensores) {
        listaDeSensores.setOnItemClickListener((adapterView, view, i, l) -> {

            String sensorClidado = (String) adapterView.getItemAtPosition(i);
            Sensores sensores = null;

            if (sensorClidado.equals("Status do Tanque")){
                Log.i("Sensor clicado:", sensorClidado);
                try {
                    sensores = new HttpService().execute("view-last-data").get();
                    alteraView(sensores);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*Intent carregaThisActivity = new Intent(StatusGeralActivity.this, StatusGeralActivity.class);
                carregaThisActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                startActivity(carregaThisActivity);*/

            }else if (sensorClidado.equals("Média")){
                Log.i("Sensor clicado:", sensorClidado);
                try {
                    sensores = new HttpService().execute("view-all-average").get();
                    alteraView(sensores);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*
                Intent carregaThisActivity = new Intent(StatusGeralActivity.this, StatusGeralActivity.class);
                carregaThisActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                startActivity(carregaThisActivity);*/

            }else if (sensorClidado.equals("Desvio Padrão")){
                Log.i("Sensor clicado:", sensorClidado);


                try {
                    sensores = new HttpService().execute("view-all-standard-deviation").get();
                    alteraView(sensores);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*
                Intent carregaThisActivity = new Intent(StatusGeralActivity.this, StatusGeralActivity.class);
                carregaThisActivity.putExtra("SENSORES_PUTEXTRA", sensores);
                startActivity(carregaThisActivity);*/
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

    private class HttpService extends AsyncTask<String, Void, Sensores> {

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
            load = ProgressDialog.show(StatusGeralActivity.this, "Por favor Aguarde ...","Baixando Imagem ...");
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