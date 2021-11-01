package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.peceinfotech.shoppre.AccountResponse.UpdateProfileResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.SignupLogin.SignUpActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfile extends Fragment {

    MaterialAutoCompleteTextView ccpSpinners, titleSpinner;
    MaterialButton logoutBtn, inviteBtn, updateBtn;
    EditText fullNameEditText, phoneNumberEditText, updateProfileEmail;
    TextInputLayout updateProfileNumber ;
    CircleImageView profileImage;
    SwitchCompat whatsappSwitch;
    CountryCodePicker countryCodePicker;
    LinearLayout resend;
    TextView profileName, lockerNo, profilePrice, wallet, manageAddresses, virtualIndianAddress;
    SharedPrefManager sharedPrefManager;
    //For Title Spinner
    String[] title = {"Mr","Ms", "Mrs"};
    String email, phoneNumber, name, ccp, titleText="";
    TextView nameError, emailError, numberError, unverified , emailWrong;

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
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        wallet = view.findViewById(R.id.wallet);
        countryCodePicker = view.findViewById(R.id.countryCodePicker);
        resend = view.findViewById(R.id.resend);
        emailError = view.findViewById(R.id.emailError);
        nameError = view.findViewById(R.id.nameError);
        unverified = view.findViewById(R.id.unverified);
        numberError = view.findViewById(R.id.numberError);
        emailWrong = view.findViewById(R.id.emailWrong);
        manageAddresses = view.findViewById(R.id.manageAddresses);
        virtualIndianAddress = view.findViewById(R.id.virtualIndianAddress);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        inviteBtn = view.findViewById(R.id.inviteBtn);
        updateBtn = view.findViewById(R.id.updateBtn);
        fullNameEditText = view.findViewById(R.id.fullNameEditText);
        updateProfileEmail = view.findViewById(R.id.updateProfileEmail);
        updateProfileNumber = view.findViewById(R.id.updateProfileNumber);
//        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        titleSpinner = view.findViewById(R.id.titleSpinner);

//        ccpSpinner = view.findViewById(R.id.ccpSpinner);
        profileImage = view.findViewById(R.id.profileImage);
        whatsappSwitch = view.findViewById(R.id.whatsappSwitch);
        profilePrice = view.findViewById(R.id.profilePrice);
        profileName = view.findViewById(R.id.profileName);
        lockerNo = view.findViewById(R.id.lockerNo);


        setProfileCredentials();


        setTextsToUpdateFields();

        getTextFromFields();


        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getTextFromFields();
                if(!validateName()||!validateEmail()||!validateNumber()){
                    return;
                }else
                {
                    if(!CheckNetwork.isInternetAvailable(getActivity()) ) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , "No Internte Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else
                    {
                        //Wallet Transaction api
                        Toast.makeText(getActivity(), titleText+"\n"+
                                name+"\n"+
                                email+"\n"+
                                ccp+"\n"+
                                phoneNumber, Toast.LENGTH_SHORT).show();
                        LoadingDialog.showLoadingDialog(getActivity(),"");
                        callUpdateProfileApi();
                    }
                }

            }
        });
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new ReferralFragment(), null)
                        .addToBackStack(null).commit();
            }
        });


        virtualIndianAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new VertualAddress(), null).addToBackStack(null).commit();
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new WalletFragment(), null)
                        .addToBackStack(null).commit();
            }
        });

        manageAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedInstanceState != null) return;
                OrderActivity.fragmentManager.beginTransaction()
                        .replace(R.id.orderFrameLayout, new EmptyAddressBook(), null)
                        .addToBackStack(null).commit();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.logOut();
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finish();
            }
        });


        return view;
    }

    private void callUpdateProfileApi() {
        String firstName , lastName="" ;
        if (name.split("\\w+").length > 1) {

            lastName = name.substring(name.lastIndexOf(" ") + 1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        } else {
            firstName = name;
        }
//        "alternate_email": null,
//                "email": "vikasjson@gmail.com",
//                "first_name": "Hanzala",
//                "group_id": 2,
//                "id": 3176,
//                "is_courier_migrated": "0",
//                "is_member": 0,
//                "last_name": "Ansari",
//                "name": "  Hanzala Ansari",
//                "phone": "+919351523344",
//                "profile_photo_url": null,
//                "salutation": "Mr",
//                "secondary_phone": null,
//                "survey": "SBK Family,UAE",
//                "virtual_address_code": "SA-10A12-3176"
        JsonObject object = new JsonObject();
        object.addProperty("alternate_email","");
        object.addProperty("email",email);
        object.addProperty("first_name",firstName);
        object.addProperty("group_id",sharedPrefManager.getGroupId());
        object.addProperty("id",sharedPrefManager.getId());
        object.addProperty("is_courier_migrated",sharedPrefManager.getIsMigrated());
        object.addProperty("is_member",sharedPrefManager.getIsMember());
        object.addProperty("last_name",lastName);
        object.addProperty("name",name);
        object.addProperty("phone","+"+ccp+phoneNumber);
        object.addProperty("profile_photo_url","");
        object.addProperty("salutation",titleText);
        object.addProperty("secondary_phone","");
        object.addProperty("survey","");
        object.addProperty("virtual_address_code",sharedPrefManager.getVirtualAddressCode());
        Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_LONG).show();

        Call<UpdateProfileResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .UpdateProfile("Bearer "+sharedPrefManager
                        .getBearerToken(),object.toString());
        String finalLastName = lastName;
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if(response.code()==200){
                    LoadingDialog.cancelLoading();

                    sharedPrefManager.storeLastName(firstName);
                    sharedPrefManager.storeLastName(finalLastName);
                    if (!titleText.equals("")){
                        sharedPrefManager.storeSalutation(titleText);
                    }
                    sharedPrefManager.storeEmail(email);
                    sharedPrefManager.storeFullName(name);
                    sharedPrefManager.storePhone("+"+ccp+phoneNumber);
                    setProfileCredentials();
                    setTextsToUpdateFields();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),"Successfully updated", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (response.code()==401){
                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{

                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout),t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void setProfileCredentials() {
        //setValues of textViews from sharedPref
        lockerNo.setText(sharedPrefManager.getVirtualAddressCode());
        String firstName, lastName, salutation;
        salutation = sharedPrefManager.getSalutation();
        firstName = sharedPrefManager.getFirstName();
        lastName = sharedPrefManager.getLastName();

        if (!firstName.equals("") && !lastName.equals("") && !salutation.equals("")) {
            profileName.setText(salutation + " " + firstName + " " + lastName);
        }
        else if (!salutation.equals("") && !firstName.equals("")) {
            profileName.setText(salutation + " " + firstName);
        } else if (!salutation.equals("") && !lastName.equals("")) {
            profileName.setText(salutation + " " + lastName);
        } else if (!firstName.equals("") && !lastName.equals("")) {
            profileName.setText(firstName + " " + lastName);
        } else if (!firstName.equals("")) {
            profileName.setText(firstName);
        } else if (!lastName.equals("")) {
            profileName.setText(firstName);
        } else {
            profileName.setText("User Name");
        }
    }

    private void getTextFromFields() {
        titleText = titleSpinner.getText().toString();
        name = fullNameEditText.getText().toString().trim();
        email = updateProfileEmail.getText().toString().trim();
        ccp = countryCodePicker.getSelectedCountryCode();
        phoneNumber = updateProfileNumber.getEditText().getText().toString();
    }

    @SuppressLint("ResourceAsColor")
    private void setTextsToUpdateFields() {
        String salutation = sharedPrefManager.getSalutation();
        if (salutation.equals("")) {
            titleSpinner.setHint("Title");
            titleSpinner.setHintTextColor(R.color.hint_color);
        }else{
            titleSpinner.setText(salutation);
        }
        updateProfileEmail.setText(sharedPrefManager.getEmail());
        fullNameEditText.setText(sharedPrefManager.getFullName());
        updateProfileNumber.getEditText().setText(sharedPrefManager.getPhone());

    }

    public boolean validateName() {

        if (name.equals("")) {
            nameError.setVisibility(View.VISIBLE);
            return false;
        } else {
            nameError.setVisibility(View.GONE);
            return true;
        }
    }

    public boolean validateEmail() {
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.equals("")) {
            emailError.setVisibility(View.VISIBLE);
            return false;
        } else if (!email.matches(emailPattern)) {
            emailWrong.setVisibility(View.VISIBLE);
            return false;
        }else {
            emailWrong.setVisibility(View.GONE);
            emailError.setVisibility(View.GONE);
            return true;
        }


    }

    public boolean validateNumber() {

        if (phoneNumber.equals("")) {
            numberError.setVisibility(View.VISIBLE);
            return false;
        } else {
            numberError.setVisibility(View.GONE);
            return true;
        }
    }
}