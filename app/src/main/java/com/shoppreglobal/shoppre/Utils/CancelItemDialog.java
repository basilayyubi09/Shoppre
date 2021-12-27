package com.shoppreglobal.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.shoppreglobal.shoppre.R;

public class CancelItemDialog {

    public void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.return_item_dialog_box);

        MaterialButton addMoreProductBtn, proceedWith1ItemBtn;
        ImageView close;

        addMoreProductBtn = dialog.findViewById(R.id.addMoreProductBtn);
        proceedWith1ItemBtn = dialog.findViewById(R.id.proceedWith1ItemBtn);
        close = dialog.findViewById(R.id.close);



        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        dialog.show();

    }
}
