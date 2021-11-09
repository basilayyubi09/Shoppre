package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.OrderAdapter.OrderUpdateAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderUpdateResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class OrderUpdates extends Fragment {

    RecyclerView orderUpdatesRecycler;
    List<OrderUpdateResponse> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_updates, container, false);

        orderUpdatesRecycler = view.findViewById(R.id.orderUpdatesRecycler);


        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
        list.add(new OrderUpdateResponse(R.drawable.shoppre_ic, "Shoppre Team", "Order Arrived at Facility", " 19 Dec 2020, 4:05 PM IST"));
        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "Where is my order?", "22 Dec 2020, 09:45 AM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.shoppre_ic, "Shoppre Team", "The Order is at our warehouse ready to be shipped", "24 Dec 2020, 3:26 IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));
//        list.add(new OrderUpdateResponse(R.drawable.n_logo, "Nikkitha", "New Order Placed", "15 Dec 2020, 2:20 PM IST"));


        OrderUpdateAdapter orderUpdateAdapter = new OrderUpdateAdapter(list, getContext());
        orderUpdatesRecycler.setAdapter(orderUpdateAdapter);


        return view;
    }
}