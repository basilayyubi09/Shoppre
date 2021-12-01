package com.peceinfotech.shoppre.UI.SignupLogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.AccessTokenResponse;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.OnBoarding.OnBoardingActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Valid extends AppCompatActivity {

    Button sendBtn;
    protected EditText passwordField;
    TextView signUpValdAlrdyAcnt, passwordErrorText, helperText;
    TextInputLayout firstlNameField, lastNameField, emailIdField,
            confirmPasswordField, referalCodeField;
    String emailId, password, confirmPassword, checkLogin, referalCode, firstName, lastName, emailIdFromIntent;
    ImageView strengthImage, backArrow;
    String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    SharedPrefManager sharedPrefManager;
    LinearLayout main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_valid);


        //Shared Preference
        sharedPrefManager = new SharedPrefManager(SignUp_Valid.this);
        if (sharedPrefManager.checkLogin()) {
            startActivity(new Intent(SignUp_Valid.this, OrderActivity.class));

        }
        //Hooks

        sendBtn = findViewById(R.id.signUpBtn);
        backArrow = findViewById(R.id.backArrow);
        firstlNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastName);
        emailIdField = findViewById(R.id.emailId);
        helperText = findViewById(R.id.helperText);
        passwordErrorText = findViewById(R.id.passwordErrorText);
        passwordField = findViewById(R.id.pf);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        referalCodeField = findViewById(R.id.referalCode);
        strengthImage = findViewById(R.id.strengthImage);
        signUpValdAlrdyAcnt = findViewById(R.id.signup_vld_alrdy_acnt);
        main = findViewById(R.id.main);

        setupUI(main);
        //get Email Id from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            emailIdFromIntent = extras.getString("emailId");
            //The key argument here must match that used in the other activity
        }

        //set Email received from intent on email field
        emailIdField.getEditText().setText(emailIdFromIntent);

        //Back Arrow Click listener
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp_Valid.this, SignUpActivity.class));
                finish();
            }
        });

        //Text Watcher on Password Field
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().length() == 0) {
                    strengthImage.setVisibility(View.VISIBLE);
                } else if (s.toString().length() <= 3) {
                    strengthImage.setImageResource(R.drawable.ic_weak);
                } else if (s.toString().length() > 3 && s.toString().length() < 7) {
                    strengthImage.setImageResource(R.drawable.ic_medium);
                } else if (s.toString().length() >= 7 && s.toString().matches(passwordPattern)) {
                    strengthImage.setImageResource(R.drawable.ic_strong);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    strengthImage.setVisibility(View.GONE);
                } else if (s.toString().length() <= 3) {
                    strengthImage.setImageResource(R.drawable.ic_weak);
                } else if (s.toString().length() > 3 && s.toString().length() < 7) {
                    strengthImage.setImageResource(R.drawable.ic_medium);
                } else if (s.toString().length() >= 7 && s.toString().matches(passwordPattern)) {
                    strengthImage.setImageResource(R.drawable.ic_strong);
                }
            }
        });


        //get text from field
        getStringFromFields();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get texts from field
                getStringFromFields();

                //Validation functions
                if (!validateFirstName() || !validateLastName() || !validateEmailField()
                        || !validatePasswordField() || !validateConfirmPasswordField()) {
                    getStringFromFields();
                    return;
                } else {
                    if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        ///registerVerify Api Call
                        registerVerifyApi(firstName,
                                emailId,
                                password,
                                referalCode,
                                lastName);

                        //Show Alerts
                        LoadingDialog.showLoadingDialog(SignUp_Valid.this, "Loading...");
                    }

                }


            }
        });

        signUpValdAlrdyAcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp_Valid.this, LoginActivity.class));
            }
        });
    }

    private void registerVerifyApi(String first_Name,
                                   String emailId,
                                   String password,
                                   String referalCode,
                                   String lastName) {

        //For sending data in JSON
        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("email", emailId);
        paramObject.addProperty("first_name", first_Name);
        paramObject.addProperty("from_domain", "shoppreglobal.com");
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("password", password);
        paramObject.addProperty("referrer", "https://www.google.com/");

        // prepare call in Retrofit 2.0


        //calling RetrofitClient2 for different BASE_URL
        Call<RegisterVerifyResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .registerVerify(paramObject.toString());
        call.enqueue(new Callback<RegisterVerifyResponse>() {
            @Override
            public void onResponse(Call<RegisterVerifyResponse> call, Response<RegisterVerifyResponse> response) {


                //If user already register then send to login screen and show error in fields
                if (response.code() == 200) {
                    if (response.body().getCustomerId() == null) {
                        LoadingDialog.cancelLoading();
                        Intent intent = new Intent(SignUp_Valid.this, SignUpActivity.class);
                        intent.putExtra("flag", 1);
                        startActivity(intent);
                        finish();
                    } else {
                        callAuthApi(response.body().getToken().getAccessToken());

                    }
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<RegisterVerifyResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callAuthApi(String bearer) {


        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("allow", "true");
        paramObject.addProperty("client_id", "app1");
        paramObject.addProperty("redirect_uri", "https://staging-app1.shoppreglobal.com/access/oauth");
        paramObject.addProperty("response_type", "code");


        Call<String> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAuth("Bearer " + bearer, paramObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {
                    String code = response.body();
                    //split string from = sign
                    String[] parts = code.split("=");
//                    String part1 = parts[0]; // https://staging-app1.shoppreglobal.com/access/oauth?code
                    String part2 = parts[1]; // 8b625060eba82f7fe1905303bed8c67638b7587b
//                    Log.d("Auth api response ",code);
                    callAccessTokenApi(part2, bearer);

                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Invalid Token", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void callAccessTokenApi(String part2, String bearer) {


        Call<AccessTokenResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAccessToken(part2, bearer);
        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.code() == 200) {


                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    clearFields();
                    LoadingDialog.cancelLoading();
                    startActivity(new Intent(SignUp_Valid.this, OnBoardingActivity.class));
                    finishAffinity();
                } else if (response.code() == 400) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Code has expired", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


    private void clearFields() {
        firstlNameField.getEditText().setText("");
        lastNameField.getEditText().setText("");
        emailIdField.getEditText().setText("");
        passwordField.setText("");
        confirmPasswordField.getEditText().setText("");
        referalCodeField.getEditText().setText("");
    }


    private Boolean validateFirstName() {


        if (firstName.isEmpty()) {
            firstlNameField.setBoxStrokeWidth(2);
            firstlNameField.setBoxStrokeWidthFocused(2);
            firstlNameField.setError("This is a required field");
            return false;
        } else {
            firstlNameField.setError(null);
            firstlNameField.setBoxStrokeWidth(0);

            return true;
        }

    }

    private Boolean validateLastName() {


        if (lastName.isEmpty()) {
            lastNameField.setBoxStrokeWidth(2);
            lastNameField.setBoxStrokeWidthFocused(2);
            lastNameField.setError("This is a required field");
            return false;
        } else {
            lastNameField.setError(null);
            lastNameField.setBoxStrokeWidth(0);
            return true;
        }

    }

    private Boolean validateEmailField() {

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setBoxStrokeWidthFocused(2);
            emailIdField.setError("This is a required field");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setBoxStrokeWidthFocused(2);
            emailIdField.setError("Enter Valid email");
            return false;
        } else {
            emailIdField.setError(null);
            emailIdField.setBoxStrokeWidth(0);
            return true;
        }
    }


    @SuppressLint("ResourceAsColor")
    private Boolean validatePasswordField() {


        if (password.isEmpty()) {


            passwordErrorText.setVisibility(View.VISIBLE);
            helperText.setVisibility(View.GONE);
            strengthImage.setImageResource(R.drawable.ic_weak);
            return false;

        } else if (!password.matches(passwordPattern)) {
            helperText.setVisibility(View.GONE);
            passwordErrorText.setVisibility(View.VISIBLE);
            strengthImage.setImageResource(R.drawable.ic_weak);
            return false;
        } else {
            helperText.setVisibility(View.VISIBLE);
            passwordErrorText.setVisibility(View.GONE);
            return true;
        }

    }

    private Boolean validateConfirmPasswordField() {

        if (confirmPassword.isEmpty()) {

            confirmPasswordField.setBoxStrokeWidth(2);
            confirmPasswordField.setBoxStrokeWidthFocused(2);
            confirmPasswordField.setError("This is a required field");
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordField.setBoxStrokeWidth(2);
            confirmPasswordField.setBoxStrokeWidthFocused(2);
            confirmPasswordField.setError("Password does not match");
            return false;

        } else {
            confirmPasswordField.setError(null);
            confirmPasswordField.setBoxStrokeWidth(0);
            return true;
        }
    }

    private void getStringFromFields() {

        firstName = firstlNameField.getEditText().getText().toString().trim();
        lastName = lastNameField.getEditText().getText().toString().trim();
        emailId = emailIdField.getEditText().getText().toString().trim();
        password = passwordField.getText().toString().trim();
        confirmPassword = confirmPasswordField.getEditText().getText().toString().trim();
        referalCode = referalCodeField.getEditText().getText().toString().trim();


    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SignUp_Valid.this);
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
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp_Valid.this, SignUpActivity.class));
        finish();
    }
}

