package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Shipment.ShippingCalculator;

public class OrderFragment extends Fragment {


    MaterialButton addYourFirstOrderBtn, shippingCalculator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        ///Hooks

        addYourFirstOrderBtn = view.findViewById(R.id.addYourFirstOrderBtn);
        shippingCalculator = view.findViewById(R.id.shippingCalculator);



        addYourFirstOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new OrderListing(), null)
                        .addToBackStack(null).commit();

            }
        });

        shippingCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;

                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new ShippingCalculator(), null)
                            .addToBackStack(null).commit();

            }
        });


        return  view;
    }
}