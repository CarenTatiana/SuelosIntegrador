package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AgregarCultivoActivity extends AppCompatActivity {

    private EditText edtNombre,edtPrecio,edtCosto,edtRendimiento,edtMin,edtMax;
    private Button btninsertar, btnactualizar,btneliminar;
    private List<Cultivo> listCultivo = new ArrayList<Cultivo>();
    ArrayAdapter<Cultivo> arrayAdapterCultivo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listV_Cultivo;

    Cultivo cultivoSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cultivo);
        edtNombre= (EditText) findViewById(R.id.edtCultivoNombre);
        edtPrecio= (EditText) findViewById(R.id.edtCultivoPrecio);
        edtCosto= (EditText) findViewById(R.id.edtCultivoCosto);
        edtRendimiento= (EditText) findViewById(R.id.edtCultivoRendimiento);
        edtMax= (EditText) findViewById(R.id.edtCultivoMax);
        edtMin= (EditText) findViewById(R.id.edtCultivoMin);
        btninsertar= (Button) findViewById(R.id.btnInsee);
        btnactualizar= (Button) findViewById(R.id.btnCultivoActualizar);
        btneliminar= (Button) findViewById(R.id.btnCultivoEliminar);
        listV_Cultivo= (ListView) findViewById(R.id.CultivoList_Nombres);
        iniciarfirebase();
        ListarCultivos();
        listV_Cultivo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //obtengo cultivo seleccionado
                cultivoSeleccionado = (Cultivo) listV_Cultivo.getItemAtPosition(position);
                edtNombre.setText(cultivoSeleccionado.getNombre());
                edtPrecio.setText(String.valueOf(cultivoSeleccionado.getPrecio()));
                edtCosto.setText(String.valueOf(cultivoSeleccionado.getCosto()));
                edtRendimiento.setText(String.valueOf(cultivoSeleccionado.getRedimiento()));
                edtMax.setText(String.valueOf(cultivoSeleccionado.getMaximo()));
                edtMin.setText(String.valueOf(cultivoSeleccionado.getMinimo()));


            }
        });

        btninsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNombre.getText().toString().isEmpty() || !edtPrecio.getText().toString().isEmpty() || !edtCosto.getText().toString().isEmpty() || !edtRendimiento.getText().toString().isEmpty() || !edtMax.getText().toString().isEmpty() || !edtMin.getText().toString().isEmpty()) {
                    Cultivo c = new Cultivo();
                    c.setId(UUID.randomUUID().toString());
                    c.setNombre(edtNombre.getText().toString());
                    c.setPrecio(Float.parseFloat(edtPrecio.getText().toString()));
                    c.setRedimiento(Float.parseFloat(edtRendimiento.getText().toString()));
                    c.setCosto(Float.parseFloat(edtCosto.getText().toString()));
                    c.setMaximo(Float.parseFloat(edtMax.getText().toString()));
                    c.setMinimo(Float.parseFloat(edtMin.getText().toString()));
                    //acceder a la base de datos
                    databaseReference.child("Cultivo").child(c.getId()).setValue(c);
                    Toast.makeText(AgregarCultivoActivity.this, "Insertado Correctamente", Toast.LENGTH_SHORT).show();
                    //Limpiar
                    LimpiarCampos();
                }else {
                    Toast.makeText(AgregarCultivoActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNombre.getText().toString().isEmpty() || !edtPrecio.getText().toString().isEmpty() || !edtCosto.getText().toString().isEmpty() || !edtRendimiento.getText().toString().isEmpty() || !edtMax.getText().toString().isEmpty() || !edtMin.getText().toString().isEmpty()) {
                    Cultivo actualizar = new Cultivo();
                    //actualizar datos
                    actualizar.setId(cultivoSeleccionado.getId());
                    actualizar.setNombre(edtNombre.getText().toString().trim());
                    actualizar.setPrecio(Float.parseFloat(edtPrecio.getText().toString().trim()));
                    actualizar.setCosto(Float.parseFloat(edtCosto.getText().toString().trim()));
                    actualizar.setRedimiento(Float.parseFloat(edtRendimiento.getText().toString().trim()));
                    actualizar.setMinimo(Float.parseFloat(edtMin.getText().toString().trim()));
                    actualizar.setMaximo(Float.parseFloat(edtMax.getText().toString().trim()));
                    //cambiar valor en la base de datos
                    databaseReference.child("Cultivo").child(actualizar.getId()).setValue(actualizar);
                    Toast.makeText(AgregarCultivoActivity.this, "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                    //Limpiar
                    LimpiarCampos();
                }else {
                    Toast.makeText(AgregarCultivoActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //elimiar datos
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNombre.getText().toString().isEmpty() || !edtPrecio.getText().toString().isEmpty() || !edtCosto.getText().toString().isEmpty() || !edtRendimiento.getText().toString().isEmpty() || !edtMax.getText().toString().isEmpty() || !edtMin.getText().toString().isEmpty()) {
                    Cultivo elimiar = new Cultivo();
                    elimiar.setId(cultivoSeleccionado.getId());
                    //Eliminar
                    databaseReference.child("Cultivo").child(elimiar.getId()).removeValue();
                    Toast.makeText(AgregarCultivoActivity.this, "Cultivo Eliminado", Toast.LENGTH_SHORT).show();
                    //Limpiar
                    LimpiarCampos();
                }else {
                    Toast.makeText(AgregarCultivoActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void LimpiarCampos(){
        edtNombre.setText("");
        edtPrecio.setText("");
        edtRendimiento.setText("");
        edtCosto.setText("");
        edtMax.setText("");
        edtMin.setText("");
    }
    private void ListarCultivos() {
        databaseReference.child("Cultivo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCultivo.clear();//limpiar cache
                for (DataSnapshot objSnapshot : snapshot.getChildren() ){
                    Cultivo c= objSnapshot.getValue(Cultivo.class);//asignamos
                    listCultivo.add(c);//llenamos la lita
                    //creamos la instanci
                    arrayAdapterCultivo = new ArrayAdapter<Cultivo>(AgregarCultivoActivity.this,android.R.layout.simple_list_item_1, listCultivo);
                    listV_Cultivo.setAdapter(arrayAdapterCultivo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void iniciarfirebase() {
        //iniciar app
        FirebaseApp.initializeApp(this);
        //instancia
        firebaseDatabase = FirebaseDatabase.getInstance();
        //referencia
        databaseReference = firebaseDatabase.getReference();

    }

}