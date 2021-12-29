package com.shoppreglobal.shoppre.Retrofit;

import com.shoppreglobal.shoppre.AccountResponse.AddAddressResponse;
import com.shoppreglobal.shoppre.AccountResponse.CountryResponse;
import com.shoppreglobal.shoppre.AccountResponse.DeleteAddressResponse;
import com.shoppreglobal.shoppre.AccountResponse.MeResponse;
import com.shoppreglobal.shoppre.AccountResponse.UpdateAddressResponse;
import com.shoppreglobal.shoppre.AccountResponse.UpdateProfileResponse;
import com.shoppreglobal.shoppre.AccountResponse.WalletAmountResponse;
import com.shoppreglobal.shoppre.AccountResponse.WalletTransactionResponse;
import com.shoppreglobal.shoppre.Adapters.ShipmentAdapters.CancelShipmentModelResponse;
import com.shoppreglobal.shoppre.AuthenticationModel.CommonModel;
import com.shoppreglobal.shoppre.AuthenticationModel.DeliveryListModel;
import com.shoppreglobal.shoppre.CreateShipmentModelResponse.CreateShipmentResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.PackageListingResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.ReadyToSendResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.RedirectShipmentResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.ReturnPackageResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.VerifyLinkResponse;
import com.shoppreglobal.shoppre.LockerModelResponse.ViewPackageResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.AddCommentResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.AddOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.CancelShopperResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.CancelledApiResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.CartModelResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.DeleteOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.GetCommentsResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.OrderListingResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ProductCategoryResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShippingRateResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShopperOrdersResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.ShowOrderResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.SlabResponse;
import com.shoppreglobal.shoppre.OrderModuleResponses.UpdateOrderResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.DownloadInvoiceModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.MinioUploadModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.PostShipmentCommentModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.PreviousShipmentModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentCommentModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentDetailsModelResponse;
import com.shoppreglobal.shoppre.ShipmentModelResponse.ShipmentIndexModelResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<AddAddressResponse> addAddress(@Header("Authorization") String auth
            , @Body String jsonObject);


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
            @Query("offset") Integer offset,
            @Query("limit") Integer limit,
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
    @POST("api/orders/submitOptions")
    Call<ResponseBody> submitOrder(
            @Header("Authorization") String auth,
            @Body String objects);

    //https://staging-app1.shoppreglobal.com/api/orders/item/428
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/orders/item/{orderId}")
    Call<UpdateOrderResponse> updateOrder(
            @Header("Authorization") String auth,
            @Path("orderId") String id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packages?type=active
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/packages?type=active")
    Call<PackageListingResponse> lockerListing(
            @Header("Authorization") String auth
    );

    //https://staging-app1.shoppreglobal.com/api/packages/891
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/packages/{packageId}")
    Call<ViewPackageResponse> viewPackage(
            @Header("Authorization") String auth,
            @Path("packageId") int id
    );

    //staging-app1.shoppreglobal.com/api/orders/fetch/store?url=
    @GET("api/orders/fetch/store?")
    Call<VerifyLinkResponse> verifyLink(

            @Query("url") String id
    );

    //https://staging-app1.shoppreglobal.com/api/packages?type=cancel
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/packages?type=cancel")
    Call<PackageListingResponse> cancelPackage(
            @Header("Authorization") String auth
    );

    //https://staging-engage.shoppre.com/api/packages/171/comments
    @GET("api/packages/{id}/comments")
    Call<List<GetCommentsResponse>> getPackageComments(
            @Path("id") Integer id
    );

    //https://staging-engage.shoppre.com/api/packages/171/comments?type=customer

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/packages/{id}/comments?type=customer")
    Call<AddCommentResponse> addPackageComment(
            @Header("Authorization") String bearer,
            @Path("id") Integer id,
            @Body String comment);

    //https://staging-app1.shoppreglobal.com/api/packages/899/return
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/packages/{packageId}/return")
    Call<ReturnPackageResponse> returnPackage(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packages/891/exchange
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/packages/{packageId}/exchange")
    Call<ReturnPackageResponse> exchangePackage(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packages/889/abandon
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/packages/{packageId}/abandon")
    Call<ReturnPackageResponse> discardPackage(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packages/23/split
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/packages/{packageId}/split")
    Call<ReturnPackageResponse> splitPackage(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packageItems/915/values
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/packageItems/{packageId}/values")
    Call<ReturnPackageResponse> updatePrice(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);


    //https://staging-app1.shoppreglobal.com/api/photoRequests/899
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/photoRequests/{packageId}")
    Call<ReturnPackageResponse> standardPhoto(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/photoRequests/911
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/photoRequests/{packageId}")
    Call<ReturnPackageResponse> additionalPhoto(
            @Header("Authorization") String auth,
            @Path("packageId") Integer id,
            @Body String object);

    //https://staging-app1.shoppreglobal.com/api/packages?type=shiprequest
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/packages?type=shiprequest")
    Call<ReadyToSendResponse> readyToSend(
            @Header("Authorization") String auth
    );

    //https://uat-app1.shoppreglobal.com/api/shipments?bucket=ALL
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipments?bucket=ALL")
    Call<ShipmentIndexModelResponse> shipmentIndex(
            @Header("Authorization") String auth
    );

    //https://uat-app1.shoppreglobal.com/api/shipments/201
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipments/{shipmentId}")
    Call<ShipmentDetailsModelResponse> shipmentDetails(
            @Header("Authorization") String auth,
            @Path("shipmentId") Integer id
    );

    //https://uat-app1.shoppreglobal.com/api/shipments/redirectShipment?packageIds=929
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipments/redirectShipment?")
    Call<RedirectShipmentResponse> redirect(
            @Header("Authorization") String auth,
            @Query("packageIds") String type
    );

    //https://uat-app1.shoppreglobal.com/api/shipments?package_ids=908
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/shipments?")
    Call<CreateShipmentResponse> createShipment(
            @Header("Authorization") String bearer,
            @Query("package_ids") String type,
            @Body String shipment);

    //https://uat-app1.shoppreglobal.com/api/shipments?bucket=DELIVERED
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipments?bucket=DELIVERED")
    Call<PreviousShipmentModelResponse> previousShipment(
            @Header("Authorization") String auth
    );

    //https://staging-engage.shoppre.com/api/shipments/207/comments
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipments/{shipmentId}/comments")
    Call<List<ShipmentCommentModelResponse>> shipmentGetComment(
            @Header("Authorization") String auth,
            @Path("shipmentId") Integer id
    );

    //https://staging-engage.shoppre.com/api/shipments/902/comments?type=customer
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/shipments/{shipmentId}/comments?type=customer")
    Call<PostShipmentCommentModelResponse> postShipmentComment(
            @Header("Authorization") String bearer,
            @Path("shipmentId") Integer id,
            @Body String comment);

    //https://uat-app1.shoppreglobal.com/api/shipments/228/cancel
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/shipments/{shipmentId}/cancel")
    Call<CancelShipmentModelResponse> cancelShipment(
            @Header("Authorization") String auth,
            @Path("shipmentId") Integer id
    );

    //https://uat-app1.shoppreglobal.com/api/shipmentInvoice/225
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/shipmentInvoice/{shipmentId}")
    Call<DownloadInvoiceModelResponse> downloadInvoice(
            @Header("Authorization") String auth,
            @Path("shipmentId") Integer id
    );

    //https://uat-app1.shoppreglobal.com/api/minio/presignedUrl?filename=punith.jpg
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/minio/presignedUrl?")
    Call<MinioUploadModelResponse> minioUpload(
            @Header("Authorization") String auth,
            @Query("filename") String fileName
    );

    //https://stag-minio.shoppre.com/test/2021/11/eabc97be-a2f8-41d5-a926-87575e3bbaba.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=VLJVI0W8O91GZ7UTP9M7%2F20211220%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20211220T122000Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=22e0fad760facfd68901063e4692c66d4c869290224f6343edd43a022c3cc555


    @PUT("{link}")
    Call<Integer> minioUpload2(
            @Path("link") String path,
            @Body RequestBody object
    );

    //https://uat-app1.shoppreglobal.com/api/packages
    @Multipart
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/packages")
    Call<Integer> selfShopper(
            @Header("Authorization") String auth,
            @Part("") RequestBody requestBody
    );

}
