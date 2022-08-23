package com.example.ecommarce.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommarce.Activity.item_display;
import com.example.ecommarce.Util.DataModel;
import com.example.ecommarce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HomeAdapter extends FirebaseRecyclerAdapter<DataModel, HomeAdapter.ViewHolder> {



    public HomeAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull DataModel model) {

        Glide.with(holder.homePrdImg.getContext()).load(model.getPimage()).into(holder.homePrdImg);
        holder.txtHomePrdDetail.setText(model.getDetail());
        holder.txtHomePrdBname.setText(model.getBrandName());
        holder.txtHomePrdName.setText(model.getName());
        holder.txtHomePrdPrice.setText("$" + model.getPrice() + ".00");
        holder.homeItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.homePrdImg.getContext(), item_display.class);
                intent.putExtra("prd_id",getRef(position).getKey());
                holder.homePrdImg.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView homePrdImg;
        TextView txtHomePrdName, txtHomePrdBname, txtHomePrdPrice, txtHomePrdDetail;
        LinearLayout homeItemLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            homePrdImg = itemView.findViewById(R.id.home_prd_img);
            txtHomePrdName = itemView.findViewById(R.id.home_prd_name);
            txtHomePrdBname = itemView.findViewById(R.id.home_prd_bname);
            txtHomePrdPrice = itemView.findViewById(R.id.home_prd_price);
            txtHomePrdDetail = itemView.findViewById(R.id.home_prd_detail);
            homeItemLayout = itemView.findViewById(R.id.homeitemLayout);



        }


    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }
}
