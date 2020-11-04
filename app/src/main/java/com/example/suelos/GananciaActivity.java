package com.example.suelos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

public class GananciaActivity extends AppCompatActivity {

    private TextView tvNombre,tvGanancia,tvCostos,tvGananciaFinal;
    private Button btnCalcular;
    private EditText edtHectarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganancia);
        tvNombre=(TextView)findViewById(R.id.tv_Nombre);
        tvGanancia=(TextView)findViewById(R.id.tv_Ganancia);
        tvGananciaFinal=(TextView)findViewById(R.id.tv_GananciaFinal);
        tvCostos=(TextView)findViewById(R.id.tv_CostoProduccion);
        btnCalcular=(Button)findViewById(R.id.btnGanancia);
        edtHectarea=(EditText)findViewById(R.id.edt_hectarea);

        tvNombre.setText(GlobalPH.cultivo.getNombre());

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent i= new Intent(GananciaActivity.this, historial.class);

                int ha = Integer.parseInt(edtHectarea.getText().toString());
                if (ha <=0 || ha> 50){
                    Toast.makeText(GananciaActivity.this, "Valor invalido", Toast.LENGTH_SHORT).show();
                    tvGanancia.setText("Ganancia Aproximada Fuera de inversion: " );
                    tvCostos.setText("Costos de produccion  : " );
                    tvGananciaFinal.setText("Ganacia Final :");
                }else {
                    float precioKg = (GlobalPH.cultivo.getPrecio());
                    float remdimiento = (GlobalPH.cultivo.getRedimiento());
                    float costos = (GlobalPH.cultivo.getCosto());
                    double costoTotal = costos * ha;
                    double Kgha = (double) (remdimiento * ha);
                    double total = (double) (Kgha * precioKg);
                    tvGanancia.setText("Ganancia Aproximada Fuera de inversion: " + convertir(total));
                    tvCostos.setText("Costos de produccion  : " + convertir(costoTotal));
                    tvGananciaFinal.setText("Ganacia Final :" + convertir(total - costoTotal));

                    i.putExtra("Gananciainversion",convertir(total));
                    i.putExtra("Costosproduccion",convertir(costoTotal));
                    i.putExtra("GananciaFinal",convertir(total - costoTotal));


                    startActivity(i);

                }
            }
        });

    }

    public static String convertir(double val) {
        Locale.setDefault(Locale.US);
        DecimalFormat num = new DecimalFormat("#,###");
        return num.format(val);
    }
}