package com.peceinfotech.shoppre.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.peceinfotech.shoppre.R;

public class LoadingDialog {

//    static ProgressDialog progressDialog;


    static AlertDialog progressDialog;


    public static void showLoadingDialog(Context context, String message) {

        if (!(progressDialog != null && progressDialog.isShowing())) {
            progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);

            progressDialog.setMessage(message);


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

