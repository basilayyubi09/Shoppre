package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
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

    setDefaultAddress mListener;


    private int lastSelectedPosition = -1;
    int addrsId;

    public GetDeliveryAddrsAdapter(List<DeliveryListModel.Address> list, Context context, setDefaultAddress mListener) {
        this.list = list;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_all_delivery_addresses , parent , false);
        return new viewHolder(view , mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DeliveryListModel.Address address = list.get(position);

        holder.deliverToName.setText(address.getName());
        holder.line1.setText(address.getLine1());
        holder.state.setText(address.getState());
        holder.country.setText(address.getCountry().getName());
        holder.deliverToContact.setText(address.getPhone());

        holder.addrsRadioBtn.setChecked(lastSelectedPosition == position);



        holder.addrsRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lastSelectedPosition = holder.getAdapterPosition();
                addrsId = holder.addrsRadioBtn.getId();
                mListener.defaultAdddressSet(address.getId());
                notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView deliverToName , line1 , state , country , deliverToContact;
        MaterialButton defaultBtn;
        RadioButton addrsRadioBtn;
        setDefaultAddress mListener;

        public viewHolder(@NonNull View itemView, setDefaultAddress mListener) {
            super(itemView);

            this.mListener = mListener;
            deliverToName = itemView.findViewById(R.id.deliverToName);
            line1 = itemView.findViewById(R.id.line1);
            state = itemView.findViewById(R.id.state);
            country = itemView.findViewById(R.id.country);
            deliverToContact = itemView.findViewById(R.id.deliverToContact);
            addrsRadioBtn = itemView.findViewById(R.id.addressRadioBtn);
            defaultBtn = itemView.findViewById(R.id.defaultButton);

        }
    }


    public interface setDefaultAddress{

        void defaultAdddressSet(int addrsId);
    }

}
