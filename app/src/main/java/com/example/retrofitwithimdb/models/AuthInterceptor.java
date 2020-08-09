package com.example.retrofitwithimdb.models;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    Context context;

    public AuthInterceptor() {
    }

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", "auth-value"); // <-- this is the important line

        requestBuilder.header("Accept-Language", LocHelper.getPersistedData(context, "en")); // <-- this is the important line



        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
