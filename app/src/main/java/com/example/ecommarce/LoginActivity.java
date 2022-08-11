package com.example.ecommarce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView txtLoginRegister;
    EditText edtLoginEmail,edtLoginPassword;
    Button btnLogin,btnAdmin;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLoginRegister=findViewById(R.id.txtLoginRegister);
        edtLoginEmail=findViewById(R.id.edtLoginEmail);
        edtLoginPassword=findViewById(R.id.edtLoginPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnAdmin=findViewById(R.id.btnAdmin);

        mAuth=FirebaseAuth.getInstance();

        //Current User
        firebaseUser=mAuth.getCurrentUser();

        //Progress Dialog
        dialog=new ProgressDialog(this);
        dialog.setMessage("Verify your Account");
        dialog.setTitle("Verify Account");
        dialog.setCancelable(false);

        //Button Admin
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,admindisplay.class));
            }
        });

        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Register Textview
        txtLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActyvity.class);
                startActivity(intent);
            }
        });

        //LogIn Button Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtLoginEmail.getText().toString().trim();
                String password=edtLoginPassword.getText().toString().trim();
                boolean check=Validation(email,password);
                if(!check)
                {
                    Toast.makeText(LoginActivity.this, "Sorry Check Your Detail", Toast.LENGTH_SHORT).show();
                }else{
                    logintofirebase(email,password);
                }

            }
        });
    }

    //Login
    private void logintofirebase(String email, String password) {
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }else{
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    //check Input Validation
    private boolean Validation(String email, String password) {
        if(email.length()==0)
        {
            edtLoginEmail.requestFocus();
            edtLoginEmail.setError("Please Enter Email");
            return false;
        }else if(!email.matches("[A-Za-z0-9+_.-]+@(.+)$"))
        {
            edtLoginEmail.requestFocus();
            edtLoginEmail.setError("Please Enter Valid Email");
            return false;
        }else if(password.length()==0)
        {
            edtLoginPassword.requestFocus();
            edtLoginPassword.setError("Please Enter Password");
            return false;
        }else if(password.length() != 6       )
        {
            edtLoginPassword.requestFocus();
            edtLoginPassword.setError("Please 4 Digit Password");
            return false;
        }else {
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
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