package com.fionera.rxgank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

import com.fionera.base.activity.BaseActivity;
import com.fionera.base.util.ShowToast;
import com.fionera.rxgank.R;
import com.fionera.rxgank.fragment.GankFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Timed;

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

        lifecycle.throttleFirst(TIME_TO_EXIT, TimeUnit.MILLISECONDS,
                AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer aInteger) {
                ShowToast.show("再按一次退出");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        lifecycle.compose(this.<Integer>bindToLifecycle()).timeInterval(
                AndroidSchedulers.mainThread()).skip(1).filter(new Predicate<Timed<Integer>>() {
            @Override
            public boolean test(Timed<Integer> timed) throws Exception {
                return timed.time() < TIME_TO_EXIT;
            }
        }).subscribe(new Consumer<Timed<Integer>>() {
            @Override
            public void accept(Timed<Integer> timed) throws Exception {
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (lifecycle != null) {
            lifecycle.onNext(0);
        }
    }
}
