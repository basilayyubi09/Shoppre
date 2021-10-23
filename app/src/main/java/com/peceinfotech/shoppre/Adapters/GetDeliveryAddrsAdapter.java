package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class GetDeliveryAddrsAdapter extends RecyclerView.Adapter<GetDeliveryAddrsAdapter.viewHolder> {

    List<DeliveryListModel.Address> list;
    Context context;

    public GetDeliveryAddrsAdapter(List<DeliveryListModel.Address> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_all_delivery_addresses , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DeliveryListModel.Address address = list.get(position);

        holder.deliverToName.setText(address.getName());
        holder.line1.setText(address.getLine1());
        holder.state.setText(address.getState());
        holder.country.setText(address.getCountry().getName());
        holder.deliverToContact.setText(address.getPhone());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView deliverToName , line1 , state , country , deliverToContact;
        RadioButton addrsRadioBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            deliverToName = itemView.findViewById(R.id.deliverToName);
            line1 = itemView.findViewById(R.id.line1);
            state = itemView.findViewById(R.id.state);
            country = itemView.findViewById(R.id.country);
            deliverToContact = itemView.findViewById(R.id.deliverToContact);
            addrsRadioBtn = itemView.findViewById(R.id.addressRadioBtn);

        }
    }
}
