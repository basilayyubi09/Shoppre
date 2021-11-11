package com.peceinfotech.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EngageRetrofitClient {
    private static final String BASE_URL = "https://staging-engage.shoppre.com/";
    private static EngageRetrofitClient myClient3;
    private Retrofit retrofit3;

    EngageRetrofitClient() {
        retrofit3 = new Retrofit.Builder()
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

    public static synchronized EngageRetrofitClient getInstance3() {
        if (myClient3 == null) {
            myClient3 = new EngageRetrofitClient();
        }
        return myClient3;
    }



    public AppApi getAppApi(){
        return retrofit3.create(AppApi.class);
    }
}
