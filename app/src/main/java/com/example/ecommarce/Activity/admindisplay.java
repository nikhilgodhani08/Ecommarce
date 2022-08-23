package com.example.ecommarce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.ecommarce.Adapter.AdminAdapter;
import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class admindisplay extends AppCompatActivity {

    RecyclerView adminrcvview;
    AdminAdapter adapter;
    FloatingActionButton btnAddPrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindisplay);
        adminrcvview=findViewById(R.id.adminrcvview);
        btnAddPrd=findViewById(R.id.btnAddProduct);

        adminrcvview.setLayoutManager(new LinearLayoutManager(this));

        //Add Product
        btnAddPrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admindisplay.this, addProductDetail.class));
            }
        });

        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseRecyclerOptions<DataModel> options=new FirebaseRecyclerOptions.Builder<DataModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Product").getRef(),DataModel.class)
                .build();

        adapter=new AdminAdapter(options);
        adapter.startListening();
        adminrcvview.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(admindisplay.this, LoginActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}