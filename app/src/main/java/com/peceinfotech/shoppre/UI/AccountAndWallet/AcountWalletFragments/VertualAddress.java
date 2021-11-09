package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

public class VertualAddress extends Fragment {

    TextView virtualAddressName, virtualAddressLine1, virtualAddressLine2, landmark, city, state, pincode, virtualAddressPhoneNo;
    MaterialButton nameCopyBtn, line1CopyBtn, line2CopyBtn, landmarkCopyBtn, cityCopyBtn, stateCopyBtn, pinCodeCopyBtn, phoneNoCopyBtn;

    SharedPrefManager sharedPrefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vertual_address, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("account");

        virtualAddressName = view.findViewById(R.id.virtualAddressName);
        virtualAddressLine1 = view.findViewById(R.id.virtualAddressLine1);
        virtualAddressLine2 = view.findViewById(R.id.virtualAddressLine2);
        landmark = view.findViewById(R.id.virtualAddressLandmark);
        city = view.findViewById(R.id.virtualAddressCity);
        state = view.findViewById(R.id.virtualAddressState);
        pincode = view.findViewById(R.id.virtualAddressPincode);
        virtualAddressPhoneNo = view.findViewById(R.id.virtualAddressPhoneNo);


        nameCopyBtn = view.findViewById(R.id.nameCopyBtn);
        line1CopyBtn = view.findViewById(R.id.line1CopyBtn);
        line2CopyBtn = view.findViewById(R.id.line2CopyBtn);
        landmarkCopyBtn = view.findViewById(R.id.landMarkCopyBtn);
        cityCopyBtn = view.findViewById(R.id.cityCopyBtn);
        stateCopyBtn = view.findViewById(R.id.stateCopyBtn);
        pinCodeCopyBtn = view.findViewById(R.id.pinCodeCopyBtn);
        phoneNoCopyBtn = view.findViewById(R.id.phoneNoCopyBtn);


        nameCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Name", virtualAddressName.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        line1CopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Line1", virtualAddressLine1.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        line2CopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("Line2", virtualAddressLine2.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        landmarkCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData data = (ClipData) ClipData.newPlainText("Landmark", landmark.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        cityCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData data = (ClipData) ClipData.newPlainText("City", city.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });



        stateCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = (ClipData) ClipData.newPlainText("State", state.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });


        pinCodeCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData data = (ClipData) ClipData.newPlainText("Pincode", pincode.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        phoneNoCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager)
                        getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData data = (ClipData) ClipData.newPlainText("Phone No", virtualAddressPhoneNo.getText());
                clipboardManager.setPrimaryClip(data);

                Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}