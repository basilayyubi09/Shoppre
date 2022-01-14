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

public class ShipmentListingAdapter extends RecyclerView.Adapter<ShipmentListingAdapter.viewHolder> {
    List<Shipment> list;
    Context context;

    public ShipmentListingAdapter(List<Shipment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShipmentListingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.shipment_listing_single, parent, false);

        return new ShipmentListingAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentListingAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {

        Shipment shipment = list.get(position);


        holder.name.setText(shipment.getCustomerName());
        holder.quantity.setText("(" + String.valueOf(shipment.getPackages().size()) + ")");

        if (shipment.getStateName() != null) {
            if (shipment.getStateName().equals("Awaiting Payment")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
                holder.action.setBackground(context.getDrawable(R.drawable.action_required_background));
            } else if (shipment.getStateName().equals("In Review")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.in_review_new_blue_color));
                holder.action.setBackground(context.getDrawable(R.drawable.in_review_shipment_bg));
            } else if (shipment.getStateName().equals("Pending Invoice Upload")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.pending_invoice_purple_color));
                holder.action.setBackground(context.getDrawable(R.drawable.pending_invoice_background));
            } else if (shipment.getStateName().equals("Payment Failed")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.pending_invoice_purple_color));
                holder.action.setBackground(context.getDrawable(R.drawable.pending_invoice_background));
            } else if (shipment.getStateName().equals("Payment Confirmed")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.payment_confirm_green_color));
                holder.action.setBackground(context.getDrawable(R.drawable.payment_confirm_background));
            } else if (shipment.getStateName().equals("Dispatched")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.dispatched_blue_color));
                holder.action.setBackground(context.getDrawable(R.drawable.dispatched_background));
            } else if (shipment.getStateName().equals("Delivered")) {
                holder.action.setText(shipment.getStateName());
                holder.action.setTextColor(context.getColor(R.color.dispatched_blue_color));
                holder.action.setBackground(context.getDrawable(R.drawable.dispatched_background));
            }
        } else {
            holder.action.setText("   ");
            holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
            holder.action.setBackground(context.getDrawable(R.drawable.action_required_background));
        }

        holder.shipmentId.setText("Shipment ID #" + shipment.getId());
        holder.viewShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();


                bundle.putInt("id", shipment.getId());
                bundle.putInt("size", shipment.getPackages().size());
                bundle.putInt("stateId", shipment.getStateId());
                bundle.putString("stateName", shipment.getStateName());
                bundle.putSerializable("packages", (Serializable) shipment.getPackages());
                ShipmentLanding shipmentLanding = new ShipmentLanding();
                shipmentLanding.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shipmentLanding, null)
                        .addToBackStack(null).commit();

            }
        });


//        holder.quantity.setText("(" + String.valueOf(list.get(position).getPackageItems().size()) + ")");
//        holder.shipmentId.setText("Package ID #" + (String.valueOf(list.get(position).getId())));
//        holder.webSiteName.setText(list.get(position).getStore().getName());
//
//        if (list.get(position).getStateName().equals("In Review")) {
//            holder.viewShipment.setVisibility(View.VISIBLE);
//
//            holder.action.setText(list.get(position).getStateName());
//            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
//            holder.action.setBackground(context.getDrawable(R.drawable.price_changed_background));
//        } else if (list.get(position).getStateName().equals("Action Required")) {
//            holder.viewShipment.setVisibility(View.VISIBLE);
//
//            holder.action.setText(list.get(position).getStateName());
//            holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
//            holder.action.setBackground(context.getDrawable(R.drawable.order_placed_background));
//        } else if (list.get(position).getStateName().equals("Ready To Send")) {
//            holder.viewShipment.setVisibility(View.VISIBLE);
//
//            holder.action.setText(list.get(position).getStateName());
//            holder.action.setTextColor(context.getColor(R.color.ready_to_ship_green_color));
//            holder.action.setBackground(context.getDrawable(R.drawable.ready_to_ship_background));
//        }else {
//            holder.viewShipment.setVisibility(View.VISIBLE);
//
//            holder.action.setText(list.get(position).getStateName());
//            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
//        }

//        holder.viewShipment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//
//
//                bundle.putInt("id", list.get(position).getId());
//
//                LockerViewPackage lockerViewPackage = new LockerViewPackage();
//                lockerViewPackage.setArguments(bundle);
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerViewPackage, null)
//                        .addToBackStack(null).commit();
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {


        TextView name, shipmentId, quantity, action;
        LinearLayout viewShipment;


        public viewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            shipmentId = itemView.findViewById(R.id.shipmentId);
            quantity = itemView.findViewById(R.id.quantity);
            action = itemView.findViewById(R.id.action);
            viewShipment = itemView.findViewById(R.id.viewShipment);
        }
    }
}
