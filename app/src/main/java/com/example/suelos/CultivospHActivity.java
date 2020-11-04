package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CultivospHActivity extends AppCompatActivity {

    private TextView textView_PH;
    private List<Cultivo> listCultivo = new ArrayList<Cultivo>();
    ArrayAdapter<Cultivo> arrayAdapterCultivo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listV_Cultivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivosp_h);
        textView_PH = (TextView) findViewById(R.id.textV_PH);
        textView_PH.setText("PH : "+(GlobalPH.ph.toString() ));
        listV_Cultivo= (ListView) findViewById(R.id.ListvCultivo);

        iniciarfirebase();
        //ListarCultivos();
        ListarCondicion();


        listV_Cultivo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cultivo CultivoSeleccionado = (Cultivo) listV_Cultivo.getItemAtPosition(position);

                GlobalPH.cultivo = CultivoSeleccionado;
                Toast.makeText(CultivospHActivity.this, ""+CultivoSeleccionado.getNombre(), Toast.LENGTH_SHORT).show();
                Intent Intent = new Intent(CultivospHActivity.this, GananciaActivity.class);
                startActivity(Intent);

            }
        });
    }

    private void ListarCondicion(){
        final Float pH = Float.parseFloat(GlobalPH.ph.toString());
        databaseReference.child("Cultivo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCultivo.clear();//limpiar cache
                for (DataSnapshot objSnapshot : snapshot.getChildren() ){
                    Cultivo c = objSnapshot.getValue(Cultivo.class);//asignamos
                    if(pH >= c.getMinimo() && pH<= c.getMaximo()) {
                        listCultivo.add(c);//llenamos la lita
                        //creamos la instanci
                        arrayAdapterCultivo = new ArrayAdapter<Cultivo>(CultivospHActivity.this, android.R.layout.simple_list_item_1, listCultivo);
                        listV_Cultivo.setAdapter(arrayAdapterCultivo);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void iniciarfirebase(){
        //iniciar app
        FirebaseApp.initializeApp(this);
        //instancia
        firebaseDatabase = FirebaseDatabase.getInstance();
        //referencia
        databaseReference = firebaseDatabase.getReference();

    }


}
