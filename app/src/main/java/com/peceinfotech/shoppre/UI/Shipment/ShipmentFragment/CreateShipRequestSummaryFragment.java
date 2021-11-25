package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.ReferralFragment;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.ThankYouFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CreateShipRequestSummaryFragment extends Fragment {

    CheckBox checkBox;
    LinearLayout billingAddressLayout , addressForm;
    MaterialButton createShipBtn;
    Spinner createShipmentTitleSpinner;
    ImageView titleTriangle;

    String[] title = {"Title", "Mr", "Ms", "Mrs"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ship_request_summary, container, false);

        checkBox = view.findViewById(R.id.checkBoxCreateShipment);
        billingAddressLayout = view.findViewById(R.id.billingAddressLayout);
        addressForm = view.findViewById(R.id.addressForm);
        createShipBtn = view.findViewById(R.id.createShipBtn);
        createShipmentTitleSpinner = view.findViewById(R.id.createShipmentTitleSpinner);
        titleTriangle = view.findViewById(R.id.titleTriangle);

        createShipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type" , "summary");
                ThankYouFragment thankYouFragment = new ThankYouFragment();
                thankYouFragment.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, thankYouFragment, null)
                        .addToBackStack(null).commit();
            }
        });

        final List<String> titleList = new ArrayList<>(Arrays.asList(title));

        final ArrayAdapter<String> createShipmentTitleArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.title_spinner, titleList){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView titletv = (TextView) view1;

                if (position == 0){
                    titletv.setTextColor(Color.GRAY);
                    titletv.setVisibility(View.GONE);
                }else {
                    titletv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };
        createShipmentTitleArrayAdapter.setDropDownViewResource(R.layout.title_spinner);
        createShipmentTitleSpinner.setAdapter(createShipmentTitleArrayAdapter);

        createShipmentTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),(String)parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        titleTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShipmentTitleSpinner.performClick();
            }
        });

        return view;
    }
}