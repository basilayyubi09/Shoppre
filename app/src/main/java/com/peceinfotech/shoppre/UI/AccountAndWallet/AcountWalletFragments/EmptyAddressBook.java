package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.Adapters.GetDeliveryAddrsAdapter;
import com.peceinfotech.shoppre.AuthenticationModel.Address;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmptyAddressBook extends Fragment {

    MaterialButton billingAddAddressBtn , deliveryAddAddressBtn;
    RecyclerView deliveryRecyclerView;
    MaterialAutoCompleteTextView allAddressSpinner;
    String[] allAddress = {"All Address" , "International Address" , "Indian Address" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_address_book, container, false);

        billingAddAddressBtn = view.findViewById(R.id.billingAddAdrsBtn);
        deliveryAddAddressBtn = view.findViewById(R.id.deliveryAddAdrsbtn);
        deliveryRecyclerView = view.findViewById(R.id.deliveryAdrsRecycler);
        allAddressSpinner = view.findViewById(R.id.allAddressSpinner);




        ArrayList<Address> list = new ArrayList<>();

        list.add(new Address("Aamir" ,"Mumbai" , "7020286762"));
        list.add(new Address("Basil" , "Mumbai" , "1234567890"));
        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));
        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));
        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));


        GetDeliveryAddrsAdapter adapter = new GetDeliveryAddrsAdapter(list , getContext());
        deliveryRecyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();






        billingAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new AddAddress() , null)
                        .addToBackStack(null).commit();

            }
        });
        
        deliveryAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new AddAddress() , null)
                        .addToBackStack(null).commit();


            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext() , R.layout.all_address_spinner , allAddress);
        allAddressSpinner.setAdapter(arrayAdapter);
    }

}