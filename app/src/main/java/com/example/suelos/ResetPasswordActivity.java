package com.example.suelos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtEmailReset;
    private Button btnReset;
    private String email;

    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        edtEmailReset= (EditText) findViewById(R.id.edtEmailReset);
        btnReset=(Button)findViewById(R.id.buttonReset);
        //instancia
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmailReset.getText().toString().trim();
                if(!email.isEmpty()){
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    resetpassword();
                }else {
                    Toast.makeText(ResetPasswordActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resetpassword() {
        auth.setLanguageCode("es");//idioma del correo
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //si se envio correctamente el correo
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Se envio un correo para restablecer su correo por favor verificar", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ResetPasswordActivity.this, "No se pudo enviar el correo, intente de nuevo", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }
        });
    }

}