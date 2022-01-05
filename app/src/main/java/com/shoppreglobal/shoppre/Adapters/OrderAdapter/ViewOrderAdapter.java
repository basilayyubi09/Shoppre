package com.shoppreglobal.shoppre.Adapters.OrderAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.OrderItem;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.tooltip.Tooltip;

import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.viewHolder> {

    List<OrderItem> list;
    Context context;

    public ViewOrderAdapter(List<OrderItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_order_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {


//        holder.productImage.getDrawable();
        holder.productName.setText(list.get(position).getName());
        if (list.get(position).getColor().equals("") || list.get(position).getColor() == null && list.get(position).getSize().equals("") || list.get(position).getSize() == null) {
            holder.productColor.setVisibility(View.GONE);
//            holder.colorHeading.setVisibility(View.GONE);
        } else if(list.get(position).getColor().equals("") || list.get(position).getColor() == null) {
            holder.productColor.setText("Size: "+list.get(position).getSize());

        } else if(list.get(position).getSize().equals("") || list.get(position).getSize() == null) {
            holder.productColor.setText("Color: "+list.get(position).getColor());

        }

        else
         {
            holder.productColor.setText("Color & Size: "+list.get(position).getColor()+" "+list.get(position).getSize());
        }

        holder.productQuantity.setText(String.valueOf(list.get(position).getQuantity()));

        holder.productRate.setText("₹ "+String.valueOf(list.get(position).getPriceAmount()));
//

        holder.webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", list.get(position).getUrl());
                WebViewFragment cash = new WebViewFragment();
                cash.setArguments(bundle);

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                        .addToBackStack(null).commit();
            }
        });
        holder.btn.setVisibility(View.VISIBLE);
        if (list.get(position).getOrderItemState().getState()!=null){
            holder.btn.setText(list.get(position).getOrderItemState().getState().getName());
        }else {
            holder.btn.setText("");
        }



        holder.toolTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                holder.toolTip.performLongClick();
                showToolTip(v);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView productImage , webView, toolTip;
        TextView productName, productColor, productQuantity, productRate, colorHeading , btn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.viewOrderProductImage);
            webView = itemView.findViewById(R.id.webview);
            btn = itemView.findViewById(R.id.btn);
            productName = itemView.findViewById(R.id.viewOrderProductName);
            productColor = itemView.findViewById(R.id.viewOrderProductColor);
            productQuantity = itemView.findViewById(R.id.viewOrderProductQuantity);
            productRate = itemView.findViewById(R.id.viewOrderProductRate);

            toolTip = itemView.findViewById(R.id.toolTip);

        }
    }

    public void showToolTip(View v){
        ImageView tooltipImage = (ImageView) v;
        @SuppressLint("ResourceAsColor") Tooltip tooltip = new Tooltip.Builder(tooltipImage)
                .setText("Cancel this item & purchase\nall the other available items")
                .setTextColor(Color.BLACK)
                .setCornerRadius(5f)
                .setBackgroundColor(Color.WHITE)
                .setCancelable(true)
                .setPadding(10f)
                .setArrowHeight(14f)
                .setArrowWidth(14f)
                .setTextSize(12f)
                .show();
    }
}
