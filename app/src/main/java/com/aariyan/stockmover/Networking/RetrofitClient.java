package com.aariyan.stockmover.Networking;

import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.MainActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
   // public static String BASE_URLs = MainActivity.getURL();
    public static String BASE_URLs = MainActivity.getURL();


    //private static String BASE_URL = "http://102.37.0.48/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URLs)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
