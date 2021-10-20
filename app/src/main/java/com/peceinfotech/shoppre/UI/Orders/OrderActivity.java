package com.peceinfotech.shoppre.UI.Orders;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.Locker.LockerReadyToShip;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.OrderFragment;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentList;

public class OrderActivity extends AppCompatActivity{

    MaterialCardView locker, shipment, account, order;
    ImageView lockerImage, orderImage, accountImage, shipmentImage;
    FrameLayout frameLayout;
    public static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        fragmentManager = getSupportFragmentManager();

        frameLayout = findViewById(R.id.orderFrameLayout);
        locker = findViewById(R.id.lockerCard);
        shipment = findViewById(R.id.shipmentCard);
        account = findViewById(R.id.accountCard);
        order = findViewById(R.id.orderCard);
        lockerImage = findViewById(R.id.lockerImage);
        orderImage = findViewById(R.id.ordersImage);
        accountImage = findViewById(R.id.accountImage);
        shipmentImage = findViewById(R.id.shipmentImage);


        if (savedInstanceState != null) return;
        fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        fragmentTransaction.add(R.id.orderFrameLayout, orderFragment, null);
        fragmentTransaction.commit();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                OrderFragment orderFragment = new OrderFragment();
                fragmentTransaction.add(R.id.orderFrameLayout, orderFragment, null);
                fragmentTransaction.commit();


                orderImage.setImageResource(R.drawable.ic_orders___selected);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account);

            }
        });

        locker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                LockerReadyToShip lockerReadyToShip = new LockerReadyToShip();
                fragmentTransaction.replace(R.id.orderFrameLayout, lockerReadyToShip, null);
                fragmentTransaction.addToBackStack(null).commit();


                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker___selected);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account);


            }
        });

        shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                ShipmentList shipmentList = new ShipmentList();
                fragmentTransaction.replace(R.id.orderFrameLayout, shipmentList, null);
                fragmentTransaction.addToBackStack(null).commit();

                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments___selected);
                accountImage.setImageResource(R.drawable.ic_account);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState != null) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                ViewProfile viewProfile = new ViewProfile();
                fragmentTransaction.replace(R.id.orderFrameLayout, viewProfile, null);
                fragmentTransaction.addToBackStack(null).commit();

                orderImage.setImageResource(R.drawable.ic_orders);
                lockerImage.setImageResource(R.drawable.ic_locker);
                shipmentImage.setImageResource(R.drawable.ic_shipments);
                accountImage.setImageResource(R.drawable.ic_account___selected);

            }
        });
    }
}