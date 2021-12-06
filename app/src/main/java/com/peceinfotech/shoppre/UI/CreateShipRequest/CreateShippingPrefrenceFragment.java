package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment.CreateShipRequestSummaryFragment;


public class CreateShippingPrefrenceFragment extends Fragment {

    MaterialButton shippingPrefProceedBtn;
    CheckBox discardShoesCheckBox, addExtraCheckBox, shipInOrignalCheckBox, giftWrapCheckBox, giftNoteCheckBox, expressProcessingCheckBox;
    EditText giftNoteEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_shipping_prefrence, container, false);

        shippingPrefProceedBtn = view.findViewById(R.id.shippingPrefProceedBtn);
        giftNoteCheckBox = view.findViewById(R.id.giftNoteCheckBox);
        giftNoteEditText = view.findViewById(R.id.giftNoteEditText);
        discardShoesCheckBox = view.findViewById(R.id.discardShoeCheckBox);
        addExtraCheckBox = view.findViewById(R.id.addExtraCheckBox);
        shipInOrignalCheckBox = view.findViewById(R.id.shipInOrignalCheckBox);
        giftWrapCheckBox = view.findViewById(R.id.giftWrapCheckBox);
        expressProcessingCheckBox = view.findViewById(R.id.expressProcessingCheckBox);

        shippingPrefProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CreateShipRequestSummaryFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        giftNoteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftNoteCheckBox.isChecked()){
                    giftNoteEditText.setVisibility(View.VISIBLE);
                }else {
                    giftNoteEditText.setVisibility(View.GONE);
                }
            }
        });



        return view;
    }
}