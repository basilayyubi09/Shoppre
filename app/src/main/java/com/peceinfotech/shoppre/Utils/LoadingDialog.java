package com.peceinfotech.shoppre.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.peceinfotech.shoppre.R;

public class LoadingDialog {

    static ProgressDialog progressDialog;


    public static void showLoadingDialog(Context context, String message) {

        try {
            if (!(progressDialog != null && progressDialog.isShowing())) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage(message);
//                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                progressDialog.show();
            }
        } catch (Exception e) {
            Log.d("", "cancelLoading: ");
        }

    }

    public static void cancelLoading() {
        try {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.cancel();
        } catch (Exception e) {
            Log.d("", "cancelLoading: ");
        }


    }
}

