package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.OrderItem;
import com.peceinfotech.shoppre.OrderModuleResponses.ProductItem;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.viewHolder> {

    List<OrderItem> productItemList;
    Context context;

    public CartItemsAdapter(List<OrderItem> productItemList, Context context) {
        this.productItemList = productItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {



        holder.productSerialNo.setText(String.valueOf(position+1));
        holder.productName.setText(productItemList.get(position).getName());

        holder.productQuantity.setText("("+String.valueOf(productItemList.get(position).getQuantity())+")");
        holder.productPrice.setText("₹ "+String.valueOf(productItemList.get(position).getPriceAmount()));

    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView productSerialNo, productName, productQuantity, productPrice;
        ImageView productEditButton, productDeleteBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productSerialNo = itemView.findViewById(R.id.productSerialNo);
            productName = itemView.findViewById(R.id.productName);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productPrice = itemView.findViewById(R.id.productPrice);
            productEditButton = itemView.findViewById(R.id.productEditButton);
            productDeleteBtn = itemView.findViewById(R.id.productDeleteBtn);

        }
    }
}
