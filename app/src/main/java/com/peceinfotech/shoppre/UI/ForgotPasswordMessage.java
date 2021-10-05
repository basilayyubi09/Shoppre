package com.peceinfotech.shoppre.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.peceinfotech.shoppre.R;

public class ForgotPasswordMessage extends AppCompatActivity {

    Button backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_message);

        backToLoginBtn = findViewById(R.id.backToLoginBtn);

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordMessage.this , LoginActivity.class));
            }
        });
    }
}