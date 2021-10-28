package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.peceinfotech.shoppre.Adapters.AccountAndWallet.ReferralAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.ReferralRetrofitClient;
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
    LinearLayout emptyReferralLayout  , showMoreLayout;
    SharedPrefManager sharedPrefManager;
    String bearerToken;
    TextView referralCodeText;
    MaterialButton copyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referral, container, false);

        emptyReferralLayout = view.findViewById(R.id.emptyReferralLayout);
        haveARefCard = view.findViewById(R.id.haveARefCard);
        showMoreLayout = view.findViewById(R.id.showMoreLayout);
        referralRecycle = view.findViewById(R.id.referralRecycler);
        copyBtn = view.findViewById(R.id.copyBtn);
        referralCodeText = view.findViewById(R.id.referralCodeText);

        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        //shared pref manager
        sharedPrefManager = new SharedPrefManager(getActivity());
        bearerToken = sharedPrefManager.getBearerToken();
        referralAdapter = new ReferralAdapter(getContext() , list);
        referralRecycle.setLayoutManager(linearLayoutManager);
        referralRecycle.setAdapter(referralAdapter);



        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data =(ClipData) ClipData.newPlainText("token" ,referralCodeText.getText() );
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getActivity(), "Referral Code Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        if(!CheckNetwork.isInternetAvailable(getActivity()) ) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , "No Internte Connection",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else
        {
            //Wallet Transaction api
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callReferralApi();
        }

        int number = referralRecycle.getAdapter().getItemCount();
        if (number == 0)
        {
            haveARefCard.setVisibility(View.VISIBLE);
            emptyReferralLayout.setVisibility(View.VISIBLE);
            referralRecycle.setVisibility(View.GONE);
            showMoreLayout.setVisibility(View.GONE);
        }
        else {
            haveARefCard.setVisibility(View.GONE);
            emptyReferralLayout.setVisibility(View.GONE);

        }



        referralAdapter.notifyDataSetChanged();
        return  view;
    }
    private void callReferralApi() {
        Call<ReferralHistoryResponse> call = ReferralRetrofitClient
                .getInstance3()
                .getRefferalApi().getReferralHistory("Bearer "+bearerToken);
        call.enqueue(new Callback<ReferralHistoryResponse>() {
            @Override
            public void onResponse(Call<ReferralHistoryResponse> call, Response<ReferralHistoryResponse> response) {
                if (response.code()==200){
                    String code = response.body().getUser().getReferralCode();
                    referralCodeText.setText(code);
                    list = response.body().getReferralHistory();
                    referralAdapter = new ReferralAdapter(getActivity() , list);
                    referralRecycle.setAdapter(referralAdapter);
                    referralAdapter.notifyDataSetChanged();
                    LoadingDialog.cancelLoading();

                }
                else if(response.code()==401){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , response.body().getErrorDescription() , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) ,response.message() , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<ReferralHistoryResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , t.toString() , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}