package com.nptec.mvp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.nptec.mvp.R;

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
        botaoEntrar.setOnClickListener(view -> {
            Log.i("Entrou", "Passou para outra tela");
            Intent vaiStatusGeralActivity = new Intent(MainActivity.this, StatusGeralActivity.class);
            startActivity(vaiStatusGeralActivity);
            //Intent vaiParaListaDeSensoresActivity = new Intent(MainActivity.this, ListaDeSensoresActivity.class);
            //startActivity(vaiParaListaDeSensoresActivity);
        });
    }

    private void inicializacaoDosCampos() {
        campoUser = findViewById(R.id.user_login_editText);
        campoPassword = findViewById(R.id.password_login_editTextText);
        campoRecuperarSenha = findViewById(R.id.esqueceuSenha_login_textView);
        botaoEntrar = findViewById(R.id.entrar_login_button);
    }
}