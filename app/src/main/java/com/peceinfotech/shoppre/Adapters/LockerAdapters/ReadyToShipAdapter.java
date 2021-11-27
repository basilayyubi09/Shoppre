package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Locker.LockerViewPackage;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

import java.util.List;

public class ReadyToShipAdapter extends RecyclerView.Adapter<ReadyToShipAdapter.viewHolder> {

    List<PackageModel> list;
    Context context;

    public ReadyToShipAdapter(List<PackageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ready_to_ship_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.readyToShipImg.getDrawable();
        holder.quantity.setText("(" + String.valueOf(list.get(position).getPackageItems().size()) + ")");
        holder.packageId.setText("Package ID #" + (String.valueOf(list.get(position).getId())));
        holder.readyToShipWebSiteName.setText(list.get(position).getStore().getName());
        if (list.get(position).getIsRestrictedItem() != null && list.get(position).getIsWrongItem() != null) {
            holder.damageRestricted.setVisibility(View.VISIBLE);
            holder.restrictedItemText.setVisibility(View.GONE);
            holder.damageRestricted.setVisibility(View.GONE);
        } else if (list.get(position).getIsRestrictedItem() != null) {
            holder.damageRestricted.setVisibility(View.GONE);
            holder.restrictedItemText.setVisibility(View.VISIBLE);
            holder.damageRestricted.setVisibility(View.GONE);
        } else if (list.get(position).getIsWrongItem() != null) {
            holder.damageRestricted.setVisibility(View.VISIBLE);
            holder.restrictedItemText.setVisibility(View.GONE);
            holder.damageRestricted.setVisibility(View.GONE);
        } else {

        }

        if (list.get(position).getStateName().equals("In Review")) {
            holder.lockerViewMore.setVisibility(View.GONE);
            holder.process.setVisibility(View.VISIBLE);
            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
            holder.action.setBackground(context.getDrawable(R.drawable.price_changed_background));
        } else if (list.get(position).getStateName().equals("Action Required")) {
            holder.lockerViewMore.setVisibility(View.VISIBLE);
            holder.process.setVisibility(View.GONE);
            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
            holder.action.setBackground(context.getDrawable(R.drawable.order_placed_background));
        } else if (list.get(position).getStateName().equals("Ready To Send")) {
            holder.lockerViewMore.setVisibility(View.VISIBLE);
            holder.process.setVisibility(View.GONE);
            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.ready_to_ship_green_color));
            holder.action.setBackground(context.getDrawable(R.drawable.ready_to_ship_background));
        } else {
            holder.lockerViewMore.setVisibility(View.VISIBLE);
            holder.process.setVisibility(View.GONE);
            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
        }

        holder.lockerViewMore.setOnClickListener(new View.OnClickListener() {
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

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView readyToShipImg;
        TextView readyToShipWebSiteName, packageId, quantity, action;
        LinearLayout lockerViewMore;
        LinearLayout process;
        TextView damageItemText, damageRestricted, restrictedItemText;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            readyToShipImg = itemView.findViewById(R.id.readyToShipImg);
            readyToShipWebSiteName = itemView.findViewById(R.id.readyToShipWebSiteName);
            packageId = itemView.findViewById(R.id.packageId);
            quantity = itemView.findViewById(R.id.quantity);
            action = itemView.findViewById(R.id.action);
            process = itemView.findViewById(R.id.process);
            damageRestricted = itemView.findViewById(R.id.damageRestricted);
            damageItemText = itemView.findViewById(R.id.damageItemText);
            lockerViewMore = itemView.findViewById(R.id.lockerViewMore);
            restrictedItemText = itemView.findViewById(R.id.restrictedItemText);
        }
    }
}
