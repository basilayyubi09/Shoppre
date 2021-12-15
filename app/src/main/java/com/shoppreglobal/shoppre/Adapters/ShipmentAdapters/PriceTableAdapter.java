package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.SlabResponse;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class PriceTableAdapter extends RecyclerView.Adapter<PriceTableAdapter.viewHolder> {

    List<SlabResponse> list;
    Context context;

    public PriceTableAdapter(List<SlabResponse> list, Context context) {
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

        String string = list.get(position).getCountryCurrencyRates();
        String[] parts = string.split("-");

        String part2 = parts[1]; // 034556
        holder.pricingTableKg.setText(String.valueOf(list.get(position).getWeight()));
        holder.priceInr.setText( list.get(position).getRate());
        holder.priceUsd.setText(parts[1]);


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
