package com.shoppreglobal.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ReferralRetrofitClient {

    private static final String BASE_URL = "https://staging-referral.shoppre.com/";
    private static ReferralRetrofitClient myClient;
    private Retrofit retrofit;

    ReferralRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient1)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static synchronized ReferralRetrofitClient getInstance3() {
        if (myClient == null) {
            myClient = new ReferralRetrofitClient();
        }
        return myClient;
    }



    public ReferralApi getRefferalApi(){
        return retrofit.create(ReferralApi.class);
    }
}
