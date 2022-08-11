package com.example.ecommarce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.platform.SlideDistanceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class item_display extends AppCompatActivity {


    TextView disPrdTname, disPrdName, disPrdBname, disPrdDetail, disPrdCapacity, disPrdPrice;
    Button btnAddToCart;
    ImageView disPrdImg;
    String key;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        disPrdTname = findViewById(R.id.dis_prd_tname);
        disPrdName = findViewById(R.id.dis_prd_name);
        disPrdBname = findViewById(R.id.dis_prd_bname);
        disPrdDetail = findViewById(R.id.dis_prd_detail);
        disPrdCapacity = findViewById(R.id.dis_prd_capacity);
        disPrdPrice = findViewById(R.id.dis_prd_price);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        disPrdImg = findViewById(R.id.dis_prd_img);

        key = getIntent().getStringExtra("prd_id");

        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Product");

        //Set Data
        setData();

    }

    private void setData() {
        //Main Page Data To Item Display
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String bname = snapshot.child("brandName").getValue().toString();
                String detail = snapshot.child("detail").getValue().toString();
                String capacity = snapshot.child("capacity").getValue().toString();
                String price =  snapshot.child("price").getValue().toString();
                String img = snapshot.child("pimage").getValue().toString();
                Integer quantity=0,total=0;

                Glide.with(item_display.this).load(img).into(disPrdImg);
                disPrdName.setText(name);
                disPrdTname.setText(bname);
                disPrdPrice.setText("$" + price + ".00");
                disPrdBname.setText(bname);
                disPrdDetail.setText(detail);
                disPrdCapacity.setText(capacity + " Liters");

                //btnAddtoCart
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("AddtoCart");
                        DataModel model = new DataModel(img, name, bname, price,quantity,total);
                        databaseReference1.push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View view1 = LayoutInflater.from(item_display.this).inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
                                Toast toast = Toast.makeText(item_display.this, "Added To Cart", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 80);
                                toast.setView(view1);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(item_display.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(item_display.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(item_display.this, MainActivity.class));

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