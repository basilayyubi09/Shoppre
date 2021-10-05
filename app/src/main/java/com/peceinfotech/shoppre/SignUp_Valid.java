package com.peceinfotech.shoppre;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.recaptcha.Recaptcha;
import com.google.android.gms.recaptcha.RecaptchaAction;
import com.google.android.gms.recaptcha.RecaptchaActionType;
import com.google.android.gms.recaptcha.RecaptchaHandle;
import com.google.android.gms.recaptcha.RecaptchaResultData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.UI.ForgetPassword;

public class SignUp_Valid extends AppCompatActivity {

    Button sendBtn;
    EditText passwordField;
    TextInputLayout fullNameField , emailIdField ,
            confirmPasswordField , referalCodeField;
    String fullName , emailId , password , confirmPassword , referalCode , recaptuaToken , firstName , lastName;
    ImageView strengthImage;
    private RecaptchaHandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_valid);


        //For Recaptcha Enterprise init()
//       reCaptchaInit();

//        Recaptcha.getClient(this)
//                .init("6LeXcqocAAAAAAVFyDeCdInLWfl1C4eusLp1rJIr")
//                .addOnSuccessListener(
//                        this,
//                        new OnSuccessListener<RecaptchaHandle>() {
//                            @Override
//                            public void onSuccess(RecaptchaHandle handle) {
//                                // Handle success ...
//                                SignUp_Valid.this.handle = handle;
//                            }
//                        })
//                .addOnFailureListener(
//                        this,
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                if (e instanceof ApiException) {
//                                    ApiException apiException = (ApiException) e;
//                                    int apiErrorStatus = apiException.getStatusCode();
//                                    // Handle api errors ...
//                                } else {
//                                    // Handle other failures ...
//                                }
//                            }
//                        });



        //Execute reCaptcha
//       executeRecaptcha();

        //Hooks

        sendBtn = findViewById(R.id.signUpBtn);
        fullNameField = findViewById(R.id.fullName);
        emailIdField = findViewById(R.id.emailId);
        passwordField = findViewById(R.id.passwordField);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        referalCodeField = findViewById(R.id.referalCode);
        strengthImage = findViewById(R.id.strengthImage);


        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().length()==0){
                    strengthImage.setVisibility(View.VISIBLE);
                }
                else if (s.toString().length()<=3){
                    strengthImage.setImageResource(R.drawable.ic_weak);
                }
                 else if (s.toString().length()>3 && s.toString().length()<7){
                    strengthImage.setImageResource(R.drawable.ic_medium);
                }
                else {
                    strengthImage.setImageResource(R.drawable.ic_strong);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()==0){
                    strengthImage.setVisibility(View.VISIBLE);
                }
                else if (s.toString().length()<=3){
                    strengthImage.setImageResource(R.drawable.ic_weak);
                }
                else if (s.toString().length()>3 && s.toString().length()<7){
                    strengthImage.setImageResource(R.drawable.ic_medium);
                }
                else {
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

                if (!validateFullName() || !validateEmailField()
                        || !validatePasswordField() || !validateConfirmPasswordField()) {
                    return;
                }

                //verifySignup(fullName , emailId , password , referalCode);

                Toast.makeText(getApplicationContext(), fullName + "\n"
                        + firstName + "\n"
                        + lastName, Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void executeRecaptcha() {

        Recaptcha.getClient(this)
                .execute(this.handle, new RecaptchaAction(new RecaptchaActionType(RecaptchaActionType.LOGIN)))
                .addOnSuccessListener(
                        this,
                        new OnSuccessListener<RecaptchaResultData>() {
                            @Override
                            public void onSuccess(RecaptchaResultData response) {
                                recaptuaToken = response.getTokenResult();
                                // Handle success ...
                                if (!recaptuaToken.isEmpty()) {
                                    Log.d("", "reCAPTCHA response token: " + recaptuaToken);
                                    // Validate the response token by following the instructions
                                    // when creating an assessment.
                                }
                            }
                        })
                .addOnFailureListener(
                        this,
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    int apiErrorStatus = apiException.getStatusCode();
                                    // Handle api errors ...
                                } else {
                                    // Handle other failures ...
                                }
                            }
                        });
    }

    private void reCaptchaInit() {
        Recaptcha.getClient(this)
                .init("6LfzxcwUAAAAAG4T4KzPdgMPv7Ovfax1aR6HsIww")
                .addOnSuccessListener(
                        this,
                        new OnSuccessListener<RecaptchaHandle>() {
                            @Override
                            public void onSuccess(RecaptchaHandle handle) {
                                // Handle success ...
                                SignUp_Valid.this.handle = handle;
                            }
                        })
                .addOnFailureListener(
                        this,
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    ApiException apiException = (ApiException) e;
                                    int apiErrorStatus = apiException.getStatusCode();
                                    // Handle api errors ...
                                } else {
                                    // Handle other failures ...
                                }
                            }
                        });
    }


    private Boolean validateFullName() {


        if (fullName.isEmpty()) {

            fullNameField.setError("Field Can't be empty");
            return false;
        } else {
            fullNameField.setError(null);
            fullNameField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmailField() {

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {

            emailIdField.setError("Email Can't be empty");
            return false;
        } else if (!emailId.matches(emailPattern)) {
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

            passwordField.setError("Password Field Can't be empty");
            return false;
        } else {
            passwordField.setError(null);
//            passwordField.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateConfirmPasswordField() {

        if (confirmPassword.isEmpty()) {

            confirmPasswordField.setError("Please confirm password");
            return false;
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordField.setError("Password does not match");
            return false;

        } else {
            confirmPasswordField.setError(null);
            confirmPasswordField.setErrorEnabled(false);
            return true;
        }
    }

    private void getStringFromFields() {

        fullName = fullNameField.getEditText().getText().toString();
        emailId = emailIdField.getEditText().getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmPasswordField.getEditText().getText().toString();
        referalCode = referalCodeField.getEditText().getText().toString();

        if (fullName.split("\\w+").length > 1) {

            int firstSpace = fullName.indexOf(" "); // detect the first space character
            firstName = fullName.substring(0, firstSpace);  // get everything upto the first space character
            lastName = fullName.substring(firstSpace).trim(); //
        } else {
            firstName = fullName;
            lastName = "";
        }
    }

}