package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.UploadInvoiceProductResponse;

import java.util.List;

public class UploadInvoiceProductAdapter extends RecyclerView.Adapter<UploadInvoiceProductAdapter.viewHolder> {

    List<UploadInvoiceProductResponse> list;
    Context context;

    public UploadInvoiceProductAdapter(List<UploadInvoiceProductResponse> list, Context context) {
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

        holder.productItems.setText(list.get(position).getUploadInvoiceProduct());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView productItems;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            productItems = itemView.findViewById(R.id.uploadInvoiceProduct);
        }
    }
}
