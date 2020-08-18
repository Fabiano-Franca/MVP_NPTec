package com.nptec.mvp.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nptec.mvp.model.Sensores;
import com.nptec.mvp.ui.activity.ListaDeSensoresActivity;
import com.nptec.mvp.ui.activity.MediaActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, Sensores> {

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

        Sensores sensores = new Gson().fromJson(resposta.toString(), Sensores.class);
        Log.i("Resposta:", sensores.toString());

        //List<Sensores> produtos = new Gson().fromJson(resposta.toString(), new TypeToken<List<Sensores>>(){}.getType());

        return sensores;
    }

}
