package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.Adapters.OrderAdapter.ViewOrderViewPagerAdapter;
import com.peceinfotech.shoppre.R;


public class ViewOrderPersonalShop extends Fragment {

    TabLayout viewOrderTabLayout;
    ViewPager2 viewOrderViewPager;

    ViewOrderViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order_personal_shop, container, false);

        viewOrderTabLayout = view.findViewById(R.id.viewOrderTabLayout);
        viewOrderViewPager = view.findViewById(R.id.viewOrderViewPager);


//        viewOrderViewPager = new ViewOrderViewPagerAdapter(fragmentManager, getLifecycle());


        viewOrderTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}