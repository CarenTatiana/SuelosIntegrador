package com.example.suelos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Historialtemperatura extends AppCompatActivity {

    Button tempMax;
    Button temMin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historialtemperatura);

        temMin=(Button)findViewById(R.id.button2);
        tempMax=(Button)findViewById(R.id.button1);


        tempMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(Historialtemperatura.this, TemperaturaMaxima.class);
                startActivity(Intent);
                finish();
            }
        });

        temMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(Historialtemperatura.this, TemperaturaMinima.class);
                startActivity(Intent);
                finish();
            }
        });


    }
}