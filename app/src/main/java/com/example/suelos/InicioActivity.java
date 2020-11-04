package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InicioActivity extends AppCompatActivity {
    private Button btnCerrar,btnPerfil,btnInstructivos,btnEstdios,btnHistorial;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btnCerrar = (Button)findViewById(R.id.btnCerrarSession);
        btnPerfil = (Button)findViewById(R.id.btnPerfil);
        btnInstructivos = (Button)findViewById(R.id.btnIntructivo);
        btnEstdios = (Button)findViewById(R.id.btnInicioEstudio);
        btnHistorial = (Button)findViewById(R.id.btnHistorial);

        //instancia
        auth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final String id = auth.getCurrentUser().getUid();

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent Intent = new Intent(InicioActivity.this, LoginActivity.class);
                startActivity(Intent);
                finish();
            }
        });
        //perfil sel usuario
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioActivity.this, PerfilActivity.class);
                startActivity(Intent);
            }
        });
        btnEstdios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioActivity.this, CalcularpHActivity.class);
                startActivity(Intent);
            }
        });
        btnInstructivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioActivity.this, instrucciones.class);
                startActivity(Intent);
            }
        });
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioActivity.this, Historialtemperatura.class);
                startActivity(Intent);
            }
        });

    }
}