package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.ShipmentModelResponse.BoxWeight;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;

import java.util.List;

public class BoxWeightAdapter extends RecyclerView.Adapter<BoxWeightAdapter.viewHolder> {

    List<BoxWeight> list;
    Context context;

    public BoxWeightAdapter(List<BoxWeight> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.box_recycler_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.tvBoxNumber.setText("Box "+String.valueOf(position+1));
        holder.tvBoxWeight.setText(String.valueOf(list.get(position).getFinalWeight()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvBoxNumber, tvBoxWeight;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvBoxNumber = itemView.findViewById(R.id.tvBoxNumber);
            tvBoxWeight = itemView.findViewById(R.id.tvBoxWeight);
        }
    }
}
