package com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.ShipmentUpdateAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.EngageRetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.ShipmentModelResponse.PostShipmentCommentModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentCommentModelResponse;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentUpdates extends Fragment {

    RecyclerView shipmentUpdateRecycler;
    List<ShipmentCommentModelResponse> list;
    SharedPrefManager sharedPrefManager;
    ShipmentUpdateAdapter shipmentUpdateAdapter;
    EditText shipmentCommentEditText;
    ImageView shipmentCommentSendBtn;
    String textString;
    int id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_updates, container, false);

        shipmentUpdateRecycler = view.findViewById(R.id.shipmentUpdateRecycler);
        shipmentCommentEditText = view.findViewById(R.id.shipmentCommentEditText);
        shipmentCommentSendBtn = view.findViewById(R.id.shipmentCommentSendBtn);
        list = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getActivity());

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
            Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        }

        LoadingDialog.showLoadingDialog(getActivity(), "");
        getShipmentCommentApi();

        shipmentCommentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateBtn()){
                    return;
                }else {
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    postCommentApi();
                }
            }
        });
        return view;
    }

    private void postCommentApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("comments", textString);

        Call<PostShipmentCommentModelResponse> call = EngageRetrofitClient.getInstance3()
                .getAppApi().postShipmentComment("Bearer "+sharedPrefManager.getBearerToken(), id, jsonObject.toString());

        call.enqueue(new Callback<PostShipmentCommentModelResponse>() {
            @Override
            public void onResponse(Call<PostShipmentCommentModelResponse> call, Response<PostShipmentCommentModelResponse> response) {
                if (response.code()==201){
                    shipmentCommentEditText.setText("");
                    getShipmentCommentApi();
                    LoadingDialog.cancelLoading();
                }else if (response.code()==401){
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<PostShipmentCommentModelResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                LoadingDialog.cancelLoading();
            }
        });
    }

    private boolean validateBtn() {
        textString = shipmentCommentEditText.getText().toString();
        if (textString.equals("")){
            return false;
        }else {
            return true;
        }

    }

    private void getShipmentCommentApi() {
        Call<List<ShipmentCommentModelResponse>> call = EngageRetrofitClient.getInstance3().
                getAppApi().shipmentGetComment("Bearer "+sharedPrefManager.getBearerToken(), id);
        call.enqueue(new Callback<List<ShipmentCommentModelResponse>>() {
            @Override
            public void onResponse(Call<List<ShipmentCommentModelResponse>> call, Response<List<ShipmentCommentModelResponse>> response) {
                if (response.code()==200){
                    list = response.body();
                    shipmentUpdateAdapter = new ShipmentUpdateAdapter(list, getContext());
                    shipmentUpdateRecycler.setAdapter(shipmentUpdateAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
                    layoutManager.scrollToPosition(0);
                    shipmentUpdateRecycler.smoothScrollToPosition(0);
                    shipmentUpdateRecycler.setLayoutManager(layoutManager);
                    LoadingDialog.cancelLoading();
                }else if (response.code()==401){
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentCommentModelResponse>> call, Throwable t) {

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
//                    callAddComment();
                    getShipmentCommentApi();
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