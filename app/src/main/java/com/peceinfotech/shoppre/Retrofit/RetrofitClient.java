package com.peceinfotech.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {
    private static final String BASE_URL = "https://staging-login.shoppreglobal.com/";
    private static RetrofitClient myClient;
    private Retrofit retrofit;

    RetrofitClient() {
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

    public static synchronized RetrofitClient getInstance() {
        if (myClient == null) {
            myClient = new RetrofitClient();
        }
        return myClient;
    }



    public Api getApi() {
        return retrofit.create(Api.class);
    }
}

