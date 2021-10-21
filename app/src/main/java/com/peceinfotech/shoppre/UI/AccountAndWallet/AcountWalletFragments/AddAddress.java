package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAddress extends Fragment {


    AutoCompleteTextView spinnerTitle, spinnerCountry , spinnerPhoneNo;
    ImageView closeBtn;
    EditText name, addressLine1, addressLine2, city, state, pinCode, phoneNumber;
    AppCompatCheckBox checkBox;
    MaterialButton addAddressBtn;
    String[] titleValue = {"Mr.", "Mrs."};
    String[] countryValue = {"India", "California"};
    String[] phoneNoValue = {"+1" , "+91" , "+98" , "+92"};


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
        addressLine1 = view.findViewById(R.id.addressLine1);
        addressLine2 = view.findViewById(R.id.addressLine2);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        pinCode = view.findViewById(R.id.pinCode);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        checkBox = view.findViewById(R.id.checkBox);
        addAddressBtn = view.findViewById(R.id.addAddressBtn);

        closeBtn = view.findViewById(R.id.closeBtn);





        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        return view;
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
}