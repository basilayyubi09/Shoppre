package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.google.gson.JsonObject;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.ReferralHistoryResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.SubmitReferralResponse;
import com.shoppreglobal.shoppre.AccountResponse.VerifyEmailDeepLinkResponse;
import com.shoppreglobal.shoppre.Adapters.OrdersAdapter;
import com.shoppreglobal.shoppre.OrderModuleResponses.IncomingPkg;
import com.shoppreglobal.shoppre.OrderModuleResponses.Order;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderListingResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderState__1;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.ReferralRetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.Retrofit.StagingRetrofitClient;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.shoppreglobal.shoppre.UI.Orders.CancelledOrderFragment;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShippingCalculator;
import com.shoppreglobal.shoppre.UI.SignupLogin.LockAccountActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LandingDialog;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

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
    CardView banner, ordersCard, verifyEmailBox, helpAndFaq,
            virtualAddressCard, shippingCalculatorCard, sevenDay, forgetSomething;
    FrameLayout main;
    TextView bannerVirtualAddress;
    List<Order> list;
    List<IncomingPkg> list1;
    LinearLayout topCard, secondContainer;
    CardView orderListing;
    OrdersAdapter ordersAdapter;
    LinearLayout cancel;
    OrderState__1 orderState;
    int flag = 0;
    EditText referralET;
    Integer shoppreId, id;
    boolean isEmailVerified;
    String showToast;
    String scheme;
    YouTubePlayerView youTubePlayerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        verifyEmailBox = view.findViewById(R.id.verifyEmailBox);
        topCard = view.findViewById(R.id.topCard);
        referralET = view.findViewById(R.id.referralET);
        verifyEmailBtn = view.findViewById(R.id.verifyEmailBtn);
        helpAndFaq = view.findViewById(R.id.helpAndFaqCard);
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
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);


        Intent intent = getActivity().getIntent();

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            scheme = uri.getScheme();
            if (scheme.equals("https")) {
                callVerifyEmailApi();

            }

        }


        Bundle bundle = getArguments();
        if (bundle != null) {
            showToast = bundle.getString("type");
            if (showToast.equals("order")) {
                LayoutInflater inflater1 = getLayoutInflater();
                View layout = inflater1.inflate(R.layout.yellow_toast,
                        (ViewGroup) view.findViewById(R.id.toast_layout_root));
                TextView toastText = (TextView) layout.findViewById(R.id.toastText);
                toastText.setText("Payment failed try again!");
                Toast toast = new Toast(getContext());
                toast.setGravity(Gravity.TOP, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

                showToast = "";
            }
        }
        list = new ArrayList<>();
        list1 = new ArrayList<>();

        setupUI(main);

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callMeApi();
        }

        bannerVirtualAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new VertualAddress(), null)
                        .addToBackStack(null).commit();
            }
        });


        helpAndFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://www.shoppre.com/faq");
                WebViewFragment cash = new WebViewFragment();
                cash.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                        .addToBackStack(null).commit();


