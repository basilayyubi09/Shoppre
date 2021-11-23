package com.peceinfotech.shoppre.UI.CreateShipRequest;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.CreateShipAdapters.DeliveryAddressAdapter;
import com.peceinfotech.shoppre.CreateShipmentModelResponse.DeliveryAddressModelResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateShipmentDeliveryAddress extends Fragment {

    RecyclerView createShipmentDeliveryAddressRecycler;
    DeliveryAddressAdapter deliveryAddressAdapter;
    List<DeliveryAddressModelResponse> list = new ArrayList<>();
    CardView emptyAddressCard, createShipmentDeliveryAddressCard;
    LinearLayout addMoreDeliveryAddressText;
    MaterialButton createShipmentAddAddrsBtn;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_shipment_delivery_address, container, false);

        createShipmentDeliveryAddressRecycler = view.findViewById(R.id.createShipmentDeliveryAddressRecycler);
        emptyAddressCard = view.findViewById(R.id.emptyAddressCard);
        createShipmentDeliveryAddressCard = view.findViewById(R.id.createShipmentDeliveryAddressCard);
        addMoreDeliveryAddressText = view.findViewById(R.id.addMoreDeliveryAddressText);
        createShipmentAddAddrsBtn = view.findViewById(R.id.createShipmentAddAddrsBtn);

        bundle = new Bundle();
        bundle.putString("type", "deliveryAddress");



        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));

         deliveryAddressAdapter = new DeliveryAddressAdapter(list, getContext());
        createShipmentDeliveryAddressRecycler.setAdapter(deliveryAddressAdapter);

        if (list.isEmpty()){
            emptyAddressCard.setVisibility(View.VISIBLE);
            createShipmentDeliveryAddressCard.setVisibility(View.GONE);
        }else {
            emptyAddressCard.setVisibility(View.GONE);
            createShipmentDeliveryAddressCard.setVisibility(View.VISIBLE);
        }

        addMoreDeliveryAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddAddress addAddress = new AddAddress();
                addAddress.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, addAddress, null)
                        .addToBackStack(null).commit();
            }
        });

        createShipmentAddAddrsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddAddress addAddress = new AddAddress();
                addAddress.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, addAddress, null)
                        .addToBackStack(null).commit();

            }
        });

        return view;
    }
}