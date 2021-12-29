package com.shoppreglobal.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient2 {
    private static final String BASE_URL = "https://uat-login.shoppreglobal.com/";
    private static RetrofitClient2 myClient;
    private Retrofit retrofit;

    RetrofitClient2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public static synchronized RetrofitClient2 getInstance() {
        if (myClient == null) {
            myClient = new RetrofitClient2();
        }
        return myClient;
    }



    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