//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                String title = URLUtil.guessFileName(url, null, null);
//                request.setTitle(title);
//                request.setDescription("Downloading file...");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.allowScanningByMediaScanner();
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
//
//                DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//                downloadManager.enqueue(request);
//
//                Toast.makeText(getActivity(), "Downloading Start", Toast.LENGTH_SHORT).show();

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
//
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
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (referralET.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Referral code", Toast.LENGTH_LONG).show();
                } else {
                    callSubmitReferralApi();
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        orderRecycler.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void callVerifyEmailApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("is_email_verified", true);
        Call<VerifyEmailDeepLinkResponse> call = StagingRetrofitClient.getInstance4()
                .getAppApi().confirmEmail("Bearer " + sharedPrefManager.getBearerToken()
                        , jsonObject.toString());
        call.enqueue(new Callback<VerifyEmailDeepLinkResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailDeepLinkResponse> call, Response<VerifyEmailDeepLinkResponse> response) {
                if (response.code() == 200) {

                    Toast.makeText(getActivity(), "Email Verified successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    callRefreshTokenApi("email");
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailDeepLinkResponse> call, Throwable t) {

            }
        });
    }


    private void callSubmitReferralApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("referral_code", referralET.getText().toString());
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<SubmitReferralResponse> call = ReferralRetrofitClient.getInstance3().getRefferalApi()
                .submitReferralCode("Bearer " + sharedPrefManager.getBearerToken()
                        , sharedPrefManager.getId(), jsonObject.toString());
        call.enqueue(new Callback<SubmitReferralResponse>() {
            @Override
            public void onResponse(Call<SubmitReferralResponse> call, Response<SubmitReferralResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getActivity(), "Referral code applied successfully", Toast.LENGTH_SHORT).show();
                        sevenDay.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                        LoadingDialog.cancelLoading();
                    }
                } else if (response.code() == 401) {
                    callRefreshTokenApi("submit");
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitReferralResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callGetOrderListing() {
        Call<OrderListingResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().getOrderListing("Bearer " + sharedPrefManager.getBearerToken());


        call.enqueue(new Callback<OrderListingResponse>() {
            @Override
            public void onResponse(Call<OrderListingResponse> call, Response<OrderListingResponse> response) {

                if (response.code() == 201) {

                    list = response.body().getOrders();
                    list1 = response.body().getIncomingPkgs();
                    ordersAdapter = new OrdersAdapter(list, list1, getContext());
                    orderRecycler.setAdapter(ordersAdapter);
                    int number = orderRecycler.getAdapter().getItemCount();
                    if (number == 0) {
                        banner.setVisibility(View.VISIBLE);
                        ordersCard.setVisibility(View.VISIBLE);

                        getLifecycle().addObserver(youTubePlayerView);
                        secondContainer.setVisibility(View.VISIBLE);
                        orderListing.setVisibility(View.GONE);
                        topCard.setVisibility(View.GONE);
                    } else {
                        banner.setVisibility(View.GONE);
                        ordersCard.setVisibility(View.GONE);
                        secondContainer.setVisibility(View.GONE);
                        orderListing.setVisibility(View.VISIBLE);
                        topCard.setVisibility(View.VISIBLE);
                    }
                    ordersAdapter.notifyDataSetChanged();

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


                    callReferralApi();
//                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi("listing");

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
    private void callReferralApi() {
        Call<ReferralHistoryResponse> call = ReferralRetrofitClient
                .getInstance3()
                .getRefferalApi().getReferralHistory("Bearer " + sharedPrefManager.getBearerToken(), sharedPrefManager.getFirstName());
        call.enqueue(new Callback<ReferralHistoryResponse>() {
            @Override
            public void onResponse(Call<ReferralHistoryResponse> call, Response<ReferralHistoryResponse> response) {
                if (response.code() == 200) {

                    if (response.body().getUser().getReferredBy() != null) {
                        sevenDay.setVisibility(View.GONE);
                    } else {
                        String s = sharedPrefManager.getCreateDate();
                        String[] split = s.split("T");
                        String date = split[0];

                        int daysBetween = getDateDiffFromNow(date);
                        if (daysBetween > 7) {

                            sevenDay.setVisibility(View.GONE);
                        } else {
                            sevenDay.setVisibility(View.VISIBLE);
                        }

                    }

                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi("referral");
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<ReferralHistoryResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    private void callMeApi() {

        Call<MeResponse> call = RetrofitClient
                .getInstance().getApi()
                .getUser("Bearer " + sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getIsEmailVerified() != null) {
                        if (response.body().getIsEmailVerified() == 0) {
                            isEmailVerified = false;
                            verifyEmailBox.setVisibility(View.VISIBLE);
                        } else if (response.body().getIsEmailVerified() == 1) {
                            isEmailVerified = true;
                            verifyEmailBox.setVisibility(View.GONE);
                        }
                    } else {
                        isEmailVerified = false;
                        verifyEmailBox.setVisibility(View.VISIBLE);
                    }

                    String s = sharedPrefManager.getCreateDate();
                    String[] split = s.split("T");
                    String date = split[0];

                    int dayGap = getDateDiffFromNow(date);
                    if (dayGap > 7) {
                        if (!isEmailVerified) {
                            LoadingDialog.cancelLoading();
                            Intent intent = new Intent(getActivity(), LockAccountActivity.class);
                            intent.putExtra("email", sharedPrefManager.getEmail());
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                    callGetOrderListing();
                } else if (response.code() == 401) {
                    callRefreshTokenApi("me");
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

    private void callRefreshTokenApi(String where) {
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
                    if (where.equals("email")) {
                        callVerifyEmailApi();
                    } else if (where.equals("me")) {
                        callMeApi();
                    } else if (where.equals("submit")) {
                        callSubmitReferralApi();
                    } else if (where.equals("listing")) {
                        callGetOrderListing();
                    } else if (where.equals("referral")) {
                        callReferralApi();
                    }

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