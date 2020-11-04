package com.example.suelos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Locale;

public class CalcularpHActivity extends AppCompatActivity {


    private EditText edt_H;
    private Button btnCalcular,btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcularp_h);
        edt_H = (EditText) findViewById(R.id.edt_H);
        btnCalcular= (Button) findViewById(R.id.btn_Calcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double H = Double.parseDouble(edt_H.getText().toString());
                double ph = -(Math.log(H));




                Toast.makeText(CalcularpHActivity.this, "Ph ="+ convertir(ph), Toast.LENGTH_SHORT).show();
                if (ph < 4.5 || ph > 8.5){
                    Intent Intent = new Intent(CalcularpHActivity.this, ConstruccionActivity.class);
                    startActivity(Intent);

                }else {
                    GlobalPH.ph = convertir(ph);
                    Intent Intent2 = new Intent(CalcularpHActivity.this, CultivospHActivity.class);
                    startActivity(Intent2);

                }
            }
        });


    }

    public static String convertir(double val) {
        Locale.setDefault(Locale.US);
        DecimalFormat num = new DecimalFormat("#.##");
        return num.format(val);
    }

}