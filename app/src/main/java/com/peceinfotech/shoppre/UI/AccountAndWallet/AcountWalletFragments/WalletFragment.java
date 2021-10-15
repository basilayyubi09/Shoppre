package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.Adapters.WalletTransactionAdapter;
import com.peceinfotech.shoppre.Models.WalletTransactionDummyModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AccountWalletActivity;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment {

    List<WalletTransactionDummyModel> list;
    String[] title = { "All" , "My Cash" ,"Rewards" };
    MaterialAutoCompleteTextView allSpinner;
    ArrayAdapter arrayAdapter;
    WalletTransactionAdapter walletAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RelativeLayout readMoreLayout;

    @Override
    public void onResume() {
        super.onResume();

         arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, title);
        allSpinner.setAdapter(arrayAdapter);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        allSpinner = view.findViewById(R.id.allSpinner);
        recyclerView = view.findViewById(R.id.walletTransactionRecycle);
        readMoreLayout = view.findViewById(R.id.readMoreLayout);

        //Initialize
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());




        list.add(new WalletTransactionDummyModel(R.mipmap.refferal_reward
                , "05 Jan 2021"
                , "Reward Bonus Expired"
                ,"","- \u20B9 250"));
        list.add(new WalletTransactionDummyModel(R.mipmap.refferal_reward
                , "02 Dec 2020"
                , "Shipment ID #8473"
                ,"Referral Reward Debited", "- \u20B9 250"));
        list.add(new WalletTransactionDummyModel(R.mipmap.refferal_reward
                , "21 Dec 2020"
                , "Referral Reward"
                ,"","+ \u20B9 750"));
        list.add(new WalletTransactionDummyModel(R.mipmap.rupees_icon
                , "26 Nov 2020"
                , "Shipment ID #7122"
                ,"Excess amount Credited to Wallet" ,"+ \u20B9 100"));




        walletAdapter = new WalletTransactionAdapter(getContext() , list);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(walletAdapter);


        return  view;
    }
}