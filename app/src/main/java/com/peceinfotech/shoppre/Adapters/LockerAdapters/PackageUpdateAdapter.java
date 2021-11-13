package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.LockerModelResponse.PackageUpdateResponse;
import com.peceinfotech.shoppre.R;

import java.util.List;

public class PackageUpdateAdapter extends RecyclerView.Adapter<PackageUpdateAdapter.viewHolder> {

    List<PackageUpdateResponse> list;
    Context context;

    public PackageUpdateAdapter(List<PackageUpdateResponse> list, Context context) {
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

        PackageUpdateResponse packageUpdateResponse = list.get(position);

        holder.name.setText(packageUpdateResponse.getName());
        holder.packageStatus.setText(packageUpdateResponse.getOrderStatus());
        holder.date.setText(packageUpdateResponse.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView name, packageStatus, date;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.packageUpdateName);
            packageStatus = itemView.findViewById(R.id.packageUpdateStatus);
            date = itemView.findViewById(R.id.packageUpdateDate);
        }
    }
}
