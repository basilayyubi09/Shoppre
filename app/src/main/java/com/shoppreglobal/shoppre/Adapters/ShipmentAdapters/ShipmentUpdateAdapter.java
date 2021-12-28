package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentCommentModelResponse;

import java.util.List;

public class ShipmentUpdateAdapter extends RecyclerView.Adapter<ShipmentUpdateAdapter.viewHolder> {

    List<ShipmentCommentModelResponse> list;
    Context context;

    char firstLetter;
    TextDrawable textDrawable;
    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
    int randomColor = colorGenerator.getRandomColor();

    public ShipmentUpdateAdapter(List<ShipmentCommentModelResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shipment_comment_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ShipmentCommentModelResponse response = list.get(position);

        if (response.getUser()!=null){
            holder.shipmentCommentName.setText(response.getUser().getName());
            if (response.getUser().getGroupId()==1){

                firstLetter = list.get(position).getUser().getFirstName().charAt(0);
                textDrawable = TextDrawable.builder()
                        .beginConfig().endConfig()
                        .beginConfig().withBorder(0)
                        .bold().toUpperCase()
                        .endConfig().buildRound(String.valueOf(firstLetter), Color.RED);

                holder.profileImage.setImageDrawable(textDrawable);


                holder.shipmentCommentName.setTextColor(context.getResources().getColor(R.color.text_red));
            }else {
                holder.shipmentCommentName.setTextColor(context.getResources().getColor(R.color.text_blue));
            }
        }

        holder.shipmentCommentStatus.setText(response.getComments());
        holder.shipmentCommentDate.setText(response.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView shipmentCommentName, shipmentCommentStatus, shipmentCommentDate;
        ImageView profileImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            shipmentCommentName = itemView.findViewById(R.id.shipmentCommentName);
            shipmentCommentStatus = itemView.findViewById(R.id.shipmentCommentStatus);
            shipmentCommentDate = itemView.findViewById(R.id.shipmentCommentDate);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }
}
