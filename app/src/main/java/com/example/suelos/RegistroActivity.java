package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistroActivity extends AppCompatActivity {
    private EditText Nombre,Apellido,Correo,Contraseña;
    private Button btnIniciarSesion, brnRegistrar;

    String nombre,apellido,email,password;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Nombre= (EditText) findViewById(R.id.editTextUsuarioNombre);
        Apellido= (EditText) findViewById(R.id.editTextUsuarioApellido);
        Correo= (EditText) findViewById(R.id.editTextUsuarioCorreo);
        Contraseña= (EditText) findViewById(R.id.editTextUsuarioContraseña);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnIniciarSesion= (Button) findViewById(R.id.editTextIniciarSesion);
        brnRegistrar= (Button) findViewById(R.id.editTextCrearUsuario);



        brnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nombre = Nombre.getText().toString();
               apellido = Apellido.getText().toString();
               email = Correo.getText().toString();
               password = Contraseña.getText().toString();
                if (!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                   registrarUsuario();
                }else {
                    Toast.makeText(RegistroActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }

            }


        });
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(Intent);
                limpiarControles();
            }
        });

    }


    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("apellido" , apellido);
                    map.put("email" , email);
                    map.put("password" , password);
                    map.put("rolid" , 1);
                    String id = mAuth.getCurrentUser().getUid();
                    //Usuario usu = new Usuario();
                    //usu.setId(mAuth.getCurrentUser().getUid());
                    //usu.setNombre(Nombre.getText().toString());
                    //usu.setApellido(Apellido.getText().toString());
                    //usu.setEmail(Correo.getText().toString());
                    //usu.setPassword(Contraseña.getText().toString());
                    databaseReference.child("Usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();
                                Intent Intent = new Intent(RegistroActivity.this, InicioActivity.class);
                                startActivity(Intent);
                                finish();
                                limpiarControles();
                            }else {
                                Toast.makeText(getApplicationContext(), "No se pudieron crear los datos correctamente", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Registro fallido por favor intente de nuevo", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void limpiarControles() {
        Nombre.setText("");
        Apellido.setText("");
        Correo.setText("");
        Contraseña.setText("");
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent Intent = new Intent(RegistroActivity.this, InicioActivity.class);
            startActivity(Intent);
            finish();
        }
    }*/

}