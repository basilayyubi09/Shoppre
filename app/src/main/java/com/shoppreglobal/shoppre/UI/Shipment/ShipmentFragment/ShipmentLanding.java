package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.BoxAdapter;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.CancelShipmentModelResponse;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.ShipmentLandingViewPager;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.UploadInvoiceAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.DownloadInvoiceModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.MinioUploadModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentBox;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentDetails;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    List<ShipmentBox> boxList;
    BoxAdapter boxAdapter;
    RecyclerView boxRecycle;
    LinearLayoutManager linearLayoutManager;
    TextView deliverToName, shipmentId, deliveryAddress, requestDate, contactNumber, packageTotalWeight, dimension, volumetricWeight, finalWeight, totalCost;
    TextView uploadInvoiceHelpText, inReviewHelpText, makePaymentHelpText, paymentFailedTag, retryPaymentHelpText;
    int size;
    TextView verifyPaymentHelpText, changePaymentMethodText, paymentConfirmHelpText;
    LinearLayout uploadInvoiceButtonLayout, uploadWireTransferText, downloadInvoiceLayout;
    int id;
    int stateId;
    String stateName;
    List<PackageModel> list;
    SharedPrefManager sharedPrefManager;
    ShipmentDetailsModelResponse modelResponse;
    CardView inReview, verifyPaymentTag, paymentConfirmTag, deliveredTag, dispatchedTagCard;
    LinearLayout firstLayout, secondLayout;
    TextView totalWeight, totalCharge;
    NestedScrollView nestedScrollView;
    Bitmap bitmap;
    Uri selectedImage, selectedInvoice;
    String url;
    String encodedFile;
    String encodedString;
    String getUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
    String responseObject;
    String splitUrl;
    String responseUrl;
    String file;
    byte[] byteArray;
    LinearLayout uploadInvoiceArrowHome;
    TextView invoiceUploadedHome;
    int strResponse;

    UploadInvoiceAdapter uploadInvoiceAdapter;
    List<PackageModel> list2;

    Intent myFileIntent;
    String strFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_landing, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
            size = bundle.getInt("size");
            stateId = bundle.getInt("stateId");
            stateName = bundle.getString("stateName");
            list = (List<PackageModel>) bundle.getSerializable("packages");
        }

        boxList = new ArrayList<>();
        list2 = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewPager = view.findViewById(R.id.viewPagerShipment);
        totalWeight = view.findViewById(R.id.totalWeight);
        totalCharge = view.findViewById(R.id.totalCharge);
        boxRecycle = view.findViewById(R.id.boxRecycle);
        firstLayout = view.findViewById(R.id.firstLayout);
        secondLayout = view.findViewById(R.id.secondLayout);
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
        LoadingDialog.showLoadingDialog(getActivity(), "");
        shipmentDetailsApi();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", id);
        shipmentDetails.setArguments(bundle1);
        shipmentUpdates.setArguments(bundle1);


        viewPagerAdapter.addFragments(shipmentDetails, "Shipment Details" + " (" + String.valueOf(size) + ")");
        viewPagerAdapter.addFragments(shipmentUpdates, "Shipment Updates");
        viewPager.setAdapter(viewPagerAdapter);

        shipmentTabLayout.setupWithViewPager(viewPager);


        LoadingDialog.showLoadingDialog(getActivity(), "");
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
                downloadInvoiceApi();
            }
        });

        uploadInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadInvoiceDialog();
