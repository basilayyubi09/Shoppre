package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class GetDeliveryAddrsAdapter extends RecyclerView.Adapter<GetDeliveryAddrsAdapter.viewHolder> {

    List<DeliveryListModel.Address> list;
    Context context;

    setDefaultAddress mListener;

    int lastSelectedposition = -1;

    int addrsId;


    public GetDeliveryAddrsAdapter(List<DeliveryListModel.Address> list, Context context, setDefaultAddress mListener) {
        this.list = list;
        this.context = context;
        this.mListener = mListener;

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_all_delivery_addresses, parent, false);
        return new viewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        DeliveryListModel.Address address = list.get(position);

        holder.deliverToName.setText(address.getName());
        holder.line1.setText(address.getLine1());
        holder.state.setText(address.getState());
        holder.country.setText(address.getCountry().getName());
        holder.deliverToContact.setText(address.getPhone());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Put the value
//                AddAddress addAddress = new AddAddress();
//                Bundle bundle = new Bundle();
//                bundle.putString("type", "update");
//                bundle.putInt("id" ,address.getId() );
//                addAddress.setArguments(bundle);
//
//                //Inflate the fragment
//                OrderActivity.fragmentManager.beginTransaction()
//                        .replace(R.id.orderFrameLayout , new AddAddress() , null)
//                        .addToBackStack(null).commit();

                Toast.makeText(context.getApplicationContext(), "Work in progress", Toast.LENGTH_SHORT).show();

            }
        });



        holder.addrsRadioBtn.setChecked(lastSelectedposition == position);


        if (address.getIsDefault()) {

            Log.d("jgjgkjkjjg", address.getIsDefault().toString());
//                holder.addrsRadioBtn.setChecked(true);

            holder.defaultBtn.setVisibility(View.VISIBLE);

        } else {

            holder.defaultBtn.setVisibility(View.GONE);

        }


        holder.addrsRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lastSelectedposition = holder.getAdapterPosition();
                addrsId = holder.addrsRadioBtn.getId();
                mListener.defaultAdddressSet(address.getId());
                notifyDataSetChanged();

//                if (position == lastSelectedposition){
//
//                    holder.addrsRadioBtn.setChecked(true);
//                    lastSelectedposition = -1;
//
//                }else {
//                    lastSelectedposition = position;
//                    notifyDataSetChanged();
//
//                }

            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView deliverToName, line1, state, country, deliverToContact , edit , delete;
        MaterialButton defaultBtn;
        RadioButton addrsRadioBtn;
        setDefaultAddress mListener;



        public viewHolder(@NonNull View itemView, setDefaultAddress mListener) {
            super(itemView);


            this.mListener = mListener;
            deliverToName = itemView.findViewById(R.id.deliverToName);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            line1 = itemView.findViewById(R.id.line1);
            state = itemView.findViewById(R.id.state);
            country = itemView.findViewById(R.id.country);
            deliverToContact = itemView.findViewById(R.id.deliverToContact);
            addrsRadioBtn = itemView.findViewById(R.id.addressRadioBtn);
            defaultBtn = itemView.findViewById(R.id.defaultButton);

        }
    }


    public interface setDefaultAddress {

        void defaultAdddressSet(int addrsId);

    }



}

