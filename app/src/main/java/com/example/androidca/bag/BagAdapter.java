package com.example.androidca.bag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidca.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.BagViewHolder> {

    private ArrayList<JSONObject> bagItems;

    public BagAdapter(ArrayList<JSONObject> bagItems) {
        this.bagItems = bagItems;
    }

    @NonNull
    @Override
    public BagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bag, parent, false);
        return new BagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BagViewHolder holder, int position) {
        try {
            JSONObject product = bagItems.get(position);
            JSONObject fields = product.getJSONObject("fields");

            holder.productName.setText(fields.getString("name"));
            holder.productPrice.setText(String.format("€%.2f", fields.getDouble("price")));
            holder.productQuantity.setText(String.format("Qty: %d", product.getInt("quantity")));

            Glide.with(holder.itemView.getContext())
                    .load(fields.getString("image_url"))
                    .into(holder.productImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bagItems.size();
    }

    static class BagViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice, productQuantity;

        public BagViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);
        }
    }
}
