package com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments;

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
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.ReferralHistory;
import com.shoppreglobal.shoppre.AccountResponse.ReferralHistoryResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.SubmitReferralResponse;
import com.shoppreglobal.shoppre.Adapters.AccountAndWallet.ReferralAdapter;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.ReferralRetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderFragments.WebViewFragment;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
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

public class ReferralFragment extends Fragment {

    List<ReferralHistory> list;
    ReferralAdapter referralAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView referralRecycle;
    MaterialCardView haveARefCard;
    LinearLayout emptyReferralLayout, referralCodeLayout, showMoreLayout, readMoreLayout, main, shareBtn;
    SharedPrefManager sharedPrefManager;
    String bearerToken;
    TextView referralCodeText, submit;
    MaterialButton copyBtn;
    TextView refer, share, use, faq;
    EditText have;
    boolean isReferred = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);


        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("account");


        emptyReferralLayout = view.findViewById(R.id.emptyReferralLayout);
        haveARefCard = view.findViewById(R.id.haveARefCard);
        submit = view.findViewById(R.id.submit);
        referralCodeLayout = view.findViewById(R.id.referralCodeLayout);
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSubmitReferralApi();
            }
        });
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
                shareIntent.setType("text/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey, I’m inviting you to use Shoppre.com, the fastest and affordable way to shop and ship from India. Get INR 350 by clicking on the link below or using the referral code during sign-up! Code -" + referralCodeText.getText().toString() + ". https://login.shoppre.com/signup?referral_code=" + referralCodeText.getText().toString());
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


    private void callSubmitReferralApi() {
        LoadingDialog.showLoadingDialog(getActivity() , "");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("referral_code", have.getText().toString());
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
                        referralCodeLayout.setVisibility(View.GONE);
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
//        Toast.makeText(getActivity(), days, Toast.LENGTH_SHORT).show();
        return days;
    }

    private void callReferralApi() {
        Call<ReferralHistoryResponse> call = ReferralRetrofitClient
                .getInstance3()
                .getRefferalApi().getReferralHistory("Bearer " + bearerToken, sharedPrefManager.getFirstName());
        call.enqueue(new Callback<ReferralHistoryResponse>() {
            @Override
            public void onResponse(Call<ReferralHistoryResponse> call, Response<ReferralHistoryResponse> response) {
                if (response.code() == 200) {
                    String code = response.body().getUser().getReferralCode();
                    if (!code.equals("")) {
                        referralCodeText.setText(code);
                    }
                    if (response.body().getUser().getReferredBy() != null) {
                        referralCodeLayout.setVisibility(View.GONE);
                    } else {
                        String s = sharedPrefManager.getCreateDate();
                        String[] split = s.split("T");
                        String date = split[0];

                        int daysBetween = getDateDiffFromNow(date);

                        if (daysBetween > 7) {
                            referralCodeLayout.setVisibility(View.GONE);
                        } else {

                            referralCodeLayout.setVisibility(View.VISIBLE);


                        }

                    }

                    list = response.body().getReferralHistory();
                    referralAdapter = new ReferralAdapter(getActivity(), list);
                    referralRecycle.setAdapter(referralAdapter);
                    referralAdapter.notifyDataSetChanged();
                    LoadingDialog.cancelLoading();

                } else if (response.code() == 401) {
                    callRefreshTokenApi("");
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

    private void callRefreshTokenApi(String submit) {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {

                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    if (submit.equals("submit")) {
                        callSubmitReferralApi();
                    } else {
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
}