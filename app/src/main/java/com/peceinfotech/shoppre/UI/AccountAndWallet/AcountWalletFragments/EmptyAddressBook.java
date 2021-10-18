package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;


public class EmptyAddressBook extends Fragment {

    MaterialButton billingAddAddressBtn , deliveryAddAddressBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_address_book, container, false);

        billingAddAddressBtn = view.findViewById(R.id.billingAddAdrsBtn);
        deliveryAddAddressBtn = view.findViewById(R.id.deliveryAddAdrsbtn);

        billingAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new AddAddress() , null)
                        .addToBackStack(null).commit();

            }
        });
        
        deliveryAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new AddAddress() , null)
                        .addToBackStack(null).commit();


            }
        });

        return view;
    }
}