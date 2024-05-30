package com.example.androidca.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidca.R;
import com.example.androidca.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderDate.setText(order.getOrderDate());
        holder.orderDetails.setText(order.getOrderDetails());
        holder.totalAmount.setText(String.valueOf(order.getTotalAmount()));
        holder.deliveryAddress.setText(order.getDeliveryAddress()); // Bind delivery address
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderDate, orderDetails, totalAmount, deliveryAddress; // Add deliveryAddress TextView

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.textViewOrderDate);
            orderDetails = itemView.findViewById(R.id.textViewOrderDetails);
            totalAmount = itemView.findViewById(R.id.textViewTotalAmount);
            deliveryAddress = itemView.findViewById(R.id.textViewDeliveryAddress); // Initialize deliveryAddress
        }
    }
}
