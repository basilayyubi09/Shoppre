package com.shoppreglobal.shoppre.Adapters.OrderAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.OrderModuleResponses.OrderItem;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class ChildFinalOrderSummaryAdapter extends RecyclerView.Adapter<ChildFinalOrderSummaryAdapter.viewHolder> {

    List<OrderItem> list;
    Context context;

    public ChildFinalOrderSummaryAdapter(List<OrderItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_child_final_order_summary, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.number.setText(String.valueOf(position + 1));
        holder.mainText.setText(list.get(position).getName());
        holder.price.setText("₹ " + String.valueOf(list.get(position).getPriceAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView number, mainText, price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            mainText = itemView.findViewById(R.id.mainText);
            price = itemView.findViewById(R.id.price);
        }
    }
}
