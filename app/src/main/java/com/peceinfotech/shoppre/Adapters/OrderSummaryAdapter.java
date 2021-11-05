package com.peceinfotech.shoppre.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.viewHolder> {
    List<String> list ;
    Context context;

    public OrderSummaryAdapter(List<String> list , Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_summary_single_layout , parent , false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.websiteName.setText(list.get(position));

        if( position == getItemCount() - 1 ){

            holder.view1.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView websiteName  , dropdown;
        EditText charges , instruction ;
        View view1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            websiteName = itemView.findViewById(R.id.websiteName);
            view1 = itemView.findViewById(R.id.view1);
            charges = itemView.findViewById(R.id.charges);
            instruction = itemView.findViewById(R.id.instruction);
            dropdown = itemView.findViewById(R.id.dropdown);
        }
    }
}
