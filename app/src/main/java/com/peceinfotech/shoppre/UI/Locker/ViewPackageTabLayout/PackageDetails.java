package com.peceinfotech.shoppre.UI.Locker.ViewPackageTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.peceinfotech.shoppre.Adapters.LockerAdapters.PackageDetailsAdapter;
import com.peceinfotech.shoppre.LockerModelResponse.PackageDetailsResponse;
import com.peceinfotech.shoppre.LockerModelResponse.PackageItem;
import com.peceinfotech.shoppre.LockerModelResponse.ViewPackageResponse;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.List;

public class PackageDetails extends Fragment {

    RecyclerView packageDetailsRecycler;
    List<PackageItem> list;
    PackageDetailsAdapter packageDetailsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package_details, container, false);
        packageDetailsRecycler = view.findViewById(R.id.packageDetailsRecycler);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            list = (List<PackageItem>) bundle.getSerializable("list");

        }




         packageDetailsAdapter = new PackageDetailsAdapter(list, getContext(), new PackageDetailsAdapter.GetData() {
            @Override
            public void dotsVisiblity() {

                for (int i=0; i< list.size(); i++){
                    packageDetailsRecycler.getChildAt(i);

                    final CheckBox checkBox = getView().findViewById(R.id.packageDetailCheckbox);
                    final ImageView dots = getView().findViewById(R.id.three_dots);

                    dots.setVisibility(View.GONE);
                    checkBox.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "jjhgj", Toast.LENGTH_SHORT).show();

                }

                packageDetailsAdapter.notifyDataSetChanged();

            }
        });
        packageDetailsRecycler.setAdapter(packageDetailsAdapter);



        return view;
    }
}