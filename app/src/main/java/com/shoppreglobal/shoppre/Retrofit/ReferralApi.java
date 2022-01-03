package com.shoppreglobal.shoppre.Retrofit;

import com.shoppreglobal.shoppre.AccountResponse.ReferralHistoryResponse;
import com.shoppreglobal.shoppre.AccountResponse.SubmitReferralResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReferralApi {
    // Referral history
    //https://staging-referral.shoppre.com/api/users/referral?first_name=hanzala
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/referral?")
    Call<ReferralHistoryResponse> getReferralHistory(
            @Header("Authorization") String auth,
            @Query("first_name") String id
    );

    //https://staging-referral.shoppre.com/api/users/100920/wallet/referrer/update
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT("api/users/{id}/wallet/referrer/update")
    Call<SubmitReferralResponse> submitReferralCode(
            @Header("Authorization") String auth,
            @Path("id") int id,
            @Body String s);
}
