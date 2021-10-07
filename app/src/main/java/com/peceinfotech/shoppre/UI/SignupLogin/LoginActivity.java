package com.peceinfotech.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peceinfotech.shoppre.R;

public class LoginActivity extends AppCompatActivity {

    TextView signupNowText, forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        signupNowText = findViewById(R.id.dont_have_acnt);
        forgotPassword = findViewById(R.id.forgot_password);

        signupNowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , SignUp_Valid.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext() , ForgetPassword.class));
            }
        });
    }
}