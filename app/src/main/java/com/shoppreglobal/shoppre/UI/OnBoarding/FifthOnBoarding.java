package com.shoppreglobal.shoppre.UI.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FifthOnBoarding extends Fragment {

    private Button startShopping;
    ImageView backBtn4;
    TextView lockerNo, virtualAddressName;
    SharedPrefManager sharedPrefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth_on_boarding, container, false);

        startShopping = view.findViewById(R.id.startShoppingBtn);
        backBtn4 = view.findViewById(R.id.back_arrow4);
        lockerNo = view.findViewById(R.id.lockerNo);
        virtualAddressName = view.findViewById(R.id.virtualAddressName);

        sharedPrefManager = new SharedPrefManager(getActivity());


        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.on), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            begin();

        }

        startShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , OrderActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        backBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        return view;
    }

    private void begin() {
        LoadingDialog.showLoadingDialog(getActivity() , "");
        callMeApi();
    }

    private void callMeApi() {

        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTAwNTU4LCJzZXNzaW9uX2lkIjoxMTMzMCwiaWF0IjoxNjM1NDIzNzAyLCJleHAiOjE2MzU0MjczMDJ9.UDJAah-VmB2fkgIob3PRD5-HueU2wuBF8PXW0mcRYXY
        Call<MeResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getUser("Bearer "+sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {

                if (response.code() == 200){

                    lockerNo.setText(response.body().getVirtualAddressCode());
                    sharedPrefManager.storeFirstName(response.body().getFirstName());
                    sharedPrefManager.storeLastName(response.body().getLastName());
                    sharedPrefManager.storeFullName(response.body().getName());
                    sharedPrefManager.storeEmail(response.body().getEmail());
                    sharedPrefManager.storeId(response.body().getId());
                    sharedPrefManager.storeSalutation(response.body().getSalutation());
                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());
                    LoadingDialog.cancelLoading();
                    virtualAddressName.setText(sharedPrefManager.getFullName());
                }
                else  if(response.code() == 401){
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {

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
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    begin();
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
}