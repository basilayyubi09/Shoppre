package com.shoppreglobal.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shoppreglobal.shoppre.R;

public class HowItWorksDialog {
    static Dialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.loading_how_it_works);
            progressDialog.setCancelable(false);

            ImageView plane = progressDialog.findViewById(R.id.plane);
            ImageView viewPhotoCloseBtn = progressDialog.findViewById(R.id.viewPhotoCloseBtn);

            if (message.equals("ps")){
            Glide.with(context).load(R.mipmap.shoppre_personal).into(plane);
            }
            else {
                            Glide.with(context).load(R.mipmap.how_shoppre_works).into(plane);
            }

            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();



            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
            viewPhotoCloseBtn.bringToFront();
            viewPhotoCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.dismiss();
                }
            });
        }

    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();

    }
}
