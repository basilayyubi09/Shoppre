package com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.DeleteAddressResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.Adapters.GetDeliveryAddrsAdapter;
import com.shoppreglobal.shoppre.AuthenticationModel.CommonModel;
import com.shoppreglobal.shoppre.AuthenticationModel.DeliveryListModel;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmptyAddressBook extends Fragment {

    MaterialButton billingAddAddressBtn, deliveryAddAddressBtn, setDefaultAddressBtn,
            emptyAddressNameCopyBtn, emptyAddressLine1CopyBtn, emptyAddressLine2CopyBtn,
            emptyAddressLandmarkCopyBtn, emptyAddressCityCopyBtn, emptyAddressStateCopyBtn,
            emptyAddressPincodeCopyBtn, emptyAddressPhoneNOCopyBtn;
    RecyclerView deliveryRecyclerView;
    MaterialAutoCompleteTextView allAddressSpinner;
    String[] allAddress = {"All Address", "International Address", "Indian Address"};
    SharedPrefManager sharedPrefManager;
    CardView deliveryAddrsCard, emptyDeliveryAdrsCard;
    GetDeliveryAddrsAdapter getDeliveryAddrsAdapter;
    TextView addMoreAddress, billingAddressText, emptyAddressNameText,
            emptyAddressLine1Text, emptyAddressLine2Text,
            emptyAddressLandmarkText, emptyAddressCityText,
            emptyAddressStateText, emptyAddressPincodeText,
            emptyAddressPhoneNoText;

    DeliveryListModel.Address deliveryAddress;

    Integer addressId = null, id;
    LinearLayout billingAddressBox;
    TextView billingAddressName, billingAddressContactNumber, billingAddressEdit, billingAddress;

    List<DeliveryListModel.Address> list = new ArrayList<>();

    String bearerToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_address_book, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());
        sharedPrefManager.fragmentValue("account");


        billingAddressContactNumber = view.findViewById(R.id.billingContactNumber);
        billingAddressEdit = view.findViewById(R.id.billingAddressEdit);
        billingAddress = view.findViewById(R.id.billingAddress);
        billingAddressName = view.findViewById(R.id.billingAddressName);
        billingAddressBox = view.findViewById(R.id.billingAddressBox);
        billingAddAddressBtn = view.findViewById(R.id.billingAddAddressBtn);
        billingAddressText = view.findViewById(R.id.billingAddressText);
        deliveryAddAddressBtn = view.findViewById(R.id.deliveryAddAdrsbtn);
        deliveryRecyclerView = view.findViewById(R.id.deliveryAdrsRecycler);
        allAddressSpinner = view.findViewById(R.id.allAddressSpinner);
        deliveryAddrsCard = view.findViewById(R.id.deliveryAddrsCard);
        emptyDeliveryAdrsCard = view.findViewById(R.id.emptyDeliveryAdrsCard);
        setDefaultAddressBtn = view.findViewById(R.id.setDefaultAddrsBtn);
        addMoreAddress = view.findViewById(R.id.addMoreAddress);

        emptyAddressNameCopyBtn = view.findViewById(R.id.emptyAddressNameCopyBtn);
        emptyAddressLine1CopyBtn = view.findViewById(R.id.emptyAddressLine1CopyBtn);
        emptyAddressLine2CopyBtn = view.findViewById(R.id.emptyAddressLine2CopyBtn);
        emptyAddressLandmarkCopyBtn = view.findViewById(R.id.emptyAddressLandmarkCopyBtn);
        emptyAddressCityCopyBtn = view.findViewById(R.id.emptyAddressCityCopyBtn);
        emptyAddressStateCopyBtn = view.findViewById(R.id.emptyAddressStateCopyBtn);
        emptyAddressPincodeCopyBtn = view.findViewById(R.id.emptyAddressPincodeCopyBtn);
        emptyAddressPhoneNOCopyBtn = view.findViewById(R.id.emptyAddressPhoneNOCopyBtn);


        emptyAddressNameText = view.findViewById(R.id.emptyAddressNameText);
        emptyAddressLine1Text = view.findViewById(R.id.emptyAddressLine1Text);
        emptyAddressLine2Text = view.findViewById(R.id.emptyAddressLine2Text);
        emptyAddressLandmarkText = view.findViewById(R.id.emptyAddressLandmarkText);
        emptyAddressCityText = view.findViewById(R.id.emptyAddressCityText);
        emptyAddressStateText = view.findViewById(R.id.emptyAddressStateText);
        emptyAddressPincodeText = view.findViewById(R.id.emptyAddressPincodeText);
        emptyAddressPhoneNoText = view.findViewById(R.id.emptyAddressPhoneNoText);


        bearerToken = sharedPrefManager.getBearerToken();

        emptyAddressNameText.setText(sharedPrefManager.getFullName());

        ////Virtual Address Copy Button

        OrderActivity.bottomNavigationView.getMenu().findItem(R.id.accountMenu).setChecked(true);
        emptyAddressNameCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Name", emptyAddressNameText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressLine1CopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Line1", emptyAddressLine1Text.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressLine2CopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Line2", emptyAddressLine2Text.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressLandmarkCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Landmark", emptyAddressLandmarkText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressCityCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("City", emptyAddressCityText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressStateCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("State", emptyAddressStateText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressPincodeCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Pincode", emptyAddressPincodeText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        emptyAddressPhoneNOCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Phone No", emptyAddressPhoneNoText.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        getDeliveryAddrsAdapter = new GetDeliveryAddrsAdapter(list, getContext(), new GetDeliveryAddrsAdapter.setDefaultAddress() {
            @Override
            public void defaultAdddressSet(DeliveryListModel.Address address) {

            }

            @Override
            public void getData(DeliveryListModel.Address address) {

            }

            @Override
            public void deleteData(DeliveryListModel.Address address) {

            }
        });
        deliveryRecyclerView.setAdapter(getDeliveryAddrsAdapter);


        ////Visibility Shown according to values either its empty or not


        int number = getDeliveryAddrsAdapter.getItemCount();
        if (number == 0) {

            emptyDeliveryAdrsCard.setVisibility(View.VISIBLE);
            deliveryAddrsCard.setVisibility(View.GONE);
        } else {

            emptyDeliveryAdrsCard.setVisibility(View.GONE);
            deliveryAddrsCard.setVisibility(View.VISIBLE);

        }
        getDeliveryAddrsAdapter.notifyDataSetChanged();


////Fetching All Delivery Address

        LoadingDialog.showLoadingDialog(getActivity(), "");
        fetchAddress();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);


        //////Add More Delivery Address

        addMoreAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new AddAddress(), null)
                        .addToBackStack(null)
                        .commit();


            }
        });


        billingAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AddAddress addAddress = new AddAddress();
