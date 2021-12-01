package com.peceinfotech.shoppre.Adapters.ShipmentAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.UploadInvoiceProductResponse;
import com.peceinfotech.shoppre.ShipmentModelResponse.UploadInvoiceResponse;

import java.util.ArrayList;
import java.util.List;

public class UploadInvoiceAdapter extends RecyclerView.Adapter<UploadInvoiceAdapter.viewHolder> {

    List<UploadInvoiceResponse> list;
    Context context;
    RecyclerView uploadInvoiceItemsRecycler;
    int flag = 1;


    public UploadInvoiceAdapter(List<UploadInvoiceResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.upload_invoice_single_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {



        UploadInvoiceResponse uploadInvoiceResponse = list.get(position);

        holder.webSiteName.setText(uploadInvoiceResponse.getWebsiteName());
        holder.packageId.setText(uploadInvoiceResponse.getPackageId());


        List<UploadInvoiceProductResponse> list1 = new ArrayList<>();

        list1.add(new UploadInvoiceProductResponse("RedmiNote 9 Pro Max"));
        list1.add(new UploadInvoiceProductResponse("RedmiNote 9 Pro Max"));
        list1.add(new UploadInvoiceProductResponse("RedmiNote 9 Pro Max"));
        list1.add(new UploadInvoiceProductResponse("RedmiNote 9 Pro Max"));
        list1.add(new UploadInvoiceProductResponse("RedmiNote 9 Pro Max"));

        UploadInvoiceProductAdapter uploadInvoiceProductAdapter = new UploadInvoiceProductAdapter(list1, context);
        holder.uploadInvoiceItemsRecycler.setAdapter(uploadInvoiceProductAdapter);

        holder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1){
                    holder.uploadInvoiceItemsRecycler.setVisibility(View.VISIBLE);
                    flag = 2;
                }else if (flag == 2){
                    holder.uploadInvoiceItemsRecycler.setVisibility(View.GONE);
                    flag = 1;
                }

            }
        });

        holder.uploadInvoiceArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.uploadInvoiceArrow.setVisibility(View.GONE);
                holder.invoiceUploaded.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView webSiteName, packageId;
        RecyclerView uploadInvoiceItemsRecycler;
        LinearLayout expandBtn, uploadInvoiceArrow;
        TextView invoiceUploaded;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            webSiteName = itemView.findViewById(R.id.uploadInvoiceWebsiteName);
            packageId = itemView.findViewById(R.id.uploadInvoicePackageId);
            uploadInvoiceItemsRecycler = itemView.findViewById(R.id.uploadInvoiceItemsRecycler);
            expandBtn = itemView.findViewById(R.id.expandBtn);
            uploadInvoiceArrow = itemView.findViewById(R.id.uploadInvoiceArrow);
            invoiceUploaded = itemView.findViewById(R.id.invoiceUploaded);
        }
    }
}
