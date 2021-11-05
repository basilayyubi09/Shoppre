package com.peceinfotech.shoppre.UI.Orders.OrderFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peceinfotech.shoppre.Adapters.OrderSummaryAdapter;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryFragment extends Fragment {

    List<String> list;
    OrderSummaryAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recycle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        recycle = view.findViewById(R.id.recycle);

        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.add("Amazon.in");
        list.add("Myntra");
        list.add("Flipkart");
        list.add("Snapdeal");



        recycle.setLayoutManager(linearLayoutManager);
        adapter = new OrderSummaryAdapter(list, getActivity());
        recycle.setAdapter(adapter);

        return view;
    }
}