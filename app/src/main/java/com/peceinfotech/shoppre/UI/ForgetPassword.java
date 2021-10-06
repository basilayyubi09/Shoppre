package com.peceinfotech.shoppre.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;

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

        Call<ForgotPasswordResponse> call = RetrofitClient.getInstance().getApi().forgotPassword(emailId);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.body().getError_description(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPassword.this , ForgotPasswordMessage.class));
                }
                else
                    emailIdField.setError("Sorry we couldn't find this Email ID in our records. Please try again!");
                    Toast.makeText(getApplicationContext(),  response.body().getError_description(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean validateEmailField() {

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
}