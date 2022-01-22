package com.shoppreglobal.shoppre.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageItem;
import com.shoppreglobal.shoppre.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ViewPhotoDialog {

    Click click;
    int size;

    public ViewPhotoDialog(int size, Click click) {
        this.click = click;
        this.size = size;
    }

    public void showDialog(Context context, PackageItem packageDetailsResponse) {
        final Dialog dialog = new Dialog(context);
        Click click = this.click;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.view_photo_dialog);


        ImageView viewPhotoCloseBtn = dialog.findViewById(R.id.viewPhotoCloseBtn);
        ImageView close = dialog.findViewById(R.id.close);
        MaterialButton unlockPhotoBtn = dialog.findViewById(R.id.unlockPhotoBtn);
        MaterialButton addMoreProductBtn = dialog.findViewById(R.id.addMoreProductBtn);
        MaterialButton proceedWith1ItemBtn = dialog.findViewById(R.id.proceedWith1ItemBtn);
        TextView popUpAdditionalText = dialog.findViewById(R.id.popUpAdditionalText);
        TextView orText = dialog.findViewById(R.id.orText);
        TextView requestText = dialog.findViewById(R.id.requestText);
        ImageView mainPhoto, secondPhoto, thirdPhoto;
        mainPhoto = dialog.findViewById(R.id.mainPhoto);
        secondPhoto = dialog.findViewById(R.id.secondPhoto);
        thirdPhoto = dialog.findViewById(R.id.thirdPhoto);
        LinearLayout layout = dialog.findViewById(R.id.main);
        LinearLayout layout1 = dialog.findViewById(R.id.second);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog1) {
                click.Dismiss(dialog);
            }
        });

        if (size>1){
            addMoreProductBtn.setVisibility(View.VISIBLE);
        }
        else {
            addMoreProductBtn.setVisibility(View.GONE);
        }
        if (!packageDetailsResponse.getObject().isEmpty()) {
            Glide.with(context)
                    .load(packageDetailsResponse.getObject())
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(22)))
                    .into(mainPhoto);

//            Glide.with(context)
//                    .load(packageDetailsResponse.getObject())
//                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(22)))
//                    .into(secondPhoto);
        }


        if (packageDetailsResponse.getPhotoRequests().isEmpty()) {
            secondPhoto.setVisibility(View.GONE);
            thirdPhoto.setVisibility(View.GONE);
        } else {
            if (packageDetailsResponse.getPhotoRequests().size() > 1) {
                unlockPhotoBtn.setVisibility(View.GONE);
                orText.setVisibility(View.GONE);
                requestText.setVisibility(View.GONE);
                popUpAdditionalText.setVisibility(View.GONE);
                secondPhoto.setVisibility(View.VISIBLE);
                thirdPhoto.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(packageDetailsResponse.getObject())
                        .into(mainPhoto);
                Glide.with(context)
                        .load(packageDetailsResponse.getObject())
                        .into(secondPhoto);
                if (packageDetailsResponse.getObjectAdvanced()!=null) {
                    thirdPhoto.setVisibility(View.VISIBLE);
                    secondPhoto.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(packageDetailsResponse.getObjectAdvanced())
                            .into(thirdPhoto);
                    Glide.with(context)
                            .load(packageDetailsResponse.getObject())
                            .into(secondPhoto);
                }

            } else if (packageDetailsResponse.getPhotoRequests().size() < 2) {
                if (packageDetailsResponse.getPhotoRequests().get(0).getType().equals("1")) {
                    unlockPhotoBtn.setVisibility(View.GONE);
                    orText.setVisibility(View.GONE);
                    popUpAdditionalText.setVisibility(View.VISIBLE);

                    Glide.with(context)
                            .load(packageDetailsResponse.getObject())
                            .into(mainPhoto);
                    secondPhoto.setVisibility(View.GONE);
                    thirdPhoto.setVisibility(View.GONE);
                }

                else if (packageDetailsResponse.getPhotoRequests().get(0).getType().equals("2")) {

                    if (packageDetailsResponse.getPhotoRequests().get(0).getStatus().equals("2")) {
                        thirdPhoto.setVisibility(View.GONE);
                        secondPhoto.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(packageDetailsResponse.getObjectAdvanced())
                                .into(thirdPhoto);
                    } else {
                        thirdPhoto.setVisibility(View.GONE);
                        secondPhoto.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(packageDetailsResponse.getObject())
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(22)))
                                .into(mainPhoto);
                    }


                    orText.setVisibility(View.GONE);
                    requestText.setVisibility(View.GONE);
                    secondPhoto.setVisibility(View.GONE);
                    thirdPhoto.setVisibility(View.GONE);
                    popUpAdditionalText.setVisibility(View.GONE);
                }
            }



        }


        secondPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(packageDetailsResponse.getObject())
                        .into(mainPhoto);

            }
        });

        thirdPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context)
                        .load(packageDetailsResponse.getObjectAdvanced())
                        .into(mainPhoto);
            }
        });


        viewPhotoCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.Dismiss(dialog);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        unlockPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.fiveRupeesClick(unlockPhotoBtn
                        , orText,
                        mainPhoto, secondPhoto,
                        thirdPhoto, popUpAdditionalText);
            }
        });

        requestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                layout.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
            }
        });

        proceedWith1ItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                dialog.dismiss();
                click.Dismiss(dialog);
                click.multi(packageDetailsResponse.getId());
            }
        });

        addMoreProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click.Dismiss(dialog);
                click.proceed(packageDetailsResponse.getId());
            }
        });


        dialog.show();

    }


    public interface Click {
        void fiveRupeesClick(MaterialButton unlockPhotoBtn
                , TextView orText, ImageView mainPhoto
                , ImageView secondPhoto, ImageView thirdPhoto
                , TextView popUpAdditionalText);

        void proceed(Integer id);


        void multi(Integer id);

        void Dismiss(Dialog dialog);



    }
}
