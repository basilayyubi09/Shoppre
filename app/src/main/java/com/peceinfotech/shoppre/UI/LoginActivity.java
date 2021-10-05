package com.peceinfotech.shoppre.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.SignUp_Valid;

public class LoginActivity extends AppCompatActivity {

    TextView signupNowText, forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        signupNowText = findViewById(R.id.signupnow_text);
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