package com.peceinfotech.shoppre.Adapters.LockerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.LockerModelResponse.ReadyToShipResponse;
import com.peceinfotech.shoppre.UI.Locker.LockerViewPackage;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

import java.util.List;

public class ReadyToShipAdapter extends RecyclerView.Adapter<ReadyToShipAdapter.viewHolder> {

    List<ReadyToShipResponse> list;
    Context context;

    public ReadyToShipAdapter(List<ReadyToShipResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ready_to_ship_single_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ReadyToShipResponse readyToShipResponse = list.get(position);

        holder.readyToShipImg.getDrawable();
        holder.packageId.setText(readyToShipResponse.getPackageId());
        holder.readyToShipWebSiteName.setText(readyToShipResponse.getReadyToShipWebSiteName());

        holder.lockerViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new LockerViewPackage(), null)
                        .addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView readyToShipImg;
        TextView readyToShipWebSiteName, packageId;
        LinearLayout lockerViewMore;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            readyToShipImg = itemView.findViewById(R.id.readyToShipImg);
            readyToShipWebSiteName = itemView.findViewById(R.id.readyToShipWebSiteName);
            packageId = itemView.findViewById(R.id.packageId);
            lockerViewMore = itemView.findViewById(R.id.lockerViewMore);
        }
    }
}
