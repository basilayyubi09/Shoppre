package com.shoppreglobal.shoppre.UI.Locker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.LockerAdapters.ReadyToShipAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageListingResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReadyToShipReturnedAndDiscard extends Fragment {


    RecyclerView returnedAndDiscardRecycler;
    SharedPrefManager sharedPrefManager;
    List<PackageModel> list = new ArrayList<>();
    ReadyToShipAdapter returnedAndDiscardAdapter;
    TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ready_to_ship_returned_and_discard, container, false);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        returnedAndDiscardRecycler = view.findViewById(R.id.returnedAndDiscardRecycler);
        emptyText = view.findViewById(R.id.emptyText);

        sharedPrefManager = new SharedPrefManager(getActivity());
        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callListingApi();
        }


        return view;

    }

    private void callListingApi() {
        Call<PackageListingResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().cancelPackage("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<PackageListingResponse>() {
            @Override
            public void onResponse(Call<PackageListingResponse> call, Response<PackageListingResponse> response) {
                if (response.code() == 201) {
                    list = response.body().getPackages();
                    returnedAndDiscardAdapter = new ReadyToShipAdapter(list, getContext());
                    returnedAndDiscardRecycler.setAdapter(returnedAndDiscardAdapter);
                    int count = returnedAndDiscardAdapter.getItemCount();
                    if (count == 0) {
                        emptyText.setVisibility(View.VISIBLE);
                        returnedAndDiscardRecycler.setVisibility(View.GONE);
                    } else {
                        emptyText.setVisibility(View.GONE);
                        returnedAndDiscardRecycler.setVisibility(View.VISIBLE);
                    }
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