package com.example.ecommarce.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.example.ecommarce.Adapter.HomeAdapter;
import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class homefrag extends Fragment {

    EditText edtHomeSearch;
    RecyclerView homeRcvview;
    HomeAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homefrag, container, false);
        edtHomeSearch = view.findViewById(R.id.edtHomeSearch);
        homeRcvview = view.findViewById(R.id.home_rcvview);
        homeRcvview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Query query = FirebaseDatabase.getInstance().getReference("Product").getRef();

        FirebaseRecyclerOptions<DataModel> options = new FirebaseRecyclerOptions.Builder<DataModel>()
                .setQuery(query, DataModel.class)
                .build();

        adapter = new HomeAdapter(options);
        adapter.startListening();
        homeRcvview.setAdapter(adapter);

       edtHomeSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(charSequence!=null) {
                   processtosearch(charSequence.toString());
               }

           }

           @Override
           public void afterTextChanged(Editable editable) {
               if(editable!=null) {
                   processtosearch(editable.toString());
               }else {
                   processtosearch("");
               }

           }
       });

        return view;


    }

    private void processtosearch(String text) {


        Query query = FirebaseDatabase.getInstance().getReference("Product").getRef().orderByChild("name").startAt(text).endAt(text + "\uf8ff");

        FirebaseRecyclerOptions<DataModel> options = new FirebaseRecyclerOptions.Builder<DataModel>()
                .setQuery(query, DataModel.class)
                .build();

        adapter = new HomeAdapter(options);
        adapter.startListening();
        homeRcvview.setAdapter(adapter);
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