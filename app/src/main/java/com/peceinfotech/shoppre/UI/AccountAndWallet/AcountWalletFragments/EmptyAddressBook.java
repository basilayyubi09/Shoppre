package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.Adapters.GetDeliveryAddrsAdapter;

import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmptyAddressBook extends Fragment {

    MaterialButton billingAddAddressBtn, deliveryAddAddressBtn;
    RecyclerView deliveryRecyclerView;
    MaterialAutoCompleteTextView allAddressSpinner;
    String[] allAddress = {"All Address", "International Address", "Indian Address"};
    SharedPrefManager sharedPrefManager;
    CardView deliveryAddrsCard , emptyDeliveryAdrsCard;
    GetDeliveryAddrsAdapter getDeliveryAddrsAdapter;


    List<DeliveryListModel.Address> list = new ArrayList<>();

    String bearerToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_address_book, container, false);

        billingAddAddressBtn = view.findViewById(R.id.billingAddAdrsBtn);
        deliveryAddAddressBtn = view.findViewById(R.id.deliveryAddAdrsbtn);
        deliveryRecyclerView = view.findViewById(R.id.deliveryAdrsRecycler);
        allAddressSpinner = view.findViewById(R.id.allAddressSpinner);
        deliveryAddrsCard = view.findViewById(R.id.deliveryAddrsCard);
        emptyDeliveryAdrsCard = view.findViewById(R.id.emptyDeliveryAdrsCard);


        sharedPrefManager = new SharedPrefManager(getContext());
        bearerToken = sharedPrefManager.getBearerToken();

        getDeliveryAddrsAdapter = new GetDeliveryAddrsAdapter(list , getContext());
        deliveryRecyclerView.setAdapter(getDeliveryAddrsAdapter);


        LoadingDialog.showLoadingDialog(getActivity() , "");
        fetchAddress();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);


//        list.add(new Address("Aamir" ,"Mumbai" , "7020286762"));
//        list.add(new Address("Basil" , "Mumbai" , "1234567890"));
//        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));
//        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));
//        list.add(new Address("Parvez" , "Mumbai" , "0987654321"));

        billingAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new AddAddress(), null)
                        .addToBackStack(null).commit();

            }
        });

        deliveryAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new AddAddress(), null)
                        .addToBackStack(null).commit();


            }
        });

        return view;
    }

    private void fetchAddress() {

        Call<DeliveryListModel> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getAddresses("Bearer " + bearerToken);

        call.enqueue(new Callback<DeliveryListModel>() {
            @Override
            public void onResponse(Call<DeliveryListModel> call, Response<DeliveryListModel> response ) {

                try {
                    if (response.code() == 200) {

                        LoadingDialog.cancelLoading();

                        list = response.body().getAddresses();
                        getDeliveryAddrsAdapter = new GetDeliveryAddrsAdapter(list , getContext());
                        deliveryRecyclerView.setAdapter(getDeliveryAddrsAdapter);
                        int number = deliveryRecyclerView.getAdapter().getItemCount();
                        if (number == 0){

                            emptyDeliveryAdrsCard.setVisibility(View.VISIBLE);
                            deliveryAddrsCard.setVisibility(View.GONE);

                        }else{

                            emptyDeliveryAdrsCard.setVisibility(View.GONE);
                            deliveryAddrsCard.setVisibility(View.VISIBLE);


                        }
                        getDeliveryAddrsAdapter.notifyDataSetChanged();


                    } else if (response.code() == 401) {

                        LoadingDialog.cancelLoading();
                        String error = response.errorBody().toString();
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<DeliveryListModel> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.all_address_spinner, allAddress);
        allAddressSpinner.setAdapter(arrayAdapter);
    }

}