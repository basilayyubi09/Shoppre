package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.LockerModelResponse.ReturnedAndDiscardResponse;

import java.util.List;

public class ReturnedAndDiscardAdapter extends RecyclerView.Adapter<ReturnedAndDiscardAdapter.viewHolder> {

    List<ReturnedAndDiscardResponse> list;
    Context context;

    public ReturnedAndDiscardAdapter(List<ReturnedAndDiscardResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.returned_and_discard_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ReturnedAndDiscardResponse returnedAndDiscardResponse = list.get(position);

        holder.returnedAndDiscardImage.getDrawable();
        holder.returnedAndDiscardWebName.setText(returnedAndDiscardResponse.getReturnAndDiscardWebName());
        holder.returnedAndDiscardPackageId.setText(returnedAndDiscardResponse.getReturnAndDiscardPackageId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView returnedAndDiscardImage;
        TextView returnedAndDiscardWebName, returnedAndDiscardPackageId;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            returnedAndDiscardImage = itemView.findViewById(R.id.returnedAndDiscardImage);
            returnedAndDiscardWebName = itemView.findViewById(R.id.returnedAndDiscardWebName);
            returnedAndDiscardPackageId = itemView.findViewById(R.id.returnedAndDiscardPackageId);


        }
    }
}
