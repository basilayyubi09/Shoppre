package com.peceinfotech.shoppre.Adapters.CreateShipAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.CreateShipmentModelResponse.DeliveryAddressModelResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.viewHolder> {

    List<DeliveryAddressModelResponse> list;
    Context context;

    public DeliveryAddressAdapter(List<DeliveryAddressModelResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.create_shipment_delivery_address, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DeliveryAddressModelResponse deliveryAddressModelResponse = list.get(position);

        holder.name.setText(deliveryAddressModelResponse.getName());
        holder.line1.setText(deliveryAddressModelResponse.getLine1());
        holder.contactNo.setText(deliveryAddressModelResponse.getPhoneNo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView line1;
        TextView city;
        TextView country;
        TextView contactNo;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.createShipmentAddressName);
            line1 = itemView.findViewById(R.id.createShipmentAddrsLine1);
            contactNo = itemView.findViewById(R.id.createShipmentPhoneNo);
        }
    }
}
