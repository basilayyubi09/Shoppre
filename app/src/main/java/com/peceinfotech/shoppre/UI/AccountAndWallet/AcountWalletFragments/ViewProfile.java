package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.SignupLogin.SignUpActivity;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfile extends Fragment {

    MaterialAutoCompleteTextView ccpSpinners , titleSpinner;
    MaterialButton logoutBtn , inviteBtn , updateBtn;
    EditText fullNameEditText , phoneNumberEditText;
    CircleImageView profileImage;
    SwitchCompat whatsappSwitch;
    TextView profileName , lockerNo , profilePrice , wallet , manageAddresses , virtualIndianAddress;
    SharedPrefManager sharedPrefManager;
    //For Title Spinner
    String[] title = { "Mr" , "Mrs" };
    String[] nameTitle = { "Mr" , "Mrs" };



    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, title);
        titleSpinner.setAdapter(arrayAdapter);

//        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, nameTitle);
//        nameSpinner.setAdapter(arrayAdapter1);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_profile, container, false);








        sharedPrefManager = new SharedPrefManager(getActivity());

        wallet = view.findViewById(R.id.wallet);
        manageAddresses = view.findViewById(R.id.manageAddresses);
        virtualIndianAddress = view.findViewById(R.id.virtualIndianAddress);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        inviteBtn = view.findViewById(R.id.inviteBtn);
        updateBtn = view.findViewById(R.id.updateBtn);
        fullNameEditText = view.findViewById(R.id.fullNameEditText);
//        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        titleSpinner = view.findViewById(R.id.titleSpinner);
//        nameSpinner = view.findViewById(R.id.nameSpinner);
        ccpSpinners = view.findViewById(R.id.ccpSpinners);
//        ccpSpinner = view.findViewById(R.id.ccpSpinner);
        profileImage = view.findViewById(R.id.profileImage);
        whatsappSwitch = view.findViewById(R.id.whatsappSwitch);
        profilePrice = view.findViewById(R.id.profilePrice);
        profileName = view.findViewById(R.id.profileName);
        lockerNo = view.findViewById(R.id.lockerNo);


        //setValues of textViews from sharedPref
        lockerNo.setText(sharedPrefManager.getVirtualAddressCode());
        String firstName , lastName , salutation;
        salutation = sharedPrefManager.getSalutation();
        firstName = sharedPrefManager.getFirstName();
        lastName = sharedPrefManager.getLastName();

            if (!firstName.equals("") && !lastName.equals("") && !salutation.equals(""))
            {
                profileName.setText(salutation+" "+firstName+" "+lastName);
            }
            else if (!salutation.equals("") && !firstName.equals("")){
                profileName.setText(salutation+" "+firstName);
            }
            else if (!salutation.equals("") && !lastName.equals("")){
                profileName.setText(salutation+" "+lastName);
            }
            else if (!firstName.equals("") && !lastName.equals("")){
                profileName.setText(firstName+" "+lastName);
            }
            else if (!firstName.equals("")){
                profileName.setText(firstName);
            }

            else if (!lastName.equals("")){
                profileName.setText(firstName);
            }
            else {
                profileName.setText("User Name");
            }



        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new ReferralFragment() , null )
                        .addToBackStack(null).commit();
            }
        });


        virtualIndianAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new VertualAddress() , null).addToBackStack(null).commit();
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new WalletFragment() , null)
                        .addToBackStack(null).commit();
            }
        });

        manageAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState!=null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout , new EmptyAddressBook() , null)
                        .addToBackStack(null).commit();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.logOut();
                startActivity(new Intent(getActivity() , SignUpActivity.class));
                getActivity().finish();
            }
        });



        return view;
    }
}