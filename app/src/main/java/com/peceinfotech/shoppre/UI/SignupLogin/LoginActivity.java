package com.peceinfotech.shoppre.UI.SignupLogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AuthenticationModel.SignInDirectResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignUpGoogleResponse;
import com.peceinfotech.shoppre.R;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient;
import com.peceinfotech.shoppre.Retrofit.RetrofitClient2;
import com.peceinfotech.shoppre.Utils.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView dont_have_acnt, forgotPassword;
    TextInputLayout passwordField;
    MaterialButton loginBtn, googleLoginBtn, fbLoginBtn;
    String emailId, password;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    TextInputLayout loginEmailIdField;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        //Hooks
        dont_have_acnt = findViewById(R.id.dont_have_acnt);
        forgotPassword = findViewById(R.id.forgot_password);
        loginEmailIdField = findViewById(R.id.emailIdField);
        passwordField = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginBtn);
        googleLoginBtn = findViewById(R.id.login_google_btn);
        fbLoginBtn = findViewById(R.id.login_fb_logo);

        //get Email Id from previous activity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int flag = extras.getInt("flag");
            if (flag == 1) {
                setError("Looks like this user already exists! Please use a new Email ID");
            }
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                LoadingDialog.showLoadingDialog(LoginActivity.this, getString(R.string.Loading));
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromField();
                if (!validateEmailField() || !validatePasswordField()) {
                    return;
                } else {
                    signInDirect();
                    LoadingDialog.showLoadingDialog(LoginActivity.this, getString(R.string.Loading));
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
                                                LoadingDialog.cancelLoading();
                                                // Application code
                                                try {
                                                    Log.i("Response", response.toString());


                                                    String firstName = response.getJSONObject().getString("first_name");
                                                    String lastName = response.getJSONObject().getString("last_name");
                                                    String email = "";
                                                    String id = response.getJSONObject().getString("id");
                                                    if (object.has("email")) {
                                                        email = object.getString("email");
                                                    }

                                                    //TODO put your code here

                                                    signUpFacebook(email, firstName, lastName);

                                                    Toast.makeText(getApplicationContext(), firstName + "\n"
                                                            + lastName +
                                                            "\n" + "email= " + email + "\n" + id, Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(LoginActivity.this, SignUp_Valid.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
            }
        });
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


        Call<SignUpGoogleResponse> call = RetrofitClient2
                .getInstance()
                .getApi()
                .signUpFacebook(paramObject.toString());

        call.enqueue(new Callback<SignUpGoogleResponse>() {
            @Override
            public void onResponse(Call<SignUpGoogleResponse> call, Response<SignUpGoogleResponse> response) {
                SignUpGoogleResponse signUpGoogleResponse = response.body();
                if (response.isSuccessful()) {
                    if (signUpGoogleResponse.getCode() == 201) {
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getApplicationContext(), "SignUp successful", Toast.LENGTH_SHORT).show();
                    } else if (signUpGoogleResponse.getCode() == 409) {
                        LoadingDialog.cancelLoading();
                        signInFacebook(email, firstName, lastName);
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

                    LoadingDialog.cancelLoading();
                    Toast.makeText(getApplicationContext(), "Sign In successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    LoadingDialog.cancelLoading();
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
                    LoadingDialog.cancelLoading();
                    clearFields();
                    Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                } else {
                    LoadingDialog.cancelLoading();
                    showError("User credentials are invalid");
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
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            if (acct != null) {


                Toast.makeText(getApplicationContext(), "register " + firstName + "\n" + lastName
                        + "\n" + fullName + "\n" +
                        email + "\n" +
                        personId, Toast.LENGTH_LONG).show();

                signInGoogle(email, firstName, lastName);

            }


        } catch (ApiException e) {

            LoadingDialog.cancelLoading();
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
                LoadingDialog.cancelLoading();

                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Sign In successfully", Toast.LENGTH_SHORT).show();
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
                if (response.isSuccessful()) {
                    if (signUpGoogleResponse.getCode() == 201) {
                        LoadingDialog.cancelLoading();
                        Toast.makeText(getApplicationContext(), "SignUp successful", Toast.LENGTH_SHORT).show();
                    } else if (signUpGoogleResponse.getCode() == 409) {
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

    public void setError(String message) {
        passwordField.setBoxStrokeWidth(2);
        passwordField.setBoxStrokeWidthFocused(2);
        passwordField.setError(" ");
        loginEmailIdField.setBoxStrokeWidthFocused(2);
        loginEmailIdField.setBoxStrokeWidth(2);
        loginEmailIdField.setError(message);

    }

}



