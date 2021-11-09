package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Adapters.OrderAdapter.ViewOrderAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderItem;
import com.peceinfotech.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ViewOrderResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class OrderDetails extends Fragment {

    RecyclerView orderDetailsRecycler;
    List<ViewOrderResponse> list = new ArrayList<>();
    List<OrderItem> list1 = new ArrayList<>();
    ViewOrderAdapter viewOrderAdapter;
    ShowOrderResponse showOrderResponse;
    OrderItem orderItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        orderDetailsRecycler = view.findViewById(R.id.orderDetailsRecycler);
        Bundle bundle = getArguments();
        if (bundle != null) {

            list1 = (List<OrderItem>) bundle.getSerializable("order");


        }

        ViewOrderAdapter viewOrderAdapter = new ViewOrderAdapter(list1, getContext());
        orderDetailsRecycler.setAdapter(viewOrderAdapter);


        return view;
    }
}