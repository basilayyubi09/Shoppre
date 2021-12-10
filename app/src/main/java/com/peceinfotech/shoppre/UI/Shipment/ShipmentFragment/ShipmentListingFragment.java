package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.ReadyToShipAdapter;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentListingAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageListingResponse;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.ShipmentModelResponse.Shipment;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentIndexModelResponse;
import com.peceinfotech.shoppre.UI.Locker.ReadyToShipReturnedAndDiscard;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShipmentListingFragment extends Fragment {
///////// implemented LockerReadyToShip's ReadyToShipAdapter will change to accordingly
    // change single adapter ready to ship single layout to shipment listing single
    SharedPrefManager sharedPrefManager;
    List<Shipment> list = new ArrayList<>();
    ShipmentListingAdapter shipmentListingAdapter;
    RecyclerView recyclerView;
    LinearLayout returnAndDiscardText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shipment_listing, container, false);


        recyclerView = view.findViewById(R.id.lockerReadyToShipRecycler);
        returnAndDiscardText = view.findViewById(R.id.returnAndDiscardText);

        sharedPrefManager = new SharedPrefManager(getActivity());

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            LoadingDialog.showLoadingDialog(getActivity(), getString(R.string.Loading));
            callShipmentIndexApi();
        }




        returnAndDiscardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewPreviousShipmentFragment(), null)
                        .addToBackStack(null).commit();

            }
        });


        return view;
    }

    private void callShipmentIndexApi() {
        Call<ShipmentIndexModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentIndex("Bearer "+sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<ShipmentIndexModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentIndexModelResponse> call, Response<ShipmentIndexModelResponse> response) {
                if (response.code()==201){
                    list = response.body().getShipments();
                    shipmentListingAdapter = new ShipmentListingAdapter(list, getContext());
                    recyclerView.setAdapter(shipmentListingAdapter);
                    LoadingDialog.cancelLoading();
                }else if (response.code()==400){
                    callRefreshTokenApi();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShipmentIndexModelResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void callListingApi() {
//        Call<PackageListingResponse> call = RetrofitClient3
//                .getInstance3()
//                .getAppApi().lockerListing("Bearer " + sharedPrefManager.getBearerToken());
//        call.enqueue(new Callback<PackageListingResponse>() {
//            @Override
//            public void onResponse(Call<PackageListingResponse> call, Response<PackageListingResponse> response) {
//                if (response.code() == 201) {
//                    list = response.body().getPackages();
//                    shipmentListingAdapter = new ShipmentListingAdapter(list, getContext());
//                    recyclerView.setAdapter(shipmentListingAdapter);
//                    LoadingDialog.cancelLoading();
//                } else if (response.code() == 401) {
//                    callRefreshTokenApi();
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PackageListingResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
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
                    callShipmentIndexApi();
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