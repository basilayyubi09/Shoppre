package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment;

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
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.PreviousShipmentAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.PreviousShipmentModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.Shipment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPreviousShipmentFragment extends Fragment {

    RecyclerView previousShipmentRecycler;
    SharedPrefManager sharedPrefManager;
    List<Shipment> list;
    TextView emptyText;
    PreviousShipmentAdapter previousShipmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_previous_shipment, container, false);
        previousShipmentRecycler = view.findViewById(R.id.previousShipmentRecycler);
        emptyText = view.findViewById(R.id.emptyText);
        list = new ArrayList<>();

        sharedPrefManager = new SharedPrefManager(getActivity());
        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            previousShipmentApi();
        }
        return view;
    }

    private void previousShipmentApi() {
        Call<PreviousShipmentModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().previousShipment("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<PreviousShipmentModelResponse>() {
            @Override
            public void onResponse(Call<PreviousShipmentModelResponse> call, Response<PreviousShipmentModelResponse> response) {
                if (response.code() == 201) {
                    list = response.body().getShipments();
                    previousShipmentAdapter = new PreviousShipmentAdapter(list, getContext());
                    previousShipmentRecycler.setAdapter(previousShipmentAdapter);
                    int count = previousShipmentAdapter.getItemCount();
                    if (count == 0) {
                        emptyText.setVisibility(View.VISIBLE);
                        previousShipmentRecycler.setVisibility(View.GONE);
                    } else {
                        emptyText.setVisibility(View.GONE);
                        previousShipmentRecycler.setVisibility(View.VISIBLE);
                    }
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PreviousShipmentModelResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                    previousShipmentApi();
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