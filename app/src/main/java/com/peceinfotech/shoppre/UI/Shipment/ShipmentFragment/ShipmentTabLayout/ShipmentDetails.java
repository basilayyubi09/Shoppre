package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsResponse;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShipmentDetails extends Fragment {

    RecyclerView shipmentDetailsRecycler;
    List<PackageModel> list= new ArrayList<>();
    ShipmentDetailsAdapter shipmentDetailsAdapter;
    SharedPrefManager sharedPrefManager;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_details, container, false);

        shipmentDetailsRecycler = view.findViewById(R.id.shipmentDetailsRecycler);
        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
//            Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        }

//        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.5F));
//        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.6F));
//        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.7F));
//        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 2.5F));
//        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 3.2F));

        shipmentDetailsTabApiCall();


        shipmentDetailsAdapter = new ShipmentDetailsAdapter(list, getActivity());
        shipmentDetailsRecycler.setAdapter(shipmentDetailsAdapter);

        return view;
    }

    private void shipmentDetailsTabApiCall() {
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer "+ sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code()==200){
                        list = response.body().getPackages();

                    shipmentDetailsAdapter = new ShipmentDetailsAdapter(list, getActivity());
                    shipmentDetailsRecycler.setAdapter(shipmentDetailsAdapter);
                }else if (response.code()==401){
                    callRefreshTokenApi();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ShipmentDetailsModelResponse> call, Throwable t) {

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
                    Toast.makeText(getActivity(), "Something Went Wrong Please try again!", Toast.LENGTH_SHORT).show();
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