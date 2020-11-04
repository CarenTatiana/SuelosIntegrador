package com.example.suelos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class InicioAdminActivity extends AppCompatActivity {

    private Button btnPerfil, btnAgregar,btnCerrar, btnInicioestudios;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_admin);

        btnPerfil=(Button)findViewById(R.id.btnAdminPerfil);
        btnAgregar=(Button)findViewById(R.id.btnAdminAgregar);
        btnCerrar=(Button)findViewById(R.id.btnCerraAdmin);
        btnInicioestudios=(Button)findViewById(R.id.btnAdmininiciar);

        //instancia
        auth = FirebaseAuth.getInstance();
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent Intent = new Intent(InicioAdminActivity.this, LoginActivity.class);
                startActivity(Intent);
                finish();
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioAdminActivity.this, PerfilActivity.class);
                startActivity(Intent);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioAdminActivity.this, AgregarCultivoActivity.class);
                startActivity(Intent);
            }
        });
        btnInicioestudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(InicioAdminActivity.this, CalcularpHActivity.class);
                startActivity(Intent);
            }
        });

    }
}