package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.AddAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.CountryResponse;
import com.peceinfotech.shoppre.AccountResponse.DeleteAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.MeResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateAddressResponse;
import com.peceinfotech.shoppre.AccountResponse.UpdateProfileResponse;
import com.peceinfotech.shoppre.AccountResponse.WalletAmountResponse;
import com.peceinfotech.shoppre.AccountResponse.WalletTransactionResponse;
import com.peceinfotech.shoppre.AuthenticationModel.CommonModel;
import com.peceinfotech.shoppre.AuthenticationModel.DeliveryListModel;
import com.peceinfotech.shoppre.OrderModuleResponses.AddOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CancelledApiResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderListingResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ShowOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //https://staging-wallet.shoppre.com/api/users/1
//    https://staging-wallet.shoppre.com/api/users/10036
//    api/users/{user_id}
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/users/{user_id}")
    Call<WalletAmountResponse> getWallet(
            @Header("Authorization") String auth,
            @Path("user_id") int id);

    //    https://staging-app1.shoppreglobal.com/api/addresses/100416
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/addresses/{id}")
    Call<UpdateAddressResponse> UpdateAddress(
            @Header("Authorization") String auth,
            @Path("id") int id,
            @Body String objects);

    //https://staging-app1.shoppreglobal.com/api/addresses/1024
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @DELETE("/api/addresses/{id}")
    Call<DeleteAddressResponse> deleteAddress(
            @Header("Authorization") String auth,
            @Path("id") int id
    );

    //https://staging-app1.shoppreglobal.com/api/users/me
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/users/me")
    Call<UpdateProfileResponse> UpdateProfile(
            @Header("Authorization") String auth,
            @Body String objects);


    //https://staging-app1.shoppreglobal.com/api/orders/history?shopperType=ps
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/orders/history?shopperType=ps")
    Call<OrderListingResponse> getOrderListing(
            @Header("Authorization") String auth
    );

    //https://staging-app1.shoppreglobal.com/api/orders
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/orders")
    Call<AddOrderResponse> addOrder(@Header("Authorization") String auth, @Body String jsonObject);

    //https://staging-app1.shoppreglobal.com/api/orders/{orders_code}
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/orders/{orders_code}")
    Call<ShowOrderResponse> showOrder(
            @Header("Authorization") String auth,
            @Path("orders_code") String orderCode
    );

    //    https://staging-app1.shoppreglobal.com/api/orders?type=cancel
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/orders?type=cancel")
    Call<CancelledApiResponse> getCancelOrder(
            @Header("Authorization") String auth
    );
}
