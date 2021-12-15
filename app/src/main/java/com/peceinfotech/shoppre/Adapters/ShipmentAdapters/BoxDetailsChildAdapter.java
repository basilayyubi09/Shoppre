package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentBox;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoxDetailsChildAdapter extends RecyclerView.Adapter<BoxDetailsChildAdapter.viewHolder> {

    Context context;
    List<ShipmentBox> list;

    public BoxDetailsChildAdapter(Context context, List<ShipmentBox> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.box_detail_child_single_layout , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView websiteName , orderId,noInvoiceFoundText , date , weight , viewPackage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            websiteName = itemView.findViewById(R.id.websiteName);
            image = itemView.findViewById(R.id.image);
            orderId = itemView.findViewById(R.id.orderId);
            noInvoiceFoundText = itemView.findViewById(R.id.noInvoiceFoundText);
            date = itemView.findViewById(R.id.date);
            weight = itemView.findViewById(R.id.weight);
            viewPackage = itemView.findViewById(R.id.viewPackage);
        }
    }
}
