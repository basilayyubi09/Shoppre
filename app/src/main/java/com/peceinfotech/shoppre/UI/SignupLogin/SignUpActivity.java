package com.peceinfotech.shoppre.UI.SignupLogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AccountResponse.AccessTokenResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignUpGoogleResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.UI.OnBoarding.OnBoardingActivity;
import com.peceinfotech.shoppre.UI.Orders.OrderActivity;
import com.peceinfotech.shoppre.Utils.CheckNetwork;
import com.peceinfotech.shoppre.Utils.LoadingDialog;
import com.peceinfotech.shoppre.Utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    TextView loginText;
    TextInputLayout emailIdField;
    Button getStartedBtn, googleSignInButton, fbSignInBtn, fbLoginBtn;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    private CallbackManager callbackManager;
    SharedPrefManager sharedPrefManager;
    LinearLayout main;
    String checkLogin;
    String personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPrefManager = new SharedPrefManager(SignUpActivity.this);

        //if user login then send to Order Activity
        if (sharedPrefManager.checkLogin()) {
            startActivity(new Intent(SignUpActivity.this, OrderActivity.class));
            finish();
        }



        //Hooks
        loginText = findViewById(R.id.signup_already_acnt);
        getStartedBtn = findViewById(R.id.get_started_btn);
        googleSignInButton = findViewById(R.id.signup_google_btn);
        fbSignInBtn = findViewById(R.id.signup_fb_btn);
        emailIdField = findViewById(R.id.emailIdField);
        main = findViewById(R.id.main);
//        SignUp_Valid.hideSoftKeyboard(SignUpActivity.this);
        //callBack for facebook
        callbackManager = CallbackManager.Factory.create();

        setupUI(main);

        //get Email Id from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int flag = extras.getInt("flag");
            if (flag == 1) {
                setError("Looks like this user already exists! Please use a new Email ID");
            }
        }
        //Click listener on Login Text
        loginText.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        //Click listener on Get started Button
        getStartedBtn.setOnClickListener(view -> {
            if (!validateEmailField()) {
                return;
            } else {

                //get text from email field and send email to SignUp_valid activity for showing in email field
                String emailId = emailIdField.getEditText().getText().toString();
                Intent intent = new Intent(SignUpActivity.this, SignUp_Valid.class);
                intent.putExtra("emailId", emailId);
                startActivity(intent);
                finish();
            }

        });

        //Google SignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);


        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        //click listener on Google Btn
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
                {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    signIn();
                    LoadingDialog.showLoadingDialog(SignUpActivity.this, getString(R.string.Loading));
                }

            }
        });

        //Facebook

