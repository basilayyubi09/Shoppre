package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.OrderModuleResponses.OrderUpdateResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class OrderUpdateAdapter extends RecyclerView.Adapter<OrderUpdateAdapter.viewHolder> {

    List<OrderUpdateResponse> list;
    Context context;

    public OrderUpdateAdapter(List<OrderUpdateResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_updates_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        OrderUpdateResponse orderUpdateResponse = list.get(position);

        holder.profileImage.getDrawable();
        holder.profileName.setText(orderUpdateResponse.getProfileName());
        holder.orderStatus.setText(orderUpdateResponse.getOrderStatus());
        holder.dateAndTime.setText(orderUpdateResponse.getDateAndTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView profileName, orderStatus, dateAndTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            profileName = itemView.findViewById(R.id.profileName);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            dateAndTime = itemView.findViewById(R.id.dateAndTime);

        }
    }
}
