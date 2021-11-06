package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.Adapters.OrderAdapter.ViewOrderViewPagerAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderDetails;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderUpdates;


public class ViewOrderPersonalShop extends Fragment {

    TabLayout viewOrderTabLayout;
    ViewPager viewOrderViewPager;


    ViewOrderViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_order_personal_shop, container, false);

        viewOrderTabLayout = view.findViewById(R.id.viewOrderTabLayout);
//        viewOrderTabLayout.addTab(viewOrderTabLayout.newTab().setText("Order Details"));
//        viewOrderTabLayout.addTab(viewOrderTabLayout.newTab().setText("Order Updates"));


        viewOrderViewPager = view.findViewById(R.id.viewOrderViewPager);
        viewPagerAdapter = new ViewOrderViewPagerAdapter(getChildFragmentManager());

        viewPagerAdapter.addFragment(new OrderDetails(), "Order Details");
        viewPagerAdapter.addFragment(new OrderUpdates(), "Order Updates");

        viewOrderViewPager.setAdapter(viewPagerAdapter);
        viewOrderTabLayout.setupWithViewPager(viewOrderViewPager);



        return view;
    }

}