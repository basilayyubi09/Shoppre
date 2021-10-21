package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppApi {


    //Me Response

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/me")
    Call<MeResponse> getUser(
           @Header("Authorization") String auth
    );

    //Add Address

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/addresses")
    Call<AddAddressResponse> registerVerify(
            @Body String jsonObject
    );

}
