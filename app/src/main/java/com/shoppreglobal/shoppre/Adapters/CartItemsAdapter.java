package com.shoppreglobal.shoppre.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderItem;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.viewHolder> {

    Order productItemList;
    Context context;
    SharedPrefManager sharedPrefManager;
    Interface interfaceObject;


    public CartItemsAdapter(Order productItemList, Context context, Interface interfaceObject) {
        this.productItemList = productItemList;
        this.context = context;
        this.interfaceObject = interfaceObject;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_single_layout, parent, false);
        return new viewHolder(view, interfaceObject);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        sharedPrefManager = new SharedPrefManager(context.getApplicationContext());

        holder.productSerialNo.setText(String.valueOf(position + 1));
        holder.productName.setText(productItemList.getOrderItems().get(position).getName());

        holder.productQuantity.setText("(" + String.valueOf(productItemList.getOrderItems().get(position).getQuantity()) + ")");
        holder.productPrice.setText("₹ " + String.valueOf(productItemList.getOrderItems().get(position).getPriceAmount()));

        holder.productDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceObject.delete(productItemList.getId(), productItemList.getOrderItems().get(position).getId());
            }
        });

        holder.productEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceObject.getData(productItemList.getOrderItems().get(position), productItemList.getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return productItemList.getOrderItems().size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView productSerialNo, productName, productQuantity, productPrice;
        ImageView productEditButton, productDeleteBtn;
        Interface interfaceObject;

        public viewHolder(@NonNull View itemView, Interface interfaceObject) {
            super(itemView);
            this.interfaceObject = interfaceObject;
            productSerialNo = itemView.findViewById(R.id.productSerialNo);
            productName = itemView.findViewById(R.id.productName);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productPrice = itemView.findViewById(R.id.productPrice);
            productEditButton = itemView.findViewById(R.id.productEditButton);
            productDeleteBtn = itemView.findViewById(R.id.productDeleteBtn);

        }
    }

    public interface Interface {
        void getData(OrderItem order, Integer id);

        void delete(Integer orderId, Integer itemId);
    }
}