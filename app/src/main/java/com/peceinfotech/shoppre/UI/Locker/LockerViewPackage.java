package com.peceinfotech.shoppre.UI.Locker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.ViewPackageViewPager;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout.PackageDetails;
import com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout.PackageUpdates;

public class LockerViewPackage extends Fragment {

    TabLayout viewPackageTablayout;
    ViewPager viewPackageViewPager;

    ViewPackageViewPager viewPackageViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_view_package, container, false);

        viewPackageTablayout = view.findViewById(R.id.viewPackageTablayout);
        viewPackageViewPager = view.findViewById(R.id.viewPackageViewPager);
        viewPackageViewPagerAdapter = new ViewPackageViewPager(getChildFragmentManager());


        viewPackageViewPagerAdapter.addFragment(new PackageDetails(), "Package Details (4)");
        viewPackageViewPagerAdapter.addFragment(new PackageUpdates(), "Locker Updates");

        viewPackageViewPager.setAdapter(viewPackageViewPagerAdapter);
        viewPackageTablayout.setupWithViewPager(viewPackageViewPager);


        return view;
    }
}