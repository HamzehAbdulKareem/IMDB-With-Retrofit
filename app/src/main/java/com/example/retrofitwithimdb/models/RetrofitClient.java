package com.example.retrofitwithimdb.models;

import android.content.Context;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.CustomOkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit;

    public Retrofit buildRetorfit(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
      return retrofit;
    }

    public Retrofit getRetrofitWithOkhttp(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))
                .addConverterFactory(GsonConverterFactory.create())
                .client(CustomOkHttpClient.addInterceptor(context))
                .build();

        return retrofit;
    }

}
