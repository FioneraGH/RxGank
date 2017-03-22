package com.fionera.base.util.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * CustomTabsKeepAliveService
 * Created by fionera on 17-3-17 in RxGank.
 */

public class CustomTabsKeepAliveService
        extends Service {

    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}