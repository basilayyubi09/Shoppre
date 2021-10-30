package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.Item;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends Fragment {


    AutoCompleteTextView spinnerTitle;
    TextView spinnerPhoneNo, spinnerCountry;

    ImageView closeBtn, triangleDropdown;
    TextInputLayout salutationText;
    LinearLayout phoneInputLayout;
    EditText name, addressLine1, addressLine2, city, state, pinCode, phoneNumber;
    AppCompatCheckBox checkBox;
    MaterialButton addAddressBtn;
    List<Item> list , duplicateList;
    ArrayAdapter<Item> arrayAdapter;
    String type;
    int id;
    TextView title;
    int countryId;
    boolean is_default = false;
    String[] n = {"a", "b"};
    String[] titleValue = {"Mr", "Ms","Mrs"};
    String nameString , addressLine1String , addressLine2String ,countryCode
            ,cityString , stateString , pinCodeString , phoneNumberString , salutation  , country;
    Integer cc;
    TextView addressError , cityError , stateError , pinCodeError , nameError , errorNo,phoneError , countryError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_adress, container, false);

        setCountryList("");

        //Hooks
        spinnerTitle = view.findViewById(R.id.spinnerTitle);
        spinnerPhoneNo = view.findViewById(R.id.countryCodeTextView);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        title = view.findViewById(R.id.title);
        errorNo = view.findViewById(R.id.errorNo);

        name = view.findViewById(R.id.name);
        salutationText = view.findViewById(R.id.salutationTextInput);
        phoneInputLayout = view.findViewById(R.id.phoneInputLayout);
        nameError = view.findViewById(R.id.nameError);
        phoneError = view.findViewById(R.id.phoneError);
        countryError = view.findViewById(R.id.countryError);

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


        triangleDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerCountry.performClick();

            }
        });



        Intent intent = getActivity().getIntent();
//       if (intent.getExtras() != null) {
            type = intent.getStringExtra("type");
            id = intent.getIntExtra("id" , 1);
//        }
        //Retrieve the value


        spinnerCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogue();
            }
        });
        getTextFromField();


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextFromField();
                if(!validateSalutation() ||!validateName()||!validateAddress()
                        || !validateCity() || !validateState() || !validateCountry()
                        || !validatePinCode() || !validateCountryCode() ||!validatePhone()){
                    return;
                }
                if (type.equals("update")){
                    Toast.makeText(getActivity(), id+"\n"+type, Toast.LENGTH_SHORT).show();
                    title.setText("Update Address");
                }
                else {
                    title.setText("Address Book");
                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callApi(view);
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        return view;
    }

    private void showDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View row = getLayoutInflater().inflate(R.layout.country_dialog,null);

        ListView listView  = row.findViewById(R.id.dialogListView);
        TextInputLayout inputLayout = row.findViewById(R.id.dialogSearch);


        AlertDialog dialog = builder.create();
        arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        listView.setTextFilterEnabled(true);
        dialog.setView(row);

        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item  = adapterView.getItemAtPosition(i);
                if (item instanceof Item){
                    Item item1 = (Item) item;
                    String str = ((Item) item).getCountryCode().toString().split("[\\(\\)]")[1];
                    cc  = ((Item) item).getPhoneCode();
                    countryId = ((Item) item).getId();
                    spinnerPhoneNo.setText("+"+cc);
                    spinnerPhoneNo.setTextColor(R.color.black);
                }
                Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString()+"\t"+cc+"\t"+countryId, Toast.LENGTH_SHORT).show();
                spinnerCountry.setText(adapterView.getItemAtPosition(i).toString());
                inputLayout.getEditText().setText("");
                dialog.dismiss();
            }
        });
        dialog.show();


    inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.equals(null)|| charSequence.length()==0) {
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

                arrayAdapter = new ArrayAdapter<Item>(getContext(), android.R.layout.simple_list_item_1,list);
                listView.setAdapter(arrayAdapter);
            setCountryList(editable.toString());
            arrayAdapter.getFilter().filter(editable);
            arrayAdapter.notifyDataSetChanged();

        }
    });


    }



    private void callApi(View view) {
        String firstName , lastName="";
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getActivity());
        JsonObject paramObject = new JsonObject();
        if(nameString.split("\\w+").length>1){

            lastName = nameString.substring(nameString.lastIndexOf(" ")+1);
            firstName = nameString.substring(0, nameString.lastIndexOf(' '));
        }
        else{
            firstName = nameString;
        }
        Toast.makeText(getActivity(), firstName+"\t"+lastName, Toast.LENGTH_SHORT).show();


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
        paramObject.addProperty("phone", cc+phoneNumberString);
        paramObject.addProperty("is_default", is_default);
        paramObject.addProperty("customer_id", sharedPrefManager.getId());
        paramObject.addProperty("is_billing_address", false);

        String bearerToken = sharedPrefManager.getBearerToken();
        Call<AddAddressResponse> call = RetrofitClient3.getInstance3()
                .getAppApi().addAddress("Bearer "+bearerToken, paramObject.toString());
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                if (response.code()==201){
                    LoadingDialog.cancelLoading();
                    clearFields();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),"Address Added Successfully",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (response.code()==406){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),response.body().getMessage(),Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),response.message(),Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar =Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) ,
                        t.toString(), Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        });

    }

    private void clearFields() {
        name.setText("");
        addressLine1.setText("");
        addressLine2.setText("");
        city.setText("");;
        state.setText("");
        pinCode.setText("");
        phoneNumber.setText("");
        spinnerTitle.setText("");
        spinnerCountry.setText("");
        spinnerPhoneNo.setText("");
    }

    private void getTextFromField() {
        nameString = name.getText().toString().trim();
        addressLine1String = addressLine1.getText().toString().trim();
        addressLine2String = addressLine2.getText().toString().trim();
        cityString = city.getText().toString().trim();
        stateString = state.getText().toString().trim();
        pinCodeString = pinCode.getText().toString().trim();
        phoneNumberString = phoneNumber.getText().toString().trim();
        salutation = spinnerTitle.getText().toString();
        country = spinnerCountry.getText().toString();
        countryCode = spinnerPhoneNo.getText().toString();
        if (checkBox.isChecked()){
            is_default = true;
        }
        else is_default = false;

    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(titleValue));
        ArrayAdapter<String> tittleArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_items, arrayList);
        spinnerTitle.setAdapter(tittleArrayAdapter);



    }

    private void setCountryList(String typedString) {
        Call<CountryResponse> call = RetrofitClient3.getInstance3().getAppApi()
                .getCountry("Country" , typedString);
        call.enqueue(new Callback<CountryResponse>() {

            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code()==200){
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
            errorNo.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorNo.setVisibility(View.GONE);
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


        if (salutation.equals("")) {
            salutationText.setErrorEnabled(true);
            salutationText.setBoxStrokeWidthFocused(2);
            salutationText.setBoxStrokeWidth(2);
            salutationText.setError("* This is a required field");
            return false;
        } else {
            salutationText.setErrorEnabled(false);
            salutationText.setBoxStrokeWidthFocused(0);
            salutationText.setBoxStrokeWidth(0);
            salutationText.setError(null);
            return true;
        }

    }
}