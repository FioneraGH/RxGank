package com.fionera.rxgank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fionera.base.activity.BaseActivity;
import com.fionera.base.util.ShowToast;
import com.fionera.rxgank.R;
import com.fionera.rxgank.fragment.GankFragment;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.TimeInterval;

/**
 * MainActivity
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class MainActivity
        extends BaseActivity {

    private static final int TIME_TO_EXIT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                GankFragment.getInstance()).commitAllowingStateLoss();

        lifecycle.asObservable().throttleFirst(TIME_TO_EXIT, TimeUnit.MILLISECONDS,
                AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ShowToast.show("再按一次退出");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        lifecycle.asObservable().compose(bindToLifecycle()).timeInterval(
                AndroidSchedulers.mainThread()).skip(1).filter(

                new Func1<TimeInterval<Object>, Boolean>() {
                    @Override
                    public Boolean call(TimeInterval<Object> objectTimeInterval) {
                        return objectTimeInterval.getIntervalInMilliseconds() < TIME_TO_EXIT;
                    }
                }).subscribe(new Action1<TimeInterval<Object>>() {
            @Override
            public void call(TimeInterval<Object> objectTimeInterval) {
                finish();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (lifecycle != null) {
            lifecycle.onNext(null);
        }
    }
}
