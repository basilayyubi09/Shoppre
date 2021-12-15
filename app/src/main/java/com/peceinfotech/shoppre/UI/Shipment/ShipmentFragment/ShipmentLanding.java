package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.CancelShipmentModelResponse;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentLandingViewPager;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.ShipmentModelResponse.DownloadInvoiceModelResponse;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentDetails;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.io.File;
import java.net.CookieManager;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;

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
    LinearLayout uploadInvoiceButtonLayout, uploadWireTransferText, downloadInvoiceLayout;
    int id;
    int stateId;
    String stateName;
    List<PackageModel> list;
    SharedPrefManager sharedPrefManager;
    ShipmentDetailsModelResponse modelResponse;
    CardView inReview, verifyPaymentTag, paymentConfirmTag, deliveredTag, dispatchedTagCard;
    NestedScrollView nestedScrollView;
    String url;
    String getUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";


    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    Future<File> downloading;

    boolean isInvoice = true;
    boolean isPayment = true;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_landing, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
            stateId = bundle.getInt("stateId");
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
        downloadInvoiceLayout = view.findViewById(R.id.downloadInvoiceLayout);

        PRDownloader.initialize(getActivity());

        viewPagerAdapter = new ShipmentLandingViewPager(getChildFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", id);
        shipmentDetails.setArguments(bundle1);
        shipmentUpdates.setArguments(bundle1);


        viewPagerAdapter.addFragments(shipmentDetails, "Shipment Details");
        viewPagerAdapter.addFragments(shipmentUpdates, "Shipment Updates");
        viewPager.setAdapter(viewPagerAdapter);

        shipmentTabLayout.setupWithViewPager(viewPager);


        LoadingDialog.showLoadingDialog(getActivity(),"");
        shipmentDetailsApi();

        uploadWireTransferText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWireDialog();
            }
        });


        cancelShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelShipmentDialog();
            }
        });


 /////////Downloading Invoice
        downloadInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                downloadInvoiceApi();
                Dexter.withContext(getContext())
                        .withPermissions(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            downloadFile();
                        }else {
                            Toast.makeText(getActivity(), "Please Allow the Permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }


                }).check();

            }
        });


        return view;
    }



    private void downloadFile() {

        ProgressDialog progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage("Downloading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        PRDownloader.download(getUrl, file.getPath(), URLUtil.guessFileName(getUrl, null, null))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long per = progress.currentBytes*100 / progress.totalBytes;
                        progressDialog.setMessage("Downloading : "+per+" %");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Download Complete", Toast.LENGTH_SHORT).show();

                        File file1 = new File(String.valueOf(file));
                        MimeTypeMap map = MimeTypeMap.getSingleton();
                        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                        String type = map.getMimeTypeFromExtension(ext);

                        if (type == null)
                            type = "*/*";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.fromFile(file);

                        intent.setDataAndType(data, type);

                        startActivity(intent);
                    }

                    @Override
                    public void onError(Error error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Download Failed", Toast.LENGTH_SHORT).show();
                    }


                });

    }

    private void downloadInvoiceApi() {

        Call<DownloadInvoiceModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().downloadInvoice("Bearer "+sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<DownloadInvoiceModelResponse>() {
            @Override
            public void onResponse(Call<DownloadInvoiceModelResponse> call, Response<DownloadInvoiceModelResponse> response) {
                if (response.code()==200){
                    url = response.body().getInvoiceObject();
                }else if (response.code()==401){
                    callRefreshTokenApi();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DownloadInvoiceModelResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showCancelShipmentDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cancel_your_shipment_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView cancelShipmentCross;
        MaterialButton cancelShipmentBtn;

        cancelShipmentCross = dialog.findViewById(R.id.cancelShipmentCross);
        cancelShipmentBtn = dialog.findViewById(R.id.cancelShipmentDialogBtn);

        cancelShipmentCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancelShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showLoadingDialog(getActivity(), "");
                cancelShipmentApi();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void cancelShipmentApi() {

        Call<CancelShipmentModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().cancelShipment("Bearer "+sharedPrefManager.getBearerToken(), id);

        call.enqueue(new Callback<CancelShipmentModelResponse>() {
            @Override
            public void onResponse(Call<CancelShipmentModelResponse> call, Response<CancelShipmentModelResponse> response) {
                if (response.code()==200){
                    OrderActivity.fragmentManager.popBackStack();
                    LoadingDialog.cancelLoading();
                }else if(response.code()==400){
                    Toast.makeText(getActivity(), "You can not cancel shipment after 1 hour from shipment creation", Toast.LENGTH_LONG).show();
                    LoadingDialog.cancelLoading();
                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<CancelShipmentModelResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });

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


/////Conditions for tags and buttons


        for (int i = 0; i < list.size(); i++) {
            if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == false && stateId == 16 || stateId == 100) {
                uploadInvoiceHelpText.setVisibility(View.VISIBLE);
                uploadInvoiceBtn.setVisibility(View.VISIBLE);
                inReviewHelpText.setVisibility(View.GONE);
                inReview.setVisibility(View.GONE);

            } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == true && stateId == 16 || stateId == 17 || stateId == 101) {
                uploadInvoiceHelpText.setVisibility(View.GONE);
                uploadInvoiceButtonLayout.setVisibility(View.GONE);
                inReviewHelpText.setVisibility(View.VISIBLE);
                inReview.setVisibility(View.VISIBLE);

            }
        }
        if (modelResponse.getTotalHours() == 0) {
            cancelShipmentBtn.setVisibility(View.VISIBLE);
        } else if (modelResponse.getTotalHours() > 0) {
            cancelShipmentBtn.setVisibility(View.GONE);
        }

        if (stateId == 18) {
            makePaymentBtn.setVisibility(View.VISIBLE);
            makePaymentHelpText.setVisibility(View.VISIBLE);
        } else if (stateId == 20) {
            uploadWireTransferText.setVisibility(View.VISIBLE);
            verifyPaymentHelpText.setVisibility(View.VISIBLE);
            verifyPaymentTag.setVisibility(View.VISIBLE);
            changePaymentMethodText.setVisibility(View.VISIBLE);
        } else if (stateId == 22) {
            paymentConfirmTag.setVisibility(View.VISIBLE);
            paymentConfirmHelpText.setVisibility(View.VISIBLE);
        } else if (stateId == 24) {
            dispatchedTagCard.setVisibility(View.VISIBLE);
            downloadInvoiceLayout.setVisibility(View.VISIBLE);
        } else if (stateId == 40) {
            deliveredTag.setVisibility(View.VISIBLE);
            downloadInvoiceLayout.setVisibility(View.VISIBLE);
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


    ///https://www.clickdimensions.com/links/TestPDFfile.pdf


}