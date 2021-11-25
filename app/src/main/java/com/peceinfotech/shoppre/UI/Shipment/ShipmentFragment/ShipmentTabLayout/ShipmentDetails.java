package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentDetailsAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.ShipmentModelResponse.ShipmentDetailsResponse;

import java.util.ArrayList;
import java.util.List;


public class ShipmentDetails extends Fragment {

    RecyclerView shipmentDetailsRecycler;
    List<ShipmentDetailsResponse> list= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_details, container, false);

        shipmentDetailsRecycler = view.findViewById(R.id.shipmentDetailsRecycler);

        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.5F));
        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.6F));
        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 1.7F));
        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 2.5F));
        list.add(new ShipmentDetailsResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#8473", "25 Dec 2021", 3.2F));

        ShipmentDetailsAdapter shipmentDetailsAdapter = new ShipmentDetailsAdapter(list, getActivity());
        shipmentDetailsRecycler.setAdapter(shipmentDetailsAdapter);

        return view;
    }
}