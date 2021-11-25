package com.peceinfotech.shoppre.UI.Locker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.ReadyToShipAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageListingResponse;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.LockerModelResponse.ReadyToShipResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.CreateShipRequest.CreateShipRequestFragment;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LockerReadyToShip extends Fragment {

    SharedPrefManager sharedPrefManager;
    List<PackageModel> list = new ArrayList<>();
    ReadyToShipAdapter readyToShipAdapter;
    RecyclerView lockerReadyToShipRecycler;
    LinearLayout returnAndDiscardText;
    MaterialButton createShipRequestBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_ready_to_ship, container, false);


        lockerReadyToShipRecycler = view.findViewById(R.id.lockerReadyToShipRecycler);
        returnAndDiscardText = view.findViewById(R.id.returnAndDiscardText);
        createShipRequestBtn = view.findViewById(R.id.createShipRequestBtn);

        sharedPrefManager = new SharedPrefManager(getActivity());

        createShipRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CreateShipRequestFragment(), null)
                        .addToBackStack(null).commit();
            }
        });

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callListingApi();
        }




        returnAndDiscardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReadyToShipReturnedAndDiscard(), null)
                        .addToBackStack(null).commit();

            }
        });


        return view;
    }

    private void callListingApi() {
        Call<PackageListingResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().lockerListing("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<PackageListingResponse>() {
            @Override
            public void onResponse(Call<PackageListingResponse> call, Response<PackageListingResponse> response) {
                if (response.code() == 201) {
                    list = response.body().getPackages();
                    readyToShipAdapter = new ReadyToShipAdapter(list, getContext());
                    lockerReadyToShipRecycler.setAdapter(readyToShipAdapter);
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PackageListingResponse> call, Throwable t) {
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
                    callListingApi();
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