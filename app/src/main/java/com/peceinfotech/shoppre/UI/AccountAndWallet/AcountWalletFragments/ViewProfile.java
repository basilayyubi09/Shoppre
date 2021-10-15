package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.UI.AccountAndWallet.AccountWalletActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfile extends Fragment {

    MaterialAutoCompleteTextView ccpSpinners , titleSpinner;
    MaterialButton logoutBtn , inviteBtn , updateBtn;
    EditText fullNameEditText , phoneNumberEditText;
    CircleImageView profileImage;
    SwitchCompat whatsappSwitch;
    TextView profileName , lockerNo , profilePrice , wallet , manageAddresses , virtualIndianAddress;
    //For Title Spinner
    String[] title = { "Mr" , "Mrs" };

    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, title);
        titleSpinner.setAdapter(arrayAdapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_profile, container, false);

        wallet = view.findViewById(R.id.wallet);
        manageAddresses = view.findViewById(R.id.manageAddresses);
        virtualIndianAddress = view.findViewById(R.id.virtualIndianAddress);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        inviteBtn = view.findViewById(R.id.inviteBtn);
        updateBtn = view.findViewById(R.id.updateBtn);
        fullNameEditText = view.findViewById(R.id.fullNameEditText);
//        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        titleSpinner = view.findViewById(R.id.titleSpinner);
        ccpSpinners = view.findViewById(R.id.ccpSpinners);
//        ccpSpinner = view.findViewById(R.id.ccpSpinner);
        profileImage = view.findViewById(R.id.profileImage);
        whatsappSwitch = view.findViewById(R.id.whatsappSwitch);
        profilePrice = view.findViewById(R.id.profilePrice);
        profileName = view.findViewById(R.id.profileName);
        lockerNo = view.findViewById(R.id.lockerNo);


        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AccountWalletActivity.fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , new ReferralFragment() , null )
                        .addToBackStack(null).commit();
            }
        });

        virtualIndianAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountWalletActivity.fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , new VertualAddress() , null)
                        .addToBackStack(null)
                        .commit();
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountWalletActivity.fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , new WalletFragment() , null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        manageAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountWalletActivity.fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout , new AddAddress() , null)
                        .addToBackStack(null)
                        .commit();
            }
        });




        return view;
    }
}