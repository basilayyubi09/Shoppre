package com.peceinfotech.shoppre.Retrofit;

import com.google.gson.JsonObject;
import com.peceinfotech.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.peceinfotech.shoppre.AuthenticationModel.SignInDirectResponse;

import java.util.HashMap;

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

            @Body String  jsonObject


//            @Field("email") String email ,
//            @Field("first_name") String first_name ,
//            @Field("first_visit") String first_visit,
//            @Field("from_domain") String from_domain,
//            @Field("last_name") String last_name,
//            @Field("password") String password ,
//            @Field("referral_code") String referral_code,
//            @Field("referrer") String referrer

//            @Body HashMap registerApiPayload
    );

    //Forgot password Api
    //https://staging-login.shoppre.com/api/password_reset
    @FormUrlEncoded
    @POST("api/password_reset")
    Call<ForgotPasswordResponse> forgotPassword(
      @Field("email") String email
    );

    //SignIn Direct
    //https://staging-login.shoppreglobal.com/oauth/token
    //https://staging-login.shoppreglobal.com/oauth/token
    @FormUrlEncoded
    @POST("oauth/token")
    Call<SignInDirectResponse> signInDirect(
            @Field("username") String userName,
            @Field("password") String password
    );
}
