package com.fionera.rxgank;

import android.app.Application;

import com.fionera.rxgank.http.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * AppComponent
 * Created by fionera on 17-3-6 in MVPPractice.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    ApiService getApiService();
    Application getApplication();
}