package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentCommentModelResponse;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;

import java.util.List;

public class ShipmentUpdateAdapter extends RecyclerView.Adapter<ShipmentUpdateAdapter.viewHolder> {

    List<ShipmentCommentModelResponse> list;
    Context context;

    public ShipmentUpdateAdapter(List<ShipmentCommentModelResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shipment_comment_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ShipmentCommentModelResponse response = list.get(position);

        if (response.getUser()!=null){
            holder.shipmentCommentName.setText(response.getUser().getName());
            if (response.getUser().getGroupId()==1){

                holder.shipmentCommentName.setTextColor(context.getResources().getColor(R.color.text_red));
            }else {
                holder.shipmentCommentName.setTextColor(context.getResources().getColor(R.color.text_blue));
            }
        }

        holder.shipmentCommentStatus.setText(response.getComments());
        holder.shipmentCommentDate.setText(response.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView shipmentCommentName, shipmentCommentStatus, shipmentCommentDate;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            shipmentCommentName = itemView.findViewById(R.id.shipmentCommentName);
            shipmentCommentStatus = itemView.findViewById(R.id.shipmentCommentStatus);
            shipmentCommentDate = itemView.findViewById(R.id.shipmentCommentDate);

        }
    }
}
