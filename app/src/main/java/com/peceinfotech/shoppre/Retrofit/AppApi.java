package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;

import com.peceinfotech.shoppre.AuthenticationModel.CommonModel;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.AuthenticationModel.RegisterVerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    //Get All delivery Address
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/addresses")
    Call<DeliveryListModel> getAddresses(
            @Header("Authorization") String auth
    );

    //Set Default Delivery Address
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/addresses/default/{id}")
    Call<CommonModel> setDefault(
            @Header("Authorization") String auth ,
            @Path("id") int id , @Body String defaultAddress);

}
