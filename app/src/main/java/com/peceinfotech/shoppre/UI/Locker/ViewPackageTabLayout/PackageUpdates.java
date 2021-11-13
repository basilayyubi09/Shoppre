package com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageUpdateAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageUpdateResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class PackageUpdates extends Fragment {

    RecyclerView packageUpdatesRecycler;
    List<PackageUpdateResponse> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_updates, container, false);

        packageUpdatesRecycler = view.findViewById(R.id.packageUpdatesRecycler);

        list.add(new PackageUpdateResponse("Nikhita", "New Order Placed", "15 Dec"));
        list.add(new PackageUpdateResponse("Team Shoppre", "New Order Placed", "19 Dec"));
        list.add(new PackageUpdateResponse("Nikhita", "Order Arrived at Facility", "22 Dec"));
        list.add(new PackageUpdateResponse("Nikhita", "Where is my order", "24 Dec"));


        PackageUpdateAdapter packageUpdateAdapter = new PackageUpdateAdapter(list, getContext());
        packageUpdatesRecycler.setAdapter(packageUpdateAdapter);

        return view;
    }
}