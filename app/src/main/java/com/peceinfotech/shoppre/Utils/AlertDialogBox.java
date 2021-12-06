package com.peceinfotech.shoppre.Utils;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.CreateShipRequest.CreateShipmentDeliveryAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

import java.util.List;


public class AlertDialogBox {

    public void showAlertDialog(Context context, List<PackageModel> tempList, int inReviewCount
            , int actionRequiredCount, boolean isBoth, boolean isRestricted, boolean isWrong){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(com.peceinfotech.shoppre.R.layout.alert_dialog_box);

        ImageView alert_close_btn;
        MaterialButton proceedToShipRequestBtn;
        TextView actionText , reviewText , damagedText , restrictedText;

        alert_close_btn = dialog.findViewById(R.id.alert_close_btn);
        proceedToShipRequestBtn = dialog.findViewById(R.id.proceedToShipRequestBtn);
        actionText = dialog.findViewById(R.id.actionText);
        reviewText = dialog.findViewById(R.id.reviewText);
        damagedText = dialog.findViewById(R.id.damagedText);
        restrictedText = dialog.findViewById(R.id.restrictedText);


         if (isBoth){
            damagedText.setVisibility(View.VISIBLE);
            restrictedText.setVisibility(View.VISIBLE);

        }
         if (isRestricted){

            restrictedText.setVisibility(View.VISIBLE);

        }
         if (isWrong){
            damagedText.setVisibility(View.VISIBLE);
        }
         if (actionRequiredCount>0){
             actionText.setVisibility(View.VISIBLE);
         }
        if (inReviewCount>0){
            reviewText.setVisibility(View.VISIBLE);
        }



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
