package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.Adapters.ShipmentAdapters.ShipmentLandingViewPager;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentDetails;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.ShipmentTabLayout.ShipmentUpdates;

public class ShipmentLanding extends Fragment {

    ViewPager viewPager;
    ShipmentLandingViewPager viewPagerAdapter;
    TabLayout shipmentTabLayout;
    ShipmentDetails shipmentDetails = new ShipmentDetails();
    ShipmentUpdates shipmentUpdates = new ShipmentUpdates();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_landing, container, false);

        viewPager = view.findViewById(R.id.viewPagerShipment);
        shipmentTabLayout = view.findViewById(R.id.shipmentTabLayout);

        viewPagerAdapter = new ShipmentLandingViewPager(getChildFragmentManager());

        viewPagerAdapter.addFragments(shipmentDetails, "Shipment Details");
        viewPagerAdapter.addFragments(shipmentUpdates, "Shipment Updates");
        viewPager.setAdapter(viewPagerAdapter);
        shipmentTabLayout.setupWithViewPager(viewPager);

        return view;
    }
}