package com.shoppreglobal.shoppre.UI.SignupLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient2;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    Button sendResendButton;
    TextInputLayout emailIdField;
    String emailId;
    LinearLayout main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sendResendButton = findViewById(R.id.sendResetBtn);
        emailIdField = findViewById(R.id.emailId);
        main = findViewById(R.id.main);

        setupUI(main);

        sendResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LoadingDialog.showLoadingDialog(getApplicationContext(),"Loading...");
                emailId = emailIdField.getEditText().getText().toString().trim();

                if (!validateEmailField()) {
                    return;
                } else {
                    if(!CheckNetwork.isInternetAvailable(getApplicationContext()) ) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "No Internet Connection",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        forgotPasswordApi(emailId);
                        LoadingDialog.showLoadingDialog(ForgetPassword.this, getString(R.string.Loading));
                    }
                }
            }
        });
    }

    private void forgotPasswordApi(String emailId) {

        JsonObject jsonEmail = new JsonObject();
        jsonEmail.addProperty("email", emailId);

        Call<ForgotPasswordResponse> call = RetrofitClient2.getInstance().getApi().forgotPassword(jsonEmail.toString());
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                Log.e(" Full json gson => ", String.valueOf(jsonEmail));
                Log.e(" response => ", response.toString());
                Log.e(" Code => ", String.valueOf(response.code()));

                int code = response.code();
                String errorDesc = "Sorry we couldn't find this Email ID in our records. Please try again!";

                if (code == 401) {
                    LoadingDialog.cancelLoading();
                    emailIdField.setBoxStrokeWidth(2);
                    emailIdField.setBoxStrokeWidthFocused(2);
                    emailIdField.setError(errorDesc);

                } else {

                    LoadingDialog.cancelLoading();
                    startActivity(new Intent(ForgetPassword.this , ForgotPasswordMessage.class));
                    finish();

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validateEmailField() {

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {

            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setBoxStrokeWidthFocused(2);
            emailIdField.setError("This is required field");
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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ForgetPassword.this);
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
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}