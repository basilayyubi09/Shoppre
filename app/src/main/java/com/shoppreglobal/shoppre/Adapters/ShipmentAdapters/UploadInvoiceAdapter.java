package com.shoppreglobal.shoppre.Adapters.ShipmentAdapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.LockerModelResponse.PackageItem;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Locker.LockerViewPackage;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;

import java.util.ArrayList;
import java.util.List;

public class UploadInvoiceAdapter extends RecyclerView.Adapter<UploadInvoiceAdapter.viewHolder> {

    List<PackageModel> list;
    Context context;
    int flag = 1;
    List<PackageItem> list1;
    UploadInvoiceProductAdapter uploadInvoiceProductAdapter;
    Dialog dialog;
    private int IMAGE_CODE = 100;
    private boolean isSelected = false;
    private CallbackInterface mCallback;

    public UploadInvoiceAdapter(List<PackageModel> list, Context context, Dialog dialog, CallbackInterface mCallback) {
        this.list = list;
        this.context = context;
        this.dialog = dialog;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.upload_invoice_single_layout, parent, false);
        return new viewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {



        PackageModel packageModel = list.get(position);

        holder.webSiteName.setText(packageModel.getStore().getName()+" ("+list.size()+")");
        holder.packageId.setText(String.valueOf("Package ID #"+packageModel.getId()));

        if (packageModel.getIsFullInvoiceReceived()==false){
            holder.uploadInvoiceArrow.setVisibility(View.VISIBLE);
            holder.invoiceUploaded.setVisibility(View.GONE);
        }else {
            holder.uploadInvoiceArrow.setVisibility(View.GONE);
            holder.invoiceUploaded.setVisibility(View.VISIBLE);
        }

        list1 = new ArrayList<>();

        list1 = list.get(position).getPackageItems();

        uploadInvoiceProductAdapter = new UploadInvoiceProductAdapter(list1, context);
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


                    mCallback.onSelection(holder.uploadInvoiceArrow, holder.invoiceUploaded);

            }
        });

        holder.viewPackageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();


                bundle.putInt("id", list.get(position).getId());

                LockerViewPackage lockerViewPackage = new LockerViewPackage();
                lockerViewPackage.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, lockerViewPackage, null)
                        .addToBackStack(null).commit();

                dialog.dismiss();
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
        TextView invoiceUploaded, viewPackageText;
        CallbackInterface mCallback;


        public viewHolder(@NonNull View itemView, CallbackInterface mCallback) {
            super(itemView);
            this.mCallback = mCallback;
            webSiteName = itemView.findViewById(R.id.uploadInvoiceWebsiteName);
            packageId = itemView.findViewById(R.id.uploadInvoicePackageId);
            uploadInvoiceItemsRecycler = itemView.findViewById(R.id.uploadInvoiceItemsRecycler);
            expandBtn = itemView.findViewById(R.id.expandBtn);
            uploadInvoiceArrow = itemView.findViewById(R.id.uploadInvoiceArrow);
            invoiceUploaded = itemView.findViewById(R.id.invoiceUploaded);
            viewPackageText = itemView.findViewById(R.id.packageViewText);
        }
    }

    public interface CallbackInterface{
        void onSelection(LinearLayout uploadInvoiceArrow, TextView invoiceUploaded);
    }
}
