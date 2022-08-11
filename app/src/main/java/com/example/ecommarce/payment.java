package com.example.ecommarce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class payment extends AppCompatActivity {

    Button btnPayNow;
    RadioButton rdbgooglepay,rdbPhonePay,rdbPaytm;
    RadioGroup rdbgroup;
    RelativeLayout layoutp1,layoutp2,layoutp3;
    TextView txtPaymentPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnPayNow=findViewById(R.id.btnPayNow);
        rdbgooglepay=findViewById(R.id.rdbgooglepay);
        rdbPaytm=findViewById(R.id.rdbPaytm);
        rdbPhonePay=findViewById(R.id.rdbPhonePay);
        rdbgroup=findViewById(R.id.rdbgroup);
        layoutp1=findViewById(R.id.layoutp1);
        layoutp2=findViewById(R.id.layoutp2);
        layoutp3=findViewById(R.id.layoutp3);
        txtPaymentPrice=findViewById(R.id.txtPaymentPrice);

        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //btn Pay
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(payment.this,Success.class));
            }
        });

        //total
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("AddtoCart");
        databaseReference.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer tot=0;
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    DataModel model= singleSnapshot.getValue(DataModel.class);
                    Integer p= model.getTotal();
                    tot+=p;
                    txtPaymentPrice.setText("$"+tot+".00");
                    Log.d("sumsum",tot.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Payment Option
        layoutp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbPhonePay.setChecked(false);
                rdbPaytm.setChecked(false);
            }
        });
        layoutp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbgooglepay.setChecked(false);
                rdbPaytm.setChecked(false);
            }
        });
        layoutp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdbPhonePay.setChecked(false);
                rdbgooglepay.setChecked(false);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(payment.this,MainActivity.class));
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