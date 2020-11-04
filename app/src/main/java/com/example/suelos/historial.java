package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class historial extends AppCompatActivity {
    TextView tvDatos;
    Button guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        recibirDatos();
        guardar= findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(historial.this, Historialtemperatura.class);
                startActivity(Intent);

            }
        });
    }
    public void recibirDatos(){
     Bundle extras = getIntent().getExtras();
     String d1= extras.getString("Gananciainversion");
     String d2= extras.getString("Costosproduccion");
     String d3= extras.getString("GananciaFinal");

     tvDatos= (TextView)findViewById(R.id.tvDatos);
     tvDatos.setText("Ganancia de la Inversión :"+d1+"   " +
             "      " +"Costos de la Producción :"+ d2+ " " +
             "      "+ "Ganancia Final : "+d3+ "");

    }



}