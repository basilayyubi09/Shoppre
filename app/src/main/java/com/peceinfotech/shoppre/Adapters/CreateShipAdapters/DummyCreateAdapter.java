package com.peceinfotech.shoppre.Adapters.CreateShipAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    MyInterface myInterface;

    public DummyCreateAdapter(List<DummyShipModel> list, Context context , MyInterface myInterface) {
        this.list = list;
        this.context = context;
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.create_ship_single_layout , parent , false);
        return new DummyCreateAdapter.viewHolder(view , myInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        DummyShipModel model = list.get(position);
        if (holder.check.isChecked()){
            holder.check.setChecked(true);
        }
        else{
            holder.check.setChecked(false);
        }
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myInterface.getCheckBox(list.get(position).getId() , holder.check);
            }

        });
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
        }
    }
    public interface MyInterface{
       void getCheckBox(String id, CheckBox check);
    }
}
