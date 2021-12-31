package com.shoppreglobal.shoppre.UI.SignupLogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.ChangePasswordResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    TextInputLayout newPassword, confirmTIL, currentPassword;
    TextView helperText, newPasswordError, confirmError, currentPasswordError;
    Button resetPassword;
    SharedPrefManager sharedPrefManager;
    LinearLayout main;
    int id;
    String token;
    String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    String currentPasswordString, newPasswordString, confirmNewPasswordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        sharedPrefManager = new SharedPrefManager(ResetPassword.this);
        newPassword = findViewById(R.id.newPassword);
        confirmTIL = findViewById(R.id.confirmTIL);
        main = findViewById(R.id.main);
        currentPassword = findViewById(R.id.currentPassword);
        helperText = findViewById(R.id.helperText);
        newPasswordError = findViewById(R.id.newPasswordError);
        confirmError = findViewById(R.id.confirmError);

        setupUI(main);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String scheme = uri.getScheme();
            if (scheme.equals("https")) {
                id = Integer.parseInt(uri.getQueryParameter("id"));
                 token = uri.getQueryParameter("token");

            }
        }

        resetPassword = findViewById(R.id.resetPassword);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateNewPassword() || !validateConfirmNewPassword()) {
                    return;
                } else {
                    callChangePasswordApi();
                }
            }
        });


    }

    private void callChangePasswordApi() {

        //{
        //	"id": "100603",
        //	"confirm_password": "1",
        //	"password": "1",
        //	"email": "",
        //	"token": "29908df865a153b00000c8d8e5915479"
        //}
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("confirm_password", confirmNewPasswordString);
        jsonObject.addProperty("password", newPasswordString);
        jsonObject.addProperty("email", "");
        jsonObject.addProperty("token",token );
        LoadingDialog.showLoadingDialog(ResetPassword.this, "");
        Call<String> call = RetrofitClient.getInstance().getApi().resetPassword( id, jsonObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {

                    LoadingDialog.cancelLoading();
                    Toast.makeText(ResetPassword.this, "Your account password has been reset. Please login to continue."
                            , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                    finish();


                }else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(ResetPassword.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(ResetPassword.this, t.toString(), Toast.LENGTH_SHORT).show();
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
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    callChangePasswordApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(ResetPassword.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(ResetPassword.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean validateNewPassword() {
        if (newPasswordString.equals("")) {
            helperText.setVisibility(View.GONE);
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        } else if (!newPasswordString.matches(passwordPattern)) {
            helperText.setVisibility(View.GONE);
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        } else {
            helperText.setVisibility(View.VISIBLE);
            newPasswordError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateConfirmNewPassword() {
        if (!confirmNewPasswordString.equals(newPasswordString)) {
            confirmError.setText("Password does not match");
            confirmError.setVisibility(View.VISIBLE);
            return false;
        } else {
            confirmError.setVisibility(View.GONE);
            return true;
        }
    }

    private void getTextFromField() {

        newPasswordString = newPassword.getEditText().getText().toString();
        confirmNewPasswordString = confirmTIL.getEditText().getText().toString();

    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ResetPassword.this);
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
}