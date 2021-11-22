package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.R;


public class CreateShippingPrefrenceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_shipping_prefrence, container, false);


        return view;
    }
}