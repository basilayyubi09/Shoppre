//package com.shoppreglobal.shoppre.Retrofit;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
//
//public class MinioRetrofitClient {
//
//    private static final String BASE_URL = "https://stag-minio.shoppre.com/test/2021/11/d9f4cfda-391b-41b1-ab36-bdde34d52662.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=VLJVI0W8O91GZ7UTP9M7%2F20211221%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20211221T050159Z&X-Amz-Expires=86400&X-Amz-SignedHeaders=host&X-Amz-Signature=dd4d868f0fcb35c40049e021c62f534707710796b09e789b13ce5dcf8519fcad";
//    private static MinioRetrofitClient minioRetrofitClient;
//    private Retrofit retrofit;
//
//    MinioRetrofitClient() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(okHttpClient1)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    public static OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .build();
//
//    public static synchronized MinioRetrofitClient getMinioInstance() {
//        if (minioRetrofitClient == null) {
//            minioRetrofitClient = new MinioRetrofitClient();
//        }
//        return minioRetrofitClient;
//    }
//
//
//
//    public AppApi getAppApi(){
//        return minioRetrofitClient.
//    }
//}
