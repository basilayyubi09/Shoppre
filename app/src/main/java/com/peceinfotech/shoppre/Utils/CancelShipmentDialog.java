package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.CancelShipmentModelResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;

import retrofit2.Call;

public class CancelShipmentDialog {
    
    public void showCancelShipmentDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cancel_your_shipment_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView cancelShipmentCross;
        MaterialButton cancelShipmentBtn;
        
        cancelShipmentCross = dialog.findViewById(R.id.cancelShipmentCross);
        cancelShipmentBtn = dialog.findViewById(R.id.cancelShipmentDialogBtn);
        
        cancelShipmentCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        cancelShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

}
