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

public class BoxDetailsParentAdapter extends RecyclerView.Adapter<BoxDetailsParentAdapter.viewHolder> {

    Context context;
    List<ShipmentBox> list;

    public BoxDetailsParentAdapter(Context context, List<ShipmentBox> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.box_details_single_layout , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if (list.get(position)!= null){
            int plus = position+1;
            holder.boxHeading.setText("Box "+ plus);
            BoxDetailsChildAdapter adapter = new BoxDetailsChildAdapter(context , list);
        }

//        holder.boxElementRecycle.setAdapter();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder{

        TextView boxHeading ;
        RecyclerView boxRecycle;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            boxHeading = itemView.findViewById(R.id.boxHeading);
            boxRecycle = itemView.findViewById(R.id.boxDetails);


        }
    }
}