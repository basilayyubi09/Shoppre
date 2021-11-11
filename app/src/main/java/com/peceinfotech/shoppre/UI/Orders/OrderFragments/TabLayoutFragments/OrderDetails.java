package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.OrderAdapter.ViewOrderAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderItem;
import com.peceinfotech.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ViewOrderResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetails extends Fragment {

    RecyclerView orderDetailsRecycler;
    List<ViewOrderResponse> list = new ArrayList<>();
    List<OrderItem> list1 = new ArrayList<>();
    ViewOrderAdapter viewOrderAdapter;
    ShowOrderResponse showOrderResponse;
    OrderItem orderItem;
    TextView text;
    SharedPrefManager sharedPrefManager ;
    String orderCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        orderDetailsRecycler = view.findViewById(R.id.orderDetailsRecycler);
        text = view.findViewById(R.id.text);

        sharedPrefManager = new SharedPrefManager(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {

            orderCode =  bundle.getString("orderCode");

        }

       LoadingDialog.showLoadingDialog(getActivity(),"");
        callShowOrderApi();


        return view;
    }

    private void callShowOrderApi() {

        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ShowOrderResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().showOrder("Bearer " + sharedPrefManager.getBearerToken(), orderCode);
        call.enqueue(new Callback<ShowOrderResponse>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ShowOrderResponse> call, Response<ShowOrderResponse> response) {
                if (response.code() == 200) {

                    list1 = response.body().getOrderItems();

                    viewOrderAdapter = new ViewOrderAdapter(list1, getContext());
                    orderDetailsRecycler.setAdapter(viewOrderAdapter);

                    int number = orderDetailsRecycler.getAdapter().getItemCount();
                    if (number == 0) {
                        text.setVisibility(View.VISIBLE);
                        orderDetailsRecycler.setVisibility(View.GONE);

                    } else {
                        text.setVisibility(View.GONE);
                        orderDetailsRecycler.setVisibility(View.VISIBLE);
                    }
                    viewOrderAdapter.notifyDataSetChanged();


                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<ShowOrderResponse> call, Throwable t) {
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
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callShowOrderApi();
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