package com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.WalletTransaction;
import com.shoppreglobal.shoppre.AccountResponse.WalletTransactionResponse;
import com.shoppreglobal.shoppre.Adapters.WalletTransactionAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClientWallet;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    List<WalletTransaction> list, list1;
    String[] title = {"  All  ", "  My Cash  ", "  My Rewards  "};
    Spinner allSpinner;
    ArrayAdapter arrayAdapter;
    WalletTransactionAdapter walletAdapter;
    LinearLayoutManager linearLayoutManager;
    LinearLayout showMoreContent;
    RecyclerView recyclerView;
    RelativeLayout readMoreLayout;
    SharedPrefManager sharedPrefManager;
    String bearerToken;
    TextView myWalletMyCash, myWalletMyRewards, emptyWalletText, myCash, myRewards, howCanI;
    ImageView emptyWalletImage;
    Integer offSet, limit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        offSet = 0;
        limit = 5;
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("account");

        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.accountMenu).setChecked(true);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        allSpinner = view.findViewById(R.id.allSpinner);
        myWalletMyCash = view.findViewById(R.id.myWalletMyCash);
        myCash = view.findViewById(R.id.myCash);
        myRewards = view.findViewById(R.id.myRewards);
        howCanI = view.findViewById(R.id.howCanI);
        myWalletMyRewards = view.findViewById(R.id.myWalletMyRewards);
        recyclerView = view.findViewById(R.id.walletTransactionRecycle);
        readMoreLayout = view.findViewById(R.id.readMoreLayout);
        showMoreContent = view.findViewById(R.id.showMoreContent);
        emptyWalletImage = view.findViewById(R.id.emptyWalletImage);
        emptyWalletText = view.findViewById(R.id.emptyWalletText);


        //Initialize
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        //shared pref manager

        bearerToken = sharedPrefManager.getBearerToken();

        recyclerView.setLayoutManager(linearLayoutManager);

        walletAdapter = new WalletTransactionAdapter(getContext(), list);
        recyclerView.setAdapter(walletAdapter);

        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            //Wallet Transaction api
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callApi();
        }

        showHideContents();


        myCash.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000392946-what-is-my-cash-");
            WebViewFragment cash = new WebViewFragment();
            cash.setArguments(bundle);
            if (savedInstanceState != null) return;
            OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                    .addToBackStack(null).commit();

        });

        showMoreContent.setOnClickListener(v -> {
            if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
            {

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internet Connection", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                //Wallet Transaction api
                LoadingDialog.showLoadingDialog(getActivity(), "");
                callApi();
            }
        });
        myRewards.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000392947-what-is-my-rewards-");
            WebViewFragment cash = new WebViewFragment();
            cash.setArguments(bundle);
            if (savedInstanceState != null) return;
            OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                    .addToBackStack(null).commit();
        });

        howCanI.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000392948-where-can-i-use-my-wallet-money-");
            WebViewFragment cash = new WebViewFragment();
            cash.setArguments(bundle);
            if (savedInstanceState != null) return;
            OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                    .addToBackStack(null).commit();
        });

        readMoreLayout.setOnClickListener(view14 -> {

            Bundle bundle = new Bundle();
            bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/folders/81000288275");
            WebViewFragment cash = new WebViewFragment();
            cash.setArguments(bundle);
            if (savedInstanceState != null) return;
            OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, cash, null)
                    .addToBackStack(null).commit();

        });

        walletAdapter.notifyDataSetChanged();

        return view;
    }

    private void showHideContents() {

    }


    private void callApi() {

        int id = sharedPrefManager.getId();
        Call<WalletTransactionResponse> call = RetrofitClientWallet.getInstanceWallet()
                .getAppApi().getDetails(id, offSet, limit, "Bearer " + bearerToken);
        call.enqueue(new Callback<WalletTransactionResponse>() {
            @Override
            public void onResponse(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                if (response.isSuccessful()) {


                    list1 = response.body().getWalletTransactions();
                    for (int i = 0; i < list1.size(); i++) {

                        list.add(list1.get(i));


                    }
                    walletAdapter = new WalletTransactionAdapter(getContext(), list);
                    recyclerView.setAdapter(walletAdapter);
                    int number = recyclerView.getAdapter().getItemCount();
                    if (number == 0) {

                        emptyWalletImage.setVisibility(View.VISIBLE);
                        emptyWalletText.setVisibility(View.VISIBLE);

                    } else {

                        emptyWalletImage.setVisibility(View.GONE);
                        emptyWalletText.setVisibility(View.GONE);

                    }


                    if (list1.size() == 0 || list1.size() < 5) {
                        showMoreContent.setVisibility(View.GONE);
                    } else {
                        showMoreContent.setVisibility(View.VISIBLE);
                    }


                    list1.clear();
                    offSet = limit;
                    limit = limit + 5;
//                    int date = walletTransactionResponse.getUser().getId();
//                    Toast.makeText(getActivity(), String.valueOf(date), Toast.LENGTH_SHORT).show();

                    recyclerView.setVisibility(View.VISIBLE);

                    walletAdapter.notifyDataSetChanged();

                    int myReward = response.body().getUser().getParcelWalletAmount() + response.body().getUser().getPsWalletAmount()
                            + response.body().getUser().getCourierWalletAmount();

                    myWalletMyCash.setText("₹ " + String.valueOf(myReward));
                    myWalletMyRewards.setText("₹ " + response.body().getUser().getMarketingWalletAmount().toString());
                    LoadingDialog.cancelLoading();


                    walletAdapter.notifyDataSetChanged();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                } else {

                    emptyWalletImage.setVisibility(View.VISIBLE);
                    emptyWalletText.setVisibility(View.VISIBLE);
                    showMoreContent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        arrayAdapter = new ArrayAdapter(getContext(), R.layout.wallet_fragment_spinner_text, title);
        allSpinner.setAdapter(arrayAdapter);

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