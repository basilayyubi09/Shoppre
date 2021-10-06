package com.peceinfotech.shoppre;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Valid extends AppCompatActivity {

    Button sendBtn;
    protected EditText passwordField;
    TextView signUpValdAlrdyAcnt;
    TextInputLayout firstlNameField, lastNameField, emailIdField,
            confirmPasswordField, referalCodeField;
    String  emailId, password, confirmPassword, referalCode, firstName, lastName;
    ImageView strengthImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_valid);

        //Hooks

        sendBtn = findViewById(R.id.signUpBtn);
        firstlNameField = findViewById(R.id.firstName);
        lastNameField = findViewById(R.id.lastName);
        emailIdField = findViewById(R.id.emailId);
        passwordField = findViewById(R.id.pf);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        referalCodeField = findViewById(R.id.referalCode);
        strengthImage = findViewById(R.id.strengthImage);
        signUpValdAlrdyAcnt = findViewById(R.id.signup_vld_alrdy_acnt);


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

                if (!validateFirstName() || !validateLastName() || !validateEmailField()
                        || !validatePasswordField() || !validateConfirmPasswordField()) {
                    return;
                }
                try {
                    registerVerifyApi(firstName,
                            emailId,
                            password,
                            referalCode,
                            lastName);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void registerVerifyApi(String fullName,
                                   String emailId,
                                   String password,
                                   String referalCode,
                                   String lastName) throws JSONException {

        // prepare call in Retrofit 2.0

            JSONObject paramObject = new JSONObject();
            paramObject.put("email", emailId);
            paramObject.put("first_name", firstName);
            paramObject.put("first_name", "https://www.shoppre.com/reviews");
            paramObject.put("from_domain", "shoppreglobal.com");
            paramObject.put("last_name", lastName);
            paramObject.put("password", password);
            paramObject.put("referral_code", emailId);
            paramObject.put("referrer", "https://www.google.com/");


            Call<RegisterVerifyResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .registerVerify(paramObject.toString());
            call.enqueue(new Callback<RegisterVerifyResponse>() {
                @Override
                public void onResponse(Call<RegisterVerifyResponse> call, Response<RegisterVerifyResponse> response) {

                    RegisterVerifyResponse registerVerifyResponse = response.body();

                    String c = String.valueOf(registerVerifyResponse.getCustomerId());
                    if (response.isSuccessful()){

                        if (c != null){
                        Toast.makeText(getApplicationContext(), registerVerifyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        clearFields();}
                        else{
                            Toast.makeText(getApplicationContext(), registerVerifyResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
            firstlNameField.setError("This is a required field");
            return false;
        } else {
            firstlNameField.setError(null);
            firstlNameField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateLastName() {


        if (lastName.isEmpty()) {
            lastNameField.setBoxStrokeWidth(2);
            lastNameField.setError("This is a required field");
            return false;
        } else {
            lastNameField.setError(null);
            lastNameField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmailField() {

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setError("This is a required field");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setError("Enter Valid email");
            return false;
        } else {
            emailIdField.setError(null);
            emailIdField.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePasswordField() {

        if (password.isEmpty()) {

            passwordField.setError("This is a required field");
            return false;
        } else {
            passwordField.setError(null);
//            passwordField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateConfirmPasswordField() {

        if (confirmPassword.isEmpty()) {

            confirmPasswordField.setBoxStrokeWidth(2);
            confirmPasswordField.setError("This is a required field");
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordField.setBoxStrokeWidth(2);
            confirmPasswordField.setError("Password does not match");
            return false;

        } else {
            confirmPasswordField.setError(null);
            confirmPasswordField.setErrorEnabled(false);
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

