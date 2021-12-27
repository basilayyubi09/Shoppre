package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.OrdersAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderListingResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderState__1;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.shoppreglobal.shoppre.UI.Orders.CancelledOrderFragment;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShippingCalculator;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LandingDialog;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {


    MaterialButton addYourFirstOrderBtn, verifyEmailBtn, submit, shippingCalculator, addNewOrderBtn, forgotContinueBtn;
    SharedPrefManager sharedPrefManager;
    RecyclerView orderRecycler;
    CardView banner, ordersCard, verifyEmailBox, virtualAddressCard, shippingCalculatorCard, sevenDay, forgetSomething;
    FrameLayout main;
    TextView bannerVirtualAddress;


    List<Order> list;
    LinearLayout orderListing, secondContainer;
    OrdersAdapter ordersAdapter;
    LinearLayout cancel;
    OrderState__1 orderState;
    int flag = 0;
    Integer shoppreId, id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        verifyEmailBox = view.findViewById(R.id.verifyEmailBox);
        verifyEmailBtn = view.findViewById(R.id.verifyEmailBtn);
        banner = view.findViewById(R.id.banner);
        main = view.findViewById(R.id.main);
        forgetSomething = view.findViewById(R.id.forgetSomething);
        ordersCard = view.findViewById(R.id.ordersCard);
        orderListing = view.findViewById(R.id.orderListing);
        submit = view.findViewById(R.id.submit);
        secondContainer = view.findViewById(R.id.secondContainer);
        forgotContinueBtn = view.findViewById(R.id.forgotContinueBtn);
        sevenDay = view.findViewById(R.id.sevenDay);
        orderRecycler = view.findViewById(R.id.orderRecyclerView);
        shippingCalculator = view.findViewById(R.id.shippingCalculator);
        addNewOrderBtn = view.findViewById(R.id.addNewOrderBtn);
        virtualAddressCard = view.findViewById(R.id.virtualCardAddress);
        cancel = view.findViewById(R.id.cancel);
        shippingCalculatorCard = view.findViewById(R.id.shippingCardAddress);
        bannerVirtualAddress = view.findViewById(R.id.bannerVirtualAddress);



        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        FirebaseApp.initializeApp(getActivity());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String scheme = uri.getScheme();
            if (scheme.equals("https")) {
                String token = uri.getQueryParameter("token");
                Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
            }
        }

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        list = new ArrayList<>();

        setupUI(main);

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callMeApi(sharedPrefManager.getBearerToken());
        }

        //check if orderCode Value stored in shared pref
        //According to this value will show and hide forget something block
