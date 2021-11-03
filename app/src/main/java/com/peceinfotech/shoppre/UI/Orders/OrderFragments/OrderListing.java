package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Adapters.OrdersAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.VertualAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Shipment.ShippingCalculator;

import java.util.ArrayList;
import java.util.List;


public class OrderListing extends Fragment {

    RecyclerView orderRecycler;
    CardView virtualAddressCard, shippingCalculatorCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_listing, container, false);

        ///Hoooks

        orderRecycler = view.findViewById(R.id.orderRecyclerView);
        virtualAddressCard = view.findViewById(R.id.virtualAddressCard);
        shippingCalculatorCard = view.findViewById(R.id.shippingCalculatorCard);


        List<OrderResponse> list = new ArrayList<>();


        list.add(new OrderResponse("Myntra", "#RNDM043", "12 Dec 2020", R.drawable.ic_self_shopper));
        list.add(new OrderResponse("Amazon.in", "#PSDM043", "15 Dec 2020", R.drawable.ic_personal_shopper));
        list.add(new OrderResponse("Nyka", "#RNDM032", "16 Dec 2020", R.drawable.ic_self_shopper));
        list.add(new OrderResponse("Flipkart", "#PSDM054", "18 Dec 2020", R.drawable.ic_personal_shopper));
        list.add(new OrderResponse("Fabindia" , "#PSDM054" , "20 Dec 2020" , R.drawable.ic_self_shopper));


        OrdersAdapter ordersAdapter = new OrdersAdapter(list , getContext());
        orderRecycler.setAdapter(ordersAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderRecycler.setLayoutManager(linearLayoutManager);


        virtualAddressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (savedInstanceState != null) return;

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout , new VertualAddress() , null)
                        .addToBackStack(null).commit();

            }
        });


        shippingCalculatorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ShippingCalculator(), null)
                        .addToBackStack(null).commit();

            }
        });


        return view;
    }
}