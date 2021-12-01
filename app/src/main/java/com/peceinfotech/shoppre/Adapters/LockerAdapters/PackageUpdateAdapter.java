package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.peceinfotech.shoppre.LockerModelResponse.PackageUpdateResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class PackageUpdateAdapter extends RecyclerView.Adapter<PackageUpdateAdapter.viewHolder> {

    List<GetCommentsResponse> list;
    Context context;

    public PackageUpdateAdapter(List<GetCommentsResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_update_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {



//        holder.name.setText(packageUpdateResponse.getName());
//        holder.packageStatus.setText(packageUpdateResponse.getOrderStatus());
//        holder.date.setText(packageUpdateResponse.getDate());

        if (list.get(position).getUser()!=null){
            holder.name.setText(list.get(position).getUser().getName());
        }
        if (list.get(position).getUser().getGroupId()==1){

            holder.profileImage.setImageResource(R.drawable.shoppre_ic);
            holder.name.setTextColor(context.getResources().getColor(R.color.text_red));
//            TextDrawable textDrawable;
//            ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

//            holder.profileImage.setImageResource(R.drawable.shoppre_ic);
//            char firstLetter = list.get(position).getUser().getName().charAt(0);
//
//            ////Profile RoundImage with Letter
//
//            textDrawable = TextDrawable.builder()
//                    .beginConfig().endConfig()
//                    .beginConfig().bold().toUpperCase()
//                    .endConfig().buildRound(String.valueOf(firstLetter), R.color.text_red);
//
//            Glide.with(context)
//                    .load(textDrawable)
//                    .into(holder.profileImage);
        }else {
            holder.profileImage.setImageResource(R.drawable.shoppre_ic);
            holder.name.setTextColor(context.getResources().getColor(R.color.text_blue));
        }
        holder.packageStatus.setText(list.get(position).getComments());
        holder.date.setText(list.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name, packageStatus, date;
        ImageView profileImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.packageUpdateName);
            packageStatus = itemView.findViewById(R.id.packageUpdateStatus);
            date = itemView.findViewById(R.id.packageUpdateDate);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
