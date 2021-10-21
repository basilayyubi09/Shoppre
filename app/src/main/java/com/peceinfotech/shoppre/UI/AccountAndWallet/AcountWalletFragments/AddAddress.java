package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.peceinfotech.shoppre.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAddress extends Fragment {


    AutoCompleteTextView spinnerTitle;
    AutoCompleteTextView spinnerCountry;
    AutoCompleteTextView spinnerPhoneNo;
    ImageView closeBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_adress, container, false);

        //Hooks
        spinnerTitle = view.findViewById(R.id.spinnerTitle);
        spinnerCountry = view.findViewById(R.id.spinnerCountry);

        closeBtn = view.findViewById(R.id.closeBtn);

        String[] titleValue = {"Mr." , "Mrs."};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(titleValue));
        ArrayAdapter<String> tittleArrayAdapter = new ArrayAdapter<>(getContext() , R.layout.dropdown_items, arrayList);
        spinnerTitle.setAdapter(tittleArrayAdapter);



        String[] countryValue = {"India" , "California"};
        ArrayList<String> countryArrayList = new ArrayList<>(Arrays.asList(countryValue));
        ArrayAdapter<String> countryArrayAdapter = new ArrayAdapter<>(getContext() , R.layout.country_spinner, countryArrayList);
        spinnerCountry.setAdapter(countryArrayAdapter);


//        String[] phoneNoValue = {"+1" , "+91" , "+98" , "+92"};
//        ArrayList<String> phoneNoArrayList = new ArrayList<>(Arrays.asList(phoneNoValue));
//        ArrayAdapter<String> phoneNoArrayAdapter = new ArrayAdapter<>(getContext() , R.layout.phone_no_spinner, phoneNoArrayList);
//        spinnerPhoneNo.setAdapter(phoneNoArrayAdapter);



        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();

            }
        });





//        autoCompleteTextView = view.findViewById(R.id.adressBookTitle);
//
//        String []option = {"Male" , "Female"};
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext() , R.layout.dropdown_items , option);
//        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
//        autoCompleteTextView.setAdapter(arrayAdapter);


        return view;
    }
}