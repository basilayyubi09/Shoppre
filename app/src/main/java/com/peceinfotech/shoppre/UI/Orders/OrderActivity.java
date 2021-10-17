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

public class OrderActivity extends AppCompatActivity {

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
        lockerImage = findViewById(R.id.unselectedLocker);
        orderImage = findViewById(R.id.unselectedOrders);
        accountImage = findViewById(R.id.unselectedAccount);
        shipmentImage = findViewById(R.id.unselectedShipments);


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
            }
        });


    }
}