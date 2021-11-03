package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.OrderResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewHolder> {

    List<OrderResponse> list;
    Context context;

    public OrdersAdapter(List<OrderResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_order_layout , parent ,  false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderResponse orderResponse = list.get(position);

        holder.orderWebsiteName.setText(orderResponse.getWebSiteName());
        holder.orderNo.setText("Order No: "+orderResponse.getOrderNo());
        holder.orderDate.setText(orderResponse.getOrderDate());
        holder.orderImage.setImageResource(R.drawable.ic_personal_shopper);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView orderImage;
        TextView orderWebsiteName, orderNo, orderDate;
        LinearLayout viewMore;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            orderImage = itemView.findViewById(R.id.singleOrderImage);
            orderWebsiteName = itemView.findViewById(R.id.orderWebsiteName);
            orderNo = itemView.findViewById(R.id.orderNo);
            orderDate = itemView.findViewById(R.id.orderDate);
            viewMore = itemView.findViewById(R.id.viewMore);


        }
    }
}
