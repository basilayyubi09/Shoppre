package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsResponse;

import java.util.List;

public class ShipmentDetailsAdapter extends RecyclerView.Adapter<ShipmentDetailsAdapter.viewHolder> {

    List<ShipmentDetailsResponse> list;
    Context context;

    public ShipmentDetailsAdapter(List<ShipmentDetailsResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shiping_details_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ShipmentDetailsResponse shipmentDetailsResponse = list.get(position);

        holder.shipmentDetailsImage.getDrawable();
        holder.ShippingDetailsWebsiteName.setText(shipmentDetailsResponse.getWebSiteName());
        holder.shippingDetailsOrderId.setText(shipmentDetailsResponse.getPackageId());
        holder.shippingDetailsDate.setText(shipmentDetailsResponse.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView shipmentDetailsImage;
        TextView ShippingDetailsWebsiteName, shippingDetailsOrderId, shippingDetailsDate, shippingDetailsWeight;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            shipmentDetailsImage = itemView.findViewById(R.id.shipmentDetailsImage);
            ShippingDetailsWebsiteName = itemView.findViewById(R.id.ShippingDetailsWebsiteName);
            shippingDetailsOrderId = itemView.findViewById(R.id.shippingDetailsOrderId);
            shippingDetailsDate = itemView.findViewById(R.id.shippingDetailsDate);
            shippingDetailsWeight = itemView.findViewById(R.id.shippingDetailsWeight);
        }
    }
}
