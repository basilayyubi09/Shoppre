package com.shoppreglobal.shoppre.UI.SignupLogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.shoppreglobal.shoppre.AccountResponse.AccessTokenResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignInDirectResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignUpGoogleResponse;
import com.shoppreglobal.shoppre.R;
import com.shoppreglobal.shoppre.Retrofit.RetrofitClient;
import com.shoppreglobal.shoppre.UI.OnBoarding.OnBoardingActivity;
import com.shoppreglobal.shoppre.UI.Orders.OrderActivity;
import com.shoppreglobal.shoppre.Utils.CheckNetwork;
import com.shoppreglobal.shoppre.Utils.LoadingDialog;
import com.shoppreglobal.shoppre.Utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView forgotPassword;
    TextInputLayout passwordField;
    MaterialButton loginBtn, googleLoginBtn, fbLoginBtn;
    LinearLayout main, dont_have_acnt;
    String emailId, password, checkLogin;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    TextInputLayout loginEmailIdField;
    String personId;
    private CallbackManager callbackManager;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();


        //Shared Pref Manager
        sharedPrefManager = new SharedPrefManager(LoginActivity.this);


        //Hooks
        dont_have_acnt = findViewById(R.id.signUpNow);
        forgotPassword = findViewById(R.id.forgot_password);
        loginEmailIdField = findViewById(R.id.emailIdField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);
        googleLoginBtn = findViewById(R.id.login_google_btn);
        fbLoginBtn = findViewById(R.id.login_fb_logo);
        main = findViewById(R.id.main);


        setupUI(main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
                {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    signIn();
                    LoadingDialog.showLoadingDialog(LoginActivity.this, getString(R.string.Loading));
                }

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateEmailField() || !validatePasswordField()) {
                    return;
                } else {
                    if (!CheckNetwork.isInternetAvailable(getApplicationContext())) //if connection not available
                    {

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "No Internet Connection", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        LoadingDialog.showLoadingDialog(LoginActivity.this, getString(R.string.Loading));
                        signInDirect();

                    }
                }
            }
        });

        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadingDialog.showLoadingDialog(LoginActivity.this, getString(R.string.Loading));

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {

                                // setFacebookData(loginResult);

                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                // Application code
                                                try {
                                                    Log.i("Response", response.toString());


                                                    String firstName = response.getJSONObject().getString("first_name");
                                                    String lastName = response.getJSONObject().getString("last_name");
                                                    String email = "";
                                                    String id = response.getJSONObject().getString("id");
                                                    if (object.has("email")) {
                                                        email = object.getString("email");
                                                        signInFacebook(emailId, firstName, lastName , id);
                                                    }
                                                    else {LoadingDialog.cancelLoading();
                                                        Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                    }


//                                                    Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();


                                                } catch (JSONException e) {
                                                    Toast.makeText(LoginActivity.this, "Error occurred try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,email,first_name,last_name");
                                request.setParameters(parameters);
                                request.executeAsync();
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

        dont_have_acnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
                finish();
            }
        });
    }

    private void signUpFacebook(String emailId, String firstName, String lastName, String id) {
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
                    if (signUpGoogleResponse.getToken() != null) {

                        checkLogin = "signup";
                        callAuthApi(signUpGoogleResponse.getToken().getAccessToken());
                    } else if (signUpGoogleResponse.getToken() == null) {
                        signInFacebook(emailId, firstName, lastName, id);
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

    private void signInFacebook(String email, String firstName, String lastName, String id) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInFacebook(emailId, "facebook");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                if (response.code() == 200) {
                    checkLogin = "login";
                    callAuthApi(response.body().getAccessToken());
                } else if (response.code() == 400) {
                    signUpFacebook(email, firstName, lastName , id);
                }
            }

            @Override
            public void onFailure(Call<SignInGoogleResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInDirect() {
        Call<SignInDirectResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signInDirect(emailId, password);
        call.enqueue(new Callback<SignInDirectResponse>() {
            @Override
            public void onResponse(Call<SignInDirectResponse> call, Response<SignInDirectResponse> response) {


                int code = response.code();

//                signInDirectResponse.getCode();
                if (code != 400) {
                    clearFields();
                    String token = response.body().getAccessToken();
                    checkLogin = "login";
                    callAuthApi(token);
                } else {
                    LoadingDialog.cancelLoading();
                    showError("Looks like the Email ID and Password you entered are incorrect. Please try again!");
                }

            }

            @Override
            public void onFailure(Call<SignInDirectResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

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

        } else {
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
            personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            if (acct != null) {

                signUpGoogle(email, firstName, lastName);

            }


        } catch (ApiException e) {

            LoadingDialog.cancelLoading();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }
    }

    private void signInGoogle(String emailId, String firstName, String lastName) {

        Call<SignInGoogleResponse> call = RetrofitClient
                .getInstance()
                .getApi().signInGoogle(emailId, "google");
        call.enqueue(new Callback<SignInGoogleResponse>() {
            @Override
            public void onResponse(Call<SignInGoogleResponse> call, Response<SignInGoogleResponse> response) {
                LoadingDialog.cancelLoading();

                if (response.code() == 200) {

                    checkLogin = "login";
                    callAuthApi(response.body().getAccessToken());
                } else if (response.code() == 400) {
                    signUpGoogle(emailId, firstName, lastName);
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

    private void clearFields() {
        loginEmailIdField.getEditText().setText("");
        passwordField.getEditText().setText("");
    }

    private void showError(String errorMessage) {
        loginEmailIdField.setBoxStrokeWidth(2);
        passwordField.setBoxStrokeWidth(2);
        loginEmailIdField.setBoxStrokeWidthFocused(2);
        passwordField.setBoxStrokeWidthFocused(2);
        loginEmailIdField.setError(" ");
        passwordField.setError(errorMessage);
    }

    private boolean validateEmailField() {
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailId.isEmpty()) {
            loginEmailIdField.setBoxStrokeWidth(2);
            loginEmailIdField.setBoxStrokeWidthFocused(2);
            loginEmailIdField.setError("This is a required field");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            loginEmailIdField.setBoxStrokeWidthFocused(2);
            loginEmailIdField.setBoxStrokeWidth(2);
            loginEmailIdField.setError("Enter Valid email");
            return false;
        } else {
            loginEmailIdField.setError(null);
            loginEmailIdField.setBoxStrokeWidth(0);
            return true;
        }
    }

    private boolean validatePasswordField() {
        if (password.isEmpty()) {
            passwordField.setBoxStrokeWidth(2);
            passwordField.setBoxStrokeWidthFocused(2);
            passwordField.setError("This is a required field");
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setBoxStrokeWidth(0);
            return true;
        }
    }

    private void getTextFromField() {
        emailId = loginEmailIdField.getEditText().getText().toString().trim();
        password = passwordField.getEditText().getText().toString().trim();
    }


    private void callAuthApi(String bearer) {
        String bearerToken = bearer;

        JsonObject paramObject = new JsonObject();

        paramObject.addProperty("allow", "true");
        paramObject.addProperty("client_id", "app1");
        paramObject.addProperty("redirect_uri", "https://uat-app1.shoppreglobal.com/access/oauth");
        paramObject.addProperty("response_type", "code");


        Call<String> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAuth("Bearer " + bearerToken, paramObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.code() == 200) {
                    String code = response.body();
                    //split string from = sign
                    String[] parts = code.split("=");
//                    String part1 = parts[0]; // https://staging-app1.shoppreglobal.com/access/oauth?code
                    String part2 = parts[1]; // 8b625060eba82f7fe1905303bed8c67638b7587b
//                    Log.d("Auth api response ",code);
                    callAccessTokenApi(part2, bearerToken);

                } else if (response.code() == 401) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Invalid Token", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void callAccessTokenApi(String part2, String bearer1) {

        String auth = bearer1;
        Call<AccessTokenResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAccessToken(part2, auth);
        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.code() == 200) {
//                  callMeApi(response.body().getAccessToken());
                    sharedPrefManager.storeBearerToken(response.body().getAccessToken());
                    sharedPrefManager.storeRefreshToken(response.body().getRefreshToken());
                    sharedPrefManager.setLogin();
                    LoadingDialog.cancelLoading();
                    if (checkLogin.equals("login")) {
                        startActivity(new Intent(LoginActivity.this, OrderActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LoginActivity.this, OnBoardingActivity.class));
                        finish();
                    }
                } else if (response.code() == 400) {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Code has expired", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    LoadingDialog.cancelLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Something Went Wrong", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                LoadingDialog.cancelLoading();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.main), t.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

//    private void callMeApi(String token) {
//
//        Call<MeResponse> call = RetrofitClient3
//                .getInstance3()
//                .getAppApi()
//                .getUser("Bearer " + token);
//        call.enqueue(new Callback<MeResponse>() {
//            @Override
//            public void onResponse(Call<MeResponse> call, Response<MeResponse> response) {
//
//                if (response.code() == 200) {
//
//                    sharedPrefManager.storeFirstName(response.body().getFirstName());
//                    sharedPrefManager.storeLastName(response.body().getLastName());
//                    sharedPrefManager.storeFullName(response.body().getName());
//                    sharedPrefManager.storeEmail(response.body().getEmail());
//                    sharedPrefManager.storeId(response.body().getId());
//                    sharedPrefManager.storeSalutation(response.body().getSalutation());
//                    sharedPrefManager.storeBearerToken(token);
//                    sharedPrefManager.storeVirtualAddressCode(response.body().getVirtualAddressCode());
//                    sharedPrefManager.setLogin();
//                    LoadingDialog.cancelLoading();
//                    Toast.makeText(getApplicationContext(), response.body().getSalutation()
//                            + "\n" + response.body().getFirstName() + "\n" +
//                            response.body().getLastName() + "\n" +
//                            response.body().getName() + "\n" +
//                            response.body().getEmail() + "\n" +
//                            response.body().getVirtualAddressCode(), Toast.LENGTH_LONG).show();
//                    if (checkLogin.equals("login")) {
//                        startActivity(new Intent(LoginActivity.this, OrderActivity.class));
//                        finishAffinity();
//                    } else {
//                        startActivity(new Intent(LoginActivity.this, OnBoardingActivity.class));
//                        finishAffinity();
//                    }
//
//                } else if (response.code() == 401) {
//                    LoadingDialog.cancelLoading();
//                    Toast.makeText(getApplicationContext(), "not registered", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MeResponse> call, Throwable t) {
//
//                LoadingDialog.cancelLoading();
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }
}



