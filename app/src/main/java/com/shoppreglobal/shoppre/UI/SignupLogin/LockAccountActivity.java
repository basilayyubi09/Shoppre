package com.shoppreglobal.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.VerifyEmailResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LockAccountActivity extends AppCompatActivity {

    Button verificationLinkBtn;
    String email;
    TextView emailTV;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_account);

        sharedPrefManager = new SharedPrefManager(LockAccountActivity.this);
        emailTV = findViewById(R.id.email);

        emailTV.setText(sharedPrefManager.getEmail());
        verificationLinkBtn = findViewById(R.id.verificationLinkBtn);


        verificationLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.showLoadingDialog(LockAccountActivity.this , "");
                callVerifyEmailId();
            }
        });
    }
    private void callVerifyEmailId() {

        Call<VerifyEmailResponse> call = RetrofitClient.getInstance()
                .getApi().getVerify("Bearer " + sharedPrefManager.getBearerToken(), sharedPrefManager.getId());
        call.enqueue(new Callback<VerifyEmailResponse>() {
            @Override
            public void onResponse(Call<VerifyEmailResponse> call, Response<VerifyEmailResponse> response) {
                if (response.code() == 200) {
                    LoadingDialog.cancelLoading();
                    Intent intent = new Intent(LockAccountActivity.this , ForgotPasswordMessage.class);
                    intent.putExtra("key" , "Lock");
                    startActivity(intent);
                    finish();
                } else if (response.code() == 403) {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(LockAccountActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                }
                else if (response.code()==401){
                    callRefreshTokenApi();
                }else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(LockAccountActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyEmailResponse> call, Throwable t) {

                LoadingDialog.cancelLoading();
                Toast.makeText(LockAccountActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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

                        callVerifyEmailId();

                } else {
                    LoadingDialog.cancelLoading();
                    Toast.makeText(LockAccountActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(LockAccountActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}