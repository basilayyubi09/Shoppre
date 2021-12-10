package com.peceinfotech.shoppre.Adapters.CreateShipAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.viewHolder> {

    List<DeliveryListModel.Address> list;
    Context context;
    Interface interfaceObject;
    int mCheckedPosition = -1;

    public DeliveryAddressAdapter(List<DeliveryListModel.Address> list, Context context, Interface interfaceObject) {
        this.list = list;
        this.context = context;
        this.interfaceObject = interfaceObject;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.create_shipment_delivery_address
                , parent, false);
        return new viewHolder(view , interfaceObject);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        DeliveryListModel.Address address = list.get(position);

        holder.createShipmentAddressName.setText(address.getName());
        holder.createShipmentPhoneNo.setText(address.getPhone());
        holder.createShipmentAddress.setText(address.getLine1()+" - "+"\n"+address.getState()+" - "+address.getCity()+" - "+"\n"+address.getCountry().getName());
        holder.createShipmentAddressRadioBtn.setChecked(position == mCheckedPosition);
        holder.createShipmentAddressRadioBtn.setOnClickListener(v -> {
            if (position == mCheckedPosition) {

            }
            else {
                mCheckedPosition = position;
                notifyDataSetChanged();
                interfaceObject.radioOperation(address , holder.createShipmentAddressRadioBtn , position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView createShipmentAddressName, createShipmentPhoneNo, createShipmentAddress;
        RadioButton createShipmentAddressRadioBtn;
        Interface interfaceObject;

        public viewHolder(@NonNull View itemView, Interface interfaceObject) {
            super(itemView);
            this.interfaceObject = interfaceObject;
            createShipmentAddressName = itemView.findViewById(R.id.createShipmentAddressName);
            createShipmentPhoneNo = itemView.findViewById(R.id.createShipmentPhoneNo);
            createShipmentAddress = itemView.findViewById(R.id.createShipmentAddrsLine1);
            createShipmentAddressRadioBtn = itemView.findViewById(R.id.radioBtn);
        }
    }
    public interface Interface{
        public void radioOperation(DeliveryListModel.Address address, RadioButton createShipmentAddressRadioBtn, int position);
    }
}
