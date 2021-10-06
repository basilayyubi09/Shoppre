package com.peceinfotech.shoppre.Retrofit;

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

    //SignUp Direct Api
    //https://staging-login.shoppreglobal.com/api/users/public/register/app
//    Call<JsonElement> register(@Body HashMap registerApiPayload);

    @Headers("Content-Type: application/json")
    @POST("api/users/public/register/app")
    Call<RegisterVerifyResponse> registerVerify(

            @Body String body

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
    @FormUrlEncoded
    @POST("oauth/token")
    Call<SignInDirectResponse> signInDirect(
            @Field("username") String userName,
            @Field("password") String password
    );
}
