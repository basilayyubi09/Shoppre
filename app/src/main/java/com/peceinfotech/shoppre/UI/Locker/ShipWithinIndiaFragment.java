package com.peceinfotech.shoppre.UI.Locker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.peceinfotech.shoppre.OrderModuleResponses.Order;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;


public class ShipWithinIndiaFragment extends Fragment {

    LinearLayout addMoreShipmentAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ship_within_india, container, false);

        addMoreShipmentAddress = view.findViewById(R.id.addMoreShipmentAddress);

        addMoreShipmentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new AddAddress(), null)
                        .addToBackStack(null).commit();

            }
        });


        return view;
    }
}