package com.nptec.mvp.dao;

import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nptec.mvp.model.Sensores;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, Sensores> {

    /*
    * url_base = victortirano.pythonanywhere.com
    média aritmética = url_base/view-all-average
    desvio padrão = url_base/view-all-standard-deviation
    todos os dados = url_base/view-all-data
    * */

    @Override
    protected Sensores doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        try {
            URL url = new URL("http://victortirano.pythonanywhere.com/view-all-average");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
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

        Sensores sensores = new Gson().fromJson(resposta.toString(), Sensores.class);
        Log.i("Resposta:", sensores.toString());

        //List<Sensores> produtos = new Gson().fromJson(resposta.toString(), new TypeToken<List<Sensores>>(){}.getType());

        return sensores;
    }

}
