package com.example.retrofitwithimdb.models;

import android.content.Context;
import android.database.Observable;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.CustomOkHttpClient;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    Retrofit retrofit;
    JsonHolder jsonHolder;
    public Retrofit buildRetorfit(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
      return retrofit;
    }

    public Retrofit getRetrofitWithOkhttp(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(CustomOkHttpClient.addInterceptor(context))
                .build();

        return retrofit;
    }



}
