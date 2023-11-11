package com.moutamid.instuitionbuilder.config;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "https://api.mailgun.net/v3/sandboxc20a85841676403491b8cb67a6412646.mailgun.org/";

    private static final String API_USERNAME = "api";

    //you need to change the value to your API key
    private static final String API_PASSWORD = "3f059901bca10c23ea6f5cecc528a3b4-8c9e82ec-488d9a3a";

    private static final String AUTH = "Basic " + Base64.encodeToString((API_USERNAME+":"+API_PASSWORD).getBytes(), Base64.NO_WRAP);

    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                //Adding basic auth
                                Request.Builder requestBuilder = original.newBuilder()
                                        .header("Authorization", AUTH)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Retrofit getClient() {
        return retrofit;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}