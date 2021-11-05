package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.ProductItem;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.viewHolder> {

    List<ProductItem> productItemList;
    Context context;

    public CartItemsAdapter(List<ProductItem> productItemList, Context context) {
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

        ProductItem productItem = productItemList.get(position);

        holder.productSerialNo.setText(String.valueOf(productItem.getProductSerialNo()));
        holder.productName.setText(productItem.getProductName());
        holder.productQuantity.setText("("+String.valueOf(productItem.getProductCount())+")");
        holder.productPrice.setText(String.valueOf(productItem.getProductPrice()));

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
