package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.SearchStoreResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.SelfShopperResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.MinioUploadModelResponse;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;
import com.shoppreglobal.shoppre.Utils.ViewSampleDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfShopperPlaceOrderFargment extends Fragment {

    SharedPrefManager sharedPrefManager;
    MaterialButton selfShopProceedBtn;
    TextView viewSample, path, storeError;
    ImageView img;
    int i = 0, storeId = 0;
    MaterialCardView layout;
    MaterialButton proceed;
    AutoCompleteTextView etStore;
    LinearLayout clickableLayout;
    byte[] byteArray;
    ArrayAdapter arrayAdapter;
    String encodedFile, responseObject, responseUrl, splitUrl;
    String file, storeName;
    SearchStoreResponse list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_shopper_place_order_fargment, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        selfShopProceedBtn = view.findViewById(R.id.proceed);

        viewSample = view.findViewById(R.id.viewSample);
        storeError = view.findViewById(R.id.storeError);
        img = view.findViewById(R.id.img);
        etStore = view.findViewById(R.id.etStore);
        clickableLayout = view.findViewById(R.id.clickableLayout);
        layout = view.findViewById(R.id.layout);
        proceed = view.findViewById(R.id.proceed);
        path = view.findViewById(R.id.path);

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
                if (i == 0) {
                    storeError.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Please Select an Image", Toast.LENGTH_SHORT).show();
                } else if (storeId == 0) {
                    storeError.setVisibility(View.VISIBLE);
                } else {


                    storeError.setVisibility(View.GONE);
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callMinioUploadApi();

                }
            }
        });
        clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FromCard();
            }
        });

        etStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    callSearchStoreApi(s.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof SearchStoreResponse.Item) {
                    SearchStoreResponse.Item item1 = (SearchStoreResponse.Item) item;
                    storeId = ((SearchStoreResponse.Item) item).getId();
                    storeName = ((SearchStoreResponse.Item) item).getName();


                }

            }
        });
        return view;

    }

    private void callSearchStoreApi(String toString) {
        Call<SearchStoreResponse> call = RetrofitClient3.getInstance3().getAppApi()
                .searchStore("Store", toString);
        call.enqueue(new Callback<SearchStoreResponse>() {
            @Override
            public void onResponse(Call<SearchStoreResponse> call, Response<SearchStoreResponse> response) {

                if (response.code() == 200) {
                    list = response.body();
                    List<SearchStoreResponse.Item> list1 = list.getItems();
                    arrayAdapter = new ArrayAdapter(getActivity()
                            , R.layout.dropdown_single_layout, list1);
                    etStore.setAdapter(arrayAdapter);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchStoreResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void FromCard() {
        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                getActivity().requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                Intent intent = new Intent();
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Set your required file type
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "DEMO"), 1001);
            }
        }

    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK && null != data) {

            Uri selectedFile = data.getData();
            String selectedFilePath = data.getData().getPath();
            String string = selectedFilePath;
            String[] parts = string.split("/");
            file = parts[parts.length - 1];
            Log.d("Path", file);

            path.setText(selectedFilePath);
            i = 1;

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedFile);
                byteArray = new byte[inputStream.available()];
                inputStream.read(byteArray);
                encodedFile = Base64.encodeToString(byteArray, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }


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

                    SelfShopperPlaceOrderFargment.MinioUploading minioUploading = new SelfShopperPlaceOrderFargment.MinioUploading();
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
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Something went wrong please try again!", Snackbar.LENGTH_LONG);
                    snackbar.show();
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

    class MinioUploading extends AsyncTask<String, String, String> {

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
                callSelfShopperApi(body);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }

    private void callSelfShopperApi(RequestBody body) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("invoice", responseObject);
        jsonObject.addProperty("store_id", storeId);

        Call<SelfShopperResponse> call = RetrofitClient3.getInstance3().getAppApi().selfShopper("Bearer " + sharedPrefManager.getBearerToken()
                , jsonObject.toString());
        call.enqueue(new Callback<SelfShopperResponse>() {
            @Override
            public void onResponse(Call<SelfShopperResponse> call, Response<SelfShopperResponse> response) {
                if (response.code() == 201) {
                    LoadingDialog.cancelLoading();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "selfOrder");
                    bundle.putString("shopName", storeName);
                    bundle.putString("id", String.valueOf(response.body().getId()));
                    ThankYouFragment thankYouFragment = new ThankYouFragment();
                    thankYouFragment.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction()
                            .replace(R.id.orderFrameLayout, thankYouFragment, null)
                            .addToBackStack(null).commit();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SelfShopperResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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