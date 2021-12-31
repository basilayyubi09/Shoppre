package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.viewpager.widget.ViewPager;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.OrderAdapter.ViewOrderViewPagerAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ViewSelfShopperResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderDetails;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderUpdates;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewOrderPersonalShop extends Fragment {


    SharedPrefManager sharedPrefManager;
    TabLayout viewOrderTabLayout;
    ViewPager viewOrderViewPager;
    String orderCode;
    CircleImageView singleOrderImage;
    TextView websiteName, status, orderNumberText, date, tvDownloadInvoice;
    ViewOrderViewPagerAdapter viewPagerAdapter;
    Bundle bundle;
    String id, dateString, type, imageUrl;
    String url;
    OrderDetails orderDetails;
    OrderUpdates orderUpdates;
    Integer shopperId;
    String testUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order_personal_shop, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");


        viewOrderViewPager = view.findViewById(R.id.viewOrderViewPager);
        viewOrderTabLayout = view.findViewById(R.id.viewOrderTabLayout);
        websiteName = view.findViewById(R.id.websiteName);
        status = view.findViewById(R.id.status);
        orderNumberText = view.findViewById(R.id.orderNumberText);
        date = view.findViewById(R.id.date);
        tvDownloadInvoice = view.findViewById(R.id.tvDownloadInvoice);
        singleOrderImage = view.findViewById(R.id.singleOrderImage);

        bundle = this.getArguments();
        orderDetails = new OrderDetails();
        orderUpdates = new OrderUpdates();
        if (bundle != null) {
            orderCode = getArguments().getString("orderCode");
            id = getArguments().getString("id");
            dateString = getArguments().getString("date");
            type = getArguments().getString("type");
        }


        if (type.equals("ss")) {
            callViewSelfShopperApi();
        } else {
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callShowOrderApi();
        }




        tvDownloadInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadOrderInvoice();

            }
        });


        return view;
    }

    private void callViewSelfShopperApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ViewSelfShopperResponse> call = RetrofitClient3.getInstance3().getAppApi().viewSelfShopper(
                "Bearer " + sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<ViewSelfShopperResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ViewSelfShopperResponse> call, Response<ViewSelfShopperResponse> response) {
                if (response.code() == 200) {
                    dateString = response.body().getPkg().getCreatedAt();
                    imageUrl = response.body().getPkg().getInvoice();

                    String[] split = dateString.split("T");
                    String date1 = split[0];

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
                    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
                    LocalDate ld = LocalDate.parse(date1, dtf);
                    String month_name = dtf2.format(ld);

                    date.setText(month_name);
                    if (response.body().getPkg().getStore()!=null){
                        websiteName.setText(response.body().getPkg().getStore().getName());
                    }
                    orderNumberText.setText("#" + response.body().getPkg().getId());
                    status.setText(response.body().getPkg().getPackageState().getState().getName());
                    singleOrderImage.setImageResource(R.drawable.ic_self_shopper);

                    bundle.putString("orderCode", orderCode);
                    bundle.putString("imageUrl", imageUrl);
                    bundle.putString("id", id);
                    orderDetails.setArguments(bundle);
                    orderUpdates.setArguments(bundle);

                    viewPagerAdapter = new ViewOrderViewPagerAdapter(getChildFragmentManager());

                    viewPagerAdapter.addFragment(orderDetails, "Order Details");
                    viewPagerAdapter.addFragment(orderUpdates, "Order Updates");
                    viewOrderViewPager.setAdapter(viewPagerAdapter);
                    viewOrderTabLayout.setupWithViewPager(viewOrderViewPager);
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi("self");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewSelfShopperResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadOrderInvoice() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<String> call = RetrofitClient3.getInstance3()
                .getAppApi().orderInvoiceDownload("Bearer "+sharedPrefManager.getBearerToken(), shopperId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code()==200){

                    url = response.body();

                    Log.d("Sucesss", url);

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


                    LoadingDialog.cancelLoading();
                }else if (response.code()==401){
                    callRefreshTokenApi(type);
                    LoadingDialog.cancelLoading();
                }else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
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



//    private void downloadInvoiceApi() {
//
//        Call<DownloadInvoiceModelResponse> call = RetrofitClient3.getInstance3()
//                .getAppApi().downloadInvoice("Bearer " + sharedPrefManager.getBearerToken(), id);
//        call.enqueue(new Callback<DownloadInvoiceModelResponse>() {
//            @Override
//            public void onResponse(Call<DownloadInvoiceModelResponse> call, Response<DownloadInvoiceModelResponse> response) {
//                if (response.code() == 200) {
//                    DownloadInvoiceModelResponse downloadInvoiceModelResponse = response.body();
//                    url = downloadInvoiceModelResponse.getInvoiceObject();
//
//                    Dexter.withContext(getContext())
//                            .withPermissions(
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE
//                            ).withListener(new MultiplePermissionsListener() {
//                        @Override
//                        public void onPermissionsChecked(MultiplePermissionsReport report) {
//                            if (report.areAllPermissionsGranted()) {
//                                downloadFile(url);
//                            } else {
//                                Toast.makeText(getActivity(), "Please Allow the Permission", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//
//                        }
//
//
//                    }).check();
//
//                } else if (response.code() == 401) {
//                    callRefreshTokenApi();
//                } else {
//                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DownloadInvoiceModelResponse> call, Throwable t) {
//                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    private void callShowOrderApi() {

        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShowOrderResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().showOrder("Bearer " + sharedPrefManager.getBearerToken(), orderCode);
        call.enqueue(new Callback<ShowOrderResponse>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ShowOrderResponse> call, Response<ShowOrderResponse> response) {
                if (response.code() == 200) {

                    shopperId = response.body().getShopperOrderId();

                    addTextToFields(response.body());

                    bundle.putString("orderCode", orderCode);
                    bundle.putString("imageUrl", imageUrl);
                    bundle.putString("id", id);
                    orderDetails.setArguments(bundle);
                    orderUpdates.setArguments(bundle);

                    viewPagerAdapter = new ViewOrderViewPagerAdapter(getChildFragmentManager());

                    viewPagerAdapter.addFragment(orderDetails, "Order Details");
                    viewPagerAdapter.addFragment(orderUpdates, "Order Updates");
                    viewOrderViewPager.setAdapter(viewPagerAdapter);
                    viewOrderTabLayout.setupWithViewPager(viewOrderViewPager);
                } else if (response.code() == 401) {
                    callRefreshTokenApi("personal");
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<ShowOrderResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void callRefreshTokenApi(String type) {
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
                    if (type.equals("self")) {
                        callViewSelfShopperApi();
                    } else {
                        callShowOrderApi();
                    }

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
    private void addTextToFields(ShowOrderResponse showOrderResponse) {

        String s = showOrderResponse.getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        date.setText(month_name);
        websiteName.setText(showOrderResponse.getStore().getName());
        orderNumberText.setText("#" + showOrderResponse.getOrderCode());
        status.setText(showOrderResponse.getOrderState().getState().getName());
        LoadingDialog.cancelLoading();
    }

}