package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AuthenticationModel.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface AppApi {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/me")
    Call<MeResponse> getUser(
           @Header("Authorization") String auth
    );

    @GET("api/addresses")
    Call<Address> getAddresses();

}
