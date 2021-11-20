package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.peceinfotech.shoppre.AccountResponse.ReferralHistory;
import com.peceinfotech.shoppre.AccountResponse.ReferralHistoryResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.Adapters.AccountAndWallet.ReferralAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.ReferralRetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralFragment extends Fragment {

    List<ReferralHistory> list;
    ReferralAdapter referralAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView referralRecycle;
    MaterialCardView haveARefCard;
    LinearLayout emptyReferralLayout, showMoreLayout, readMoreLayout, main;
    SharedPrefManager sharedPrefManager;
    String bearerToken;
    TextView referralCodeText;
    MaterialButton copyBtn, shareBtn;
    TextView refer, share, use, faq;
    EditText have;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);


        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("account");


        emptyReferralLayout = view.findViewById(R.id.emptyReferralLayout);
        haveARefCard = view.findViewById(R.id.haveARefCard);
        showMoreLayout = view.findViewById(R.id.showMoreLayout);
        referralRecycle = view.findViewById(R.id.referralRecycler);
        copyBtn = view.findViewById(R.id.copyBtn);
        referralCodeText = view.findViewById(R.id.referralCodeText);
        readMoreLayout = view.findViewById(R.id.readMoreLayout);
        refer = view.findViewById(R.id.refer);
        share = view.findViewById(R.id.share);
        have = view.findViewById(R.id.have);
        use = view.findViewById(R.id.use);
        faq = view.findViewById(R.id.faq);
        shareBtn = view.findViewById(R.id.shareBtn);
        main = view.findViewById(R.id.main);
        setupUI(main);
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        //shared pref manager

        bearerToken = sharedPrefManager.getBearerToken();
        referralAdapter = new ReferralAdapter(getContext(), list);
        referralRecycle.setLayoutManager(linearLayoutManager);
        referralRecycle.setAdapter(referralAdapter);


        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("token", referralCodeText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getActivity(), "Referral Code Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internte Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            //Wallet Transaction api
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callReferralApi();
        }

        int number = referralRecycle.getAdapter().getItemCount();
        if (number == 0) {
            haveARefCard.setVisibility(View.VISIBLE);
            emptyReferralLayout.setVisibility(View.VISIBLE);
            referralRecycle.setVisibility(View.GONE);
            showMoreLayout.setVisibility(View.GONE);
        } else {
            haveARefCard.setVisibility(View.GONE);
            emptyReferralLayout.setVisibility(View.GONE);

        }


        referralAdapter.notifyDataSetChanged();

        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment web = new WebViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000393245-what-is-the-shoppre-s-refer-and-earn-programme-");
                web.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, web, null)
                        .addToBackStack(null).commit();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plane");
                shareIntent.putExtra(Intent.EXTRA_TEXT, referralCodeText.getText().toString());
                getActivity().startActivity(shareIntent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment web = new WebViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000393247-where-can-i-share-my-referral-code-");
                web.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, web, null)
                        .addToBackStack(null).commit();
            }
        });


        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment web = new WebViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/articles/81000393250-where-can-i-use-the-referral-reward-");
                web.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, web, null)
                        .addToBackStack(null).commit();
            }
        });

        readMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment web = new WebViewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://go-shoppre.freshdesk.com/support/solutions/folders/81000288497");
                web.setArguments(bundle);
                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, web, null)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }

    private void callReferralApi() {
        Call<ReferralHistoryResponse> call = ReferralRetrofitClient
                .getInstance3()
                .getRefferalApi().getReferralHistory("Bearer " + bearerToken);
        call.enqueue(new Callback<ReferralHistoryResponse>() {
            @Override
            public void onResponse(Call<ReferralHistoryResponse> call, Response<ReferralHistoryResponse> response) {
                if (response.code() == 200) {
                    String code = response.body().getUser().getReferralCode();
                    referralCodeText.setText(code);
                    list = response.body().getReferralHistory();
                    referralAdapter = new ReferralAdapter(getActivity(), list);
                    referralRecycle.setAdapter(referralAdapter);
                    referralAdapter.notifyDataSetChanged();
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi();
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

    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callReferralApi();

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
}