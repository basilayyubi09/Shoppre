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

import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Locker.LockerViewPackage;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;

import java.util.List;

public class ViewPreviousShipmentAdapter extends RecyclerView.Adapter<ViewPreviousShipmentAdapter.viewHolder>{

    List<PackageModel> list;
    Context context;

    public ViewPreviousShipmentAdapter(List<PackageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPreviousShipmentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.shipment_listing_single, parent, false);

        return new ViewPreviousShipmentAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPreviousShipmentAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.quantity.setText("(" + String.valueOf(list.get(position).getPackageItems().size()) + ")");
        holder.shipmentId.setText("Package ID #" + (String.valueOf(list.get(position).getId())));
        holder.webSiteName.setText(list.get(position).getStore().getName());

        if (list.get(position).getStateName().equals("In Review")) {
            holder.viewShipment.setVisibility(View.VISIBLE);

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
            holder.action.setBackground(context.getDrawable(R.drawable.price_changed_background));
        } else if (list.get(position).getStateName().equals("Action Required")) {
            holder.viewShipment.setVisibility(View.VISIBLE);

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
            holder.action.setBackground(context.getDrawable(R.drawable.order_placed_background));
        } else if (list.get(position).getStateName().equals("Ready To Send")) {
            holder.viewShipment.setVisibility(View.VISIBLE);

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.ready_to_ship_green_color));
            holder.action.setBackground(context.getDrawable(R.drawable.ready_to_ship_background));
        }else {
            holder.viewShipment.setVisibility(View.VISIBLE);

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
        }

        holder.viewShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();


                bundle.putInt("id", list.get(position).getId());

                LockerViewPackage lockerViewPackage = new LockerViewPackage();
                lockerViewPackage.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerViewPackage, null)
                        .addToBackStack(null).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView webSiteName, shipmentId, quantity, action;
        LinearLayout viewShipment;


        public viewHolder(@NonNull View itemView) {
            super(itemView);


            webSiteName = itemView.findViewById(R.id.webSiteName);
            shipmentId = itemView.findViewById(R.id.shipmentId);
            quantity = itemView.findViewById(R.id.quantity);
            action = itemView.findViewById(R.id.action);
            viewShipment = itemView.findViewById(R.id.viewShipment);
        }
    }
}
