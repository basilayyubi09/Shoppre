package com.peceinfotech.shoppre.Adapters.CreateShipAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class ShowAddressPopUpAdapter extends RecyclerView.Adapter<ShowAddressPopUpAdapter.viewHolder> {

    List<DeliveryListModel.Address> list;
    Context context;
    Interface interfaceObject;

    int lastSelectedPosition = -1;

    public ShowAddressPopUpAdapter(List<DeliveryListModel.Address> list, Context context, Interface interfaceObject) {
        this.list = list;
        this.context = context;
        this.interfaceObject = interfaceObject;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_address_single_layout, parent, false);
        return new viewHolder(view, interfaceObject);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        DeliveryListModel.Address address = list.get(position);

        holder.showAddressName.setText(address.getName());
        holder.showAddressPhoneNo.setText(address.getPhone());
        holder.showAddressLine1.setText(address.getLine1()+" - "+"\n"+address.getState()+" - "+address.getCity()+" - "+"\n"+address.getCountry().getName());

        holder.showAddressRadioButton.setChecked(lastSelectedPosition == position);
        holder.showAddressRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (position==lastSelectedPosition){
//
//                }else {
//                    lastSelectedPosition=position;
//
//                    Toast.makeText(v.getContext(), address.getName(), Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                }

                interfaceObject.popUpRadioButtonOperation(address, holder.showAddressRadioButton);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        RadioButton showAddressRadioButton;
        TextView showAddressName, showAddressPhoneNo, showAddressLine1;
        Interface interfaceObject;

        public viewHolder(@NonNull View itemView, Interface interfaceObject) {
            super(itemView);

            this.interfaceObject = interfaceObject;
            showAddressRadioButton = itemView.findViewById(R.id.showAddressRadioBtn);
            showAddressName = itemView.findViewById(R.id.showAddressName);
            showAddressPhoneNo = itemView.findViewById(R.id.showAddressPhoneNo);
            showAddressLine1 = itemView.findViewById(R.id.showAddressLine1);

        }
    }

    public interface Interface{
        public void popUpRadioButtonOperation(DeliveryListModel.Address address, RadioButton showAddressRadioButton);
    }
}
