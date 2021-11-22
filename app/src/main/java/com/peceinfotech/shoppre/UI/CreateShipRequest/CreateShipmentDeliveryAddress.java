package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.R;

public class CreateShipmentDeliveryAddress extends Fragment {

    RecyclerView createShipmentDeliveryAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_shipment_delivery_address, container, false);

        createShipmentDeliveryAddress = view.findViewById(R.id.createShipmentDeliveryAddress);



        return view;
    }
}