package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;

public class ViewPhotoDialog {

    public void showDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.view_photo_dialog);


        ImageView viewPhotoCloseBtn = dialog.findViewById(R.id.viewPhotoCloseBtn);
        MaterialButton unlockPhotoBtn = dialog.findViewById(R.id.unlockPhotoBtn);
        TextView popUpAdditionalText = dialog.findViewById(R.id.popUpAdditionalText);
        TextView orText = dialog.findViewById(R.id.orText);
        TextView requestText = dialog.findViewById(R.id.requestText);




        viewPhotoCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        unlockPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockPhotoBtn.setVisibility(View.GONE);
                orText.setVisibility(View.GONE);
                popUpAdditionalText.setVisibility(View.VISIBLE);
            }
        });

        requestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestDialog(context);

            }
        });


        dialog.show();

    }

    private void requestDialog(Context context) {

        final Dialog dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.request_photo_dialog_box);
        dialog1.setCanceledOnTouchOutside(true);




        dialog1.show();
    }
}
