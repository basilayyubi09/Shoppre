package com.peceinfotech.shoppre.Adapters.CreateShipAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Models.DummyShipModel;
import com.peceinfotech.shoppre.R;

import java.util.List;


public class DummyCreateAdapter extends RecyclerView.Adapter<DummyCreateAdapter.viewHolder> {
    List<DummyShipModel> list;
    Context context;

    public DummyCreateAdapter(List<DummyShipModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.create_ship_single_layout , parent , false);
        return new DummyCreateAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DummyShipModel model = list.get(position);
        if (holder.check.isChecked()){
            holder.check.setChecked(true);
        }
        else{
            holder.check.setChecked(false);
        }
        holder.website.setText(model.getWebSiteName());
        holder.quantity.setText("("+model.getQuantity()+")");
        holder.packageId.setText("Package ID #"+model.getPackageId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CheckBox check;
        TextView website , quantity , packageId , action;
        LinearLayout viewPackage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.check);
            website = itemView.findViewById(R.id.webSiteName);
            quantity = itemView.findViewById(R.id.quantity);
            packageId = itemView.findViewById(R.id.packageId);
            action = itemView.findViewById(R.id.action);
            viewPackage = itemView.findViewById(R.id.viewPackage);
        }
    }
}
