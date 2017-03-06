package com.fionera.rxgank;

import android.app.Application;
import android.content.Context;

import com.fionera.base.BaseApplication;
import com.fionera.rxgank.http.ApiService;
import com.fionera.rxgank.http.HttpConstants;
import com.fionera.rxgank.http.LogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
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
    public Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10,
                TimeUnit.SECONDS).addInterceptor(new LogInterceptor()).cache(cache);

        return builder.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(
                HttpConstants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        return retrofit;
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
