package com.peceinfotech.shoppre.UI.Shipment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Utils.PricingTableDialog;

public class ShippingCalculatorResultFragment extends Fragment {

    TextView viewPricingTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_calculator_result, container, false);

        viewPricingTable = view.findViewById(R.id.viewPricingTable);

        viewPricingTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PricingTableDialog pricingTableDialog = new PricingTableDialog();
                pricingTableDialog.showDialog(getContext());
            }
        });

        return view;
    }
}