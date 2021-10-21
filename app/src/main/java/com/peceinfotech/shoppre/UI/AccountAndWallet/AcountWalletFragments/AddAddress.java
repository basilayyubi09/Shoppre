package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAddress extends Fragment {


    AutoCompleteTextView spinnerTitle, spinnerCountry , spinnerPhoneNo;
    ImageView closeBtn;
    TextInputLayout salutationText , phoneInputLayout;
    EditText name, addressLine1, addressLine2, city, state, pinCode, phoneNumber;
    AppCompatCheckBox checkBox;
    MaterialButton addAddressBtn;
    boolean is_default = false;
    String[] titleValue = {"Mr.", "Mrs."};
    String[] countryValue = {"India", "California"};
    String[] phoneNoValue = {"+1" , "+91" , "+98" , "+92"};
    String nameString , addressLine1String , addressLine2String
            ,cityString , stateString , pinCodeString , phoneNumberString , salutation , countryCode , country;
    TextView addressError , cityError , stateError , pinCodeError , nameError , phoneError , countryError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_adress, container, false);

        //Hooks
        spinnerTitle = view.findViewById(R.id.spinnerTitle);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        spinnerPhoneNo = view.findViewById(R.id.countryCode);

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

                    callApi(view);

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
            phoneInputLayout.setErrorEnabled(true);
           phoneInputLayout.setBoxStrokeWidthFocused(2);
            phoneInputLayout.setBoxStrokeWidth(2);
            phoneInputLayout.setError("* This is a required field");
            return false;
        } else {
            phoneInputLayout.setErrorEnabled(false);
            phoneInputLayout.setBoxStrokeWidthFocused(0);
            phoneInputLayout.setBoxStrokeWidth(0);
            phoneInputLayout.setError(null);
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

    private void callApi(View view) {

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), is_default+"\t"+salutation+"\t"+ country+"\t"+countryCode, Snackbar.LENGTH_LONG);
        snackbar.show();
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



        ArrayList<String> countryArrayList = new ArrayList<>(Arrays.asList(countryValue));
        ArrayAdapter<String> countryArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.country_spinner, countryArrayList);
        spinnerCountry.setAdapter(countryArrayAdapter);


        ArrayList<String> phoneNoArrayList = new ArrayList<>(Arrays.asList(phoneNoValue));
        ArrayAdapter<String> phoneNoArrayAdapter = new ArrayAdapter<>(getContext() , R.layout.phone_no_spinner, phoneNoArrayList);
        spinnerPhoneNo.setAdapter(phoneNoArrayAdapter);
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