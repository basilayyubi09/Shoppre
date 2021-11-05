package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Utils.ViewSampleDialog;

import java.io.File;

public class SelfShopperPlaceOrderFargment extends Fragment {

    TextView viewSample;
    ImageView img ;
    int i = 0;
    MaterialCardView layout ;
    MaterialButton proceed;
    Bitmap bitmap;
    LinearLayout clickableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_self_shopper_place_order_fargment, container, false);
        viewSample = view.findViewById(R.id.viewSample);
        img = view.findViewById(R.id.img);
        clickableLayout = view.findViewById(R.id.clickableLayout);
        layout = view.findViewById(R.id.layout);
        proceed = view.findViewById(R.id.proceed);

        viewSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewSampleDialog viewSampleDialog = new ViewSampleDialog();
                viewSampleDialog.showDialog(getActivity());
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0){
                    Toast.makeText(getActivity(), "Please Select an Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), " OK", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FromCard();
            }
        });
        return view;
    }

    public void FromCamera() {

        Log.i("camera", "startCameraActivity()");
        File file = new File(File.pathSeparator);
        Uri outputFileUri = Uri.fromFile(file);



        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 1);

    }

    public void FromCard() {
        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                getActivity().requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
            }
        }

    }
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            img.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            i = 1;
            bitmap = BitmapFactory.decodeFile(picturePath);
            Toast.makeText(getActivity(), picturePath, Toast.LENGTH_SHORT).show();
            img.setImageBitmap(bitmap);

//            if (bitmap != null) {
//                ImageView rotate = (ImageView) findViewById(R.id.rotate);
//
//            }

        } else {

            Log.i("SonaSys", "resultCode: " + resultCode);
            switch (resultCode) {
                case 0:
                    Log.i("SonaSys", "User cancelled");
                    break;
//                case -1:
//                    onPhotoTaken();
//                    break;

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent i = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 2);
                } else {
                    Toast.makeText(getActivity(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
//    protected void onPhotoTaken() {
//        // Log message
//        Log.i("SonaSys", "onPhotoTaken");
//        taken = true;
//        imgCapFlag = true;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        bitmap = BitmapFactory.decodeFile(path, options);
//        imgx.setImageBitmap(bitmap);
//
//
//    }
}