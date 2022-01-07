package com.shoppreglobal.shoppre.UI.SignupLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

public class ForgotPasswordMessage extends AppCompatActivity {

    Button backToLoginBtn;
    TextView check , sent;
    String whichActivity ="";
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_message);
        sharedPrefManager = new SharedPrefManager(ForgotPasswordMessage.this);
        backToLoginBtn = findViewById(R.id.backToLoginBtn);
        sent = findViewById(R.id.sent);
        check = findViewById(R.id.check);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            whichActivity = extras.getString("key");
            sent.setVisibility(View.GONE);
            check.setVisibility(View.GONE);
        }


        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whichActivity.equals("")){
                    startActivity(new Intent(ForgotPasswordMessage.this , LoginActivity.class));
                    finish();
                }
                else {
                    sharedPrefManager.logOut();
                    GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions
                            .DEFAULT_SIGN_IN).build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ForgotPasswordMessage.this ,  googleSignInOptions);

                    googleSignInClient.signOut();

                    startActivity(new Intent(ForgotPasswordMessage.this, SignUpActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPasswordMessage.this , LoginActivity.class));
        finish();
    }
}