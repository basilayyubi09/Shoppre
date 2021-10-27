package com.peceinfotech.shoppre.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientWallet {
    private static final String BASE_URL = "https://staging-wallet.shoppre.com/";
    private static RetrofitClientWallet myClient3;
    private Retrofit retrofitWallet;

    RetrofitClientWallet() {
        retrofitWallet = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()


            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180,TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public static synchronized RetrofitClientWallet getInstanceWallet() {
        if (myClient3 == null) {
            myClient3 = new RetrofitClientWallet();
        }
        return myClient3;
    }



    public AppApi getAppApi(){
        return retrofitWallet.create(AppApi.class);
    }
}
