package com.peceinfotech.shoppre.UI.SignupLogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignUpGoogleResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient2;
import com.peceinfotech.shoppre.UI.OnBoarding.OnBoardingActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView loginText;
    TextInputLayout emailIdField;
    Button getStartedBtn, googleSignInButton, fbSignInBtn, fbLoginBtn;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        loginText = findViewById(R.id.signup_already_acnt);
        getStartedBtn = findViewById(R.id.get_started_btn);
        googleSignInButton = findViewById(R.id.signup_google_btn);
        fbSignInBtn = findViewById(R.id.signup_fb_btn);
        emailIdField = findViewById(R.id.emailIdField);


        callbackManager = CallbackManager.Factory.create();


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmailField()) {
                    return;
                } else {
                    String emailId = emailIdField.getEditText().getText().toString();
                    Intent intent = new Intent(SignUpActivity.this , SignUp_Valid.class);
                    intent.putExtra("emailId" , emailId);
                    startActivity(intent);
                }

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                LoadingDialog.showLoadingDialog(SignUpActivity.this , getString(R.string.Loading));
            }
        });

        //Facebook

        fbSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog.showLoadingDialog(SignUpActivity.this , getString(R.string.Loading));
                LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                /*
 App code
                                startActivity(new Intent(SignUpActivity.this, OrderActivity.class));
                                Toast.makeText(SignUpActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
*/
                                setFacebookData(loginResult);
                            }

                            @Override
                            public void onCancel() {

                                LoadingDialog.cancelLoading();
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                LoadingDialog.cancelLoading();
                                // App code
                            }
                        });
            }
        });


    }

    private void setFacebookData(LoginResult loginResult) {

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            LoadingDialog.cancelLoading();
                            // Application code
                            try {
                                Log.i("Response", response.toString());


                                String firstName = response.getJSONObject().getString("first_name");
                                String lastName = response.getJSONObject().getString("last_name");
                                String email="";
                                if (object.has("email")) {
                                    email = object.getString("email");
                                }

                                //TODO put your code here

                                signUpFacebook(email, firstName , lastName);
                            } catch (JSONException e) {
                                Toast.makeText(SignUpActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
        }

    private void signUpFacebook(String email, String firstName, String lastName) {
        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("salutation", "");
        paramObject.addProperty("first_name", firstName);
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("email", email);
        paramObject.addProperty("phone", "");
        paramObject.addProperty("password", "");
        paramObject.addProperty("domain", "app");
        paramObject.addProperty("login_type", "facebook");
        paramObject.addProperty("is_email_verified", "true");
        paramObject.addProperty("referral_code", "");
        Call<SignUpGoogleResponse> call = RetrofitClient2.getInstance().getApi().signUpFacebook(paramObject.toString());
        call.enqueue(new Callback<SignUpGoogleResponse>() {
            @Override
            public void onResponse(Call<SignUpGoogleResponse> call, Response<SignUpGoogleResponse> response) {
                SignUpGoogleResponse signUpGoogleResponse = response.body();
                if (response.isSuccessful())
                {
                    if (signUpGoogleResponse.getCode()==201){
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getApplicationContext(), "SignUp successful", Toast.LENGTH_SHORT).show();
                    }
                    else if (signUpGoogleResponse.getCode()==409){
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getApplicationContext(), "Already Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///Facebook User details

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken==null){

            }else {
                loadUserProfile(currentAccessToken);
            }


        }
    };

    private void loadUserProfile(AccessToken newAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                LoadingDialog.cancelLoading();
                // Application code
                try {
                    Log.i("Response", response.toString());
                    String firstName = response.getJSONObject().getString("first_name");
                    String lastName = response.getJSONObject().getString("last_name");
                    String email="";
                    if (object.has("email")) {
                        email = object.getString("email");
                    }

                    //TODO put your code here

                    signUpFacebook(email, firstName , lastName);
                } catch (JSONException e) {
                    Toast.makeText(SignUpActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    ///Google

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        //For Google
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
        //For Facebook
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

            String fullName = acct.getDisplayName();
            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            String email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            if (acct != null) {


                Toast.makeText(getApplicationContext(), "register " +firstName + "\n" + lastName
                        + "\n" + fullName + "\n" +
                        email + "\n" +
                        personId, Toast.LENGTH_LONG).show();

//
                signInGoogle(email , firstName , lastName);

            }




        } catch (ApiException e) {

            LoadingDialog.cancelLoading();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }

    private void signInGoogle(String email , String firstName , String lastName) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi().signInGoogle(email , "google");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                LoadingDialog.cancelLoading();

                if (response.code()==200){

                    ///Aamir
                    startActivity(new Intent(getApplicationContext() , OrderActivity.class));
                    Toast.makeText(getApplicationContext(), "Sign In successfully", Toast.LENGTH_SHORT).show();

                }
                else if(response.code()==400){
                    signUpGoogle(email , firstName , lastName);
                }
            }

            @Override
            public void onFailure(Call<SignInGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpGoogle(String email, String firstName, String lastName) {

        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("salutation", "");
        paramObject.addProperty("first_name", firstName);
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("email", email);
        paramObject.addProperty("phone", "");
        paramObject.addProperty("password", "");
        paramObject.addProperty("domain", "app");
        paramObject.addProperty("login_type", "google");
        paramObject.addProperty("is_email_verified", true);
        paramObject.addProperty("referral_code", "");

        Call<SignUpGoogleResponse> call = RetrofitClient2
                .getInstance()
                .getApi().signUpGoogle(paramObject.toString());
        call.enqueue(new Callback<SignUpGoogleResponse>() {
            @Override
            public void onResponse(Call<SignUpGoogleResponse> call, Response<SignUpGoogleResponse> response) {
                SignUpGoogleResponse signUpGoogleResponse = response.body();
                if (response.isSuccessful())
                {
                    if (signUpGoogleResponse.getCode()==201){
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getApplicationContext(), "SignUp successful", Toast.LENGTH_SHORT).show();

                    }
                    else if (signUpGoogleResponse.getCode()==409){
                      signInGoogle(email , firstName , lastName);
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean validateEmailField() {
        String emailId = emailIdField.getEditText().getText().toString();

        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setBoxStrokeWidthFocused(2);
            emailIdField.setError("This is a required field");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            emailIdField.setBoxStrokeWidth(2);
            emailIdField.setBoxStrokeWidthFocused(2);
            emailIdField.setError("Enter Valid email");
            return false;
        } else {
            emailIdField.setError(null);

            return true;
        }
    }
}