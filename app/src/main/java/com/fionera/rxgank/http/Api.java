package com.fionera.rxgank.http;

import com.fionera.base.BaseApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class Api {

    private static ApiService apiService;

    private static final int CONNECT_TIMEOUT = 20;

    private static class Holder {
        private static Api instance = new Api();
    }

    public static Api getInstance() {
        return Holder.instance;
    }

    private Api() {
        init();
    }

    private void init() {
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT,
                TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).cache(cache);

        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).baseUrl(
                HttpConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        if (apiService == null) {
            init();
        }
        return apiService;
    }
}
