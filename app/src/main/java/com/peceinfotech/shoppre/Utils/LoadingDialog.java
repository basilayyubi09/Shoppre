package com.peceinfotech.shoppre.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peceinfotech.shoppre.R;

import dmax.dialog.SpotsDialog;

public class LoadingDialog {

    static Dialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new Dialog(context);
                    progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    progressDialog.setContentView(R.layout.loading_dialog_plan);
                    progressDialog.setCancelable(false);

            ImageView plane = progressDialog.findViewById(R.id.plane);


        Glide.with(context).load(R.drawable.loading_dialog_gif).into(plane);
        progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();



            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
        }
    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();

    }

}