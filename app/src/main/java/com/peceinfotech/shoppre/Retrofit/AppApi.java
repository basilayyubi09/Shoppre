package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.WalletTransactionResponse;
import com.peceinfotech.shoppre.AuthenticationModel.CommonModel;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApi {


    //Me Response
//    https://staging-app.shoppreglobal.com/api/users/me
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/me")
    Call<MeResponse> getUser(
            @Header("Authorization") String auth
    );

    //Add Address
    //https://staging-app1.shoppreglobal.com/api/addresses
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/addresses")
    Call<AddAddressResponse> addAddress(@Header("Authorization") String auth, @Body String jsonObject);


    //Country Array

    @GET("api/search")
    public Call<CountryResponse> getCountry
            (
                    @Query("type") String country,
                    @Query("q") String typedString
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
            @Header("Authorization") String auth,
            @Path("id") int id, @Body String defaultAddress);

    //api/users/
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/{id}/transactions")
    Call<WalletTransactionResponse> getDetails(
            @Path("id") int id,
            @Query("offset") String offset,
            @Query("limit") String limit,
            @Header("Authorization") String auth
    );

    //    https://staging-app1.shoppreglobal.com/api/addresses/100416
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/addresses/{id}")
    Call<UpdateAddressResponse> UpdateAddress(
            @Header("Authorization") String auth,
            @Path("id") int id, @Body String objects);

}
