package com.fionera.rxgank;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.fionera.base.BaseApplication;
import com.fionera.rxgank.dagger.AppComponent;
import com.fionera.rxgank.dagger.AppComponentHolder;
import com.fionera.rxgank.dagger.AppModule;
import com.fionera.rxgank.dagger.DaggerAppComponent;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

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

        Stetho.initialize(Stetho.newInitializerBuilder(this).enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());
    }
}
