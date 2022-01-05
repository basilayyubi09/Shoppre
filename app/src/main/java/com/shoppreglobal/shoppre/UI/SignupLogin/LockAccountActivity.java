package com.shoppreglobal.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.shoppreglobal.shoppre.R;

public class LockAccountActivity extends AppCompatActivity {

    Button verificationLinkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_account);

        verificationLinkBtn = findViewById(R.id.verificationLinkBtn);
    }
}