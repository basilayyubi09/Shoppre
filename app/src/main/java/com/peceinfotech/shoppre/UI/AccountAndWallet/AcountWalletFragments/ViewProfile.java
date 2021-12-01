package com.peceinfotech.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.RefreshTokenResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateProfileResponse;
import com.peceinfotech.shoppre.AccountResponse.User;
import com.peceinfotech.shoppre.AccountResponse.VerifyEmailResponse;
import com.peceinfotech.shoppre.AccountResponse.WalletTransactionResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient3;
import com.peceinfotech.shoppre.Retrofit.RetrofitClientWallet;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.UI.SignupLogin.SignUpActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfile extends Fragment {

    MaterialCardView redBoxText;
    MaterialButton inviteBtn, updateBtn;
    CardView logoutBtn;
    EditText fullNameEditText;
    TextInputLayout updateProfileNumber;
    LinearLayout viewProfileWalletText;
    ImageView profileImage, triangleImage, nameCheck, emailCheck, phoneNoCheck;
    SwitchCompat whatsappSwitch;
    CountryCodePicker countryCodePicker;
    LinearLayout resend , main;
    TextView profileName, lockerNo, profilePrice, wallet, manageAddresses, virtualIndianAddress, salutationError, titleValue;
    SharedPrefManager sharedPrefManager;
    EditText phoneNoEditText;

    //For Title Spinner
    String[] title = new String[]{"Title", "Mr", "Ms", "Mrs"};
    String email, phoneNumber, name, ccp, titleText ;
    TextView nameError, emailError, numberError, unverified, emailWrong ,updateProfileEmail;

    char firstLetter;
    TextDrawable textDrawable;
    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
    int randomColor = colorGenerator.getRandomColor();

    Spinner chooseTitle;



//    @Override
//    public void onResume() {
//        super.onResume();
//
//        arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_text_layout, title);
//        titleSpinner.setAdapter(arrayAdapter);
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.fragmentValue("account");


        wallet = view.findViewById(R.id.wallet);
        redBoxText = view.findViewById(R.id.redBoxText);
        countryCodePicker = view.findViewById(R.id.countryCodePicker);
        resend = view.findViewById(R.id.resend);
        emailError = view.findViewById(R.id.emailError);
        nameError = view.findViewById(R.id.nameError);
        unverified = view.findViewById(R.id.unverified);
        salutationError = view.findViewById(R.id.salutationError);
        numberError = view.findViewById(R.id.numberError);
        main = view.findViewById(R.id.main);
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
        chooseTitle = view.findViewById(R.id.chooseTitle);

//        ccpSpinner = view.findViewById(R.id.ccpSpinner);
        profileImage = view.findViewById(R.id.profileImage);
        whatsappSwitch = view.findViewById(R.id.whatsappSwitch);
        profilePrice = view.findViewById(R.id.profilePrice);
        profileName = view.findViewById(R.id.profileName);
        lockerNo = view.findViewById(R.id.lockerNo);
        titleValue = view.findViewById(R.id.titleValue);
        viewProfileWalletText = view.findViewById(R.id.viewProfileWalletText);
        triangleImage = view.findViewById(R.id.triangleImage);
        nameCheck = view.findViewById(R.id.nameCheck);
        emailCheck = view.findViewById(R.id.emailCheck);
        phoneNoCheck = view.findViewById(R.id.phoneNoCheck);
        phoneNoEditText = view.findViewById(R.id.phoneNoEditText);






        ///Title Spinner

        final List<String> titleList = new ArrayList<>(Arrays.asList(title));

        final ArrayAdapter<String> titleSpinerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.title_spinner, titleList){
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
        titleSpinerArrayAdapter.setDropDownViewResource(R.layout.title_spinner);
        chooseTitle.setAdapter(titleSpinerArrayAdapter);

        chooseTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        triangleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTitle.performClick();
            }
        });


        viewProfileWalletText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.fragmentManager.beginTransaction().replace(R.id.orderFrameLayout, new WalletFragment(), null)
                        .addToBackStack(null).commit();
            }
        });





        LoadingDialog.showLoadingDialog(getActivity(),"");
        callMeApi(sharedPrefManager.getBearerToken());



        firstLetter = sharedPrefManager.getFirstName().charAt(0);

        ////Profile RoundImage with Letter

        textDrawable = TextDrawable.builder()
                .beginConfig().endConfig()
                .beginConfig().withBorder(4)
                .bold().toUpperCase()
                .endConfig().buildRound(String.valueOf(firstLetter), randomColor);

        profileImage.setImageDrawable(textDrawable);
        setProfileCredentials();


        setTextsToUpdateFields();

        getTextFromFields();

        setupUI(main);

        fullNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (fullNameEditText.getText().toString().equals("") && titleText.equals("Title")){
                    nameCheck.setVisibility(View.GONE);
                }else {
                    nameCheck.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (fullNameEditText.getText().toString().equals("")){
//                    nameCheck.setVisibility(View.GONE);
//                }else {
//                    nameCheck.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (fullNameEditText.getText().toString().equals("")  && titleText.equals("Title")){
                    nameCheck.setVisibility(View.GONE);
                }else {
                    nameCheck.setVisibility(View.VISIBLE);
                }

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getTextFromFields();
                if (!validateSalutation() || !validateName() || !validateNumber()) {
                    return;
                } else {
                    if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internte Connection", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {

                        LoadingDialog.showLoadingDialog(getActivity(), "");
                        callUpdateProfileApi();
                    }
                }

            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckNetwork.isInternetAvailable(getActivity())) //if connection not available
                {

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "No Internte Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    LoadingDialog.showLoadingDialog(getActivity(), "");
                    callVerifyEmailId();
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
//                Log.d("aaaaaaaaa", getChildFragmentManager().toString());
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
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN).build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity() , googleSignInOptions);

                googleSignInClient.signOut();

                startActivity(new Intent(getActivity(), SignUpActivity.class));
                getActivity().finishAffinity();
            }
        });


        phoneNoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (phoneNoEditText.getText().length()==10)
                {
                    phoneNoCheck.setVisibility(View.VISIBLE);
                }else {
                    phoneNoCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (phoneNoEditText.getText().length()==10)
                {
                    phoneNoCheck.setVisibility(View.VISIBLE);
                }else {
                    phoneNoCheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (phoneNoEditText.getText().length()==10)
                {
                    phoneNoCheck.setVisibility(View.VISIBLE);
                }else {
                    phoneNoCheck.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

    private void callApi() {

        int id = sharedPrefManager.getId();
        Call<WalletTransactionResponse> call = RetrofitClientWallet.getInstanceWallet()
                .getAppApi().getDetails(id,0 , 20 , "Bearer "+sharedPrefManager.getBearerToken());
        call.enqueue(new Callback<WalletTransactionResponse>() {
            @Override
            public void onResponse(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                if (response.isSuccessful()) {

                    User user = response.body().getUser();
                    profilePrice.setText("₹ "+String.valueOf(user.getWalletAmount()));
                    LoadingDialog.cancelLoading();

                }else{

                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , response.message() , Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout) , t.toString() , Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }

    private void callVerifyEmailId() {

        Call<VerifyEmailResponse> call = RetrofitClient.getInstance()
                .getApi().getVerify("Bearer "+sharedPrefManager.getBearerToken(), sharedPrefManager.getId());
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.code()==200){
                    LoadingDialog.cancelLoading();
                    redBoxText.setVisibility(View.VISIBLE);
                }
                else if(response.code()==403){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.body().getError(), Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void callMeApi(String bearerToken) {
        Call<MeResponse> call = RetrofitClient
                .getInstance().getApi()
                .getUser("Bearer "+bearerToken);
        call.enqueue(new Callback<MeResponse>() {
            @Override
            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
                if (response.code()==200){

                    if (response.body().getIsEmailVerified()==0){
                        unverified.setVisibility(View.VISIBLE);
                        resend.setVisibility(View.VISIBLE);
                    }
                    else if (response.body().getIsEmailVerified()==1){

                        unverified.setVisibility(View.GONE);
                        resend.setVisibility(View.GONE);
                        emailCheck.setVisibility(View.VISIBLE);
                    }
                    callApi();
                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }
                else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<MeResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
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
                    callMeApi(sharedPrefManager.getBearerToken());
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

    private void callUpdateProfileApi() {

        String firstName, lastName = "";
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
        object.addProperty("alternate_email", "");
        object.addProperty("email", email);
        object.addProperty("first_name", firstName);
        object.addProperty("group_id", sharedPrefManager.getGroupId());
        object.addProperty("id", sharedPrefManager.getId());
        object.addProperty("is_courier_migrated", sharedPrefManager.getIsMigrated());
        object.addProperty("is_member", sharedPrefManager.getIsMember());
        object.addProperty("last_name", lastName);
        object.addProperty("name", name);
        object.addProperty("phone", "+" + ccp + phoneNumber);
        object.addProperty("profile_photo_url", "");
        object.addProperty("salutation", titleText);
        object.addProperty("secondary_phone", "");
        object.addProperty("survey", "");
        object.addProperty("virtual_address_code", sharedPrefManager.getVirtualAddressCode());

        Call<UpdateProfileResponse> call = RetrofitClient3
                .getInstance3()
                .getAppApi()
                .UpdateProfile("Bearer " + sharedPrefManager
                        .getBearerToken(), object.toString());
        String finalLastName = lastName;
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeFirstName(firstName);
                    sharedPrefManager.storeLastName(finalLastName);
                    sharedPrefManager.storeSalutation(titleText);
                    sharedPrefManager.storeEmail(email);
                    sharedPrefManager.storeFullName(name);
                    sharedPrefManager.storePhone("+" + ccp + phoneNumber);

                    ////Profile RoundImage with Letter
                    firstLetter = name.charAt(0);
                    textDrawable = TextDrawable.builder()
                            .beginConfig().endConfig()
                            .beginConfig().withBorder(4)
                            .bold().toUpperCase()
                            .endConfig().buildRound(String.valueOf(firstLetter), randomColor);

                    profileImage.setImageDrawable(textDrawable);
                    setProfileCredentials();
                    setTextsToUpdateFields();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), "Successfully updated", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    LoadingDialog.cancelLoading();

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), response.message(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.orderFrameLayout), t.toString(), Snackbar.LENGTH_LONG);
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
        } else if (!salutation.equals("") && !firstName.equals("")) {
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
        final List<String> titleList = new ArrayList<>(Arrays.asList(title));

        final ArrayAdapter<String> titleSpinerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.title_spinner, titleList){
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
        titleSpinerArrayAdapter.setDropDownViewResource(R.layout.title_spinner);
        chooseTitle.setAdapter(titleSpinerArrayAdapter);

        chooseTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (position == 0 && fullNameEditText.getText().toString().equals("")) {
                        nameCheck.setVisibility(View.GONE);
                    } else {
                        nameCheck.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTextFromFields() {
        titleText = chooseTitle.getSelectedItem().toString();
        name = fullNameEditText.getText().toString().trim();
        email = updateProfileEmail.getText().toString().trim();
        ccp = countryCodePicker.getSelectedCountryCode();
        phoneNumber = updateProfileNumber.getEditText().getText().toString();
    }

    @SuppressLint("ResourceAsColor")
    private void setTextsToUpdateFields() {

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

    public boolean validateSalutation() {

        if (titleText.equals("Title")) {
            salutationError.setVisibility(View.VISIBLE);

            return false;
        } else {
            salutationError.setVisibility(View.GONE);

            return true;
        }
    }


//    public boolean validateEmail() {
//        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
//        if (email.equals("")) {
//            emailError.setVisibility(View.VISIBLE);
//            return false;
//        } else if (!email.matches(emailPattern)) {
//            emailWrong.setVisibility(View.VISIBLE);
//            return false;
//        } else {
//            emailWrong.setVisibility(View.GONE);
//            emailError.setVisibility(View.GONE);
//            return true;
//        }
//
//
//    }

    public boolean validateNumber() {

        if (phoneNumber.equals("")) {
            numberError.setVisibility(View.VISIBLE);
            return false;

        }else if (phoneNumber.equals("")){
            phoneNoCheck.setVisibility(View.GONE);
            return false;
        }
        else {
            numberError.setVisibility(View.GONE);
            phoneNoCheck.setVisibility(View.VISIBLE);
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