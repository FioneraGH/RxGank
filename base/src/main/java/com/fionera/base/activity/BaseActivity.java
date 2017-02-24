package com.fionera.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fionera.base.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * BaseActivity
 * Created by fionera on 17-1-9 in AndroidDemo.
 */

public class BaseActivity
        extends RxAppCompatActivity {

    public Subject<Void> lifecycle = PublishSubject.create();

    protected Context mContext;
    protected boolean isDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isDestroy = false;
        setStatusBarTranslucent(false);
    }

    protected void setStatusBarTranslucent(boolean isLightStatusBar) {
        StatusBarUtil.setStatusBarTranslucent(this, isLightStatusBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        if(lifecycle != null){
            lifecycle.onComplete();
            lifecycle = null;
        }
    }
}
