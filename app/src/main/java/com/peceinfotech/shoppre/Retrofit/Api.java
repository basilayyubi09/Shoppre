package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface Api {

    //SignUp Direct Api
    // https://staging-login.shoppre.com/api/users/public/registerVerify
    @POST("users/public/registerVerify")
    Call<RegisterVerifyResponse> registerVerify(

            @Field("email") String email ,
            @Field("first_name") String first_name ,
            @Field("first_visit") String first_visit,
            @Field("from_domain") String from_domain,
            @Field("last_name") String last_name,
            @Field("password") String password ,
            @Field("recaptuaToken") String recaptuaToken,
            @Field("referral_code") String referral_code,
            @Field("referrer") String referrer
    );
}