////                Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", id);
//                bundle.putString("type", "updateBilling");


                Bundle bundle = new Bundle();
                bundle.putSerializable("address", (Serializable) deliveryAddress);
                bundle.putString("type", "updateBilling");

                // Set Fragment class Arguments
                AddAddress addAddress1 = new AddAddress();
                addAddress1.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, addAddress1, null).addToBackStack(null).commit();

                // Set Fragment class Arguments

//                addAddress1.setArguments(bundle);
//                OrderActivity.fragmentManager.beginTransaction()
//                        .replace(R.id.orderFrameLayout, addAddress1, null).addToBackStack(null).commit();

            }
        });
        ///Set Default Address Button

        setDefaultAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addressId == null) {
                    Toast.makeText(getActivity(), "Please Select an address ", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    setDefaultAddress();
                }


            }
        });


/////Add Billing Address Button


        billingAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "billing");

                // Set Fragment class Arguments
                AddAddress addAddress = new AddAddress();
                addAddress.setArguments(bundle);
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, addAddress, null).addToBackStack(null).commit();
            }
        });

        /////Add Delivery Address Button

        deliveryAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new AddAddress(), null)
                        .addToBackStack(null)
                        .commit();


            }
        });

        return view;
    }


    ////Set Default Address API call

    private void setDefaultAddress() {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("is_default", true);

        Call<CommonModel> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .setDefault("Bearer " + bearerToken, addressId, jsonObject.toString());

        call.enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                if (response.code() == 200) {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    addressId = null;
                    fetchAddress();

                    getDeliveryAddrsAdapter.notifyDataSetChanged();


                } else if (response.code() == 401) {


                    callRefreshTokenApi();
                } else {


                }


            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();

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
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    fetchAddress();
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

//    Fetch All delivery Addresses


    private void fetchAddress() {

        Call<DeliveryListModel> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .getAddresses("Bearer " + bearerToken);

        call.enqueue(new Callback<DeliveryListModel>() {
            @Override
            public void onResponse(Call<DeliveryListModel> call, Response<DeliveryListModel> response) {

                try {
                    if (response.code() == 200) {

                        LoadingDialog.cancelLoading();


                        list = response.body().getAddresses();


                        for (int i = 0; i < list.size(); i++) {
                            deliveryAddress = list.get(i);
                            boolean isBilling = response.body().getAddresses().get(i).getBillingAddress();

                            if (isBilling) {
                                billingAddressBox.setVisibility(View.VISIBLE);
                                billingAddressText.setVisibility(View.GONE);
                                billingAddAddressBtn.setVisibility(View.GONE);
                                id = response.body().getAddresses().get(i).getId();
                                billingAddressName.setText(response.body().getAddresses().get(i).getName());
                                billingAddressContactNumber.setText(response.body().getAddresses().get(i).getPhone());
                                String line1A = response.body().getAddresses().get(i).getLine1();
                                String stateA = response.body().getAddresses().get(i).getState();
                                String countryA = response.body().getAddresses().get(i).getCountry().getName();

                                billingAddress.setText(line1A + " " + "\n" + stateA + " " + "\n" + countryA);
                                list.remove(i);
                            }

                        }
                        getDeliveryAddrsAdapter = new GetDeliveryAddrsAdapter(list, getContext(), new GetDeliveryAddrsAdapter.setDefaultAddress() {
                            @Override
                            public void defaultAdddressSet(DeliveryListModel.Address address) {
                                addressId = address.getId();
                            }

                            @Override
                            public void getData(DeliveryListModel.Address address) {
                                DeliveryListModel.Address address1 = address;


                                Bundle bundle = new Bundle();
                                bundle.putSerializable("address", address1);
                                bundle.putString("type", "update");

                                // Set Fragment class Arguments
                                AddAddress addAddress = new AddAddress();
                                addAddress.setArguments(bundle);
                                OrderActivity.fragmentManager.beginTransaction()
                                        .replace(R.id.orderFrameLayout, addAddress, null).addToBackStack(null).commit();

                            }

                            @Override
                            public void deleteData(DeliveryListModel.Address address) {
                                DeliveryListModel.Address address1 = address;


                                new AlertDialog.Builder(getActivity())
                                        .setMessage("Are you sure want to Delete?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                LoadingDialog.showLoadingDialog(getActivity(), "");
                                                callDeleteApi(address1.getId());
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        });
                        deliveryRecyclerView.setAdapter(getDeliveryAddrsAdapter);


                        int number = deliveryRecyclerView.getAdapter().getItemCount();
                        if (number == 0) {

                            emptyDeliveryAdrsCard.setVisibility(View.VISIBLE);
                            deliveryAddrsCard.setVisibility(View.GONE);
//                            billingAddressBox.setVisibility(View.GONE);
//                            billingAddressText.setVisibility(View.VISIBLE);
//                            billingAddAddressBtn.setVisibility(View.VISIBLE);
                        } else {

                            emptyDeliveryAdrsCard.setVisibility(View.GONE);
                            deliveryAddrsCard.setVisibility(View.VISIBLE);
//                            billingAddressBox.setVisibility(View.VISIBLE);
//                            billingAddressText.setVisibility(View.GONE);
//                            billingAddAddressBtn.setVisibility(View.GONE);


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

    private void callDeleteApi(Integer id) {
        Call<DeleteAddressResponse> call = RetrofitClient3
                .getInstance3().getAppApi().deleteAddress("Bearer " + sharedPrefManager.getBearerToken()
                        , id);
        call.enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {
                if (response.code() == 200) {

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getStatus(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    getDeliveryAddrsAdapter.notifyDataSetChanged();
                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getErrorDescription(),
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        fetchAddress();
    }


    ///Spinner Adapter
    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.all_address_spinner, allAddress);
        allAddressSpinner.setAdapter(arrayAdapter);
    }


}