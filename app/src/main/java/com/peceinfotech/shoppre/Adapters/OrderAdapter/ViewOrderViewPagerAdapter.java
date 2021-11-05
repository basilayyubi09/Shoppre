package com.peceinfotech.shoppre.Adapters.OrderAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderDetails;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments.OrderUpdates;

public class ViewOrderViewPagerAdapter extends FragmentStateAdapter {
    public ViewOrderViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new OrderUpdates();
        }
        return new OrderDetails();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
