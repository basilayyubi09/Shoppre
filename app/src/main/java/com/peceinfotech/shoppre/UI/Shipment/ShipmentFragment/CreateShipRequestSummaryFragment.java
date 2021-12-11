package com.peceinfotech.shoppre.UI.Shipment.ShipmentFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.Item;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateAddressResponse;
import com.peceinfotech.shoppre.Adapters.CreateShipAdapters.ShowAddressPopUpAdapter;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.CreateShipmentModelResponse.CreateShipmentResponse;
import com.peceinfotech.shoppre.LockerModelResponse.ShipmentMeta;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderFragments.ThankYouFragment;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateShipRequestSummaryFragment extends Fragment {

    CheckBox checkBoxCreateShipment;
    LinearLayout billingAddressLayout, addressForm, spinnerCountryLayout;
    MaterialButton createShipBtn;
    Spinner createShipmentTitleSpinner;
    ImageView titleTriangle;
    List<String> choicesList;
    String liquid;
    boolean photoAmount = false, consolidation = false, gift = false, addExtraValue = false, shipInOriginal = false, giftWrapValue = false, expressProcessing = false, discardShoe = false;
    RelativeLayout shippingCharges, consolidationCharges, discardShoeBoxes,
            addExtra, shipIn, giftWrap, giftNote, setMax, express, overWeight, photoCharges;
    DeliveryListModel.Address address;
    String allIds;
    LinearLayout useLayout;
    TextInputLayout updateProfileNumber;
    DeliveryListModel.Address billingAdd;
    List<DeliveryListModel.Address> addressList;
    TextView addressText, name, estimatePrice, number, billingName, spinnerCountry,
            billingEdit, billingAddress, billingNumber, billingTitle, photoPriceText, consPrice;
    String[] title = {"Title", "Mr", "Ms", "Mrs"};
    boolean isBilling = false;
    Integer countryId;
    TextView addressError, cityError, stateError, pinCodeError, nameError, phoneError, countryError, titleError;
    ShipmentMeta meta;
    MaterialButton addAddressBtn;
    String salutation, nameString, phoneNumberString, addressLine1String, addressLine2String, cityString, country, pinCodeString, cc, stateString;
    CountryCodePicker countryCodePicker;
    Integer estimateTotal = 0, billingId, deliveryId;
    EditText addressLine1, addressLine2, city, state, pinCode, billingEditName;
    CardView back;
    SharedPrefManager sharedPrefManager;
    ArrayAdapter<Item> arrayAdapter;
    List<Item> list, duplicateList;
    boolean isVisible = false;
    List<DeliveryListModel.Address> list1;
    ShowAddressPopUpAdapter showAddressPopUpAdapter;
    DeliveryListModel.Address interfacedAddress;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ship_request_summary, container, false);

        addressList = new ArrayList<>();

        nameError = view.findViewById(R.id.nameError);
        phoneError = view.findViewById(R.id.phoneError);
        titleError = view.findViewById(R.id.titleError);
        countryError = view.findViewById(R.id.countryError);
        addressError = view.findViewById(R.id.addressError);
        cityError = view.findViewById(R.id.cityError);
        stateError = view.findViewById(R.id.stateError);
        pinCodeError = view.findViewById(R.id.pinCodeError);


        billingAddressLayout = view.findViewById(R.id.billingAddressLayout);
        addressForm = view.findViewById(R.id.addressForm);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        countryCodePicker = view.findViewById(R.id.countryCodePicker);
        photoPriceText = view.findViewById(R.id.photoPriceText);
        createShipmentTitleSpinner = view.findViewById(R.id.createShipmentTitleSpinner);
        consPrice = view.findViewById(R.id.consPrice);
        createShipBtn = view.findViewById(R.id.createShipBtn);
        checkBoxCreateShipment = view.findViewById(R.id.checkBoxCreateShipment);
        createShipmentTitleSpinner = view.findViewById(R.id.createShipmentTitleSpinner);
        titleTriangle = view.findViewById(R.id.titleTriangle);
        billingEdit = view.findViewById(R.id.billingEdit);
        addAddressBtn = view.findViewById(R.id.addAddressBtn);
        addressText = view.findViewById(R.id.address);
        back = view.findViewById(R.id.back);
        name = view.findViewById(R.id.deliveryAddressName);
        number = view.findViewById(R.id.number);
        estimatePrice = view.findViewById(R.id.estimatePrice);
        billingEditName = view.findViewById(R.id.name);
        billingName = view.findViewById(R.id.billingAddressName);
        billingAddress = view.findViewById(R.id.billingAddressText);
        billingNumber = view.findViewById(R.id.billingAddressNumberText);
        shippingCharges = view.findViewById(R.id.shippingCharges);
        consolidationCharges = view.findViewById(R.id.consolidationCharges);
        discardShoeBoxes = view.findViewById(R.id.discardShoeBoxes);
        addExtra = view.findViewById(R.id.addExtra);
        shipIn = view.findViewById(R.id.shipIn);
        giftWrap = view.findViewById(R.id.giftWrap);
        giftNote = view.findViewById(R.id.giftNote);
        setMax = view.findViewById(R.id.setMax);
        express = view.findViewById(R.id.express);
        overWeight = view.findViewById(R.id.overWeight);
        useLayout = view.findViewById(R.id.useLayout);
        updateProfileNumber = view.findViewById(R.id.updateProfileNumber);
        addressLine1 = view.findViewById(R.id.addressLine1);
        addressLine2 = view.findViewById(R.id.addressLine2);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        pinCode = view.findViewById(R.id.pinCode);
        billingTitle = view.findViewById(R.id.title);
        photoCharges = view.findViewById(R.id.photoCharges);
        spinnerCountryLayout = view.findViewById(R.id.spinnerCountryLayoutSummaryFragment);
        change = view.findViewById(R.id.change);
        editAddress = view.findViewById(R.id.editAddress);
        addAddress = view.findViewById(R.id.addAddress);
        list1 = new ArrayList<>();
        setCountryList("");


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            choicesList = (List<String>) bundle.getSerializable("array");
            addressList = (List<DeliveryListModel.Address>) bundle.getSerializable("addressList");
            allIds = bundle.getString("allIds");
            liquid = bundle.getString("liquid");
            meta = (ShipmentMeta) bundle.getSerializable("meta");
            address = (DeliveryListModel.Address) bundle.getSerializable("address");


            deliveryId = address.getId();
            name.setText(address.getName());
            if (!address.getLine2().equals("")) {
                addressText.setText(address.getLine1() + "\n"
                        + address.getLine2() + "\n"
                        + address.getCity() + " - "
                        + address.getState() + "\n"
                        + address.getPincode() + "\n"
                        + address.getCountry().getName());
            } else {
                addressText.setText(address.getLine1() + "\n"
                        + address.getCity() + " - "
                        + address.getState() + "\n"
                        + address.getPincode() + "\n"
                        + address.getCountry().getName());

            }

            if (!address.getPhone().equals("")) {
                number.setText(String.valueOf(address.getPhone()));
            } else {
                number.setText("");
            }


        }


        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressDialog();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressDialog();
            }
        });




        ////Country Dropdown

        spinnerCountryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
            }
        });
        spinnerCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCountryLayout.performClick();
            }
        });


        sharedPrefManager = new SharedPrefManager(getActivity());
        setUpBillingAddress();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        createShipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (!isVisible) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("address_id", deliveryId);
                    jsonObject.addProperty("billingAddress", false);
                    jsonObject.addProperty("billing_address_id", deliveryId);
                    jsonObject.addProperty("express_processing", expressProcessing);
                    jsonObject.addProperty("extra_packing", addExtraValue);
                    jsonObject.addProperty("gift_note", gift);
                    jsonObject.addProperty("gift_note_text", "");
                    jsonObject.addProperty("gift_wrap", giftWrapValue);
                    jsonObject.addProperty("is_liquid", liquid);
                    jsonObject.addProperty("original", shipInOriginal);


                    callCreateShipmentApi(jsonObject);

                } else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("address_id", deliveryId);
                    jsonObject.addProperty("billingAddress", false);
                    jsonObject.addProperty("billing_address_id", billingId);
                    jsonObject.addProperty("express_processing", expressProcessing);
                    jsonObject.addProperty("extra_packing", addExtraValue);
                    jsonObject.addProperty("gift_note", gift);
                    jsonObject.addProperty("gift_note_text", "");
                    jsonObject.addProperty("gift_wrap", giftWrapValue);


                    jsonObject.addProperty("is_liquid", liquid);

                    jsonObject.addProperty("max_weight", 0);
                    jsonObject.addProperty("original", shipInOriginal);


                    callCreateShipmentApi(jsonObject);

                }

            }
        });

        final List<String> titleList = new ArrayList<>(Arrays.asList(title));

        final ArrayAdapter<String> createShipmentTitleArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.title_spinner, titleList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView titletv = (TextView) view1;

                if (position == 0) {
                    titletv.setTextColor(Color.GRAY);
                    titletv.setVisibility(View.GONE);
                } else {
                    titletv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();

                if (!validateSalutation() || !validateName() || !validatePhone() || !validateAddress()
                        || !validateCity() || !validateState() || !validateCountry() || !validatePinCode()) {
                    return;
                } else {

                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callApi();
                }
            }
        });
        createShipmentTitleArrayAdapter.setDropDownViewResource(R.layout.title_spinner);
        createShipmentTitleSpinner.setAdapter(createShipmentTitleArrayAdapter);

        createShipmentTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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

        checkBoxCreateShipment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxCreateShipment.isChecked()) {
                    if (isBilling) {
                        addressForm.setVisibility(View.GONE);
                        billingAddressLayout.setVisibility(View.VISIBLE);
                        isVisible = true;
                    } else {
                        addressForm.setVisibility(View.VISIBLE);
                        billingAddressLayout.setVisibility(View.GONE);
                        isVisible = false;

                    }

                } else {
                    isVisible = false;
                    addressForm.setVisibility(View.GONE);
                    billingAddressLayout.setVisibility(View.GONE);
                }
            }
        });

        billingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingAddressLayout.setVisibility(View.GONE);
                checkBoxCreateShipment.setChecked(true);
                addressForm.setVisibility(View.VISIBLE);
                setValuesOnEditField();

            }
        });
        handleEstimate();
        return view;
    }

    private void AddAddressDialog() {

        Dialog dialog1 = new Dialog(getContext());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.add_address_popup);

        ImageView closeBtn;

        closeBtn = dialog1.findViewById(R.id.closeBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();

    }

    private void showAddressDialog() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.show_address_popup);
        dialog.setCancelable(false);

        RecyclerView showAddressPopupRecycler;
        ImageView close;
        showAddressPopupRecycler = dialog.findViewById(R.id.showAddressPopupRecycler);
        close = dialog.findViewById(R.id.showAddressClose);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        LoadingDialog.showLoadingDialog(getActivity(), "");
        showAllAddressApi(showAddressPopupRecycler, dialog);

        dialog.show();

    }

    private void showAllAddressApi(RecyclerView showAddressPopupRecycler, Dialog dialog) {
        Call<DeliveryListModel> call = RetrofitClient3.getInstance3().getAppApi().getAddresses("Bearer "+sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<DeliveryListModel>() {
            @Override
            public void onResponse(Call<DeliveryListModel> call, Response<DeliveryListModel> response) {
                if (response.code()==200){
                    list1 = response.body().getAddresses();
                    showAddressPopUpAdapter = new ShowAddressPopUpAdapter(list1, getActivity(), new ShowAddressPopUpAdapter.Interface() {
                        @Override
                        public void popUpRadioButtonOperation(DeliveryListModel.Address address, RadioButton showAddressRadioButton) {


                            interfacedAddress = address;

                            name.setText(interfacedAddress.getName());
                            if (!interfacedAddress.getLine1().equals("")){
                                addressText.setText(interfacedAddress.getLine1() + "\n"
                                        + interfacedAddress.getLine2() + "\n"
                                        + interfacedAddress.getCity() + " - "
                                        + interfacedAddress.getState() + "\n"
                                        + interfacedAddress.getCountry().getName());
                            }else {
                                addressText.setText(interfacedAddress.getLine1() + "\n"
                                        + interfacedAddress.getCity() + " - "
                                        + interfacedAddress.getState() + "\n"
                                        + interfacedAddress.getCountry().getName());
                            }

                            if (!interfacedAddress.getPhone().equals("")) {
                                number.setText(String.valueOf(interfacedAddress.getPhone()));
                            } else {
                                number.setText("");
                            }

                            LoadingDialog.cancelLoading();
                            dialog.dismiss();
                        }
                    });
                    showAddressPopupRecycler.setAdapter(showAddressPopUpAdapter);


                }else if (response.code()==401){
                    callRefreshTokenApi();
                    LoadingDialog.cancelLoading();
                }else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeliveryListModel> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callCreateShipmentApi(JsonObject jsonObject) {
        LoadingDialog.showLoadingDialog(getActivity() , "");
        Call<CreateShipmentResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi().createShipment("Bearer " + sharedPrefManager.getBearerToken(), allIds, jsonObject.toString());
        call.enqueue(new Callback<CreateShipmentResponse>() {
            @Override
            public void onResponse(Call<CreateShipmentResponse> call, Response<CreateShipmentResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    Bundle bundle = new Bundle();
                    bundle.putString("type" , "summary");
                    ThankYouFragment thankYouFragment = new ThankYouFragment();
                    thankYouFragment.setArguments(bundle);
                    OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout
                            ,thankYouFragment , null).addToBackStack(null).commit();
                }
                else if (response.code()==401){
                    callRefreshTokenApi("create " , jsonObject);
                }
                else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateShipmentResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleEstimate() {

        if (meta.getPhotoAmount() > 0) {
            photoAmount = true;
            photoCharges.setVisibility(View.VISIBLE);
            estimateTotal = meta.getPhotoAmount() + estimateTotal;
            photoPriceText.setText("₹ " + String.valueOf(meta.getPhotoAmount()));
        }
        if (meta.getConsolidationChargeAmount() > 0) {
            consolidation = true;
            consolidationCharges.setVisibility(View.VISIBLE);
            estimateTotal = meta.getConsolidationChargeAmount() + estimateTotal;
            consPrice.setText("₹ " + String.valueOf(meta.getConsolidationChargeAmount()));

        }
        if (choicesList.contains("gift")) {
            gift = true;
            giftNote.setVisibility(View.VISIBLE);
            estimateTotal = 50 + estimateTotal;
        }

        if (choicesList.contains("add")) {
            addExtraValue = true;
            addExtra.setVisibility(View.VISIBLE);
            estimateTotal = 500 + estimateTotal;
        }

        if (choicesList.contains("ship")) {
            shipInOriginal = true;
            shipIn.setVisibility(View.VISIBLE);
        }
        if (choicesList.contains("wrap")) {
            giftWrapValue = true;
            giftWrap.setVisibility(View.VISIBLE);
            estimateTotal = 100 + estimateTotal;
        }
        if (choicesList.contains("express")) {
            expressProcessing = true;
            express.setVisibility(View.VISIBLE);
            estimateTotal = 200 + estimateTotal;
        }
        if (choicesList.contains("discard")) {
            discardShoe = true;
            discardShoeBoxes.setVisibility(View.VISIBLE);
            estimateTotal = 100 + estimateTotal;
        }
        estimatePrice.setText("₹ " + String.valueOf(estimateTotal));
    }


    private boolean validateSalutation() {
        if (salutation.equals("Title")) {
            titleError.setVisibility(View.VISIBLE);
            return false;
        } else {
            titleError.setVisibility(View.GONE);
            return true;
        }
    }


    private boolean validateName() {
        if (nameString.equals("")) {
            nameError.setVisibility(View.VISIBLE);
            return false;
        } else {
            nameError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validatePhone() {
        if (phoneNumberString.equals("")) {
            phoneError.setVisibility(View.VISIBLE);
            return false;
        } else {
            phoneError.setVisibility(View.GONE);
            return true;
        }
    }


    private boolean validateAddress() {
        if (addressLine1String.equals("")) {
            addressError.setVisibility(View.VISIBLE);
            return false;
        } else {
            addressError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateCity() {
        if (cityString.equals("")) {
            cityError.setVisibility(View.VISIBLE);
            return false;
        } else {
            cityError.setVisibility(View.GONE);
            return true;
        }
    }


    private boolean validateState() {
        if (stateString.equals("")) {
            stateError.setVisibility(View.VISIBLE);
            return false;
        } else {
            stateError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateCountry() {
        if (country.equals("")) {
            countryError.setVisibility(View.VISIBLE);
            return false;
        } else {
            countryError.setVisibility(View.GONE);
            return true;
        }
    }


    private boolean validatePinCode() {
        if (pinCodeString.equals("")) {
            pinCodeError.setVisibility(View.VISIBLE);
            return false;
        } else {
            pinCodeError.setVisibility(View.GONE);
            return true;
        }
    }

    private void setValuesOnEditField() {

        checkBoxCreateShipment.setChecked(true);
        addAddressBtn.setText("Update Address");
        billingTitle.setText("Update Billing Address");

        billingEditName.setText(nameString);
        updateProfileNumber.getEditText().setText(phoneNumberString);
        addressLine1.setText(addressLine1String);
        city.setText(cityString);
        state.setText(stateString);
        spinnerCountry.setText(country);
        pinCode.setText(pinCodeString);

    }


    private void setUpBillingAddress() {

        for (int i = 0; i < addressList.size(); i++) {
            DeliveryListModel.Address deliveryAddress = addressList.get(i);

            if (deliveryAddress.getBillingAddress()) {
                if (deliveryAddress.getBillingAddress() != null) {
                    isBilling = true;
                    billingId = deliveryAddress.getId();
                    billingAdd = deliveryAddress;
                    callAddBillingItem(deliveryAddress);
                    addressForm.setVisibility(View.GONE);
                    useLayout.setVisibility(View.VISIBLE);
                    billingAddressLayout.setVisibility(View.GONE);
                } else {
                    useLayout.setVisibility(View.GONE);
                    isBilling = false;
                    addressForm.setVisibility(View.VISIBLE);
                    billingAddressLayout.setVisibility(View.GONE);
                }
            }


        }
    }

    @SuppressLint("SetTextI18n")
    private void callAddBillingItem(DeliveryListModel.Address deliveryAddress) {


        nameString = deliveryAddress.getName();
        addressLine1String = deliveryAddress.getLine1();
        addressLine2String = deliveryAddress.getLine2();
        cityString = deliveryAddress.getCity();
        stateString = deliveryAddress.getState();
        salutation = deliveryAddress.getSalutation();
        country = deliveryAddress.getCountry().getName();
        phoneNumberString = deliveryAddress.getPhone();
        pinCodeString = deliveryAddress.getPincode();

        billingId = deliveryAddress.getId();


        billingName.setText(nameString);
        billingNumber.setText(phoneNumberString);
        billingAddress.setText(addressLine1String + "\n"
                + cityString + " - "
                + stateString + "\n"
                + pinCodeString + "\n"
                + country);

    }

    @SuppressLint("SetTextI18n")
    private void setTextAfterUpdate() {
        billingName.setText(nameString);
        billingNumber.setText(phoneNumberString);
        getTextFromField();
        if (addressLine2String.equals("")) {
            billingAddress.setText(addressLine1String + "\n"
                    + addressLine2String + "\n"
                    + cityString + " - "
                    + stateString + "\n"
                    + country);
        } else {
            billingAddress.setText(addressLine1String + "\n"
                    + cityString + " - "
                    + stateString + "\n"
                    + country);
        }
    }

    private void getTextFromField() {
        nameString = billingEditName.getText().toString().trim();
        addressLine1String = addressLine1.getText().toString().trim();
        addressLine2String = addressLine2.getText().toString().trim();
        cityString = city.getText().toString().trim();
        stateString = state.getText().toString().trim();
        pinCodeString = pinCode.getText().toString().trim();
        phoneNumberString = updateProfileNumber.getEditText().getText().toString().trim();
        salutation = createShipmentTitleSpinner.getSelectedItem().toString();
        country = spinnerCountry.getText().toString();
        cc = countryCodePicker.getSelectedCountryCode();


    }

    private void callApi() {
        getTextFromField();
        String firstName, lastName = "";


        if (nameString.split("\\w+").length > 1) {

            lastName = nameString.substring(nameString.lastIndexOf(" ") + 1);
            firstName = nameString.substring(0, nameString.lastIndexOf(' '));
        } else {
            firstName = nameString;
        }

        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("salutation", salutation);
        paramObject.addProperty("first_name", firstName);
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("line1", addressLine1String);
        paramObject.addProperty("line2", addressLine2String);
        paramObject.addProperty("city", cityString);
        paramObject.addProperty("state", stateString);
        paramObject.addProperty("country_id", countryId);
        paramObject.addProperty("pincode", pinCodeString);
        paramObject.addProperty("phone", "+" + cc + phoneNumberString);
        paramObject.addProperty("is_default", false);
        paramObject.addProperty("customer_id", sharedPrefManager.getId());
        paramObject.addProperty("is_billing_address", true);
        paramObject.addProperty("name", nameString);
        String bearerToken = sharedPrefManager.getBearerToken();
        Toast.makeText(getActivity(), paramObject.toString(), Toast.LENGTH_SHORT).show();
        Call<UpdateAddressResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .UpdateAddress("Bearer " + sharedPrefManager.getBearerToken()
                        , billingId
                        , paramObject.toString());
        String finalLastName = lastName;
        call.enqueue(new Callback<UpdateAddressResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                if (response.code() == 200) {

                    billingAddressLayout.setVisibility(View.VISIBLE);
                    addressForm.setVisibility(View.GONE);
//                    callAddBillingItem(newAddress);
                    billingName.setText(nameString);
                    billingNumber.setText(phoneNumberString);
                    billingAddress.setText(addressLine1String + "\n"
                            + cityString + " - "
                            + stateString + "\n"
                            + country);

                    checkBoxCreateShipment.setChecked(false);
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Address Updated Successfully", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else if (response.code() == 401) {
//
                    JsonObject jsonObject = new JsonObject();
                    callRefreshTokenApi("" , jsonObject);

                } else if (response.code() == 406) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }

    private void callRefreshTokenApi(String type, JsonObject jsonObject) {
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
                    if (type.equals("create")){
                        callCreateShipmentApi(jsonObject);
                    }
                    else {
                        callApi();
                    }

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


    private void showDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View row = getLayoutInflater().inflate(R.layout.country_dialog, null);

        ListView listView = row.findViewById(R.id.dialogListView);
        TextInputLayout inputLayout = row.findViewById(R.id.dialogSearch);


        AlertDialog dialog = builder.create();
        arrayAdapter = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        listView.setTextFilterEnabled(true);
        dialog.setView(row);

        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if (item instanceof Item) {
                    Item item1 = (Item) item;
                    countryId = ((Item) item).getId();
                    String str = ((Item) item).getCountryCode().toString().split("[\\(\\)]")[1];

                }
                spinnerCountry.setText(adapterView.getItemAtPosition(i).toString());
                spinnerCountry.setTextColor(R.color.black);
                inputLayout.getEditText().setText("");
                dialog.dismiss();
            }
        });
        dialog.show();


        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals(null) || charSequence.length() == 0) {
                    arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1, list);
                listView.setAdapter(arrayAdapter);
                setCountryList(editable.toString());
                arrayAdapter.getFilter().filter(editable);
                arrayAdapter.notifyDataSetChanged();

            }
        });


    }

    private void setCountryList(String typedString) {
        Call<CountryResponse> call = RetrofitClient3.getInstance3().getAppApi()
                .getCountry("Country", typedString);
        call.enqueue(new Callback<CountryResponse>() {

            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code() == 200) {
                    list = response.body().getItems();
                    duplicateList = response.body().getItems();

                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}