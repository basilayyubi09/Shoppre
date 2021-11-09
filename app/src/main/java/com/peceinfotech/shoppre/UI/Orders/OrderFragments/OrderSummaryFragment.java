package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.OrderSummaryAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryFragment extends Fragment {

    SharedPrefManager sharedPrefManager;
    List<String> list;
    OrderSummaryAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recycle;
    MaterialButton orderSummaryProceedBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("orders");

        recycle = view.findViewById(R.id.recycle);
        orderSummaryProceedBtn = view.findViewById(R.id.orderSummaryProceedBtn);


        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.add("Amazon.in");
        list.add("Myntra");
        list.add("Flipkart");
        list.add("Snapdeal");



        recycle.setLayoutManager(linearLayoutManager);
        adapter = new OrderSummaryAdapter(list, getActivity());
        recycle.setAdapter(adapter);


        orderSummaryProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new FinalOrderSummaryFragment(), null)
                        .addToBackStack(null).commit();

            }
        });

        return view;
    }
}