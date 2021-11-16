package com.peceinfotech.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageDetailsResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class CancelItemDialog {

    public void showDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.return_item_dialog_box);

        MaterialButton addMoreProductBtn, proceedWith1ItemBtn;

        addMoreProductBtn = dialog.findViewById(R.id.addMoreProductBtn);
        proceedWith1ItemBtn = dialog.findViewById(R.id.proceedWith1ItemBtn);

        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        dialog.show();

    }
}
