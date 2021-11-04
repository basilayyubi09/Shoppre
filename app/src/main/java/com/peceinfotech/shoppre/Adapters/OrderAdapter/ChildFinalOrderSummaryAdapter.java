package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Models.SectionItem;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class ChildFinalOrderSummaryAdapter extends RecyclerView.Adapter<ChildFinalOrderSummaryAdapter.viewHolder> {

    List<SectionItem> list;
    Context context;

    public ChildFinalOrderSummaryAdapter(List<SectionItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_child_final_order_summary , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        SectionItem sectionItem = list.get(position);
        holder.number.setText(String.valueOf(sectionItem.getNumber()));
        holder.mainText.setText(sectionItem.getName());
        holder.price.setText("₹ "+String.valueOf(sectionItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView number , mainText , price;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            mainText = itemView.findViewById(R.id.mainText);
            price = itemView.findViewById(R.id.price);
        }
    }
}
