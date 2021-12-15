package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Locker.LockerViewPackage;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ShipmentDetailsAdapter extends RecyclerView.Adapter<ShipmentDetailsAdapter.viewHolder> {

    List<PackageModel> list;
    Context context;

    public ShipmentDetailsAdapter(List<PackageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shiping_details_single_layout, parent, false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PackageModel packageModel = list.get(position);

        holder.ShippingDetailsWebsiteName.setText(packageModel.getStore().getName()+"("+packageModel.getPackageItems().size()+")");
        holder.shippingDetailsOrderId.setText("Package ID #"+String.valueOf(packageModel.getId()));

        String s = packageModel.getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        holder.shippingDetailsDate.setText(month_name);

        holder.shippingDetailsWeight.setText(String.valueOf(packageModel.getWeight()));

        if (packageModel.getInvoice()==null){
            holder.noInvoiceFoundText.setVisibility(View.VISIBLE);
        }

        holder.viewPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", packageModel.getId());
                LockerViewPackage lockerViewPackage = new LockerViewPackage();
                lockerViewPackage.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerViewPackage, null)
                        .addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView shipmentDetailsImage;
        TextView ShippingDetailsWebsiteName, shippingDetailsOrderId, shippingDetailsDate, shippingDetailsWeight, noInvoiceFoundText;
        LinearLayout viewPackage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            shipmentDetailsImage = itemView.findViewById(R.id.shipmentDetailsImage);
            ShippingDetailsWebsiteName = itemView.findViewById(R.id.ShippingDetailsWebsiteName);
            shippingDetailsOrderId = itemView.findViewById(R.id.shippingDetailsOrderId);
            shippingDetailsDate = itemView.findViewById(R.id.shippingDetailsDate);
            shippingDetailsWeight = itemView.findViewById(R.id.shippingDetailsWeight);
            noInvoiceFoundText = itemView.findViewById(R.id.noInvoiceFoundText);
            viewPackage = itemView.findViewById(R.id.viewPackage);
        }
    }
}
