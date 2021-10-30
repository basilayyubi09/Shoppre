package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.OrdersAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class OrderListing extends Fragment {

    RecyclerView orderRecycler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_listing, container, false);

        ///Hoooks

        orderRecycler = view.findViewById(R.id.orderRecyclerView);


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


        return view;
    }
}