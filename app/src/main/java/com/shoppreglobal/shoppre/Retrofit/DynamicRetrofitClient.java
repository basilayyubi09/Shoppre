package com.shoppreglobal.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DynamicRetrofitClient {

    public static final String BASE_URL = "https://stag-minio.shoppre.com/test/2021/11/";
    private static DynamicRetrofitClient dynamicClient;
    private Retrofit retrofitDynamic;

    DynamicRetrofitClient() {
        retrofitDynamic = new Retrofit.Builder()
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

    public static synchronized DynamicRetrofitClient getDynamicInstance() {
        if (dynamicClient == null) {
            dynamicClient = new DynamicRetrofitClient();
        }
        return dynamicClient;
    }



    public AppApi getAppApi(){
        return retrofitDynamic.create(AppApi.class);
    }
}
