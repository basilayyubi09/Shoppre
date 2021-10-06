package com.peceinfotech.shoppre.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.SignUp_Valid;
import com.peceinfotech.shoppre.UI.OnBoarding.OnBoarding;

public class SignUpActivity extends AppCompatActivity {

    TextView loginText;
    Button getStarttedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        loginText = findViewById(R.id.signup_already_acnt);
        getStarttedBtn = findViewById(R.id.get_started_btn);


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
            }
        });

        getStarttedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this , OnBoarding.class));
            }
        });

    }
}