//        fbSignInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                LoadingDialog.showLoadingDialog(SignUpActivity.this, getString(R.string.Loading));
//
//                LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile", "email"));
//                LoginManager.getInstance().registerCallback(callbackManager,
//                        new FacebookCallback<LoginResult>() {
//                            @Override
//                            public void onSuccess(LoginResult loginResult) {
//
//                                // setFacebookData(loginResult);
//
//                                GraphRequest request = GraphRequest.newMeRequest(
//                                        loginResult.getAccessToken(),
//                                        new GraphRequest.GraphJSONObjectCallback() {
//                                            @Override
//                                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                                // Application code
//                                                try {
//                                                    Log.i("Response", response.toString());
//
//
//                                                    String firstName = response.getJSONObject().getString("first_name");
//                                                    String lastName = response.getJSONObject().getString("last_name");
//                                                    String email = "";
//                                                    String id = response.getJSONObject().getString("id");
//                                                    if (object.has("email")) {
//                                                        email = object.getString("email");
//                                                    }
//
//
//
//
////                                                    Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
//                                                    signUpFacebook(email, firstName, lastName);
//
//                                                } catch (JSONException e) {
//                                                    Toast.makeText(SignUpActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                                Bundle parameters = new Bundle();
//                                parameters.putString("fields", "id,email,first_name,last_name");
//                                request.setParameters(parameters);
//                                request.executeAsync();
//                            }
//
//                            @Override
//                            public void onCancel() {
//
//                                LoadingDialog.cancelLoading();
//                                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(FacebookException exception) {
//                                LoadingDialog.cancelLoading();
//                                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure want to exit?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    //    private void setFacebookData(LoginResult loginResult) {
//
//            GraphRequest request = GraphRequest.newMeRequest(
//                    loginResult.getAccessToken(),
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            LoadingDialog.cancelLoading();
//                            // Application code
//                            try {
//                                Log.i("Response", response.toString());
//
//
//                                String firstName = response.getJSONObject().getString("first_name");
//                                String lastName = response.getJSONObject().getString("last_name");
//                                String email="";
//                                if (object.has("email")) {
//                                    email = object.getString("email");
//                                }
//
//
//                                signUpFacebook(email, firstName , lastName);
//                            } catch (JSONException e) {
//                                Toast.makeText(SignUpActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,email,first_name,last_name");
//            request.setParameters(parameters);
//            request.executeAsync();
//        }

    private void signUpFacebook(String emailId, String firstName, String lastName) {


        /*"salutation": "Mr.",
                "first_name": "firstName",
                "last_name": "lastName",
                "email": "email",
                "phone": "phone_number",
                "password": "password",
                "domain": "app",
                "login_type": "facebook",
                "is_email_verified": true,
                "referral_code": "referralCode"*/
        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("salutation", "");
        paramObject.addProperty("first_name", firstName);
        paramObject.addProperty("last_name", lastName);
        paramObject.addProperty("email", emailId);
        paramObject.addProperty("phone", "");
        paramObject.addProperty("password", "");
        paramObject.addProperty("domain", "app");
        paramObject.addProperty("login_type", "facebook");
        paramObject.addProperty("is_email_verified", true);
        paramObject.addProperty("referral_code", "");


        Call<SignUpGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signUpFacebook(paramObject.toString());

        call.enqueue(new Callback<SignUpGoogleResponse>() {
            @Override
            public void onResponse(Call<SignUpGoogleResponse> call, Response<SignUpGoogleResponse> response) {
                SignUpGoogleResponse signUpGoogleResponse = response.body();
                if (response.isSuccessful()) {

                    //If user already register then there is no other response code
                    // both true and false condition coming in 200 response code
                    // hence checking token if user already register will receive token null and vise versa
                    if (signUpGoogleResponse.getToken() != null) {
                        checkLogin = "signup";
                        callAuthApi(response.body().getToken().getAccessToken());

                    } else if (signUpGoogleResponse.getToken() == null) {
                        signInFacebook(emailId, firstName, lastName);
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

    private void signInFacebook(String email, String firstName, String lastName) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInFacebook(email, "facebook");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                if (response.code() == 200) {
                    checkLogin = "login";
                    callAuthApi(response.body().getAccessToken());
                } else if (response.code() == 400) {
                    signUpFacebook(email, firstName, lastName);
                }
            }

            @Override
            public void onFailure(Call<SignInGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ///Facebook User details

//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//
//            if (currentAccessToken==null){
//
//            }else {
//                loadUserProfile(currentAccessToken);
//            }
//
//
//        }
//    };

    //Get values from facebook login
//    private void loadUserProfile(AccessToken newAccessToken){
//
//        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                LoadingDialog.cancelLoading();
//                // Application code
//                try {
//                    Log.i("Response", response.toString());
//                    String firstName = response.getJSONObject().getString("first_name");
//                    String lastName = response.getJSONObject().getString("last_name");
//                    String email="";
//                    if (object.has("email")) {
//                        email = object.getString("email");
//                    }
//
//
//
//                    signUpFacebook(email, firstName , lastName);
//                } catch (JSONException e) {
//                    Toast.makeText(SignUpActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,email,first_name,last_name");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }

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
//            String fullName = acct.getDisplayName();
//            String firstName = "asd";
//            String lastName = "lastName";
//            String email = "abcd@sdasdsadgmail.commma";
//            personId = "id";
            String fullName = acct.getDisplayName();
            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            String email = acct.getEmail();
            personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            if (acct != null) {

                signUpGoogle(email, firstName, lastName);

            }


        } catch (ApiException e) {

            LoadingDialog.cancelLoading();
            Snackbar snackbar = Snackbar.make(getApplicationContext()
                    , findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT);
            snackbar.show();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }

    private void signInGoogle(String email, String firstName, String lastName) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi().signInGoogle(email, "google");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {

                if (response.code() == 200) {

                    //check login => this is for checking user login or signup
                    //according checkLogin will send to next activity accordingly
                    checkLogin = "login";
                    callAuthApi(response.body().getAccessToken());
                } else if (response.code() == 400) {
                    signUpGoogle(email, firstName, lastName);
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
        paramObject.addProperty("password", personId);
        paramObject.addProperty("domain", "app");
        paramObject.addProperty("login_type", "google");
        paramObject.addProperty("is_email_verified", true);
        paramObject.addProperty("referral_code", "");

        Call<SignUpGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi().signUpGoogle(paramObject.toString());
        call.enqueue(new Callback<SignUpGoogleResponse>() {
            @Override
            public void onResponse(Call<SignUpGoogleResponse> call, Response<SignUpGoogleResponse> response) {
                SignUpGoogleResponse signUpGoogleResponse = response.body();
                if (response.isSuccessful()) {
                    if (signUpGoogleResponse.getToken() != null) {
                        checkLogin = "signup";
                        callAuthApi(response.body().getToken().getAccessToken());
                    } else if (signUpGoogleResponse.getToken() == null) {
                        signInGoogle(email, firstName, lastName);
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
    private void callAuthApi(String bearer) {
        String bearerToken = bearer;

        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("allow", "true");
        paramObject.addProperty("client_id", "app1");
        paramObject.addProperty("redirect_uri", "https://staging-app1.shoppreglobal.com/access/oauth");
        paramObject.addProperty("response_type", "code");


        Call<String> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAuth("Bearer "+bearerToken , paramObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code()==200){
                    String code = response.body();
                    //split string from = sign
                    String[] parts = code.split("=");
                    String part1 = parts[0]; // https://staging-app1.shoppreglobal.com/access/oauth?code
                    String part2 = parts[1]; // 8b625060eba82f7fe1905303bed8c67638b7587b
//                    Log.d("Auth api response ",code);
                    callAccessTokenApi(part2 , bearerToken);

                }
                else if (response.code()==401){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Invalid Token",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(),Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void callAccessTokenApi(String part2, String bearer1) {

        String auth =bearer1;
        Call<AccessTokenResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAccessToken(part2 , auth);
        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.code()==200){
//                  callMeApi(response.body().getAccessToken());
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    sharedPrefManager.setLogin();
                    LoadingDialog.cancelLoading();
                    if (checkLogin.equals("login")){
                        startActivity(new Intent(SignUpActivity.this , OrderActivity.class));
                        finishAffinity();
                    }
                    else{
                        startActivity(new Intent(SignUpActivity.this , OnBoardingActivity.class));
                        finishAffinity();
                    }
                }

                else if (response.code()==400){
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Code has expired",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(),Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
//    private void callAuthApi(String bearer) {
//
//
//        JsonObject paramObject = new JsonObject();
//
//        paramObject.addProperty("allow", "true");
//        paramObject.addProperty("client_id", "app1");
//        paramObject.addProperty("redirect_uri", "https://staging-app1.shoppreglobal.com/access/oauth");
//        paramObject.addProperty("response_type", "code");
//
//
//        Call<String> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getAuth("Bearer " + bearer, paramObject.toString());
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (response.code() == 200) {
//                    String code = response.body();
//                    //split string from "=" sign
//                    String[] parts = code.split("=");
////                    String part1 = parts[0]; // https://staging-app1.shoppreglobal.com/access/oauth?code
//                    String part2 = parts[1]; // 8b625060eba82f7fe1905303bed8c67638b7587b
//                    callAccessTokenApi(part2, bearer);
//
//                } else if (response.code() == 401) {
//                    LoadingDialog.cancelLoading();
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Invalid Token", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                LoadingDialog.cancelLoading();
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        });
//    }
//
//    private void callAccessTokenApi(String part2, String bearer1) {
//
//
//        Call<AccessTokenResponse> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getAccessToken(part2, bearer1);
//        call.enqueue(new Callback<AccessTokenResponse>() {
//            @Override
//            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
//                if (response.code() == 200) {
////                  callMeApi(response.body().getAccessToken());
//                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
//                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
//                    sharedPrefManager.setLogin();
//                    LoadingDialog.cancelLoading();
//                    if (checkLogin.equals("login")) {
//                        startActivity(new Intent(SignUpActivity.this, OrderActivity.class));
//                    } else {
//                        startActivity(new Intent(SignUpActivity.this, OnBoardingActivity.class));
//                    }
//                    finish();
//                } else if (response.code() == 400) {
//                    LoadingDialog.cancelLoading();
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Code has expired", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                } else {
//                    LoadingDialog.cancelLoading();
//                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AccessTokenResponse> call, @NonNull Throwable t) {
//                LoadingDialog.cancelLoading();
//                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//        });
//    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SignUpActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void setError(String message) {

        emailIdField.setBoxStrokeWidthFocused(2);
        emailIdField.setBoxStrokeWidth(2);
        emailIdField.setError(message);

    }
}