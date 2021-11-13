package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.EmptyAddressBook;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ReferralFragment;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;


public class ThankYouFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    LinearLayout address;
    MaterialButton invite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        address = view.findViewById(R.id.address);
        invite = view.findViewById(R.id.invite);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReferralFragment(), null)
                        .addToBackStack(null).commit();
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new EmptyAddressBook(), null)
                        .addToBackStack(null).commit();
            }
        });


        return view;
    }
}