package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.ViewOrderResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.viewHolder> {

    List<ViewOrderResponse> list;
    Context context;

    public ViewOrderAdapter(List<ViewOrderResponse> list, Context context) {
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
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ViewOrderResponse viewOrderResponse = list.get(position);

        holder.productImage.getDrawable();
        holder.productName.setText(viewOrderResponse.getProductName());
        holder.productColor.setText(viewOrderResponse.getProductColor());
        holder.productQuantity.setText(viewOrderResponse.getProductQuantity());
        holder.productRate.setText(viewOrderResponse.getProductRate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName, productColor, productQuantity, productRate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.viewOrderProductImage);
            productName = itemView.findViewById(R.id.viewOrderProductName);
            productColor = itemView.findViewById(R.id.viewOrderProductColor);
            productQuantity = itemView.findViewById(R.id.viewOrderProductQuantity);
            productRate = itemView.findViewById(R.id.viewOrderProductRate);

        }
    }
}
