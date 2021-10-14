package com.peceinfotech.shoppre.UI.SignupLogin;

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
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Utils.LoadingDialog;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView dont_have_acnt, forgotPassword;
    TextInputLayout passwordField;
    MaterialButton loginBtn;
    String emailId, password;
    TextInputLayout loginEmailIdField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        dont_have_acnt = findViewById(R.id.dont_have_acnt);
        forgotPassword = findViewById(R.id.forgot_password);
        loginEmailIdField = findViewById(R.id.emailIdField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);

        //get Email Id from previous activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int flag = extras.getInt("flag");
            if (flag == 1) {
                setError("Looks like this user already exists! Please use a new Email ID");
            }
        }

            //The key argument here must match that used in the other activity


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateEmailField() || !validatePasswordField()) {
                    return;
                } else {
                    signInDirect();
                    LoadingDialog.showLoadingDialog(LoginActivity.this , getString(R.string.Loading));
                }
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

//                signInDirectResponse.getCode();
                if (code != 400) {
                    LoadingDialog.cancelLoading();
                    clearFields();
                    Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.cancelLoading();
                    showError("User credentials are invalid");
                }

            }

            @Override
            public void onFailure(Call<SignInDirectResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void clearFields() {
        loginEmailIdField.getEditText().setText("");
        passwordField.getEditText().setText("");
    }

    private void showError(String errorMessage) {
        loginEmailIdField.setBoxStrokeWidth(2);
        passwordField.setBoxStrokeWidth(2);
        loginEmailIdField.setBoxStrokeWidthFocused(2);
        passwordField.setBoxStrokeWidthFocused(2);
        loginEmailIdField.setError(" ");
        passwordField.setError(errorMessage);
    }

    private boolean validateEmailField() {
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            loginEmailIdField.setBoxStrokeWidth(2);
            loginEmailIdField.setBoxStrokeWidthFocused(2);
            loginEmailIdField.setError("This is a required field");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            loginEmailIdField.setBoxStrokeWidthFocused(2);
            loginEmailIdField.setBoxStrokeWidth(2);
            loginEmailIdField.setError("Enter Valid email");
            return false;
        } else {
            loginEmailIdField.setError(null);
            loginEmailIdField.setBoxStrokeWidth(0);
            return true;
        }
    }

    private boolean validatePasswordField() {
        if (password.isEmpty()) {
            passwordField.setBoxStrokeWidth(2);
            passwordField.setBoxStrokeWidthFocused(2);
            passwordField.setError("This is a required field");
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setBoxStrokeWidth(0);
            return true;
        }
    }

    private void getTextFromField() {
        emailId = loginEmailIdField.getEditText().getText().toString().trim();
        password = passwordField.getEditText().getText().toString().trim();
    }
    public void setError(String message){
        passwordField.setBoxStrokeWidth(2);
        passwordField.setBoxStrokeWidthFocused(2);
        passwordField.setError(" ");
        loginEmailIdField.setBoxStrokeWidthFocused(2);
        loginEmailIdField.setBoxStrokeWidth(2);
        loginEmailIdField.setError(message);

    }

}



