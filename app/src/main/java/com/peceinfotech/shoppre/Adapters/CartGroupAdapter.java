package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.CartModelResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderItem;
import com.peceinfotech.shoppre.OrderModuleResponses.ProductItem;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class CartGroupAdapter extends RecyclerView.Adapter<CartGroupAdapter.viewHolder> {

    List<Order> list;
    Context context;

    public CartGroupAdapter(List<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.weSiteName.setText(list.get(position).getStore().getName());

        List<OrderItem> list1 = list.get(position).getOrderItems();
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(list1, context);
        holder.productItemRecycler.setAdapter(cartItemsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.productItemRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView weSiteName;
        RecyclerView productItemRecycler;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productItemRecycler = itemView.findViewById(R.id.productItemRecycler);
            weSiteName = itemView.findViewById(R.id.cartWebSiteName);

        }
    }
}
