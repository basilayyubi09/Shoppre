package com.peceinfotech.shoppre.Utils;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.CreateShipRequest.CreateShipmentDeliveryAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;


public class AlertDialogBox {

    public void showAlertDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(com.peceinfotech.shoppre.R.layout.alert_dialog_box);

        ImageView alert_close_btn;
        MaterialButton proceedToShipRequestBtn;

        alert_close_btn = dialog.findViewById(R.id.alert_close_btn);
        proceedToShipRequestBtn = dialog.findViewById(R.id.proceedToShipRequestBtn);

        alert_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        proceedToShipRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CreateShipmentDeliveryAddress(), null)
                        .addToBackStack(null).commit();

                dialog.dismiss();

            }
        });

        dialog.show();
    }

}
