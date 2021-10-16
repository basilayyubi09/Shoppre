package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInDirectResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignUpGoogleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    /*
    SignUp Direct Api
    https://staging-login.shoppreglobal.com/api/users/public/register/app
    */

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/users/public/register/app")
    Call<RegisterVerifyResponse> registerVerify(
            @Body String jsonObject
    );

    /*
       Forgot password Api
        https://staging-login.shoppre.com/api/password_reset
    */
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/password_reset")
    Call<ForgotPasswordResponse> forgotPassword(
            @Body String email
    );

    /*
    SignIn Direct
    https://staging-login.shoppreglobal.com/oauth/token
    */

    @FormUrlEncoded
    @POST("oauth/token")
    Call<SignInDirectResponse> signInDirect(
            @Field("username") String userName,
            @Field("password") String password
    );


    /*
    Google Signup
    https://staging-login.shoppreglobal.com/api/users/social/app
*/
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/users/social/app")
    Call<SignUpGoogleResponse> signUpGoogle(
            @Body String value
    );


    /*
    Google Signup
    https://staging-login.shoppreglobal.com/api/users/social/app
*/
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/users/social/app")
    Call<SignUpGoogleResponse> signUpFacebook(
            @Body String value
    );



    //SignIn Google
    //https://staging-login.shoppreglobal.com/oauth/token/app

    @FormUrlEncoded

    @POST("oauth/token/app")
    Call<SignInGoogleResponse> signInGoogle(
            @Field("email") String email,
            @Field("grant_type") String google
    );
}
