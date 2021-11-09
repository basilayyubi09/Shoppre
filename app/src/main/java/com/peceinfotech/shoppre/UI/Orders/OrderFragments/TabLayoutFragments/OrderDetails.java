package com.peceinfotech.shoppre.UI.Orders.OrderFragments.TabLayoutFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.OrderAdapter.ViewOrderAdapter;
import com.peceinfotech.shoppre.OrderModuleResponses.ViewOrderResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;


public class OrderDetails extends Fragment {

    RecyclerView orderDetailsRecycler;
    List<ViewOrderResponse> list = new ArrayList<>();
    ViewOrderAdapter viewOrderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        orderDetailsRecycler = view.findViewById(R.id.orderDetailsRecycler);

        list.add(new ViewOrderResponse(R.drawable.mobile1, "Redmi 3s Prime", "Black", "02", "32000"));
        list.add(new ViewOrderResponse(R.drawable.view_order_image2, "Redmi 3s Prime", "Black", "02", "32000"));
        list.add(new ViewOrderResponse(R.drawable.view_order_image3, "One Plus Nord 128 gb 6gb Ram Mirror Black", "Black", "02", "32000"));
        list.add(new ViewOrderResponse(R.drawable.view_order_image4, "Redmi 3s Prime", "Black", "02", "32000"));
        list.add(new ViewOrderResponse(R.drawable.mobile1, "Redmi 3s Prime", "Black", "02", "32000"));


        viewOrderAdapter = new ViewOrderAdapter(list, getContext());
        orderDetailsRecycler.setAdapter(viewOrderAdapter);



        return view;
    }
}