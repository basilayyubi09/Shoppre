package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.R;


public class AddressBook extends Fragment {


    MaterialAutoCompleteTextView allAddressSpinner;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);

        allAddressSpinner = view.findViewById(R.id.allAddressSpinner);

        return view;
    }


}