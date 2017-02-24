package com.fionera.rxgank.test;

import com.fionera.base.BaseApplication;
import com.fionera.rxgank.http.LogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class TestApi {

    private static TestApiService apiService;

    private static final int CONNECT_TIMEOUT = 20;

    private static class Holder {
        private static TestApi instance = new TestApi();
    }

    public static TestApi getInstance() {
        return Holder.instance;
    }

    private TestApi() {
        init();
    }

    private void init() {
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT,
                TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).cache(cache);

        Retrofit retrofit = new Retrofit.Builder().client(builder.build()).baseUrl(
                TestHttpConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        apiService = retrofit.create(TestApiService.class);
    }

    TestApiService getApiService() {
        if (apiService == null) {
            init();
        }
        return apiService;
    }
}
