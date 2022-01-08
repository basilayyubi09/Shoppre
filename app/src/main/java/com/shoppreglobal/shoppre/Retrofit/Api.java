package com.shoppreglobal.shoppre.Retrofit;

import com.shoppreglobal.shoppre.AccountResponse.AccessTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.ChangePasswordResponse;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.RefreshTokenResponse;
import com.shoppreglobal.shoppre.AccountResponse.VerifyEmailDeepLinkResponse;
import com.shoppreglobal.shoppre.AccountResponse.VerifyEmailResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.ForgotPasswordResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.RegisterVerifyResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignInDirectResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignInGoogleResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.SignUpGoogleResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
        https://uat-login.shoppreglobal.com/api/password_reset
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

    //SignIn Facebook
    //https://staging-login.shoppreglobal.com/oauth/token/app
    @FormUrlEncoded
    @POST("oauth/token/app")
    Call<SignInGoogleResponse> signInFacebook(
            @Field("email") String email,
            @Field("grant_type") String facebook
    );

    @FormUrlEncoded
    @POST("oauth/token")
    Call<AccessTokenResponse> getAccessToken(
            @Field("code") String code,
            @Field("Authorization") String auth
    );

    //api/authorise
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/authorise")
    Call<String> getAuth(
            @Header("Authorization") String auth,
            @Body String value
    );

    //    https://staging-login.shoppreglobal.com/oauth/token/app
    //Refresh Token
    @FormUrlEncoded
    @POST("oauth/token/app")
    Call<RefreshTokenResponse> getRefreshToken(
            @Field("refresh_token") String code

    );

    //Me Response
//    https://staging-login.shoppreglobal.com/api/users/me
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/me")
    Call<MeResponse> getUser(
            @Header("Authorization") String auth
    );

    //https://staging-login.shoppreglobal.com/api/users/sendVerify/10076/email
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/sendVerify/{id}/email")
    Call<VerifyEmailResponse> getVerify(
            @Header("Authorization") String auth,
            @Path("id") int id
    );

    //https://uat-login.shoppreglobal.com/api/users/100376/changePassword
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/users/{id}/changePassword")
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String auth,
            @Path("id") Integer id
            ,@Body String object
    );

    //https://staging-login.shoppreglobal.com/api/authorise
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/authorise")
    Call<String> payAuthorize(
            @Header("Authorization") String auth,
            @Body String objects
    );

    //https://uat-login.shoppreglobal.com/api/password_reset/100376
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/password_reset/{id}")
    Call<String> resetPassword(

            @Path("id") Integer id
            ,@Body String object
    );

    //https://uat-login.shoppreglobal.com/api/users/me
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/password_reset/{id}")
    Call<VerifyEmailDeepLinkResponse> confirmEmail(
            @Header("Authorization") String auth,
            @Body String object
    );
}
