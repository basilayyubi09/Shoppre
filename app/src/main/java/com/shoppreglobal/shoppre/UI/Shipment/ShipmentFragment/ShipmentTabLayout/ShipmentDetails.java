package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.BoxDetailsParentAdapter;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.ShipmentDetailsAdapter;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentBox;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShipmentDetails extends Fragment {

    RecyclerView shipmentDetailsRecycler , boxRecycle;
    List<PackageModel> list = new ArrayList<>();
    ShipmentDetailsAdapter shipmentDetailsAdapter;
    SharedPrefManager sharedPrefManager;
    LinearLayout secondRecycle;
    Spinner boxSpinner;
    BoxDetailsParentAdapter boxDetailsParentAdapter;
    List<ShipmentBox> boxList;
    ArrayAdapter arrayAdapter;
    String[] dynamicString;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_details, container, false);

        shipmentDetailsRecycler = view.findViewById(R.id.shipmentDetailsRecycler);
        secondRecycle = view.findViewById(R.id.secondRecycle);
        boxSpinner = view.findViewById(R.id.boxSpinner);
        boxRecycle = view.findViewById(R.id.boxRecycle);
        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
//            Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        }

        shipmentDetailsTabApiCall();


        shipmentDetailsAdapter = new ShipmentDetailsAdapter(list, getActivity());
        shipmentDetailsRecycler.setAdapter(shipmentDetailsAdapter);

        return view;
    }

    private void shipmentDetailsTabApiCall() {
        Call<ShipmentDetailsModelResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().shipmentDetails("Bearer " + sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<ShipmentDetailsModelResponse>() {
            @Override
            public void onResponse(Call<ShipmentDetailsModelResponse> call, Response<ShipmentDetailsModelResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getShipment().getShipmentBoxes().isEmpty()){
                        list = response.body().getPackages();
                        shipmentDetailsAdapter = new ShipmentDetailsAdapter(list, getActivity());
                        shipmentDetailsRecycler.setAdapter(shipmentDetailsAdapter);
                        shipmentDetailsRecycler.setVisibility(View.VISIBLE);
                        secondRecycle.setVisibility(View.GONE);
                    }
                    else {

                        shipmentDetailsRecycler.setVisibility(View.GONE);
                        secondRecycle.setVisibility(View.VISIBLE);
                        boxList = response.body().getShipment().getShipmentBoxes();
                        dynamicString = new String[boxList.size()];
                        boxDetailsParentAdapter = new BoxDetailsParentAdapter(getActivity() , boxList);
                        boxRecycle.setAdapter(boxDetailsParentAdapter);
                        for (int i=0;i<boxList.size();i++){
                            dynamicString[i] = "Box "+String.valueOf(i+1);
                        }
                        arrayAdapter = new ArrayAdapter(getContext(), R.layout.wallet_fragment_spinner_text, dynamicString);
                        boxSpinner.setAdapter(arrayAdapter);
                    }

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
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