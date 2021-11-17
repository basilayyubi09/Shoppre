package com.peceinfotech.shoppre.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class LoadingDialog {

//    static ProgressDialog progressDialog;


    static AlertDialog progressDialog;

    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {

            progressDialog = new SpotsDialog.Builder()
                    .setContext(context)
                    .setCancelable(false)
                    .setMessage(message)
                    .build();
            progressDialog.show();

//            progressDialog.setMessage(message);
//
//
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//
//            progressDialog.show();
        }
    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();

    }
}

