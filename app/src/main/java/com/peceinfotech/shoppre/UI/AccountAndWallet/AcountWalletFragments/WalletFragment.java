package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.AccountResponse.WalletTransactionResponse;
import com.peceinfotech.shoppre.Adapters.WalletTransactionAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClientWallet;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    List<WalletTransactionResponse> list;
    String[] title = {"All", "My Cash", "Rewards"};
    MaterialAutoCompleteTextView allSpinner;
    ArrayAdapter arrayAdapter;
    WalletTransactionAdapter walletAdapter;
    LinearLayoutManager linearLayoutManager;
    LinearLayout showMoreContent;
    RecyclerView recyclerView;
    RelativeLayout readMoreLayout;
    SharedPrefManager sharedPrefManager;
    String bearerToken;
    TextView myWalletMyCash, myWalletMyRewards;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        allSpinner = view.findViewById(R.id.allSpinner);
        myWalletMyCash = view.findViewById(R.id.myWalletMyCash);
        myWalletMyRewards = view.findViewById(R.id.myWalletMyRewards);
        recyclerView = view.findViewById(R.id.walletTransactionRecycle);
        readMoreLayout = view.findViewById(R.id.readMoreLayout);
        showMoreContent = view.findViewById(R.id.showMoreContent);


        //Initialize
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        //shared pref manager
        sharedPrefManager = new SharedPrefManager(getActivity());
        bearerToken = sharedPrefManager.getBearerToken();

        recyclerView.setLayoutManager(linearLayoutManager);
        walletAdapter = new WalletTransactionAdapter(getContext(), list);
        recyclerView.setAdapter(walletAdapter);


        if(!CheckNetwork.isInternetAvailable(getActivity()) ) //if connection not available
        {

            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , "No Internte Connection",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else
        {
            //Wallet Transaction api
            LoadingDialog.showLoadingDialog(getActivity(), "");
            callApi();
        }


        int number = recyclerView.getAdapter().getItemCount();
        if (number == 0) {
            showMoreContent.setVisibility(View.GONE);

        } else {
            showMoreContent.setVisibility(View.VISIBLE);

        }

        walletAdapter.notifyDataSetChanged();

            return view;
}


    private void callApi() {

        int id = sharedPrefManager.getId();
        Call<WalletTransactionResponse> call = RetrofitClientWallet.getInstanceWallet()
                .getAppApi().getDetails(id,"0" , "20" , "Bearer "+bearerToken);
        call.enqueue(new Callback<WalletTransactionResponse>() {
            @Override
            public void onResponse(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                if (response.isSuccessful()) {

                    WalletTransactionResponse walletTransactionResponse = response.body();
//                    int date = walletTransactionResponse.getUser().getId();
//                    Toast.makeText(getActivity(), String.valueOf(date), Toast.LENGTH_SHORT).show();
                    walletAdapter = new WalletTransactionAdapter(getContext() , list);
                    recyclerView.setAdapter(walletAdapter);
                    walletAdapter.notifyDataSetChanged();
                    myWalletMyCash.setText("₹ "+walletTransactionResponse.getUser().getWalletAmount().toString());
                    myWalletMyRewards.setText("₹ "+walletTransactionResponse.getUser().getPsWalletAmount().toString());
                    LoadingDialog.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , t.toString() , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, title);
        allSpinner.setAdapter(arrayAdapter);


    }
}