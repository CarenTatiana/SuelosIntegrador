package com.example.suelos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_Registrar,btn_Iniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Iniciar= (Button) findViewById(R.id.btnIniciarSesion);
        btn_Registrar= (Button) findViewById(R.id.btnRegistrarse);
        btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(Intent);
            }
        });
        btn_Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Intent2);
            }
        });

    }


}