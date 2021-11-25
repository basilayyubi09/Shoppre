package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.Item;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateAddressResponse;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.SignupLogin.SignUp_Valid;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends Fragment {



    Spinner chooseTitleAddAddress;
    ImageView closeBtn, triangleDropdown;
    TextView spinnerCountry;
    TextView salutationText;
    LinearLayout phoneInputLayout;
    EditText name, addressLine1, addressLine2, city, state, pinCode, phoneNumber;
    AppCompatCheckBox checkBox;
    MaterialButton addAddressBtn;
    List<Item> list, duplicateList;
    ArrayAdapter<Item> arrayAdapter;
    String type = "";
    SharedPrefManager sharedPrefManager;
    TextView title, countryCodeTextView;
    boolean is_billing;
    DeliveryListModel.Address address;
    int countryId;
    boolean is_default = false;
    String[] titleArray = new String[]{"Title", "Mr", "Ms", "Mrs"};
    String nameString, addressLine1String, addressLine2String, countryCode, cityString, stateString, pinCodeString, phoneNumberString, salutation, country;
    Integer cc;
    int id;
    FrameLayout main;
    TextView addressError, cityError, stateError, pinCodeError, nameError, phoneError, countryError, titleError;
    LinearLayout spinnerCountryLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_adress, container, false);


        sharedPrefManager = new SharedPrefManager(getActivity());

        sharedPrefManager.fragmentValue("accounts");

        setCountryList("");

        //Hooks
        chooseTitleAddAddress = view.findViewById(R.id.chooseTitleAddAddress);
//        spinnerPhoneNo = view.findViewById(R.id.countryCodeTextView);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);

        title = view.findViewById(R.id.title);
        main = view.findViewById(R.id.main);
