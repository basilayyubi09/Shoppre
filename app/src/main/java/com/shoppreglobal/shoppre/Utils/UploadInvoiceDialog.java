package com.shoppreglobal.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.UploadInvoiceAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.ShipmentModelResponse.UploadInvoiceResponse;

import java.util.ArrayList;
import java.util.List;

public class UploadInvoiceDialog {

    public void uploadInvoiceShowDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.upload_invoice_dialog_box);

        RecyclerView uploadInvoiceRecycler;
        List<UploadInvoiceResponse> list = new ArrayList<>();

        uploadInvoiceRecycler = dialog.findViewById(R.id.uploadInvoiceRecycler);

        list.add(new UploadInvoiceResponse("Myntra", "Package ID"+"#444"));
        list.add(new UploadInvoiceResponse("Myntra", "Package ID"+"#444"));
        list.add(new UploadInvoiceResponse("Myntra", "Package ID"+"#444"));
        list.add(new UploadInvoiceResponse("Myntra", "Package ID"+"#444"));
        list.add(new UploadInvoiceResponse("Myntra", "Package ID"+"#444"));

        UploadInvoiceAdapter uploadInvoiceAdapter = new UploadInvoiceAdapter(list, context);
        uploadInvoiceRecycler.setAdapter(uploadInvoiceAdapter);


        dialog.show();
    }

}
