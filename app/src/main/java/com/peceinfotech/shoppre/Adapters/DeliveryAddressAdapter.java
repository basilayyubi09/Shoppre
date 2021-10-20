package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;

import java.util.List;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.viewHolder> {

    List<Address> addressList;
    Context context;

    public DeliveryAddressAdapter(List<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView deliverToPersonName , deliverToPersonContactNo , deliverToPersonAddress;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            deliverToPersonName = itemView.findViewById(R.id.deliverToName);
            deliverToPersonContactNo = itemView.findViewById(R.id.deliverToContactNo);
            deliverToPersonAddress = itemView.findViewById(R.id.deliverToContactNo);

        }
    }
}
