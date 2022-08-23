package com.example.ecommarce.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommarce.Activity.payment;
import com.example.ecommarce.Adapter.CartAdapter;
import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class addtocartfrag extends Fragment {

    TextView totalPrice;
    Button btnproces;
    RecyclerView cartRcvview;
    CartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addtocartfrag, container, false);

        totalPrice = view.findViewById(R.id.txt_total_price);
        btnproces = view.findViewById(R.id.btnProcessToBuy);
        cartRcvview = view.findViewById(R.id.cartRcvview);
        cartRcvview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Query query = FirebaseDatabase.getInstance().getReference("AddtoCart").getRef();

        FirebaseRecyclerOptions<DataModel> options = new FirebaseRecyclerOptions.Builder<DataModel>()
                .setQuery(query, DataModel.class).build();

        adapter = new CartAdapter(options);
        adapter.startListening();
        cartRcvview.setAdapter(adapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AddtoCart");
        databaseReference.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer tot = 0;
                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    DataModel model = singleSnapshot.getValue(DataModel.class);
                    Integer p = model.getTotal();
                    tot += p;
                    totalPrice.setText("$" + tot + ".00");
                    Log.d("sumsum", tot.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btnproces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), payment.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}