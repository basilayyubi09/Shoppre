package com.peceinfotech.shoppre.UI.Orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.OrdersAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.CancelledApiResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.peceinfotech.shoppre.UI.Shipment.ShippingCalculator;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CancelledOrderFragment extends Fragment {
    SharedPrefManager sharedPrefManager;
    RecyclerView recycle;
    List<Order> list;
    TextView text;
    OrdersAdapter ordersAdapter;
    CardView cancelOrderVirtualAddressCard, cancelOrderShippingCalculatorCard, cancelOrderFaqAndHelpCard;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancelled_order, container, false);

        recycle = view.findViewById(R.id.recycle);
        text = view.findViewById(R.id.text);
        cancelOrderVirtualAddressCard = view.findViewById(R.id.cancelOrderVirtualAddressCard);
        cancelOrderShippingCalculatorCard = view.findViewById(R.id.cancelOrderShippingCalculatorCard);


        cancelOrderVirtualAddressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new VertualAddress(), null)
                        .addToBackStack(null).commit();
            }
        });

        cancelOrderShippingCalculatorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();
            }
        });


        sharedPrefManager = new SharedPrefManager(getActivity());
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        LoadingDialog.showLoadingDialog(getActivity(), "");
        callCancelOrderApi();


        return view;
    }

    private void callCancelOrderApi() {
        Call<CancelledApiResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getCancelOrder("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<CancelledApiResponse>() {
            @Override
            public void onResponse(Call<CancelledApiResponse> call, Response<CancelledApiResponse> response) {
                if (response.code() == 201) {

                    list = response.body().getOrders();
                    ordersAdapter = new OrdersAdapter(list, getContext());
                    recycle.setLayoutManager(linearLayoutManager);
                    recycle.setAdapter(ordersAdapter);

                    int number = recycle.getAdapter().getItemCount();
                    if (number == 0) {
                       recycle.setVisibility(View.GONE);
                       text.setVisibility(View.VISIBLE);

                    } else {
                        recycle.setVisibility(View.VISIBLE);
                        text.setVisibility(View.GONE);
                    }
                    ordersAdapter.notifyDataSetChanged();
                    LoadingDialog.cancelLoading();
                }
                else if (response.code() == 401) {
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<CancelledApiResponse> call, Throwable t) {
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
                    callCancelOrderApi();
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