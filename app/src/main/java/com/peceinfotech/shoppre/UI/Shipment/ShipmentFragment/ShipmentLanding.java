package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentLandingViewPager;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentDetails;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentLanding extends Fragment {

    ViewPager viewPager;
    ShipmentLandingViewPager viewPagerAdapter;
    TabLayout shipmentTabLayout;
    ShipmentDetails shipmentDetails = new ShipmentDetails();
    ShipmentUpdates shipmentUpdates = new ShipmentUpdates();
    MaterialButton makePaymentBtn, uploadInvoiceBtn, trackShipmentBtn, retryPaymentBtn;
    CardView cancelShipmentBtn, downloadInvoiceBtn;
    TextView filePathGlobal;
    String picturePath;

    TextView deliverToName, shipmentId, deliveryAddress, requestDate, contactNumber, packageTotalWeight, dimension, volumetricWeight, finalWeight, totalCost;
    TextView uploadInvoiceHelpText, inReviewHelpText, makePaymentHelpText, paymentFailedTag, retryPaymentHelpText;

    TextView verifyPaymentHelpText, changePaymentMethodText, paymentConfirmHelpText;
    LinearLayout uploadInvoiceButtonLayout, uploadWireTransferText;
    int id;
    String stateName;
    List<PackageModel> list;
    SharedPrefManager sharedPrefManager;
    ShipmentDetailsModelResponse modelResponse;
    CardView inReview, verifyPaymentTag, paymentConfirmTag, deliveredTag, dispatchedTagCard;
    NestedScrollView nestedScrollView;

    boolean isInvoice = true;
    boolean isPayment = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_landing, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            stateName = bundle.getString("stateName");
            list = (List<PackageModel>) bundle.getSerializable("packages");
        }


        viewPager = view.findViewById(R.id.viewPagerShipment);
        shipmentTabLayout = view.findViewById(R.id.shipmentTabLayout);
        makePaymentBtn = view.findViewById(R.id.makePaymentBtn);
        uploadInvoiceBtn = view.findViewById(R.id.uploadInvoiceBtn);
        cancelShipmentBtn = view.findViewById(R.id.cancelShipmentBtn);
        downloadInvoiceBtn = view.findViewById(R.id.downloadInvoiceBtn);
        trackShipmentBtn = view.findViewById(R.id.trackShipmentBtn);


        deliverToName = view.findViewById(R.id.deliverToName);
        shipmentId = view.findViewById(R.id.viewShipmentShipmentId);
        deliveryAddress = view.findViewById(R.id.deliveryAddress);
        requestDate = view.findViewById(R.id.requestDate);
        contactNumber = view.findViewById(R.id.contactNumber);
        packageTotalWeight = view.findViewById(R.id.packageWeight);
        dimension = view.findViewById(R.id.dimension);
        volumetricWeight = view.findViewById(R.id.volumetricWeight);
        finalWeight = view.findViewById(R.id.finalWeight);
        totalCost = view.findViewById(R.id.totalCost);
        uploadInvoiceHelpText = view.findViewById(R.id.uploadInvoiceHelpText);
        uploadInvoiceButtonLayout = view.findViewById(R.id.uploadInvoiceButtonLayout);
        inReview = view.findViewById(R.id.inReviewTag);
        inReviewHelpText = view.findViewById(R.id.inReviewHelpText);
        makePaymentHelpText = view.findViewById(R.id.makePaymentHelpText);
        paymentFailedTag = view.findViewById(R.id.paymentFailedTag);
        retryPaymentBtn = view.findViewById(R.id.retryPaymentBtn);
        retryPaymentHelpText = view.findViewById(R.id.retryPaymentHelpText);
        uploadWireTransferText = view.findViewById(R.id.uploadWireTransferText);
        verifyPaymentTag = view.findViewById(R.id.verifyPaymentTag);
        verifyPaymentHelpText = view.findViewById(R.id.verifyPaymentHelpText);
        changePaymentMethodText = view.findViewById(R.id.changePaymentMethodText);
        paymentConfirmTag = view.findViewById(R.id.paymentConfirmTag);
        paymentConfirmHelpText = view.findViewById(R.id.paymentConfirmHelpText);
        dispatchedTagCard = view.findViewById(R.id.dispatchedTagCard);
        deliveredTag = view.findViewById(R.id.deliveredTagCard);
//        nestedScrollView = view.findViewById(R.id.nestedScrollView);
//
//        nestedScrollView.setFillViewport(true);


        viewPagerAdapter = new ShipmentLandingViewPager(getChildFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", id);
        shipmentDetails.setArguments(bundle1);


        viewPagerAdapter.addFragments(shipmentDetails, "Shipment Details");
        viewPagerAdapter.addFragments(shipmentUpdates, "Shipment Updates");
        viewPager.setAdapter(viewPagerAdapter);

        shipmentTabLayout.setupWithViewPager(viewPager);


        uploadWireTransferText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWireDialog();
            }
        });

