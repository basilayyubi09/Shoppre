package com.shoppreglobal.shoppre.UI.Locker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.LockerAdapters.ViewPackageViewPager;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageItem;
import com.shoppreglobal.shoppre.LockerModelResponse.ViewPackageResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.MinioUploadModelResponse;
import com.shoppreglobal.shoppre.UI.Locker.ViewPackageTabLayout.PackageDetails;
import com.shoppreglobal.shoppre.UI.Locker.ViewPackageTabLayout.PackageUpdates;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LockerViewPackage extends Fragment {

    TabLayout viewPackageTablayout;
    ViewPager viewPackageViewPager;
    SharedPrefManager sharedPrefManager;
    TextView comment, websiteName, packageId, weight, date, trackingNumber;
    public TextView amount;
    int id;
    Bundle bundle1 = new Bundle();
    PackageDetails packageDetails = new PackageDetails();
    List<PackageItem> list;
    PackageUpdates packageUpdates = new PackageUpdates();
    CardView uploadInvoiceCard;
    ViewPackageViewPager viewPackageViewPagerAdapter;
    LinearLayout viewPackageViewMore;
    String orderCode;
    MaterialButton uploadInvoiceBtn;
    byte[] byteArray;
    String encodedFile;
    String file;
    String responseUrl;
    String responseObject;
    String splitUrl;
    int strResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_view_package, container, false);
        viewPackageTablayout = view.findViewById(R.id.viewPackageTablayout);
        viewPackageViewPager = view.findViewById(R.id.viewPackageViewPager);
        comment = view.findViewById(R.id.comment);
        packageId = view.findViewById(R.id.packageId);
        weight = view.findViewById(R.id.weight);
        date = view.findViewById(R.id.date);
        trackingNumber = view.findViewById(R.id.trackingNumber);
        amount = view.findViewById(R.id.amount);
        websiteName = view.findViewById(R.id.websiteName);
        uploadInvoiceCard = view.findViewById(R.id.uploadInvoiceCard);
        viewPackageViewMore = view.findViewById(R.id.viewPackageViewMore);
        uploadInvoiceBtn = view.findViewById(R.id.uploadInvoiceBtn);
        sharedPrefManager = new SharedPrefManager(getActivity());
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");


        }

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callViewPackage();

        }

        uploadInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInvoiceApi();
            }
        });

        viewPackageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();

//                bundle2.putString("id", String.valueOf(id));
//                bundle2.putString("orderCode", orderCode);
//
//
//                ViewOrderPersonalShop viewOrderPersonalShop = new ViewOrderPersonalShop();
//                viewOrderPersonalShop.setArguments(bundle2);
//                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, viewOrderPersonalShop, null)
//                        .addToBackStack(null).commit();

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                        , new OrderFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }

    private void uploadInvoiceApi() {

        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Set your required file type
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "DEMO"), 1001);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
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
                    callMinioUploadApi();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void callMinioUploadApi() {
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
                    callRefreshTokenApi("minio");

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
            if (strResponse == 200) {
                Toast.makeText(getActivity(), "Invoice upload successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Invoice upload Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callViewPackage() {
        Call<ViewPackageResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().viewPackage("Bearer " + sharedPrefManager.getBearerToken()
                        , id);
        call.enqueue(new Callback<ViewPackageResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ViewPackageResponse> call, Response<ViewPackageResponse> response) {
                if (response.code() == 200) {

                    list = response.body().getPackageItems();
                    bundle1.putInt("id", response.body().getId());
                    bundle1.putSerializable("list", (Serializable) list);
                    orderCode = String.valueOf(response.body().getOrderCode());
                    packageDetails.setArguments(bundle1);


                    Bundle bundle = new Bundle();

                    if (response.body().getInvoice() == null) {
                        uploadInvoiceCard.setVisibility(View.VISIBLE);
                    } else {
                        uploadInvoiceCard.setVisibility(View.GONE);
                    }

                    bundle.putInt("id", response.body().getId());

                    packageUpdates.setArguments(bundle);
                    viewPackageViewPagerAdapter = new ViewPackageViewPager(getChildFragmentManager());
                    viewPackageViewPagerAdapter.addFragment(packageDetails, "Package Details " + "(" + String.valueOf(list.size()) + ")");
                    viewPackageViewPagerAdapter.addFragment(packageUpdates, "Locker Updates");

                    viewPackageViewPager.setAdapter(viewPackageViewPagerAdapter);
                    viewPackageTablayout.setupWithViewPager(viewPackageViewPager);

                    setTextToFields(response.body());
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi("view");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewPackageResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTextToFields(ViewPackageResponse list) {

        String s = list.getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        date.setText(month_name);

        trackingNumber.setText(list.getInvoiceCode());
        if (list.getStore() != null) {
            websiteName.setText(list.getStore().getName());
        } else {
            websiteName.setText("");
        }

        packageId.setText("#" + String.valueOf(list.getId()));
        weight.setText(String.valueOf(list.getWeight()));

        Double sum = Double.valueOf(0);
        for (int i = 0; i < list.getPackageItems().size(); i++) {
            sum = sum + list.getPackageItems().get(i).getPriceAmount();
        }

        amount.setText("??? " + String.valueOf(sum));


        if (list.getStateNameAndColor().getStateName() != null) {
            if (list.getStateNameAndColor().getStateName().equals("In Review")) {
                comment.setText(list.getStateNameAndColor().getStateName());
                comment.setTextColor(getActivity().getColor(R.color.in_review_blue_color));
                comment.setBackground(getActivity().getDrawable(R.drawable.price_changed_background));
            } else if (list.getStateNameAndColor().getStateName().equals("Action Required")) {
                comment.setText(list.getStateNameAndColor().getStateName());
                comment.setTextColor(getActivity().getColor(R.color.action_required_yellow_color));
                comment.setBackground(getActivity().getDrawable(R.drawable.order_placed_background));
            } else if (list.getStateNameAndColor().getStateName().equals("Ready To Send")) {
                comment.setText(list.getStateNameAndColor().getStateName());
                comment.setTextColor(getActivity().getColor(R.color.ready_to_ship_green_color));
                comment.setBackground(getActivity().getDrawable(R.drawable.ready_to_ship_background));
            }
        } else {
            comment.setText("   ");
            comment.setTextColor(getActivity().getColor(R.color.in_review_blue_color));
            comment.setBackground(getActivity().getDrawable(R.drawable.price_changed_background));
        }

    }

    private void callRefreshTokenApi(String whichApi) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    if (whichApi.equals("minio")) {
                        callMinioUploadApi();
                    } else if (whichApi.equals("view")) {
                        callViewPackage();
                    }
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}