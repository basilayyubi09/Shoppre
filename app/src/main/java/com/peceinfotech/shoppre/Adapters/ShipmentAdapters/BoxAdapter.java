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
import com.peceinfotech.shoppre.ShipmentModelResponse.Shipment;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentBox;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;

import java.util.List;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.viewHolder> {

    Context context;
    List<ShipmentBox> list;

    public BoxAdapter(Context context, List<ShipmentBox> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.box_single_layout , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if (list.get(position)!= null){
            holder.boxHeading.setText("Box "+ String.valueOf(position+1));
            holder.dimension.setText(String.valueOf(list.get(position).getBoxLength())+" cm x "+
                    String.valueOf(list.get(position).getBoxWidth())+" cm x "
                    +String.valueOf(list.get(position).getBoxHeight())+" cm x ");
            holder.finalWeight.setText(String.valueOf(list.get(position).getFinalWeight())+" KG");
        }

//        holder.boxElementRecycle.setAdapter();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder{

        TextView boxHeading , dimension , finalWeight;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            boxHeading = itemView.findViewById(R.id.boxHeading);
            dimension = itemView.findViewById(R.id.dimension);
            finalWeight = itemView.findViewById(R.id.finalWeight);

        }
    }
}
