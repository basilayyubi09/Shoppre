package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.CreateShipRequestSummaryFragment;


public class CreateShippingPrefrenceFragment extends Fragment {

    MaterialButton shippingPrefProceedBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_shipping_prefrence, container, false);

        shippingPrefProceedBtn = view.findViewById(R.id.shippingPrefProceedBtn);

        shippingPrefProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CreateShipRequestSummaryFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }
}