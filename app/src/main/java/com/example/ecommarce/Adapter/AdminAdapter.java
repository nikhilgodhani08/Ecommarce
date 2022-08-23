package com.example.ecommarce.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAdapter extends FirebaseRecyclerAdapter<DataModel, AdminAdapter.ViewHolder> {

    public AdminAdapter(@NonNull FirebaseRecyclerOptions<DataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull DataModel model) {
        Glide.with(holder.adminPrdImg.getContext()).load(model.getPimage()).into(holder.adminPrdImg);
        holder.txtPrdName.setText(model.getName());
        holder.txtPrdBname.setText(model.getBrandName());
        holder.txtPrdPrice.setText("$" + model.getPrice() + ".00");

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.adminPrdImg.getContext())
                        .setTitle("Delete")
                        .setCancelable(false)
                        .setMessage("Are you sure you want to delete " + model.getName() + "?")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
                                databaseReference.child(getRef(position).getKey()).removeValue();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alertDialog.show();


            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView adminPrdImg, btnDelete;
        TextView txtPrdName, txtPrdBname, txtPrdPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adminPrdImg = itemView.findViewById(R.id.admin_prd_img);
            btnDelete = itemView.findViewById(R.id.btndelete);
            txtPrdName = itemView.findViewById(R.id.admin_prd_name);
            txtPrdBname = itemView.findViewById(R.id.admin_prd_bname);
            txtPrdPrice = itemView.findViewById(R.id.admin_prd_price);
        }
    }


}
