package com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageDetailsResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class PackageDetails extends Fragment {

    RecyclerView packageDetailsRecycler;
    List<PackageDetailsResponse> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_details, container, false);

        packageDetailsRecycler = view.findViewById(R.id.packageDetailsRecycler);



        list.add(new PackageDetailsResponse(R.drawable.shoppre_ic, "VASTRAMANIAA Wommann", "#29357", "Qty: " + "03", "19000"));
        list.add(new PackageDetailsResponse(R.drawable.mobile1, "Redmi Note 9 Pro Max", "#29357", "Qty: " + "03", "19000"));
        list.add(new PackageDetailsResponse(R.drawable.mobile1, "Reebok Men's Essential mamamks", "#29357", "Qty: " + "03", "19000"));
        list.add(new PackageDetailsResponse(R.drawable.mobile1, "Slurrp Farm Organic Ba...", "#29357", "Qty: " + "03", "19000"));
        list.add(new PackageDetailsResponse(R.drawable.mobile1, "Redmi Note 9 Pro Maxbdkja", "#29357", "Qty: " + "03", "19000"));


        PackageDetailsAdapter packageDetailsAdapter = new PackageDetailsAdapter(list, getContext());
        packageDetailsRecycler.setAdapter(packageDetailsAdapter);



        return view;
    }
}