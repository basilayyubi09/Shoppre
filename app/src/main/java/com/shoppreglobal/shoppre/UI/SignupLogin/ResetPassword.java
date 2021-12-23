package com.shoppreglobal.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shoppreglobal.shoppre.R;

public class ResetPassword extends AppCompatActivity {

    Button resetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            Uri uri = intent.getData();
            String scheme = uri.getScheme();
            if (scheme.equals("https")) {
                String id = uri.getQueryParameter("id");
                String token = uri.getQueryParameter("token");
                Toast.makeText(ResetPassword.this, id+"\n"+token, Toast.LENGTH_SHORT).show();
            }
        }

        resetPassword = findViewById(R.id.resetPassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Reset Password Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}