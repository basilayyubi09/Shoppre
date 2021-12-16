package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.OrderAdapter.ViewOrderViewPagerAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderDetails;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderUpdates;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewOrderPersonalShop extends Fragment {

    SharedPrefManager sharedPrefManager;
    TabLayout viewOrderTabLayout;
    ViewPager viewOrderViewPager;
    String orderCode;
    TextView websiteName, status, orderNumberText, date;
    ViewOrderViewPagerAdapter viewPagerAdapter;
    Bundle bundle;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order_personal_shop, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");


        viewOrderViewPager = view.findViewById(R.id.viewOrderViewPager);
        viewOrderTabLayout = view.findViewById(R.id.viewOrderTabLayout);
        websiteName = view.findViewById(R.id.websiteName);
        status = view.findViewById(R.id.status);
        orderNumberText = view.findViewById(R.id.orderNumberText);
        date = view.findViewById(R.id.date);

        bundle = this.getArguments();
        OrderDetails orderDetails = new OrderDetails();
        OrderUpdates orderUpdates = new OrderUpdates();
        if (bundle != null) {
            orderCode = getArguments().getString("orderCode");
            id = getArguments().getString("id");
        }
        callShowOrderApi();


        bundle.putString("orderCode", orderCode);
        bundle.putString("id" , id);
        orderDetails.setArguments(bundle);
        orderUpdates.setArguments(bundle);




        viewPagerAdapter = new ViewOrderViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(orderDetails, "Order Details");
        viewPagerAdapter.addFragment(orderUpdates, "Order Updates");
        viewOrderViewPager.setAdapter(viewPagerAdapter);
        viewOrderTabLayout.setupWithViewPager(viewOrderViewPager);


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

                    addTextToFields(response.body());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addTextToFields(ShowOrderResponse showOrderResponse) {
        String s = showOrderResponse.getCreatedAt();
        String[] split = s.split("T");
        String date1 = split[0];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(date1, dtf);
        String month_name = dtf2.format(ld);

        date.setText(month_name);
        websiteName.setText(showOrderResponse.getStore().getName());
        orderNumberText.setText("#" + showOrderResponse.getOrderCode());
        status.setText(showOrderResponse.getOrderState().getState().getName());
        LoadingDialog.cancelLoading();
    }


}