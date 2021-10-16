package com.peceinfotech.shoppre.UI.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ViewProfile;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.OrderFragment;

public class    OrderActivity extends AppCompatActivity {

    MaterialCardView locker , shipment , account , order;
    ImageView lockerImage  , orderImage , accountImage , shipmentImage;
    FrameLayout frameLayout;
    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        fragmentManager =  getSupportFragmentManager();

        frameLayout = findViewById(R.id.orderFrameLayout);
        locker = findViewById(R.id.lockerCard);
        shipment = findViewById(R.id.shipmentCard);
        account = findViewById(R.id.accountCard);
        order = findViewById(R.id.orderCard);
        lockerImage = findViewById(R.id.unselectedLocker);
        orderImage = findViewById(R.id.unselectedOrders);
        accountImage = findViewById(R.id.unselectedAccount);
        shipmentImage = findViewById(R.id.unselectedShipments);



        if (savedInstanceState!=null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        fragmentTransaction.add(R.id.orderFrameLayout , orderFragment , null);
        fragmentTransaction.commit();



    }
}