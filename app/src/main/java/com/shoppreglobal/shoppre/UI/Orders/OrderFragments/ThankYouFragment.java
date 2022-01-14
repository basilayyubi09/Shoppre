package com.shoppreglobal.shoppre.UI.Orders.OrderFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class ThankYouFragment extends Fragment {


    LinearLayout address;
    TextView shipment, order, shipment24, orderId, oneShopText, paymentShipment;
    MaterialButton invite;
    MaterialCardView note, addressCard, oneShop;
    LinearLayout viewOrder, viewShipment;
    MaterialButton wtspBtn;
    String id;
    String shopName;
    CircleImageView facebook, instagram, youtube, linkedin, twitter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        OrderActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.orderMenu).setChecked(true);
        address = view.findViewById(R.id.address);
        oneShop = view.findViewById(R.id.oneShop);
        paymentShipment = view.findViewById(R.id.paymentShipment);
        facebook = view.findViewById(R.id.facebook);
        instagram = view.findViewById(R.id.instagram);
        youtube = view.findViewById(R.id.youtube);
        oneShopText = view.findViewById(R.id.oneShopText);
        linkedin = view.findViewById(R.id.linkedin);
        twitter = view.findViewById(R.id.twitter);
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
        if (bundle != null) {

            if (bundle.getString("type").equals("summary")) {
                addressCard.setVisibility(View.GONE);
                note.setVisibility(View.VISIBLE);
                order.setVisibility(View.GONE);
                viewOrder.setVisibility(View.GONE);
                viewShipment.setVisibility(View.VISIBLE);
                orderId.setVisibility(View.GONE);
                shipment.setVisibility(View.VISIBLE);
                shipment24.setVisibility(View.VISIBLE);
            } else if (bundle.getString("type").equals("order")) {
                id = bundle.getString("id");
            } else if (bundle.getString("type").equals("selfOrder")) {
                id = bundle.getString("id");
                shopName = bundle.getString("shopName");
                oneShop.setVisibility(View.VISIBLE);
                oneShopText.setText("Looks like you’ve added products only from " + shopName);
            } else if (bundle.getString("type").equals("shipment")) {
                addressCard.setVisibility(View.GONE);
                note.setVisibility(View.GONE);
                order.setVisibility(View.GONE);
                viewOrder.setVisibility(View.GONE);
                viewShipment.setVisibility(View.VISIBLE);
                orderId.setVisibility(View.GONE);
                shipment.setVisibility(View.VISIBLE);
                shipment24.setVisibility(View.VISIBLE);
            }

        }
        if (id != null) {
            orderId.setText("Order ID: " + "#" + String.valueOf(id));

        }
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                        , new OrderFragment(), null).commit();
            }
        });


        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "https://www.instagram.com/shoppre_official/";
                String package1 = "com.instagram.android";
                openLink(link, package1);

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "https://www.facebook.com/shoppreofficial";
                String package1 = "com.facebook.katana";
                openLink(link, package1);

            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "https://www.youtube.com/shoppreofficial";
                String package1 = "com.google.android.youtube";
                openLink(link, package1);

            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "https://www.linkedin.com/company/shoppre.com";
                String package1 = "com.linkedin.android";
                openLink(link, package1);

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = "https://twitter.com/shoppreofficial";
                String package1 = "com.twitter.android";
                openLink(link, package1);

            }
        });
        viewShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                        , new ShipmentListingFragment(), null).commit();
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

    private void openLink(String appLink, String aPackage) {
        try {
            Uri uri = Uri.parse(appLink);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(aPackage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Uri uri = Uri.parse(appLink);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}