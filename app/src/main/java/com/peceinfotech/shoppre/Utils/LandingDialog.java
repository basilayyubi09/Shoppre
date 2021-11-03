package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;

public class LandingDialog {

    public void showDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.landing_dialog);


        MaterialButton continueOrder = dialog.findViewById(R.id.continueOrder);
        MaterialButton createNewOrder = dialog.findViewById(R.id.createNewOrder);

        continueOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        createNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}