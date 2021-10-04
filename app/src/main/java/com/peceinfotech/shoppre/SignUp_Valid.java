package com.peceinfotech.shoppre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.peceinfotech.shoppre.UI.ForgetPassword;

public class SignUp_Valid extends AppCompatActivity {

    Button sendBtn;
    TextInputLayout fullNameField , emailIdField , passwordField ,
            confirmPasswordField , referalCodeField;
    String fullName , emailId , password , confirmPassword , referalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_valid);

        //Hooks

        sendBtn = findViewById(R.id.signUpBtn);
        fullNameField = findViewById(R.id.fullName);
        emailIdField = findViewById(R.id.emailId);
        passwordField = findViewById(R.id.password);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        referalCodeField = findViewById(R.id.referalCode);


//        passwordField.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                if (s.toString().length()<=3){
////                    passwordField.setEndIconDrawable(R.drawable.ic_weak);
//                    Toast.makeText(getApplicationContext(), "weak", Toast.LENGTH_SHORT).show();
//                }
//                 else if (s.toString().length()>3 && s.toString().length()<7){
////                    passwordField.setEndIconDrawable(R.drawable.ic_medium);
//                    Toast.makeText(getApplicationContext(), "medium", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    passwordField.setEndIconDrawable(R.drawable.ic_weak);
//                    Toast.makeText(getApplicationContext(), "Strong", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.toString().length()<=3){
////                    passwordField.setEndIconDrawable(R.drawable.ic_weak);
//                    Toast.makeText(getApplicationContext(), "weak", Toast.LENGTH_SHORT).show();
//                }
//                else if (s.toString().length()>3 && s.toString().length()<7){
////                    passwordField.setEndIconDrawable(R.drawable.ic_medium);
//                    Toast.makeText(getApplicationContext(), "medium", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    passwordField.setEndIconDrawable(R.drawable.ic_weak);
//                    Toast.makeText(getApplicationContext(), "Strong", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


        //get text from field
        getStringFromFields();



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getStringFromFields() {

        fullName = fullNameField.getEditText().getText().toString();
        emailId = emailIdField.getEditText().getText().toString();
        password = passwordField.getEditText().getText().toString();
        confirmPassword = confirmPasswordField.getEditText().getText().toString();
        referalCode = referalCodeField.getEditText().getText().toString();

    }

}