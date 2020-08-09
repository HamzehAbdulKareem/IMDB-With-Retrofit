package com.example.retrofitwithimdb.models;

import android.content.Context;
import android.util.Log;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.retrofitwithimdb.BuildConfig;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class CustomOkHttpClient {

    public static OkHttpClient addInterceptor(Context context) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(context))
            .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(new ChuckerInterceptor(context));
        return client.build();
    }


    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(
                        message -> Log.d("API_LOG", message));
        httpLoggingInterceptor.level(BuildConfig.DEBUG ? BODY : NONE);
        return httpLoggingInterceptor;
    }







}
