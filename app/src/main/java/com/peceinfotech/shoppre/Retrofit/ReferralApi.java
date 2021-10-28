package com.peceinfotech.shoppre.Retrofit;

import com.peceinfotech.shoppre.AccountResponse.ReferralHistoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ReferralApi {
    // Referral history
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/users/referral")
    Call<ReferralHistoryResponse> getReferralHistory(
            @Header("Authorization") String auth
    );
}
