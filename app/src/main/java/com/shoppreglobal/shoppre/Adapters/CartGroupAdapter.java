package com.shoppreglobal.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderItem;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class CartGroupAdapter extends RecyclerView.Adapter<CartGroupAdapter.viewHolder> {

    List<Order> list;
    Context context;
    CartItemsAdapter cartItemsAdapter;
    OrderItem order1;
    SecondInterface secondInterface;

    public CartGroupAdapter(List<Order> list, Context context, SecondInterface secondInterface) {
        this.list = list;
        this.context = context;
        this.secondInterface = secondInterface;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_single_layout, parent, false);

        return new viewHolder(view, secondInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Order order = list.get(position);

        if (position == 0) {
            holder.line.setVisibility(View.GONE);
        }
        holder.weSiteName.setText(list.get(position).getStore().getName());
        cartItemsAdapter = new CartItemsAdapter(order, context, new CartItemsAdapter.Interface() {
            @Override
            public void getData(OrderItem order, Integer id) {

                order1 = order;
                secondInterface.second(order1, id);
            }

            @Override
            public void delete(Integer orderId, Integer itemId) {
                secondInterface.delete(orderId, itemId);
            }
        });
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
        SecondInterface secondInterface;
        View line;

        public viewHolder(@NonNull View itemView, SecondInterface secondInterface) {
            super(itemView);
            this.secondInterface = secondInterface;
            productItemRecycler = itemView.findViewById(R.id.productItemRecycler);
            weSiteName = itemView.findViewById(R.id.cartWebSiteName);
            line = itemView.findViewById(R.id.line);

        }
    }

    public interface SecondInterface {
        void second(OrderItem order, Integer id);

        void delete(Integer orderId, Integer itemId);
    }
}