package com.fionera.rxgank;

/**
 * AppComponentHolder
 * Created by fionera on 17-3-6 in MVPPractice.
 */

public class AppComponentHolder {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        AppComponentHolder.appComponent = appComponent;
    }
}
