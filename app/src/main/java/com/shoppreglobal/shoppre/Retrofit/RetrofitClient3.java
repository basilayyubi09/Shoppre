package com.shoppreglobal.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient3 {


    private static final String BASE_URL = "https://uat-app1.shoppreglobal.com/";
    private static RetrofitClient3 myClient3;
    private Retrofit retrofit3;

    RetrofitClient3() {

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

    public static synchronized RetrofitClient3 getInstance3() {
        if (myClient3 == null) {
            myClient3 = new RetrofitClient3();
        }
        return myClient3;
    }



    public AppApi getAppApi(){
        return retrofit3.create(AppApi.class);
    }
}
