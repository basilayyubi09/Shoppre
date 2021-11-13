package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.PriceTableResponse;

import java.util.List;

public class PriceTableAdapter extends RecyclerView.Adapter<PriceTableAdapter.viewHolder> {

    List<PriceTableResponse> list;
    Context context;

    public PriceTableAdapter(List<PriceTableResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pricing_table_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PriceTableResponse priceTableResponse = list.get(position);

        holder.pricingTableKg.setText(priceTableResponse.getTableKg());
        holder.priceInr.setText(priceTableResponse.getPriceInr());
        holder.priceUsd.setText(priceTableResponse.getPriceUsd());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView pricingTableKg, priceInr, priceUsd;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            pricingTableKg = itemView.findViewById(R.id.pricingTableKg);
            priceInr = itemView.findViewById(R.id.priceInr);
            priceUsd = itemView.findViewById(R.id.priceUsd);

        }
    }
}