//        if (sharedPrefManager.getOrderCode().equals("")) {
//            forgetSomething.setVisibility(View.GONE);
//        } else {
//            forgetSomething.setVisibility(View.VISIBLE);
//        }

        bannerVirtualAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new VertualAddress(), null)
                        .addToBackStack(null).commit();
            }
        });


        virtualAddressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedInstanceState != null) return;

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new VertualAddress(), null)
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

        shippingCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();
            }
        });

        addNewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (sharedPrefManager.getOrderCode().equals("")){
                if (flag == 1) {
                    LandingDialog landingDialog = new LandingDialog();
                    landingDialog.showDialog(getActivity(), id, shoppreId, orderState);
                } else {

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
                            .addToBackStack(null).commit();
                }

//                }
//                else {
//                    LandingDialog landingDialog = new LandingDialog();
//                    landingDialog.showDialog(getActivity());
//                }


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CancelledOrderFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewProfile(), null)
                        .addToBackStack(null).commit();
            }
        });

        forgotContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                EmptyCart emptyCart = new EmptyCart();

                bundle.putInt("id", shoppreId);
                bundle.putString("type", "exist");
                emptyCart.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, emptyCart, null)
                        .addToBackStack(null).commit();
            }
        });

        addYourFirstOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 1) {
                    //check if order is pending or not if pending then send id to Landing dialog
                    LandingDialog landingDialog = new LandingDialog();
                    landingDialog.showDialog(getActivity(), id, shoppreId, orderState);
                } else {

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new SelfShopper(), null)
                            .addToBackStack(null).commit();
                }

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

        Log.i("TAG", "callGetOrderListing:bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<OrderListingResponse>() {
            @Override
            public void onResponse(Call<OrderListingResponse> call, Response<OrderListingResponse> response) {

                if (response.code() == 201) {

                    list = response.body().getOrders();
                    ordersAdapter = new OrdersAdapter(list, getContext());
                    orderRecycler.setAdapter(ordersAdapter);
                    int number = orderRecycler.getAdapter().getItemCount();
                    if (number == 0) {
                        banner.setVisibility(View.VISIBLE);
                        ordersCard.setVisibility(View.VISIBLE);
                        secondContainer.setVisibility(View.VISIBLE);
                        orderListing.setVisibility(View.GONE);
                    } else {
                        banner.setVisibility(View.GONE);
                        ordersCard.setVisibility(View.GONE);
                        secondContainer.setVisibility(View.GONE);
                        orderListing.setVisibility(View.VISIBLE);
                    }
                    ordersAdapter.notifyDataSetChanged();
                    String s = sharedPrefManager.getCreateDate();
                    String[] split = s.split("T");
                    String date = split[0];

                    int daysBetween = getDateDiffFromNow(date);
                    if (daysBetween > 7) {
                        sevenDay.setVisibility(View.GONE);
                    } else {
                        sevenDay.setVisibility(View.VISIBLE);
                    }

                    if (response.body().getPendingOrders().size() > 0) {
                        id = response.body().getPendingOrders().get(0).getId();
                        shoppreId = response.body().getPendingOrders().get(0).getShopperOrderId();
                        orderState = response.body().getPendingOrders().get(0).getOrderState();
                    }

                    if (shoppreId != null) {
                        addNewOrderBtn.setText("Add More Orders");
                        addNewOrderBtn.setLetterSpacing((float) 0.03);
                        forgetSomething.setVisibility(View.VISIBLE);
                        flag = 1;
                    } else {
                        forgetSomething.setVisibility(View.GONE);
                        flag = 0;
                    }


//                    callShopperOrdersApi();
                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderListingResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//

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


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
//    private void callCreateShopper() {
//        Call<ShopperOrdersResponse> call = RetrofitClient3
//                .getInstance3()
//                .getAppApi().shopperOrder("Bearer " + sharedPrefManager.getBearerToken());
//        call.enqueue(new Callback<ShopperOrdersResponse>() {
//            @Override
//            public void onResponse(Call<ShopperOrdersResponse> call, Response<ShopperOrdersResponse> response) {
//                if (response.code() == 200) {
//                    LoadingDialog.cancelLoading();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", response.body().getShopperOrder().getId());
//                    bundle.putString("type", "new");
//                    EmptyCart emptyCart = new EmptyCart();
//                    emptyCart.setArguments(bundle);
//                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, emptyCart, null)
//                            .commit();
//                } else if (response.code() == 401) {
////                    callRefreshTokenApia();
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShopperOrdersResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void callRefreshTokenApia() {
//        Call<RefreshTokenResponse> call = RetrofitClient
//                .getInstance().getApi()
//                .getRefreshToken(sharedPrefManager.getRefreshToken());
//        call.enqueue(new Callback<RefreshTokenResponse>() {
//            @Override
//            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
//                if (response.code() == 200) {
//
//                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
//                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
////                    callCreateShopper();
//
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        });
//
//    }
//
//    private void callShopperOrdersApi() {
//        Call<ShopperOrdersResponse> call =
//                RetrofitClient3
//                        .getInstance3()
//                        .getAppApi().shopperOrder("Bearer " + sharedPrefManager.getBearerToken());
//        call.enqueue(new Callback<ShopperOrdersResponse>() {
//            @Override
//            public void onResponse(Call<ShopperOrdersResponse> call, Response<ShopperOrdersResponse> response) {
//                if (response.code() == 200) {
////                    sharedPrefManager.storeOrderId(response.body().getShopperOrder().getId());
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
//            public void onFailure(Call<ShopperOrdersResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}