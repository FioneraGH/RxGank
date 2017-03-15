package com.fionera.rxgank.dagger;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fionera.base.BaseApplication;
import com.fionera.rxgank.BuildConfig;
import com.fionera.rxgank.http.ApiService;
import com.fionera.rxgank.http.HttpConstants;
import com.fionera.rxgank.http.LogInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AppModule
 * Created by fionera on 17-3-6 in MVPPractice.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10,
                TimeUnit.SECONDS).cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookiesStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookiesStore.put(HttpUrl.parse(url.host()), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookiesStore.get(HttpUrl.parse(url.host()));
                return cookies == null ? new ArrayList<Cookie>() : cookies;
            }
        }).cache(cache);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LogInterceptor()).addNetworkInterceptor(
                    new StethoInterceptor());
        }

        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder().client(okHttpClient).baseUrl(
                HttpConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
