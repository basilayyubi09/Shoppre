package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.VerifyEmailResponse;
import com.peceinfotech.shoppre.Adapters.OrdersAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderListingResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.peceinfotech.shoppre.UI.Shipment.ShippingCalculator;

public class OrderFragment extends Fragment {


    MaterialButton addYourFirstOrderBtn , verifyEmailBtn, submit, shippingCalculator, addNewOrderBtn;
    SharedPrefManager sharedPrefManager;
    RecyclerView orderRecycler;
    CardView banner , ordersCard, verifyEmailBox, virtualAddressCard, shippingCalculatorCard;


    List<Order> list;
    LinearLayout orderListing;
    OrdersAdapter ordersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        verifyEmailBox = view.findViewById(R.id.verifyEmailBox);
        verifyEmailBtn = view.findViewById(R.id.verifyEmailBtn);
        banner = view.findViewById(R.id.banner);
        ordersCard = view.findViewById(R.id.ordersCard);
        orderListing = view.findViewById(R.id.orderListing);
        submit = view.findViewById(R.id.submit);
        sevenDay = view.findViewById(R.id.sevenDay);
        orderRecycler = view.findViewById(R.id.orderRecyclerView);
        shippingCalculator = view.findViewById(R.id.shippingCalculator);
        addNewOrderBtn = view.findViewById(R.id.addNewOrderBtn);
        virtualAddressCard = view.findViewById(R.id.virtualAddressCard);
        shippingCalculatorCard = view.findViewById(R.id.shippingCalculatorCard);
        sharedPrefManager = new SharedPrefManager(getActivity());
        list = new ArrayList<>();


        LoadingDialog.showLoadingDialog(getActivity(), "");
        callMeApi(sharedPrefManager.getBearerToken());




        ///Product Card Visibility Handling





        virtualAddressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedInstanceState != null) return;

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new VertualAddress() , null)
                        .addToBackStack(null).commit();

            }
        });


        shippingCalculatorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();

            }
        });



        addNewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
                        .addToBackStack(null).commit();

            }
        });





        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LandingDialog alert = new LandingDialog();
//                alert.showDialog(getActivity());
                LoadingDialog.showLoadingDialog(getActivity(), "");
                callVerifyEmailId();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ThankYouFragment(), null)
                        .addToBackStack(null).commit();
            }
        });
        addYourFirstOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new OrderListing(), null)
                        .addToBackStack(null).commit();

            }
        });

        shippingCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new ShippingCalculator(), null)
                            .addToBackStack(null).commit();

            }
        });


        ordersAdapter = new OrdersAdapter(list, getContext());
        orderRecycler.setAdapter(ordersAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderRecycler.setLayoutManager(linearLayoutManager);

        int number = orderRecycler.getAdapter().getItemCount();
        if (number == 0) {
            banner.setVisibility(View.VISIBLE);
            ordersCard.setVisibility(View.VISIBLE);
            orderListing.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.GONE);
            ordersCard.setVisibility(View.GONE);
            orderListing.setVisibility(View.VISIBLE);
        }
        ordersAdapter.notifyDataSetChanged();

        return view;
    }

    private void callGetOrderListing() {
        Call<OrderListingResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().getOrderListing("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<OrderListingResponse>() {
            @Override
            public void onResponse(Call<OrderListingResponse> call, Response<OrderListingResponse> response) {

                if (response.code() == 200) {
                    String s = sharedPrefManager.getCreateDate();
                    String[] split = s.split("T");
                    String date = split[0];

                    int daysBetween = getDateDiffFromNow(date);
                    if (daysBetween > 7) {
                        sevenDay.setVisibility(View.GONE);
                    } else {
                        sevenDay.setVisibility(View.VISIBLE);
                    }

                    LoadingDialog.cancelLoading();
                    list = response.body().getOrders();
                    ordersAdapter = new OrdersAdapter(list, getContext());
                    orderRecycler.setAdapter(ordersAdapter);
                    int number = orderRecycler.getAdapter().getItemCount();
                    if (number == 0) {
                        banner.setVisibility(View.VISIBLE);
                        ordersCard.setVisibility(View.VISIBLE);
                        orderListing.setVisibility(View.GONE);
                    } else {
                        banner.setVisibility(View.GONE);
                        ordersCard.setVisibility(View.GONE);
                        orderListing.setVisibility(View.VISIBLE);
                    }
                    ordersAdapter.notifyDataSetChanged();
                } else {
                    callRefreshTokenApi();

                }
            }

            @Override
            public void onFailure(Call<OrderListingResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMeApi(String bearerToken) {
        Call<MeResponse> call = RetrofitClient
                .getInstance().getApi()
                .getUser("Bearer " + bearerToken);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.code() == 200) {

                    if (response.body().getIsEmailVerified() == 0) {

                        verifyEmailBox.setVisibility(View.VISIBLE);
                    } else if (response.body().getIsEmailVerified() == 1) {

                        verifyEmailBox.setVisibility(View.GONE);
                    }

                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callGetOrderListing();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    public int getDateDiffFromNow(String date) {
        int days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long diff = new Date().getTime() - sdf.parse(date).getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            days = ((int) (long) hours / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
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
                    callMeApi(sharedPrefManager.getBearerToken());
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

    private void callVerifyEmailId() {
        Call<VerifyEmailResponse> call = RetrofitClient.getInstance()
                .getApi().getVerify("Bearer " + sharedPrefManager.getBearerToken(), sharedPrefManager.getId());
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Verification link has sent to your email.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (response.code() == 403) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getError(), Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}