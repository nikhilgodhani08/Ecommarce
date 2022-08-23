package com.example.ecommarce.Adapter;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartAdapter extends FirebaseRecyclerAdapter<DataModel,CartAdapter.ViewHolder> {

    public CartAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options) {
        super(options);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull DataModel model) {
        Glide.with(holder.cartimg.getContext()).load(model.getPimage()).into(holder.cartimg);
        holder.name.setText(model.getName());
        holder.bname.setText(model.getBrandName());
        holder.price.setText("$" + model.getPrice() + ".00");
        holder.count.setText(""+model.getQuantity());
        Map<String,Object> map=new HashMap<>();
        map.put("total",model.getQuantity()*Integer.parseInt(model.getPrice()));
        getRef(position).updateChildren(map);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("AddtoCart").child(Objects.requireNonNull(getRef(position).getKey())).removeValue();
            }
        });
        //plus
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<>();
                map.put("total",model.getQuantity()*Integer.parseInt(model.getPrice()));
                map.put("quantity",model.getQuantity() + 1);
                getRef(position).updateChildren(map);

            }
        });


        //minus
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<>();
                map.put("quantity",model.getQuantity() - 1);
                getRef(position).updateChildren(map);
                if(model.getQuantity() <= 1 ){
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("quantity", 1);
                    getRef(position).updateChildren(map1);
                }

            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
       return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartimg,plus,minus,btnDelete;
        TextView name,bname,price,count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartimg=itemView.findViewById(R.id.card_prd_img);
            plus=itemView.findViewById(R.id.cart_plus);
            minus=itemView.findViewById(R.id.cart_minus);
            name=itemView.findViewById(R.id.card_prd_name);
            bname=itemView.findViewById(R.id.card_prd_bname);
            price=itemView.findViewById(R.id.card_prd_price);
            btnDelete=itemView.findViewById(R.id.btn_cart_delete);
            count=itemView.findViewById(R.id.cart_prd_count);


        }
    }
}
