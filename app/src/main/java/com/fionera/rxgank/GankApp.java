package com.fionera.rxgank;

import com.fionera.base.BaseApplication;

import java.util.List;

/**
 * GankApp
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankApp
        extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .build();
        AppComponentHolder.setAppComponent(appComponent);
    }
}
