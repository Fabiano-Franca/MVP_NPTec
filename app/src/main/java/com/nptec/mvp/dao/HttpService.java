package com.nptec.mvp.dao;

import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.nptec.mvp.model.Sensores;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        try {
            URL url = new URL("http://victortirano.pythonanywhere.com/view-all-data");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Resposta:", resposta.toString());
        //Sensores sensores = new Gson().fromJson(resposta.toString(), Sensores.class);

        return null;
    }

}
