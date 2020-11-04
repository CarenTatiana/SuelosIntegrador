package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private EditText edtCorreo,edtContra;
    private Button btnLogin,btnRegis,btnReset;
    private String id;
    private int rolid;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtCorreo= (EditText)findViewById(R.id.editTextCorreo);
        edtContra= (EditText)findViewById(R.id.editTextContraseña);
        btnLogin= (Button)findViewById(R.id.btnLogin);
        btnRegis= (Button)findViewById(R.id.btnRegis);
        btnReset= (Button)findViewById(R.id.btnResetPassword);
        mAuth =  FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo,contra;
                correo = edtCorreo.getText().toString().trim();
                contra = edtContra.getText().toString().trim();

                if (!correo.isEmpty() && !contra.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener
                            (new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        databaseReference= FirebaseDatabase.getInstance().getReference();
                                        id = mAuth.getCurrentUser().getUid();

                                        Toast.makeText(LoginActivity.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                                        //filtrar al admin
                                        databaseReference.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String aux = snapshot.child("rolid").getValue().toString();
                                                rolid = Integer.parseInt(aux);
                                                if (Integer.parseInt(aux) == 2) {
                                                    Intent Intent = new Intent(LoginActivity.this, InicioAdminActivity.class);
                                                    startActivity(Intent);
                                                    limpiarControles();
                                                    finish();
                                                } else {
                                                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                                                    startActivity(intent);
                                                    limpiarControles();
                                                    finish();

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }else {
                    Toast.makeText(LoginActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent2);
            }
        });

    }
    private void limpiarControles(){
        edtCorreo.setText("");
        edtContra.setText("");
    }
    //valida si el usuario ya inicio sesion anteriormente
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            if(rolid == 1){
                Intent Intent = new Intent(LoginActivity.this, InicioActivity.class);
                startActivity(Intent);
                finish();
            }else {
                Intent Intent = new Intent(LoginActivity.this, InicioAdminActivity.class);
                startActivity(Intent);
                finish();
            }

        }
    }

}