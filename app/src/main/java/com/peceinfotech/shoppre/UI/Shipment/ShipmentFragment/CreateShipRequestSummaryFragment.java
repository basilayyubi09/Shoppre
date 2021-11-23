package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ReferralFragment;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.ThankYouFragment;


public class CreateShipRequestSummaryFragment extends Fragment {

    CheckBox checkBox;
    LinearLayout billingAddressLayout , addressForm;
    MaterialButton createShipBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ship_request_summary, container, false);

        checkBox = view.findViewById(R.id.check);
        billingAddressLayout = view.findViewById(R.id.billingAddressLayout);
        addressForm = view.findViewById(R.id.addressForm);
        createShipBtn = view.findViewById(R.id.createShipBtn);

        createShipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type" , "summary");
                ThankYouFragment thankYouFragment = new ThankYouFragment();
                thankYouFragment.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, thankYouFragment, null)
                        .addToBackStack(null).commit();
            }
        });
        if (checkBox.isChecked()){
            billingAddressLayout.setVisibility(View.GONE);
            addressForm.setVisibility(View.VISIBLE);
        }else {
            billingAddressLayout.setVisibility(View.VISIBLE);
            addressForm.setVisibility(View.GONE);
        }
        return view;
    }
}