//        trackShipmentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CancelShipmentDialog cancelShipmentDialog = new CancelShipmentDialog();
//                cancelShipmentDialog.showCancelShipmentDialog(getContext());
//            }
//        });
//
//
//        downloadInvoiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UploadInvoiceDialog uploadInvoiceDialog = new UploadInvoiceDialog();
//                uploadInvoiceDialog.uploadInvoiceShowDialog(getContext());
//            }
//        });
//
//        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showWireDialog();
//            }
//        });
//
//
//
//        uploadInvoiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
////                Toast ToastMessage = Toast.makeText(getContext(),"We’re reviewing your Return Request",Toast.LENGTH_SHORT);
////                ToastMessage.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
////                View toastView = ToastMessage.getView(); toastView.setBackground(getResources().getDrawable(R.drawable.toast_background));
////                ToastMessage.show();
//            }
//        });
//
//        cancelShipmentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new PaymentSummary(), null)
//                        .addToBackStack(null).commit();
//            }
//        });
        LoadingDialog.showLoadingDialog(getActivity(), "");
        shipmentDetailsApi();


        return view;
    }

    private void shipmentDetailsApi() {
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer " + sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    modelResponse = response.body();
                    setShipmentDetailsValue();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                }
            }

            @Override
            public void onFailure(Call<ShipmentDetailsModelResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setShipmentDetailsValue() {

        String s = modelResponse.getShipment().getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        deliverToName.setText(modelResponse.getShipment().getCustomerName());
        shipmentId.setText(String.valueOf(modelResponse.getShipment().getId()));
        deliveryAddress.setText(modelResponse.getShipment().getAddress());
        requestDate.setText(month_name);
        contactNumber.setText(modelResponse.getShipment().getPhone());
        packageTotalWeight.setText(String.valueOf(modelResponse.getShipment().getWeight()));

        if (modelResponse.getShipment().getBoxLength() == 0 && modelResponse.getShipment().getBoxHeight() == 0 && modelResponse.getShipment().getBoxWidth() == 0) {
            dimension.setText("To be Calculated");
        } else {
            dimension.setText(String.valueOf(modelResponse.getShipment().getBoxLength()) + "cm" + " x " + String.valueOf(modelResponse.getShipment().getBoxHeight()) + "cm" + " x " + String.valueOf(modelResponse.getShipment().getBoxWidth()));
        }

        if (modelResponse.getShipment().getVolumetricWeight() == 0) {
            volumetricWeight.setText("To be Calculated");
        } else {
            volumetricWeight.setText(String.valueOf(modelResponse.getShipment().getBoxWidth()));
        }

        if (modelResponse.getShipment().getFinalWeight() == 0) {
            finalWeight.setText("To be Calculated");
        } else {
            finalWeight.setText(String.valueOf(modelResponse.getShipment().getFinalWeight()));
        }


        if (modelResponse.getShipment().getSubTotalAmount() == 0) {
            totalCost.setText("To be Calculated");
        } else {
            totalCost.setText(String.valueOf(modelResponse.getShipment().getSubTotalAmount()));
        }


//        if (modelResponse.getPayment().getPaymentGatewayId()==null){
//            makePaymentBtn.setVisibility(View.VISIBLE);
//            makePaymentHelpText.setVisibility(View.VISIBLE);
//        }else {
//            makePaymentHelpText.setVisibility(View.GONE);
//            makePaymentBtn.setVisibility(View.GONE);
//        }


        if (stateName.equals("Awaiting Payment")) {
            verifyPaymentTag.setVisibility(View.VISIBLE);
            verifyPaymentHelpText.setVisibility(View.VISIBLE);
            uploadWireTransferText.setVisibility(View.VISIBLE);
            changePaymentMethodText.setVisibility(View.VISIBLE);
        } else if (stateName.equals("In Review")) {
            inReview.setVisibility(View.VISIBLE);
            inReviewHelpText.setVisibility(View.VISIBLE);
        } else if (stateName.equals("Pending Invoice Upload")) {
            uploadInvoiceHelpText.setVisibility(View.VISIBLE);
            uploadInvoiceButtonLayout.setVisibility(View.VISIBLE);
        } else if (stateName.equals("Payment Failed")) {
            paymentFailedTag.setVisibility(View.VISIBLE);
            retryPaymentBtn.setVisibility(View.VISIBLE);
            retryPaymentHelpText.setVisibility(View.VISIBLE);
        } else if (stateName.equals("Payment Confirmed")) {
            paymentConfirmTag.setVisibility(View.VISIBLE);
            paymentConfirmHelpText.setVisibility(View.VISIBLE);
        } else if (stateName.equals("Dispatched")) {
            dispatchedTagCard.setVisibility(View.VISIBLE);
            uploadInvoiceButtonLayout.setVisibility(View.VISIBLE);
        } else if (stateName.equals("Delivered")) {
            deliveredTag.setVisibility(View.VISIBLE);
            uploadInvoiceButtonLayout.setVisibility(View.VISIBLE);
        }


    }


    private void showWireDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(com.peceinfotech.shoppre.R.layout.wire_transfer_details_dialog);

        MaterialButton browsBtn;
        MaterialButton submitBtn;
        TextView filePathTextView;
        ImageView cross;

        browsBtn = dialog.findViewById(R.id.browsBtn);
        submitBtn = dialog.findViewById(R.id.SubmitBtn);
        filePathGlobal = filePathTextView = dialog.findViewById(R.id.filePathTextView);
        cross = dialog.findViewById(R.id.cross);

        filePathTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browsBtn.performClick();
            }
        });


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        browsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });


        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex); //path of image...
                cursor.close();

                filePathGlobal.setText(picturePath);
            }
        }
    }

}