package com.shoppreglobal.shoppre.Adapters.CreateShipAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Locker.LockerViewPackage;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;

import java.util.List;


public class DummyCreateAdapter extends RecyclerView.Adapter<DummyCreateAdapter.viewHolder> {
    List<PackageModel> list;
    Context context;
    MyInterface myInterface;

    public DummyCreateAdapter(List<PackageModel> list, Context context, MyInterface myInterface) {
        this.list = list;
        this.context = context;
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.create_ship_single_layout, parent, false);
        return new DummyCreateAdapter.viewHolder(view, myInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        PackageModel model = list.get(position);
        if (holder.check.isChecked()) {
            holder.check.setChecked(true);
        } else {
            holder.check.setChecked(false);
        }
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myInterface.getCheckBox(model.getId(), holder.check, model.getPriceAmount(), model.getInvoice(), model);
            }

        });
        holder.website.setText(model.getStore().getName());
        holder.quantity.setText("(" + model.getPackageItems().size() + ")");
        holder.packageId.setText("Package ID #" + model.getId());
        if (list.get(position).getIsRestrictedItem() != null && list.get(position).getIsWrongItem() != null) {
            holder.both.setVisibility(View.VISIBLE);
            holder.restricted.setVisibility(View.GONE);
            holder.damaged.setVisibility(View.GONE);
        } else if (list.get(position).getIsRestrictedItem() != null) {
            holder.both.setVisibility(View.GONE);
            holder.restricted.setVisibility(View.VISIBLE);
            holder.damaged.setVisibility(View.GONE);
        } else if (list.get(position).getIsWrongItem() != null) {
            holder.both.setVisibility(View.VISIBLE);
            holder.restricted.setVisibility(View.GONE);
            holder.damaged.setVisibility(View.GONE);
        } else {

        }

        if (list.get(position).getStateName().equals("In Review")) {

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
            holder.action.setBackground(context.getDrawable(R.drawable.price_changed_background));
        } else if (list.get(position).getStateName().equals("Action Required")) {

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.action_required_yellow_color));
            holder.action.setBackground(context.getDrawable(R.drawable.order_placed_background));
        } else if (list.get(position).getStateName().equals("Ready To Send")) {

            holder.action.setText("Ready To Ship");
            holder.action.setTextColor(context.getColor(R.color.ready_to_ship_green_color));
            holder.action.setBackground(context.getDrawable(R.drawable.ready_to_ship_background));
        } else {

            holder.action.setText(list.get(position).getStateName());
            holder.action.setTextColor(context.getColor(R.color.in_review_blue_color));
        }
        holder.viewPackage.setOnClickListener(new View.OnClickListener() {
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

        CheckBox check;
        TextView website, quantity, packageId, action, both, damaged, restricted;
        LinearLayout viewPackage;
        MyInterface myInterface;

        public viewHolder(@NonNull View itemView, MyInterface myInterface) {
            super(itemView);

            this.myInterface = myInterface;
            check = itemView.findViewById(R.id.check);
            website = itemView.findViewById(R.id.webSiteName);
            quantity = itemView.findViewById(R.id.quantity);
            packageId = itemView.findViewById(R.id.packageId);
            action = itemView.findViewById(R.id.action);
            viewPackage = itemView.findViewById(R.id.viewPackage);
            both = itemView.findViewById(R.id.both);
            damaged = itemView.findViewById(R.id.damaged);
            restricted = itemView.findViewById(R.id.restricted);
        }
    }

    public interface MyInterface {
        void getCheckBox(Integer id, CheckBox check, Integer priceAmount, Object invoice, PackageModel model);
    }
}
