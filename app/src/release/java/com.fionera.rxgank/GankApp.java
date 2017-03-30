package com.fionera.rxgank;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fionera.base.BaseApplication;
import com.fionera.rxgank.dagger.AppComponent;
import com.fionera.rxgank.dagger.AppComponentHolder;
import com.fionera.rxgank.dagger.AppModule;
import com.fionera.rxgank.dagger.DaggerAppComponent;

import io.realm.Realm;

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

        Fresco.initialize(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().schemaVersion(1)
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        ActivityStackManager.getInstance().init(this);
    }
}
