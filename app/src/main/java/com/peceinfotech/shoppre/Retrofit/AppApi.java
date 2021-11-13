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
import com.peceinfotech.shoppre.OrderModuleResponses.AddCommentResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.AddOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CancelShopperResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CancelledApiResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.CartModelResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.DeleteOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.OrderListingResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ProductCategoryResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ShippingRateResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ShopperOrdersResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.peceinfotech.shoppre.OrderModuleResponses.SlabResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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

    //    https://staging-app1.shoppreglobal.com/api/orders?type=active
    //https://staging-app1.shoppreglobal.com/api/orders/history?shopperType=ps
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/orders?type=active")
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

    //https://staging-app1.shoppreglobal.com/api/orders/172/item/240?shopperType=ps
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @DELETE("api/orders/{order_id}/item/{order_item_id}?shopperType=ps")
    Call<List<DeleteOrderResponse>> deleteOrder(
            @Header("Authorization") String bearer,
            @Path("order_id") int orderCode,
            @Path("order_item_id") Integer OrderId

    );

    //https://staging-app1.shoppreglobal.com/api/shopperOrders
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/shopperOrders")
    Call<ShopperOrdersResponse> shopperOrder(
            @Header("Authorization") String bearer


    );

    //https://staging-app1.shoppreglobal.com/api/shopperOrders/172
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shopperOrders/{order_id}")
    Call<CartModelResponse> getCartItem(
            @Header("Authorization") String auth,
            @Path("order_id") int orderCode
    );

    //https://staging-logistics.shoppre.com/api/pricing/global
    // ?country_id=226&type=nondoc&weight=0.5&length=0.5&
    // width=0.5&height=0.5&scale=cm&unit=kg&is_liquid=false&category_id=1
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/pricing/global?")
    Call<ShippingRateResponse> getShippingRate(

            @Query("country_id") String id,
            @Query("type") String type,
            @Query("weight") String weight,
            @Query("length") String length,
            @Query("width") String width,
            @Query("height") String height,
            @Query("scale") String scale,
            @Query("unit") String unit,
            @Query("is_liquid") String liquid,
            @Query("category_id") String categoryId
    );

    //https://staging-logistics.shoppre.com/api/pricing/slab?country_id=13&type=nondoc&is_liquid=false&category_id=1
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/pricing/slab?")
    Call<List<SlabResponse>> getSLab(

            @Query("country_id") String id,
            @Query("type") String type,
            @Query("is_liquid") String liquid,
            @Query("category_id") String categoryId
    );


    //https://staging-engage.shoppre.com/api/orders/170/comments
    @GET("api/orders/{id}/comments")
        Call<List<GetCommentsResponse>> getComments(
                @Path("id") String id
    );

    //https://staging-engage.shoppre.com/api/orders/171/comments?type=customer
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/orders/{id}/comments?type=customer")
    Call<AddCommentResponse> addComment(
            @Header("Authorization") String bearer,
            @Path("id") String id,
            @Body String comment


    );
    //https://staging-app1.shoppreglobal.com/api/shopperOrders/cancel
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/shopperOrders/cancel")
    Call<CancelShopperResponse> cancel(
            @Header("Authorization") String bearer,
            @Body String jsonArray

    );

    //https://staging-logistics.shoppre.com/api/categories
    @GET("api/categories")
    Call<List<ProductCategoryResponse>> getCategory();

    //https://staging-app1.shoppreglobal.com/api/orders/submitOptions
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/orders/submitOptions")
    Call<ResponseBody> submitOrder(
            @Header("Authorization") String auth,
            @Body String objects);
}
