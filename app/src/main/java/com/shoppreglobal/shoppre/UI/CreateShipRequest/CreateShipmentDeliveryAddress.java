package com.shoppreglobal.shoppre.UI.CreateShipRequest;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.CreateShipAdapters.DeliveryAddressAdapter;
import com.shoppreglobal.shoppre.AuthenticationModel.DeliveryListModel;
import com.shoppreglobal.shoppre.LockerModelResponse.ShipmentMeta;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments.AddAddress;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateShipmentDeliveryAddress extends Fragment {

    RecyclerView createShipmentDeliveryAddressRecycler;
    DeliveryAddressAdapter deliveryAddressAdapter;
    List<DeliveryListModel.Address> list;
    List<DeliveryListModel.Address> forSendList;
    List<DeliveryListModel.Address> list1;
    CardView emptyAddressCard, createShipmentDeliveryAddressCard;
    LinearLayout addMoreDeliveryAddressText;
    MaterialButton createShipmentAddAddrsBtn, deliveryAddrsProceedBtn;
    Bundle bundle;
    String allIds;
    MaterialButton back;
    SharedPrefManager sharedPrefManager;
    ShipmentMeta meta;
    boolean radioClicked = false;
    String liquid;
    DeliveryListModel.Address selectedAddress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_shipment_delivery_address, container, false);

        createShipmentDeliveryAddressRecycler = view.findViewById(R.id.createShipmentDeliveryAddressRecycler);
        emptyAddressCard = view.findViewById(R.id.emptyAddressCard);
        back = view.findViewById(R.id.back);
        createShipmentDeliveryAddressCard = view.findViewById(R.id.createShipmentDeliveryAddressCard);
        addMoreDeliveryAddressText = view.findViewById(R.id.addMoreDeliveryAddressText);
        createShipmentAddAddrsBtn = view.findViewById(R.id.createShipmentAddAddrsBtn);
        deliveryAddrsProceedBtn = view.findViewById(R.id.deliveryAddrsProceedBtn);
        list = new ArrayList<>();
        forSendList = new ArrayList<>();

        sharedPrefManager = new SharedPrefManager(getActivity());
        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.lockerMenu).setChecked(true);

        //////////Address Api
        allAddressesApi();

        bundle = new Bundle();
        bundle.putString("type", "deliveryAddress");

        Bundle bundle1 = this.getArguments();
        if (bundle1 != null) {
            allIds = bundle1.getString("ids");
            liquid = bundle.getString("liquid");
            meta = (ShipmentMeta) bundle1.getSerializable("meta");
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
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
                if (radioClicked) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("address", selectedAddress);
                    bundle2.putSerializable("addressList", (Serializable) forSendList);
                    bundle2.putString("allIds", allIds);
                    bundle2.putString("liquid", liquid);
                    bundle2.putSerializable("meta", meta);
                    CreateShippingPrefrenceFragment shippingPrefrenceFragment = new CreateShippingPrefrenceFragment();
                    shippingPrefrenceFragment.setArguments(bundle2);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, shippingPrefrenceFragment, null)
                            .addToBackStack(null).commit();
                }


            }
        });

        return view;
    }

    private void allAddressesApi() {
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<DeliveryListModel> call = RetrofitClient3.getInstance3().getAppApi().getAddresses("Bearer " + sharedPrefManager.getBearerToken());

        call.enqueue(new Callback<DeliveryListModel>() {
            @Override
            public void onResponse(Call<DeliveryListModel> call, Response<DeliveryListModel> response) {
                if (response.code() == 200) {

                    emptyAddressCard.setVisibility(View.GONE);
                    createShipmentDeliveryAddressCard.setVisibility(View.VISIBLE);
                    list = response.body().getAddresses();
                    forSendList = response.body().getAddresses();
//                    forSendList = list1;
                    list1 = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getBillingAddress()) {
                            list1.add(list.get(i));
                        }

                    }


                    deliveryAddressAdapter = new DeliveryAddressAdapter(list1, getContext(), new DeliveryAddressAdapter.Interface() {
                        @Override
                        public void radioOperation(DeliveryListModel.Address address, RadioButton radioButton, int position) {
                            if (radioButton.isChecked()) {
                                radioClicked = true;
                                selectedAddress = address;
                                deliveryAddrsProceedBtn.setEnabled(true);
                                deliveryAddrsProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_blue)));
                            } else {
                                deliveryAddrsProceedBtn.setEnabled(false);
                                deliveryAddrsProceedBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                                radioClicked = false;
                            }
                        }
                    });
                    createShipmentDeliveryAddressRecycler.setAdapter(deliveryAddressAdapter);

                    int count = deliveryAddressAdapter.getItemCount();
                    if (count == 0) {
                        emptyAddressCard.setVisibility(View.VISIBLE);
                        createShipmentDeliveryAddressCard.setVisibility(View.GONE);
                    } else {
                        emptyAddressCard.setVisibility(View.GONE);
                        createShipmentDeliveryAddressCard.setVisibility(View.VISIBLE);
                    }

                    LoadingDialog.cancelLoading();
                } else if (response.code() == 401) {
                    callRefreshTokenApi();
                }

            }

            @Override
            public void onFailure(Call<DeliveryListModel> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callRefreshTokenApi() {
        Call<RefreshTokenResponse> call = RetrofitClient
                .getInstance().getApi()
                .getRefreshToken(sharedPrefManager.getRefreshToken());
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.code() == 200) {
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    allAddressesApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }
}