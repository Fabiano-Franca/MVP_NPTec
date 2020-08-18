package com.nptec.mvp.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nptec.mvp.R;
import com.nptec.mvp.model.Sensores;

public class MediaActivity extends AppCompatActivity {

    SeekBar temperaturaSeekBar;
    TextView valorTemperatura;
    TextView statusTemperatura;
    SeekBar phSeekBar;
    TextView valorPh;
    TextView statusPh;
    Sensores sensores;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        inicializaCampos();
        pegaSensores();



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void alteraView() {

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void pegaSensores() {
        Intent dados = getIntent();
        if (dados.hasExtra("SENSORES_PUTEXTRA")) {
            sensores = (Sensores) dados.getSerializableExtra("SENSORES_PUTEXTRA");
            alteraView();
        }
    }

    private void inicializaCampos() {
        temperaturaSeekBar = findViewById(R.id.temperatura_media_seekBar);
        valorTemperatura = findViewById(R.id.valorTemperatura_media_textView);
        statusTemperatura = findViewById(R.id.statusTemperatura_media_textView);
        phSeekBar = findViewById(R.id.ph_media_seekBar);
        valorPh = findViewById(R.id.valorPh_media_textView);
        statusPh = findViewById(R.id.statusPh_media_textView);
    }
}