package com.example.ecommarce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommarce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActyvity extends AppCompatActivity {
    TextView txtLogin;
    EditText edtName, edtEmail, edtPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actyvity);

        txtLogin = findViewById(R.id.txtLogin);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();


        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Log In Textview
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActyvity.this, LoginActivity.class));
            }
        });

        //Progress Bar
        dialog=new ProgressDialog(this);
        dialog.setTitle("Register");
        dialog.setMessage("Create Your Account");
        dialog.setCancelable(false);



        //Register Button Click Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString().trim();
                String emailAddress = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                boolean check = validation(name, emailAddress, password);
                if (check) {
                    dialog.show();
                } else {
                    Toast.makeText(RegisterActyvity.this, "Sorry Check Your Detail", Toast.LENGTH_SHORT).show();
                }
                registeredata(emailAddress, password);

            }

        });

    }

    //Register Authentication
    private void registeredata(String emailAddress, String password) {
        mAuth.createUserWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterActyvity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(RegisterActyvity.this, MainActivity.class));
                        }else {
                            Toast.makeText(RegisterActyvity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            Log.d("RegisterError",task.getException().toString());
                            dialog.dismiss();

                        }

                    }
                });
    }


    //Check Validation
    private boolean validation(String name, String email, String Password) {
        if (name.length() == 0) {
            edtName.requestFocus();
            edtName.setError("Please Complete Name Field");
            return false;
        } else if (!name.matches("[a-zA-Z]+")) {
            edtName.requestFocus();
            edtName.setError("Enter Only Alphabetic character");
            return false;
        } else if (email.length() == 0) {
            edtEmail.requestFocus();
            edtEmail.setError("Please complete Email Field");
            return false;
        } else if (!email.matches("[A-Za-z0-9+_.-]+@(.+)$")) {
            edtEmail.requestFocus();
            edtEmail.setError("Please Enter Valid Email");
            return false;
        } else if (edtPassword.length() == 0) {
            edtPassword.requestFocus();
            edtPassword.setError("Please Enter Password");
            return false;
        } else if (edtPassword.length() > 6 || edtPassword.length() <= 5) {
            edtPassword.requestFocus();
            edtPassword.setError("Please Enter 4 Digit Password");
            return false;
        } else {
            edtPassword.setError(null);
            edtEmail.setError(null);
            edtName.setError(null);
            return true;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}