package com.peceinfotech.shoppre.UI.SignupLogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient2;

import com.peceinfotech.shoppre.Utils.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.peceinfotech.shoppre.R;

public class SignUp_Valid extends AppCompatActivity {

    Button sendBtn ;
    protected EditText passwordField;
    TextView signUpValdAlrdyAcnt, passwordErrorText;
    TextInputLayout firstlNameField, lastNameField, emailIdField,
            confirmPasswordField, referalCodeField;
    String emailId, password, confirmPassword, referalCode, firstName, lastName , emailIdFromIntent;
    ImageView strengthImage, backArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_valid);

        //Hooks

        sendBtn = findViewById(R.id.signUpBtn);
        backArrow = findViewById(R.id.backArrow);
        firstlNameField = findViewById(R.id.firstName);
        lastNameField = findViewById(R.id.lastName);
        emailIdField = findViewById(R.id.emailId);
        passwordErrorText = findViewById(R.id.passwordErrorText);
        passwordField = findViewById(R.id.pf);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        referalCodeField = findViewById(R.id.referalCode);
        strengthImage = findViewById(R.id.strengthImage);
        signUpValdAlrdyAcnt = findViewById(R.id.signup_vld_alrdy_acnt);


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
                } else {
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
                } else {
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
                    return;
                }

                ///registerVerify Api Call
                registerVerifyApi(firstName,
                        emailId,
                        password,
                        referalCode,
                        lastName);

                //Show Alerts
                LoadingDialog.showLoadingDialog(SignUp_Valid.this, "Loading...");
            }
        });

        signUpValdAlrdyAcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp_Valid.this, LoginActivity.class));
            }
        });
    }

    private void registerVerifyApi(String fullName,
                                   String emailId,
                                   String password,
                                   String referalCode,
                                   String lastName)
    {

        //For sending data in JSON
        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("email", emailId);
        paramObject.addProperty("first_name", firstName);
        paramObject.addProperty("first_visit", "https://www.shoppre.com/reviews");
        paramObject.addProperty("from_domain", "shoppreglobal.com");
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("password", password);
        paramObject.addProperty("referral_code", "");
        paramObject.addProperty("referrer", "https://www.google.com/");

        // prepare call in Retrofit 2.0

        //calling RetrofitClient2 for different BASE_URL
        Call<RegisterVerifyResponse> call = RetrofitClient2
                .getInstance()
                .getApi()
                .registerVerify(paramObject.toString());
        call.enqueue(new Callback<RegisterVerifyResponse>() {
            @Override
            public void onResponse(Call<RegisterVerifyResponse> call, Response<RegisterVerifyResponse> response) {

                //print respone
                Log.e(" Full json gson => ", String.valueOf(paramObject));
                Log.e(" responce => ", response.toString());
                Log.e(" Message => ", response.body().toString());

                String message = "Already User Is Registered, Please Verify Your email To Continue";

                RegisterVerifyResponse registerVerifyResponse = response.body();

                if (registerVerifyResponse.getMessage().equals(message))
                {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), registerVerifyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    clearFields();
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), registerVerifyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<RegisterVerifyResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
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


        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        if (password.isEmpty()) {

            passwordField.setError("This is a required field");
            passwordErrorText.setVisibility(View.VISIBLE);
            return false;

        } else if (!password.matches(passwordPattern)) {
            passwordErrorText.setVisibility(View.VISIBLE);

            return false;
        } else {
            passwordErrorText.setVisibility(View.GONE);
            passwordField.setError(null);
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

}

