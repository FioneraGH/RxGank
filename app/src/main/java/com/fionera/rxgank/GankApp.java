package com.fionera.rxgank;

import com.fionera.base.BaseApplication;
import com.fionera.rxgank.dagger.AppComponent;
import com.fionera.rxgank.dagger.AppComponentHolder;
import com.fionera.rxgank.dagger.AppModule;
import com.fionera.rxgank.dagger.DaggerAppComponent;

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
