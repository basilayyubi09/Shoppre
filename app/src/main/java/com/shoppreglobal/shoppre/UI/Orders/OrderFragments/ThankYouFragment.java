package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.EmptyAddressBook;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.ReferralFragment;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.ShipmentListingFragment;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;


public class ThankYouFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    LinearLayout address;
    TextView shipment , order , shipment24 , orderId;
    MaterialButton invite;
    MaterialCardView note , addressCard;
    LinearLayout viewOrder , viewShipment;
    MaterialButton wtspBtn;
    Integer id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        address = view.findViewById(R.id.address);
        invite = view.findViewById(R.id.invite);
        shipment = view.findViewById(R.id.shipment);
        note = view.findViewById(R.id.note);
        wtspBtn = view.findViewById(R.id.wtspBtn);
        orderId = view.findViewById(R.id.orderId);
        shipment24 = view.findViewById(R.id.shipment24);
        viewOrder = view.findViewById(R.id.viewOrder);
        viewShipment = view.findViewById(R.id.viewShipment);

        order = view.findViewById(R.id.order);
        addressCard = view.findViewById(R.id.addressCard);

        Bundle bundle = this.getArguments();
        if (bundle!=null){

            if (bundle.getString("type").equals("summary")){
                addressCard.setVisibility(View.GONE);
                note.setVisibility(View.VISIBLE);
                order.setVisibility(View.GONE);
                viewOrder.setVisibility(View.GONE);
                viewShipment.setVisibility(View.VISIBLE);
                orderId.setVisibility(View.GONE);
                shipment.setVisibility(View.VISIBLE);
                shipment24.setVisibility(View.VISIBLE);
            }
            else if (bundle.getString("type").equals("order")){
             id = bundle.getInt("id");
            }

        }
        if (id!=null){
            orderId.setText("#"+String.valueOf(id));
            viewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                            , new OrderFragment(), null)
                            .addToBackStack(null).commit();
                }
            });
        }
        viewShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                        , new ShipmentListingFragment(), null)
                        .addToBackStack(null).commit();
            }
        });
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

        wtspBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ViewProfile(), null)
                        .addToBackStack(null).commit();
            }
        });
        return view;
    }
}