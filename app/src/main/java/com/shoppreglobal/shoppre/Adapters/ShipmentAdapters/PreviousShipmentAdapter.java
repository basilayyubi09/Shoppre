package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.ShipmentModelResponse.Shipment;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentLanding;

import java.io.Serializable;
import java.util.List;

public class PreviousShipmentAdapter extends RecyclerView.Adapter<PreviousShipmentAdapter.viewHolder> {

    List<Shipment> list;
    Context context;

    public PreviousShipmentAdapter(List<Shipment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.previous_shipment_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        Shipment response = list.get(position);

        holder.previousShipmentName.setText(response.getCustomerName()+" ("+response.getPackages().size()+")");
        holder.previousShipmentId.setText("Shipment ID #"+String.valueOf(response.getId()));
        if (response.getStateName().equals("Delivered")){
            holder.previousShipmentStatus.setText(response.getStateName());
            holder.previousShipmentStatus.setTextColor(context.getColor(R.color.dispatched_blue_color));
            holder.previousShipmentStatus.setBackground(context.getDrawable(R.drawable.dispatched_background));
        }else {
            holder.previousShipmentStatus.setText(response.getStateName());
        }
        holder.viewShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", response.getId());
                bundle.putString("stateName", response.getStateName());
                bundle.putSerializable("packages", (Serializable) response.getPackages());
                ShipmentLanding shipmentLanding = new ShipmentLanding();
                shipmentLanding.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shipmentLanding, null)
                        .addToBackStack(null).commit();

//                bundle.putInt("id", shipment.getId());
//                bundle.putString("stateName", shipment.getStateName());
//                bundle.putSerializable("packages", (Serializable) shipment.getPackages());
//                ShipmentLanding shipmentLanding = new ShipmentLanding();
//                shipmentLanding.setArguments(bundle);
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shipmentLanding, null)
//                        .addToBackStack(null).commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView previousShipmentName, previousShipmentId, previousShipmentStatus;
        LinearLayout viewShipment;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            previousShipmentName = itemView.findViewById(R.id.previousShipmentName);
            previousShipmentId = itemView.findViewById(R.id.previousShipmentId);
            previousShipmentStatus = itemView.findViewById(R.id.previousShipmentStatus);
            viewShipment = itemView.findViewById(R.id.previousShipmentViewShipment);
        }
    }
}
