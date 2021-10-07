package com.peceinfotech.shoppre.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.AuthenticationModel.SignInDirectResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.SignUp_Valid;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView dont_have_acnt, forgotPassword;
    TextInputLayout emailIdField, passwordField;
    MaterialButton loginBtn;
    String emailId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        dont_have_acnt = findViewById(R.id.dont_have_acnt);
        forgotPassword = findViewById(R.id.forgot_password);
        emailIdField = findViewById(R.id.emailIdField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateEmailField() || !validatePasswordField()) {
                    return;
                } else signInDirect();
            }
        });

        dont_have_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUp_Valid.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
            }
        });
    }

    private void signInDirect() {
        Call<SignInDirectResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInDirect(emailId, password);
        call.enqueue(new Callback<SignInDirectResponse>() {
            @Override
            public void onResponse(Call<SignInDirectResponse> call, Response<SignInDirectResponse> response) {


                int code = response.code();

                Log.d("abcd", String.valueOf(code));
//                signInDirectResponse.getCode();

                    if(code != 400) {
                        SignInDirectResponse signInDirectResponse = response.body();

                        clearFields();
                        Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        showError("User credentials are invalid");
                        Toast.makeText(getApplicationContext(), "User credentials are invalid", Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void onFailure(Call<SignInDirectResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void clearFields() {
        emailIdField.getEditText().setText("");
        passwordField.getEditText().setText("");
    }

    private void showError(String errorMessage) {
        emailIdField.setBoxStrokeWidth(2);
        passwordField.setBoxStrokeWidth(2);
        emailIdField.setError(" ");
        passwordField.setError(errorMessage);
    }

    private boolean validateEmailField() {
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

    private boolean validatePasswordField() {
        if (password.isEmpty()) {
            passwordField.setBoxStrokeWidth(2);
            passwordField.setError("This is a required field");
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setErrorEnabled(false);
            return true;
        }
    }

    private void getTextFromField() {
        emailId = emailIdField.getEditText().getText().toString().trim();
        password = passwordField.getEditText().getText().toString().trim();
    }

}



