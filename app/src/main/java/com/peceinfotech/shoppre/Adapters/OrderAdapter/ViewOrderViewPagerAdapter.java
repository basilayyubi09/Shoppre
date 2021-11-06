package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderDetails;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderUpdates;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> title = new ArrayList<>();
    
    public ViewOrderViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
    
    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
    }
}

