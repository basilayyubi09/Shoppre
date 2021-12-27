package com.shoppreglobal.shoppre.UI.CreateShipRequest;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.shoppreglobal.shoppre.AuthenticationModel.DeliveryListModel;
import com.shoppreglobal.shoppre.LockerModelResponse.ShipmentMeta;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.UI.Shipment.ShipmentFragment.CreateShipRequestSummaryFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CreateShippingPrefrenceFragment extends Fragment {

    MaterialButton shippingPrefProceedBtn, back;
    CheckBox discardShoesCheckBox, addExtraCheckBox, shipInOrignalCheckBox, giftWrapCheckBox, giftNoteCheckBox, expressProcessingCheckBox;
    EditText giftNoteEditText;
    DeliveryListModel.Address address;
    String allIds;
    List<String> list;
    String giftText;
    ShipmentMeta meta;
    String liquid;
    List<DeliveryListModel.Address> addressList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_shipping_prefrence, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            address = (DeliveryListModel.Address) bundle.getSerializable("address");
            allIds = bundle.getString("allIds");
            liquid = bundle.getString("liquid");
            addressList = (List<DeliveryListModel.Address>) bundle.getSerializable("addressList");
            meta = (ShipmentMeta) bundle.getSerializable("meta");

        }
        list = new ArrayList<>();

        shippingPrefProceedBtn = view.findViewById(R.id.shippingPrefProceedBtn);
        giftNoteCheckBox = view.findViewById(R.id.giftNoteCheckBox);
        giftNoteEditText = view.findViewById(R.id.giftNoteEditText);
        discardShoesCheckBox = view.findViewById(R.id.discardShoeCheckBox);
        addExtraCheckBox = view.findViewById(R.id.addExtraCheckBox);
        back = view.findViewById(R.id.back);
        shipInOrignalCheckBox = view.findViewById(R.id.shipInOrignalCheckBox);
        giftWrapCheckBox = view.findViewById(R.id.giftWrapCheckBox);
        expressProcessingCheckBox = view.findViewById(R.id.expressProcessingCheckBox);

        shippingPrefProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.contains("gift")) {
                    giftText = giftNoteEditText.getText().toString();
                } else {
                    giftText = "";
                }

                CreateShipRequestSummaryFragment summaryFragment = new CreateShipRequestSummaryFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("address", address);
                bundle1.putString("allIds", allIds);
                bundle1.putString("giftNote", giftText);
                bundle1.putString("liquid", liquid);
                bundle1.putSerializable("array", (Serializable) list);
                bundle1.putSerializable("addressList", (Serializable) addressList);
                bundle1.putSerializable("meta", meta);


                summaryFragment.setArguments(bundle1);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, summaryFragment, null)
                        .addToBackStack(null).commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        giftNoteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                giftNoteEditText.setVisibility(View.VISIBLE);
                giftText = giftNoteEditText.getText().toString();

                if (giftNoteCheckBox.isChecked()) {

                    if (!list.contains("gift")) {
                        giftNoteEditText.setVisibility(View.VISIBLE);


                        addItemInList("gift");

                    }
                } else {

                    if (list.contains("gift")) {
                        giftNoteEditText.setVisibility(View.GONE);

                        removeItemFromList("gift");


                    }
                }
                enableButton();
            }
        });


        addExtraCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (addExtraCheckBox.isChecked()) {
                    if (!list.contains("add")) {
                        addItemInList("add");
                    }
                } else {
                    if (list.contains("add")) {
                        removeItemFromList("add");

                    }
                }
                enableButton();
            }
        });


        shipInOrignalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (shipInOrignalCheckBox.isChecked()) {
                    if (!list.contains("ship")) {
                        addItemInList("ship");
                    }
                } else {
                    if (list.contains("ship")) {
                        removeItemFromList("ship");

                    }
                }
                enableButton();
            }
        });

        giftWrapCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (giftWrapCheckBox.isChecked()) {
                    if (!list.contains("wrap")) {
                        addItemInList("wrap");
                    }
                } else {
                    if (list.contains("wrap")) {
                        removeItemFromList("wrap");

                    }
                }
                enableButton();
            }
        });

        discardShoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (discardShoesCheckBox.isChecked()) {
                    if (!list.contains("discard")) {
                        addItemInList("discard");
                    }
                } else {
                    if (list.contains("discard")) {
                        removeItemFromList("discard");

                    }
                }
                enableButton();
            }
        });

        expressProcessingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (expressProcessingCheckBox.isChecked()) {
                    if (!list.contains("express")) {
                        addItemInList("express");
                    }
                } else {
                    if (list.contains("express")) {
                        removeItemFromList("express");

                    }
                }
                enableButton();
            }
        });

        return view;
    }

    private void enableButton() {
        if (list.isEmpty()) {
            shippingPrefProceedBtn.setEnabled(false);
            shippingPrefProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        } else {

            shippingPrefProceedBtn.setEnabled(true);
            shippingPrefProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_blue)));
        }
    }

    private void removeItemFromList(String stringValues) {
        list.remove(stringValues);
    }

    private void addItemInList(String stringValues) {

        list.add(stringValues);
    }
}