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
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.Adapters.CreateShipAdapters.DeliveryAddressAdapter;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.CreateShipmentModelResponse.DeliveryAddressModelResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddAddress;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShipmentDeliveryAddress extends Fragment {

    RecyclerView createShipmentDeliveryAddressRecycler;
    DeliveryAddressAdapter deliveryAddressAdapter;
    List<DeliveryListModel.Address> list = new ArrayList<>();
    CardView emptyAddressCard, createShipmentDeliveryAddressCard;
    LinearLayout addMoreDeliveryAddressText;
    MaterialButton createShipmentAddAddrsBtn, deliveryAddrsProceedBtn;
    Bundle bundle;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
    String bearerToken;

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
        deliveryAddrsProceedBtn = view.findViewById(R.id.deliveryAddrsProceedBtn);

        bundle = new Bundle();
        bundle.putString("type", "deliveryAddress");


        allAddressesApi();

//        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
//        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
//        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
//        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));
//        list.add(new DeliveryAddressModelResponse("Nikkita", "12, near Burj khalifa, Dubai, Unied Arab Emirates", "Dubai", "United Arab Emirates", "0987654321"));

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

        deliveryAddrsProceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new CreateShippingPrefrenceFragment(), null)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void allAddressesApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<DeliveryListModel> call = RetrofitClient3.getInstance3().getAppApi().getAddresses("Bearer "+ sharedPrefManager.getBearerToken());

        call.enqueue(new Callback<DeliveryListModel>() {
            @Override
            public void onResponse(Call<DeliveryListModel> call, Response<DeliveryListModel> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    list = response.body().getAddresses();
                }else if (response.code()==401){
                    LoadingDialog.cancelLoading();
                    String error = response.errorBody().toString();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeliveryListModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}