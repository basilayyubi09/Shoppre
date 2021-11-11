package com.peceinfotech.shoppre.UI.Locker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.peceinfotech.shoppre.Adapters.LockerAdapters.ReadyToShipAdapter;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.LockerModelResponse.ReadyToShipResponse;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class LockerReadyToShip extends Fragment {

    SharedPrefManager sharedPrefManager;
    List<ReadyToShipResponse> list = new ArrayList<>();

    RecyclerView lockerReadyToShipRecycler;
    LinearLayout returnAndDiscardText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_locker_ready_to_ship, container, false);


        lockerReadyToShipRecycler = view.findViewById(R.id.lockerReadyToShipRecycler);
        returnAndDiscardText = view.findViewById(R.id.returnAndDiscardText);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("locker");


        list.add(new ReadyToShipResponse(R.drawable.ic_self_shopper, "Myntra", "PackageID "+"#0978"));
        list.add(new ReadyToShipResponse(R.drawable.ic_personal_shopper, "Amazon.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_self_shopper, "Amazon.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_personal_shopper, "Myntra.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_self_shopper, "Amazon.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_personal_shopper, "Nyka", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_self_shopper, "Myntra.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_personal_shopper, "Nyka", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_self_shopper, "Myntra.in", "PackageID "+"#9863"));
        list.add(new ReadyToShipResponse(R.drawable.ic_personal_shopper, "Amazon.in", "PackageID "+"#9863"));

        ReadyToShipAdapter readyToShipAdapter = new ReadyToShipAdapter(list, getContext());
        lockerReadyToShipRecycler.setAdapter(readyToShipAdapter);

        returnAndDiscardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new ReadyToShipReturnedAndDiscard(), null)
                        .addToBackStack(null).commit();

            }
        });



        return view;
    }
}