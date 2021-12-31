package com.shoppreglobal.shoppre.UI.AccountAndWallet.AcountWalletFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.ChangePasswordResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient3;
import com.shoppreglobal.shoppre.UI.SignupLogin.LoginActivity;
import com.shoppreglobal.shoppre.UI.SignupLogin.SignUp_Valid;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {

    TextInputLayout newPassword, confirmTIL, currentPassword;
    TextView helperText, newPasswordError, confirmError, currentPasswordError;
    Button changePasswordBtn;
    SharedPrefManager sharedPrefManager;
    LinearLayout main;
    String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    String currentPasswordString, newPasswordString, confirmNewPasswordString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());
        newPassword = view.findViewById(R.id.newPassword);
        confirmTIL = view.findViewById(R.id.confirmTIL);
        main = view.findViewById(R.id.main);
        currentPassword = view.findViewById(R.id.currentPassword);
        helperText = view.findViewById(R.id.helperText);
        newPasswordError = view.findViewById(R.id.newPasswordError);
        confirmError = view.findViewById(R.id.confirmError);
        currentPasswordError = view.findViewById(R.id.currentPasswordError);
        changePasswordBtn = view.findViewById(R.id.changePasswordBtn);


        setupUI(main);
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateCurrentPassword() || !validateNewPassword() || !validateConfirmNewPassword()) {
                    return;
                } else {
                    callChangePasswordApi();
                }
            }
        });
        return view;
    }

    private void callChangePasswordApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("old_password", currentPasswordString);
        jsonObject.addProperty("password", newPasswordString);
        jsonObject.addProperty("confirm_password", confirmNewPasswordString);
        LoadingDialog.showLoadingDialog(getActivity(), "");
        Call<ChangePasswordResponse> call = RetrofitClient.getInstance().getApi().changePassword("Bearer " + sharedPrefManager.getBearerToken()
                , sharedPrefManager.getId(), jsonObject.toString());
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                if (response.code() == 200) {

                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    sharedPrefManager.logOut();
                    GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions
                            .DEFAULT_SIGN_IN).build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

                    googleSignInClient.signOut();

                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finishAffinity();


                } else if (response.code() == 401) {

                    callRefreshTokenApi();
                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateCurrentPassword() {
        if (currentPasswordString.equals("")) {
            currentPasswordError.setVisibility(View.VISIBLE);
            return false;
        } else {
            currentPasswordError.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validateNewPassword() {
        if (newPasswordString.equals("")) {
            helperText.setVisibility(View.GONE);
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        }
        else if(!newPasswordString.matches(passwordPattern)){
            helperText.setVisibility(View.GONE);
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        }else {
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
        currentPasswordString = currentPassword.getEditText().getText().toString();
        newPasswordString = newPassword.getEditText().getText().toString();
        confirmNewPasswordString = confirmTIL.getEditText().getText().toString();

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
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}