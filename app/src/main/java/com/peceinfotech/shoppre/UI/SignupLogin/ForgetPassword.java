package com.peceinfotech.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.peceinfotech.shoppre.R;

public class ForgetPassword extends AppCompatActivity {

    Button sendResendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        sendResendButton = findViewById(R.id.sendResetBtn);
        sendResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , ForgotPasswordMessage.class));
            }
        });
    }
}