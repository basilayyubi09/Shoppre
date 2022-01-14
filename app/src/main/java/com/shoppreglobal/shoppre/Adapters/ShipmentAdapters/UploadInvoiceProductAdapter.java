package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.LockerModelResponse.PackageItem;
import com.shoppreglobal.shoppre.R;

import java.util.List;

public class UploadInvoiceProductAdapter extends RecyclerView.Adapter<UploadInvoiceProductAdapter.viewHolder> {

    List<PackageItem> list;
    Context context;

    public UploadInvoiceProductAdapter(List<PackageItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.upload_invoice_item_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.productItems.setText(list.get(position).getName());


//        if (list.get(position).getIsFullInvoiceReceived()!=null){
//            if (list.get(position).getIsFullInvoiceReceived()==false){
//                holder.productItems.setText(list.get(position).getPackageItems().get(position).getName());
//            }else {
//                holder.productItems.setVisibility(View.GONE);
//            }
//        }else {
//            holder.productItems.setText(list.get(position).getPackageItems().get(position).getName());
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView productItems;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productItems = itemView.findViewById(R.id.uploadInvoiceProduct);
        }
    }
}
