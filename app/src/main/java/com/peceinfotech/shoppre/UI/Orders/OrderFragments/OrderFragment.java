package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.VerifyEmailResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.peceinfotech.shoppre.UI.Shipment.ShippingCalculator;

public class OrderFragment extends Fragment {


    MaterialButton addYourFirstOrderBtn , verifyEmailBtn, shippingCalculator;
    SharedPrefManager sharedPrefManager;
    CardView verifyEmailBox;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        verifyEmailBox = view.findViewById(R.id.verifyEmailBox);
        verifyEmailBtn = view.findViewById(R.id.verifyEmailBtn);
        sharedPrefManager = new SharedPrefManager(getActivity());
        ///Hooks
        LoadingDialog.showLoadingDialog(getActivity(),"");
        callMeApi(sharedPrefManager.getBearerToken());
        shippingCalculator = view.findViewById(R.id.shippingCalculator);


        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LandingDialog alert = new LandingDialog();
//                alert.showDialog(getActivity());
                LoadingDialog.showLoadingDialog(getActivity() , "");
                callVerifyEmailId();
            }
        });
        addYourFirstOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new OrderListing(), null)
                        .addToBackStack(null).commit();

            }
        });

        shippingCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new ShippingCalculator(), null)
                            .addToBackStack(null).commit();

            }
        });


        return  view;
    }
    private void callMeApi(String bearerToken) {
        Call<MeResponse> call = RetrofitClient
                .getInstance().getApi()
                .getUser("Bearer "+bearerToken);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    if (response.body().getIsEmailVerified()==0){

                        verifyEmailBox.setVisibility(View.VISIBLE);
                    }
                    else if (response.body().getIsEmailVerified()==1){

                        verifyEmailBox.setVisibility(View.GONE);
                    }
                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
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
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                }
                else {
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
    private void callVerifyEmailId() {
        Call<VerifyEmailResponse> call = RetrofitClient.getInstance()
                .getApi().getVerify("Bearer "+sharedPrefManager.getBearerToken(), sharedPrefManager.getId());
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Verification link has sent to your email.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if(response.code()==403){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getError(), Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}