//        errorNo = view.findViewById(R.id.errorNo);

        name = view.findViewById(R.id.name);
        salutationText = view.findViewById(R.id.addAddressTitle);
        phoneInputLayout = view.findViewById(R.id.phoneInputLayout);
        nameError = view.findViewById(R.id.nameError);
        phoneError = view.findViewById(R.id.phoneError);
        titleError = view.findViewById(R.id.titleError);
        countryError = view.findViewById(R.id.countryError);
        countryCodeTextView = view.findViewById(R.id.countryCodeTextView);
        triangleDropdown = view.findViewById(R.id.triangleDropdown);

        addressError = view.findViewById(R.id.addressError);
        cityError = view.findViewById(R.id.cityError);
        stateError = view.findViewById(R.id.stateError);
        pinCodeError = view.findViewById(R.id.pinCodeError);

        addressLine1 = view.findViewById(R.id.addressLine1);
        addressLine2 = view.findViewById(R.id.addressLine2);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        pinCode = view.findViewById(R.id.pinCode);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        checkBox = view.findViewById(R.id.checkBox);
        addAddressBtn = view.findViewById(R.id.addAddressBtn);
        closeBtn = view.findViewById(R.id.closeBtn);
        triangleDropdown = view.findViewById(R.id.triangleDropdown);
        spinnerCountryLayout = view.findViewById(R.id.spinnerCountryLayout);



        final List<String> titleList = new ArrayList<>(Arrays.asList(titleArray));


        final ArrayAdapter<String> titleSpinerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.add_address_title, titleList){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView titleTv = (TextView) view1;
                if (position == 0){
                    titleTv.setVisibility(View.GONE);
                    titleTv.setTextColor(Color.GRAY);
                }else {
                    titleTv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };
        titleSpinerArrayAdapter.setDropDownViewResource(R.layout.add_address_title);
        chooseTitleAddAddress.setAdapter(titleSpinerArrayAdapter);

        chooseTitleAddAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        spinnerCountryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCountry.performClick();
            }
        });


        triangleDropdown.setOnClickListener(view1 -> spinnerCountry.performClick());

        setupUI(main);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            type = getArguments().getString("type");
            id = getArguments().getInt("id");
            if (type.equals("update")) {
                address = (DeliveryListModel.Address) getArguments().getSerializable("address");
                id = ((DeliveryListModel.Address) getArguments().getSerializable("address")).getId();
                is_billing = ((DeliveryListModel.Address) getArguments().getSerializable("address")).getBillingAddress();
                setTextsOnFields();
                addAddressBtn.setText("Update Address");
                addAddressBtn.setLetterSpacing((float) 0.03);

                title.setText(R.string.update_address);
            } else if (type.equals("billing")) {
                title.setText("Billing Address");

                is_billing = true;
            }
            else if(type.equals("updateBilling")){
                is_billing = true;
                address = (DeliveryListModel.Address) getArguments().getSerializable("address");
                id = ((DeliveryListModel.Address) getArguments().getSerializable("address")).getId();
                is_billing = ((DeliveryListModel.Address) getArguments().getSerializable("address")).getBillingAddress();
                setTextsOnFields();
                title.setText("Update Billing Address");
                addAddressBtn.setText("Update Address");
                addAddressBtn.setLetterSpacing((float) 0.03);
            }else if (type.equals("deliveryAddress")){
                is_billing = false;
                title.setText("Add Shipping Address");
            }
            else {
                is_billing = false;
                title.setText("Add Address");
            }

        }


        spinnerCountry.setOnClickListener(view12 -> showDialogue());
        getTextFromField();


        addAddressBtn.setOnClickListener(view13 -> {
            getTextFromField();
            if (!validateSalutation() || !validateName() || !validateAddress()
                    || !validateCity() || !validateState() || !validateCountry()
                    || !validatePinCode() || !validatePhone()) {
                return;
            }

            if (type.equals("update")) {
                getTextFromField();
//                    Toast.makeText(getActivity(), String.valueOf(sharedPrefManager.getId()), Toast.LENGTH_SHORT).show();
                LoadingDialog.showLoadingDialog(getActivity(), "");

                callUpdateApi(id);
            }
            else if(type.equals("updateBilling")) {
                LoadingDialog.showLoadingDialog(getActivity(), "");
                is_billing = true;
                callUpdateApi(id);
            }else
             {
                LoadingDialog.showLoadingDialog(getActivity(), "");
                callApi(view13);
            }

        });

        closeBtn.setOnClickListener(view14 -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void setTextsOnFields() {
        name.setText(address.getName());
        addressLine1.setText(address.getLine1());
        addressLine2.setText(address.getLine2());
        city.setText(address.getCity());
        state.setText(address.getState());
        pinCode.setText(address.getPincode());
        phoneNumber.setText(address.getPhone());
        spinnerCountry.setText(address.getCountry().getName());


        if (address.getIsDefault()) {
            checkBox.setChecked(true);
            is_default = true;
        } else is_default = false;

    }

    private void callUpdateApi( int i) {
        JsonObject object = new JsonObject();
        String firstName, lastName = "";

        if (countryId == 0) {
            countryId = address.getCountryId();
        }

        if (nameString.split("\\w+").length > 1) {

            lastName = nameString.substring(nameString.lastIndexOf(" ") + 1);
            firstName = nameString.substring(0, nameString.lastIndexOf(' '));
        } else {
            firstName = nameString;
        }
        object.addProperty("name", nameString);
        object.addProperty("salutation", salutation);
        object.addProperty("first_name", firstName);
        object.addProperty("last_name", lastName);
        object.addProperty("line1", addressLine1String);
        object.addProperty("line2", addressLine2String);
        object.addProperty("city", cityString);
        object.addProperty("state", stateString);
        object.addProperty("country_id", countryId);
        object.addProperty("pincode", pinCodeString);
        if (cc == null) {
            object.addProperty("phone", phoneNumberString);

        } else {
            object.addProperty("phone", cc + phoneNumberString);

        }
//        Toast.makeText(getActivity(), String.valueOf(cc), Toast.LENGTH_SHORT).show();
        object.addProperty("is_default", is_default);
        object.addProperty("customer_id", sharedPrefManager.getId());
        object.addProperty("is_billing_address", is_billing);

        Call<UpdateAddressResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .UpdateAddress("Bearer " + sharedPrefManager.getBearerToken()
                        , i
                        , object.toString());
        call.enqueue(new Callback<UpdateAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    clearFields();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    getActivity().getSupportFragmentManager().popBackStack();
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


    private void showDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View row = getLayoutInflater().inflate(R.layout.country_dialog, null);

        ListView listView = row.findViewById(R.id.dialogListView);
        TextInputLayout inputLayout = row.findViewById(R.id.dialogSearch);


        AlertDialog dialog = builder.create();
        arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1, list);
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
                    String str = ((Item) item).getCountryCode().toString().split("[\\(\\)]")[1];
                    cc = ((Item) item).getPhoneCode();
                    countryId = ((Item) item).getId();
                    countryCodeTextView.setText("+" + cc);
                    countryCodeTextView.setTextColor(R.color.black);
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


    private void callApi(View view) {

        String firstName, lastName = "";


        if (nameString.split("\\w+").length > 1) {

            lastName = nameString.substring(nameString.lastIndexOf(" ") + 1);
            firstName = nameString.substring(0, nameString.lastIndexOf(' '));
        } else {
            firstName = nameString;
        }

        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("name", nameString);
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
        paramObject.addProperty("is_default", is_default);
        paramObject.addProperty("customer_id", sharedPrefManager.getId());
        paramObject.addProperty("is_billing_address", this.is_billing);
        String bearerToken = sharedPrefManager.getBearerToken();
        Call<AddAddressResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().addAddress("Bearer " + bearerToken, paramObject.toString());
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.code() == 201) {
                    LoadingDialog.cancelLoading();
                    clearFields();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Address Added Successfully", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else if(response.code() == 401){

                    callRefreshTokenApi();

                }
                else if (response.code() == 406) {
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
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),
                        t.toString(), Snackbar.LENGTH_SHORT);
                snackbar.show();

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
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                }
                else {
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

    @SuppressLint("ResourceAsColor")
    private void clearFields() {
        name.setText("");
        addressLine1.setText("");
        addressLine2.setText("");
        city.setText("");
        state.setText("");
        pinCode.setText("");
        phoneNumber.setText("");

        spinnerCountry.setText("");
        countryCodeTextView.setText("+91");
        countryCodeTextView.setTextColor(R.color.hint_color);




        final List<String> titleList = new ArrayList<>(Arrays.asList(titleArray));


        final ArrayAdapter<String> titleSpinerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.add_address_title, titleList){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view1 = super.getDropDownView(position, convertView, parent);
                TextView titleTv = (TextView) view1;
                if (position == 0){
                    titleTv.setVisibility(View.GONE);
                    titleTv.setTextColor(Color.GRAY);
                }else {
                    titleTv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };
        titleSpinerArrayAdapter.setDropDownViewResource(R.layout.add_address_title);
        chooseTitleAddAddress.setAdapter(titleSpinerArrayAdapter);

        chooseTitleAddAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTextFromField() {
        nameString = name.getText().toString().trim();
        addressLine1String = addressLine1.getText().toString().trim();
        addressLine2String = addressLine2.getText().toString().trim();
        cityString = city.getText().toString().trim();
        stateString = state.getText().toString().trim();
        pinCodeString = pinCode.getText().toString().trim();
        phoneNumberString = phoneNumber.getText().toString().trim();
        salutation =  chooseTitleAddAddress.getSelectedItem().toString();
        country = spinnerCountry.getText().toString();
        countryCode = countryCodeTextView.getText().toString();
        if (checkBox.isChecked()) {
            is_default = true;
        } else is_default = false;



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


    private boolean validatePhone() {
        if (phoneNumberString.isEmpty()) {
            phoneError.setVisibility(View.VISIBLE);
            return false;
        } else {
            phoneError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateCountryCode() {
        if (countryCode.equals("")) {
//            errorNo.setVisibility(View.VISIBLE);
            return false;
        } else {
//            errorNo.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateCountry() {
        if (country.isEmpty()) {
            countryError.setVisibility(View.VISIBLE);
            return false;
        } else {
            countryError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateName() {
        if (nameString.isEmpty()) {
            nameError.setVisibility(View.VISIBLE);
            return false;
        } else {
            nameError.setVisibility(View.GONE);
            return true;
        }
    }

    private Boolean validateAddress() {


        if (addressLine1String.isEmpty()) {
            addressError.setVisibility(View.VISIBLE);
            return false;
        } else {
            addressError.setVisibility(View.GONE);
            return true;
        }

    }

    private Boolean validateCity() {


        if (cityString.isEmpty()) {
            cityError.setVisibility(View.VISIBLE);
            return false;
        } else {
            cityError.setVisibility(View.GONE);
            return true;
        }

    }

    private Boolean validatePinCode() {


        if (pinCodeString.isEmpty()) {
            pinCodeError.setVisibility(View.VISIBLE);
            return false;
        } else {
            pinCodeError.setVisibility(View.GONE);
            return true;
        }

    }

    private Boolean validateState() {


        if (stateString.isEmpty()) {
            stateError.setVisibility(View.VISIBLE);
            return false;
        } else {
            stateError.setVisibility(View.GONE);
            return true;
        }

    }

    private Boolean validateSalutation() {


        if (salutation.equals("Title")) {
            titleError.setVisibility(View.VISIBLE);
            return false;
        } else {
            titleError.setVisibility(View.GONE);
            return true;
        }

    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}