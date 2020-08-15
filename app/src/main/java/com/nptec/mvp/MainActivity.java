package com.nptec.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText campoUser;
    private EditText campoPassword;
    private TextView campoRecuperarSenha;
    private Button botaoEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializacaoDosCampos();
        clickBotaoEntrar();

    }

    private void clickBotaoEntrar() {
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Entrou", "Passou para outra tela");
                Intent vaiParaListaDeSensoresActivity = new Intent(MainActivity.this, ListaDeSensoresActivity.class);
                startActivity(vaiParaListaDeSensoresActivity);
            }
        });
    }

    private void inicializacaoDosCampos() {
        campoUser = findViewById(R.id.user_login_editText);
        campoPassword = findViewById(R.id.password_login_editTextText);
        campoRecuperarSenha = findViewById(R.id.esqueceuSenha_login_textView);
        botaoEntrar = findViewById(R.id.entrar_login_button);
    }
}