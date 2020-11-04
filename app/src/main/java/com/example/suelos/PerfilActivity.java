package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilActivity extends AppCompatActivity {
    private EditText edtNombre,edtApellido,edtemail,edtpassword;
    private Button btnActualizar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        edtNombre = (EditText)findViewById(R.id.edtPerfilNombre);
        edtApellido = (EditText)findViewById(R.id.edtPerfilApellido);
        edtemail = (EditText)findViewById(R.id.edtPerfilEmail);
        edtpassword = (EditText)findViewById(R.id.edtPerfilPassword);
        btnActualizar=(Button) findViewById(R.id.btnActualizarDatos);
        //instanciar
        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final String id = mAuth.getCurrentUser().getUid();

        databaseReference.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    edtNombre.setText(snapshot.child("nombre").getValue().toString());
                    edtApellido.setText(snapshot.child("apellido").getValue().toString());
                    edtemail.setText(snapshot.child("email").getValue().toString());
                    edtpassword.setText(snapshot.child("password").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //actualizar
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNombre.getText().toString().isEmpty() || !edtApellido.getText().toString().isEmpty() || !edtemail.getText().toString().isEmpty() || !edtpassword.getText().toString().isEmpty()){
                    Usuario actualizar = new Usuario();
                    actualizar.setId(id);
                    actualizar.setNombre(edtNombre.getText().toString().trim());
                    actualizar.setApellido(edtApellido.getText().toString().trim());
                    actualizar.setEmail(edtemail.getText().toString().trim());
                    actualizar.setPassword(edtpassword.getText().toString().trim());
                    actualizar.setRolid(1);
                    databaseReference.child("Usuario").child(actualizar.getId()).setValue(actualizar).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PerfilActivity.this, "Datos Actualizados", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(PerfilActivity.this, "No se pudo actualizar", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(PerfilActivity.this,"No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}