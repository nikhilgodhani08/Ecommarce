package com.example.ecommarce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btmnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btmnav=findViewById(R.id.botmnav);


        getSupportFragmentManager().beginTransaction().replace(R.id.frmContainer,new homefrag()).commit();



        //hide titlebar
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Bottom Navigation Drawer
        btmnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;
                switch(item.getItemId())
                {

                    case R.id.menuHome:temp=new homefrag();
                    break;

                    case R.id.menuCart:temp=new addtocartfrag();
                    break;

                    default:
                        temp=new homefrag();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frmContainer,temp).commit();
                return  true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         finishAffinity();
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