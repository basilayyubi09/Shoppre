package com.peceinfotech.shoppre.Utils;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.peceinfotech.shoppre.R;


public class AlretDialogBox {

    public void showAlertDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(com.peceinfotech.shoppre.R.layout.alert_dialog_box);

        ImageView alert_close_btn;

        alert_close_btn = dialog.findViewById(R.id.alert_close_btn);

        alert_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
