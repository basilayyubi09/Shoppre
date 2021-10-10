package com.peceinfotech.shoppre.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    Button sendResendButton;
    TextInputLayout emailIdField;
    String emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sendResendButton = findViewById(R.id.sendResetBtn);
        emailIdField = findViewById(R.id.emailId);


        sendResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LoadingDialog.showLoadingDialog(getApplicationContext(),"Loading...");
                emailId = emailIdField.getEditText().getText().toString().trim();

                if(!validateEmailField()){
                    return;
                }
                else
                    forgotPasswordApi(emailId);
            }
        });
    }

    private void forgotPasswordApi(String emailId) {

        JsonObject jsonEmail = new JsonObject();
        jsonEmail.addProperty("email",emailId);

        Call<ForgotPasswordResponse> call = RetrofitClient2.getInstance().getApi().forgotPassword(jsonEmail.toString());
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

                Log.e(" Full json gson => ", String.valueOf(jsonEmail));
                Log.e(" response => ", response.toString());
//                Log.e(" Message => ", response.body().getCustomerId().toString());
//                Log.e(" Message => ", response.errorBody().toString());
                Log.e(" Code => ", String.valueOf(response.code()));

                int code  = response.code();
                String errorDesc = "Sorry we couldn't find this Email ID in our records. Please try again!";

                if(code == 401){
//                    LoadingDialog.cancelLoading();
                    emailIdField.setError(errorDesc);
                    Toast.makeText(getApplicationContext(), errorDesc, Toast.LENGTH_SHORT).show();
                }
                else{

//                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), response.body().getExpires(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPassword.this , ForgotPasswordMessage.class));
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
//                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validateEmailField() {

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {

            emailIdField.setBoxStrokeWidth(1);
            emailIdField.setError("Email Can't be empty");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            emailIdField.setBoxStrokeWidth(1);
            emailIdField.setError("Enter Valid email");
            return false;
        } else {
            emailIdField.setError(null);
            emailIdField.setBoxStrokeWidth(0);
            emailIdField.setErrorEnabled(false);
            return true;
        }
    }
}