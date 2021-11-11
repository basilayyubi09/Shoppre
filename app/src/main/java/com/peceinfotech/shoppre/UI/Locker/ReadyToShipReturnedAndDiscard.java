package com.peceinfotech.shoppre.UI.Locker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.LockerAdapters.ReturnedAndDiscardAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.LockerModelResponse.ReturnedAndDiscardResponse;

import java.util.ArrayList;
import java.util.List;


public class ReadyToShipReturnedAndDiscard extends Fragment {


    RecyclerView returnedAndDiscardRecycler;
    List<ReturnedAndDiscardResponse> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ready_to_ship_returned_and_discard, container, false);

        returnedAndDiscardRecycler = view.findViewById(R.id.returnedAndDiscardRecycler);

        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_personal_shopper, "Myntra", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Amazon.in", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_personal_shopper, "Nyka", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#54234"));
        list.add(new ReturnedAndDiscardResponse(R.drawable.ic_self_shopper, "Myntra", "Package ID "+"#54234"));

        ReturnedAndDiscardAdapter returnedAndDiscardAdapter = new ReturnedAndDiscardAdapter(list, getContext());
        returnedAndDiscardRecycler.setAdapter(returnedAndDiscardAdapter);




        return view;
    }
}