//                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                myFileIntent.setType("*/*");
//                startActivityForResult(myFileIntent, 10);
            }
        });

        makePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("shipmentId", id);
                bundle2.putInt("size", size);
                PaymentSummary paymentSummary = new PaymentSummary();
                paymentSummary.setArguments(bundle2);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, paymentSummary, null)
                        .addToBackStack(null).commit();
            }
        });


        trackShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!modelResponse.getShipment().getTracking_url().equals("")) {

                    Bundle bundle = new Bundle();
                    bundle.putString("url", modelResponse.getShipment().getTracking_url());
                    WebViewFragment cash = new WebViewFragment();
                    cash.setArguments(bundle);
                    if (savedInstanceState != null) return;
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                            .addToBackStack(null).commit();
                }
            }
        });

        return view;
    }

    private void uploadInvoiceDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.upload_invoice_dialog_box);

        RecyclerView uploadInvoiceRecycler;


        uploadInvoiceRecycler = dialog.findViewById(R.id.uploadInvoiceRecycler);


        uploadInvoicePackagesApi(uploadInvoiceRecycler, dialog);


    }

    private void uploadInvoicePackagesApi(RecyclerView uploadInvoiceRecycler, Dialog dialog) {

        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer " + sharedPrefManager.getBearerToken(), id);

        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code() == 200) {
                    list2 = response.body().getPackages();

                    uploadInvoiceAdapter = new UploadInvoiceAdapter(list2, getActivity(), dialog, new UploadInvoiceAdapter.CallbackInterface() {
                        @Override
                        public void onSelection(LinearLayout uploadInvoiceArrow, TextView invoiceUploaded) {
                            Intent intent = new Intent();
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            // Set your required file type
                            intent.setType("*/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "DEMO"), 1001);

                            uploadInvoiceArrowHome = uploadInvoiceArrow;
                            invoiceUploadedHome = invoiceUploaded;

                        }

                    });

                    uploadInvoiceRecycler.setAdapter(uploadInvoiceAdapter);
                    LoadingDialog.cancelLoading();

                    dialog.show();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<ShipmentDetailsModelResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });

    }


    private void downloadFile() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Downloading....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        PRDownloader.download(url, file.getPath(), URLUtil.guessFileName(url, null, null))
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
                        long per = progress.currentBytes * 100 / progress.totalBytes;
                        progressDialog.setMessage("Downloading : " + per + " %");
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
                        Uri data = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", file);

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
                .getAppApi().downloadInvoice("Bearer " + sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<DownloadInvoiceModelResponse>() {
            @Override
            public void onResponse(Call<DownloadInvoiceModelResponse> call, Response<DownloadInvoiceModelResponse> response) {
                if (response.code() == 200) {
                    DownloadInvoiceModelResponse downloadInvoiceModelResponse = response.body();
                    url = downloadInvoiceModelResponse.getInvoiceObject();

                    Dexter.withContext(getContext())
                            .withPermissions(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                downloadFile();
                            } else {
                                Toast.makeText(getActivity(), "Please Allow the Permission", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        }


                    }).check();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
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
                .getAppApi().cancelShipment("Bearer " + sharedPrefManager.getBearerToken(), id);

        call.enqueue(new Callback<CancelShipmentModelResponse>() {
            @Override
            public void onResponse(Call<CancelShipmentModelResponse> call, Response<CancelShipmentModelResponse> response) {
                if (response.code() == 200) {
                    OrderActivity.fragmentManager.popBackStack();
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 400) {
                    Toast.makeText(getActivity(), "You can not cancel shipment after 1 hour from shipment creation", Toast.LENGTH_LONG).show();
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
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

//                    if (modelResponse.getShipment().getShipmentBoxes().isEmpty()) {
//                        firstLayout.setVisibility(View.VISIBLE);
//                        secondLayout.setVisibility(View.GONE);
//
//                        setShipmentDetailsValue();
//
//                    } else {
//                        firstLayout.setVisibility(View.GONE);
//                        secondLayout.setVisibility(View.VISIBLE);
//
//                        setBoxShipmentDetailsValue();
//
//
//                    }

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<ShipmentDetailsModelResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setBoxShipmentDetailsValue() {
//
//        String s = modelResponse.getShipment().getCreatedAt();
//        String[] split = s.split("T");
//        String date1 = split[0];
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
//        LocalDate ld = LocalDate.parse(date1, dtf);
//        String month_name = dtf2.format(ld);
//
//        deliverToName.setText(modelResponse.getShipment().getCustomerName());
//        shipmentId.setText(String.valueOf("#" + modelResponse.getShipment().getId()));
//        deliveryAddress.setText(modelResponse.getShipment().getAddress());
//        requestDate.setText(month_name);
//        contactNumber.setText(modelResponse.getShipment().getPhone());
//        packageTotalWeight.setText(String.valueOf(modelResponse.getShipment().getWeight()) + " KG");
//
//        if (modelResponse.getShipment().getShipmentBoxes().size() > 0) {
//            totalWeight.setText(String.valueOf(modelResponse.getShipment().getFinalWeight()));
//            totalCharge.setText("₹ " + String.valueOf(modelResponse.getShipment().getSubTotalAmount()));
//            boxAdapter = new BoxAdapter(getActivity(), modelResponse.getShipment().getShipmentBoxes());
//            boxRecycle.setLayoutManager(linearLayoutManager);
//            boxRecycle.setAdapter(boxAdapter);
//        }
//
//
//        if (modelResponse.getShipment().getSubTotalAmount() == 0) {
//            totalCost.setText("To be Calculated");
//        } else {
//            totalCost.setText(String.valueOf(modelResponse.getShipment().getSubTotalAmount()));
//        }


/////Conditions for tags and buttons

//        if (modelResponse.getTotalHours() == 0) {
//            cancelShipmentBtn.setVisibility(View.VISIBLE);
//        } else if (modelResponse.getTotalHours() > 0) {
//            cancelShipmentBtn.setVisibility(View.GONE);
//        }
//
//
//
//        for (int i = 0; i < list.size(); i++) {
//            if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() != null) {
//
//                if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == false) {
//
//                    if (stateId == 16 || stateId == 100) {
//                        uploadInvoiceHelpText.setVisibility(View.VISIBLE);
//                        uploadInvoiceBtn.setVisibility(View.VISIBLE);
//                        inReviewHelpText.setVisibility(View.GONE);
//                        inReview.setVisibility(View.GONE);
//                    }
//
//
//                } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == true) {
//
//
//                    if (stateId == 16 || stateId == 17 || stateId == 101) {
//                        uploadInvoiceHelpText.setVisibility(View.GONE);
//                        uploadInvoiceButtonLayout.setVisibility(View.GONE);
//                        inReviewHelpText.setVisibility(View.VISIBLE);
//                        inReview.setVisibility(View.VISIBLE);
//                    }
//                }
//
//
//            } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == null) {
//
//                if (stateId == 16 || stateId == 100) {
//                    uploadInvoiceHelpText.setVisibility(View.VISIBLE);
//                    uploadInvoiceButtonLayout.setVisibility(View.VISIBLE);
//                    inReviewHelpText.setVisibility(View.GONE);
//                    inReview.setVisibility(View.GONE);
//                }
//
//            }
//            break;
//
//        }
//
//
//
//
//
//        if (stateId == 18) {
//            makePaymentBtn.setVisibility(View.VISIBLE);
//            makePaymentHelpText.setVisibility(View.VISIBLE);
//        } else if (stateId == 20) {
//            uploadWireTransferText.setVisibility(View.VISIBLE);
//            verifyPaymentHelpText.setVisibility(View.VISIBLE);
//            verifyPaymentTag.setVisibility(View.VISIBLE);
//            changePaymentMethodText.setVisibility(View.VISIBLE);
//        } else if (stateId == 22) {
//            paymentConfirmTag.setVisibility(View.VISIBLE);
//            paymentConfirmHelpText.setVisibility(View.VISIBLE);
//        } else if (stateId == 24) {
//            dispatchedTagCard.setVisibility(View.VISIBLE);
//            downloadInvoiceLayout.setVisibility(View.VISIBLE);
//        } else if (stateId == 40) {
//            deliveredTag.setVisibility(View.VISIBLE);
//            downloadInvoiceLayout.setVisibility(View.VISIBLE);
//        } else if (stateId == 21) {
//            paymentFailedTag.setVisibility(View.VISIBLE);
//            retryPaymentBtn.setVisibility(View.VISIBLE);
//            retryPaymentHelpText.setVisibility(View.VISIBLE);
//        }
//
//    }

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
        shipmentId.setText(String.valueOf("#" + modelResponse.getShipment().getId()));
        deliveryAddress.setText(modelResponse.getShipment().getAddress());
        requestDate.setText(month_name);
        contactNumber.setText(modelResponse.getShipment().getPhone());
        packageTotalWeight.setText(String.valueOf(modelResponse.getShipment().getWeight()) + " KG");

        if (modelResponse.getShipment().getShipmentBoxes().size() > 0) {
            if (modelResponse.getShipment().getBoxLength()!=null && modelResponse.getShipment().getBoxHeight()!=null && modelResponse.getShipment().getBoxWidth()!=null){
                if (modelResponse.getShipment().getBoxLength() == 0 && modelResponse.getShipment().getBoxHeight() == 0 && modelResponse.getShipment().getBoxWidth() == 0) {
                    dimension.setText("To be Calculated");
                } else {
                    dimension.setText(String.valueOf(modelResponse.getShipment().getBoxLength()) + "cm" + " x " + String.valueOf(modelResponse.getShipment().getBoxHeight()) + "cm" + " x " + String.valueOf(modelResponse.getShipment().getBoxWidth()));
                }
            }else {
                dimension.setText("To be Calculated");

            }

            if (modelResponse.getShipment().getVolumetricWeight()!=null && modelResponse.getShipment().getFinalWeight()!=null && modelResponse.getShipment().getFinalWeight()!=null){
                if (modelResponse.getShipment().getVolumetricWeight() == 0) {
                    volumetricWeight.setText("To be Calculated");
                } else {
                    volumetricWeight.setText(String.valueOf(modelResponse.getShipment().getBoxWidth()) + " KG");
                }

                if (modelResponse.getShipment().getFinalWeight() == 0) {
                    finalWeight.setText("To be Calculated");
                } else {
                    finalWeight.setText(String.valueOf(modelResponse.getShipment().getFinalWeight()) + " KG");
                }
            }else {
                volumetricWeight.setText("To be Calculated");
                finalWeight.setText("To be Calculated");
            }

        }


        if (modelResponse.getShipment().getSubTotalAmount() == 0) {
            totalCost.setText("To be Calculated");
        } else {
            totalCost.setText(String.valueOf(modelResponse.getShipment().getSubTotalAmount()));
        }

        if (modelResponse.getTotalHours() == 0) {
            cancelShipmentBtn.setVisibility(View.VISIBLE);
        } else if (modelResponse.getTotalHours() > 0) {
            cancelShipmentBtn.setVisibility(View.GONE);
        }



        for (int i = 0; i < list.size(); i++) {
            if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() != null) {

                if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == false) {

                    if (stateId == 16 || stateId == 100) {
                        uploadInvoiceHelpText.setVisibility(View.VISIBLE);
                        uploadInvoiceBtn.setVisibility(View.VISIBLE);
                        inReviewHelpText.setVisibility(View.GONE);
                        inReview.setVisibility(View.GONE);
                    }


                } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == true) {


                    if (stateId == 16 || stateId == 17 || stateId == 101) {
                        uploadInvoiceHelpText.setVisibility(View.GONE);
                        uploadInvoiceButtonLayout.setVisibility(View.GONE);
                        inReviewHelpText.setVisibility(View.VISIBLE);
                        inReview.setVisibility(View.VISIBLE);
                    }
                }


            } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == null) {

                if (stateId == 16 || stateId == 100) {
                    uploadInvoiceHelpText.setVisibility(View.VISIBLE);
                    uploadInvoiceButtonLayout.setVisibility(View.VISIBLE);
                    inReviewHelpText.setVisibility(View.GONE);
                    inReview.setVisibility(View.GONE);
                }

            }
            break;

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
        } else if (stateId == 21) {
            paymentFailedTag.setVisibility(View.VISIBLE);
            retryPaymentBtn.setVisibility(View.VISIBLE);
            retryPaymentHelpText.setVisibility(View.VISIBLE);
        }



/////Conditions for tags and buttons


//        for (int i = 0; i < list.size(); i++) {
//            if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == false && stateId == 16 || stateId == 100) {
//                uploadInvoiceHelpText.setVisibility(View.VISIBLE);
//                uploadInvoiceBtn.setVisibility(View.VISIBLE);
//                inReviewHelpText.setVisibility(View.GONE);
//                inReview.setVisibility(View.GONE);
//
//            } else if (modelResponse.getPackages().get(i).getIsFullInvoiceReceived() == true && stateId == 16 || stateId == 17 || stateId == 101) {
//                uploadInvoiceHelpText.setVisibility(View.GONE);
//                uploadInvoiceButtonLayout.setVisibility(View.GONE);
//                inReviewHelpText.setVisibility(View.VISIBLE);
//                inReview.setVisibility(View.VISIBLE);
//
//            }
//        }
//        if (modelResponse.getTotalHours() == 0) {
//            cancelShipmentBtn.setVisibility(View.VISIBLE);
//        } else if (modelResponse.getTotalHours() > 0) {
//            cancelShipmentBtn.setVisibility(View.GONE);
//        }
//
//        if (stateId == 18) {
//            makePaymentBtn.setVisibility(View.VISIBLE);
//            makePaymentHelpText.setVisibility(View.VISIBLE);
//        } else if (stateId == 20) {
//            uploadWireTransferText.setVisibility(View.VISIBLE);
//            verifyPaymentHelpText.setVisibility(View.VISIBLE);
//            verifyPaymentTag.setVisibility(View.VISIBLE);
//            changePaymentMethodText.setVisibility(View.VISIBLE);
//        } else if (stateId == 22) {
//            paymentConfirmTag.setVisibility(View.VISIBLE);
//            paymentConfirmHelpText.setVisibility(View.VISIBLE);
//        } else if (stateId == 24) {
//            dispatchedTagCard.setVisibility(View.VISIBLE);
//            downloadInvoiceLayout.setVisibility(View.VISIBLE);
//        } else if (stateId == 40) {
//            deliveredTag.setVisibility(View.VISIBLE);
//            downloadInvoiceLayout.setVisibility(View.VISIBLE);
//        } else if (stateId == 21) {
//            paymentFailedTag.setVisibility(View.VISIBLE);
//            retryPaymentBtn.setVisibility(View.VISIBLE);
//            retryPaymentHelpText.setVisibility(View.VISIBLE);
//        }
    }


    private void showWireDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(com.shoppreglobal.shoppre.R.layout.wire_transfer_details_dialog);

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
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });


        dialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex); //path of image...
                cursor.close();

                filePathGlobal.setText(picturePath);
            }
        } else if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {

                Uri selectedInvoice = data.getData();
                String selectedFilePath = data.getData().getPath();
                String string = selectedFilePath;
                String[] parts = string.split("/");
                file = parts[parts.length - 1];
                Log.d("Path", file);

                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedInvoice);
                    byteArray = new byte[inputStream.available()];
                    inputStream.read(byteArray);
                    encodedFile = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.d("Encoded String", encodedFile);

                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callMinioUploadApi(byteArray);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void callMinioUploadApi(byte[] encodedFile) {

        Call<MinioUploadModelResponse> call = RetrofitClient3.getInstance3().getAppApi().minioUpload("Bearer " + sharedPrefManager.getBearerToken(),
                file);
        call.enqueue(new Callback<MinioUploadModelResponse>() {
            @Override
            public void onResponse(Call<MinioUploadModelResponse> call, Response<MinioUploadModelResponse> response) {
                if (response.code() == 200) {
                    responseObject = response.body().getObject();

                    responseUrl = response.body().getUrl();
                    String[] parts = responseUrl.split("/");
                    splitUrl = parts[parts.length - 1];

//                    Toast.makeText(getActivity(), splitUrl, Toast.LENGTH_SHORT).show();

                    Log.d("splitUrl", splitUrl);
                    LoadingDialog.cancelLoading();

//                    callMinioUpload2Api(responseUrl , encodedFile);
                    MinioUploading minioUploading = new MinioUploading();
                    minioUploading.execute();


                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<MinioUploadModelResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });
    }

//    private void callMinioUpload2Api(String responseUrl, byte[] encodedFile) {
//
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), encodedFile);
//
//
//        Call<Integer> call = DynamicRetrofitClient.getDynamicInstance()
//                .getAppApi().minioUpload2(splitUrl, requestBody);
//        call.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if (response.code() == 200) {
//                    Log.d("Sucsessssss", String.valueOf(response.code()));
//                    LoadingDialog.cancelLoading();
//                } else if (response.code() == 401) {
//                    callRefreshTokenApi();
//                    LoadingDialog.cancelLoading();
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    public class MinioUploading extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String[] params) {


            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/octet");
            RequestBody body = RequestBody.create(mediaType, byteArray);
            Request request = new Request.Builder()
                    .url(responseUrl)
                    .method("PUT", body)
                    .addHeader("Content-Type", "application/octet-stream")
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
                Log.d("Codeeeeeeeeeeeeeee", String.valueOf(response.code()));
                strResponse = response.code();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
            if (strResponse==200){
                Toast.makeText(getActivity(), "Invoice upload successfully", Toast.LENGTH_SHORT).show();
                uploadInvoiceArrowHome.setVisibility(View.GONE);
                invoiceUploadedHome.setVisibility(View.VISIBLE);
            }else {
                Toast.makeText(getActivity(), "Invoice upload Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
