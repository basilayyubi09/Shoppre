package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.peceinfotech.shoppre.R;

public class PlaneLoading {

//    static Dialog dialog;
//    public void showLoadingDialog(Context context, String message){
//
//        dialog = new Dialog(context);
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.loading_dialog_plan);
//
//
//        ImageView plane = dialog.findViewById(R.id.plane);
//
//        Glide.with(context).load(R.drawable.loading_dialog_gif).into(plane);
//        dialog.setCanceledOnTouchOutside(false);
//
//
//        dialog.show();
//
//    }
//
//    public static void cancelLoading(){
//        if (dialog != null && dialog.isShowing())
//            dialog.cancel();
//    }
}
