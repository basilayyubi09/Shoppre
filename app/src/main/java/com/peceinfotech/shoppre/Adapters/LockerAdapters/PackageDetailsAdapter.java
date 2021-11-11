package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.LockerModelResponse.PackageDetailsResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class PackageDetailsAdapter extends RecyclerView.Adapter<PackageDetailsAdapter.viewHolder>{

    List<PackageDetailsResponse> list;
    Context context;

    public PackageDetailsAdapter(List<PackageDetailsResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.package_details_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PackageDetailsResponse packageDetailsResponse = list.get(position);

        holder.packageItemImage.getDrawable();
        holder.packageItemName.setText(packageDetailsResponse.getPackageItemName());
        holder.packageItemId.setText(packageDetailsResponse.getPackageItemId());
        holder.packageQuantity.setText(packageDetailsResponse.getPackageItemQuantity());
        holder.packageItemPrice.setText(packageDetailsResponse.getPackageItemPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView packageItemImage, editPackageItem;
        TextView packageItemName, packageItemId, packageQuantity, packageItemPrice;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            packageItemImage = itemView.findViewById(R.id.packageItemImage);
            editPackageItem = itemView.findViewById(R.id.editPackageItem);
            packageItemName = itemView.findViewById(R.id.packageItemName);
            packageItemId = itemView.findViewById(R.id.packageItemId);
            packageQuantity = itemView.findViewById(R.id.packageQuantity);
            packageItemPrice = itemView.findViewById(R.id.packageItemPrice);

        }


    }
}
