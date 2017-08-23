package com.fionera.rxgank.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.fionera.base.activity.BaseActivity;
import com.fionera.base.util.L;
import com.fionera.base.util.ShowToast;
import com.fionera.base.widget.CommonImageView;
import com.fionera.rxgank.R;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.event.NavigationHeaderUpdateEvent;
import com.fionera.rxgank.fragment.GankFragment;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

import java.util.concurrent.TimeUnit;

import cn.nekocode.resinspector.ResourceInspector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Timed;
import io.realm.Realm;
import timber.log.Timber;

/**
 * MainActivity
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class MainActivity
        extends BaseActivity {

    private static final int TIME_TO_EXIT = 2000;

    private DrawerLayout drawerLayout;
    private CommonImageView commonImageView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ResourceInspector.wrap(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxBus.get().register(this);

        drawerLayout = findViewById(R.id.dl_main);
        final NavigationView navigationView = findViewById(R.id.nv_main);
        commonImageView = (CommonImageView) navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawerLayout.closeDrawers();
                        switch (item.getItemId()) {
                            case R.id.menu_main_home:
                                ShowToast.show("主页");
                                return true;
                            case R.id.menu_main_album:
                                ShowToast.show("图集");
                                return true;
                            case R.id.menu_main_about:
                                ShowToast.show("关于");
                                return true;
                        }
                        return false;
                    }
                });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                GankFragment.getInstance()).commitAllowingStateLoss();

        lifecycle.throttleFirst(TIME_TO_EXIT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
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
                moveTaskToBack(true);
                L.d("MainActivity Moved To Back");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Realm instance = Realm.getDefaultInstance();
        final GankDayResults gankDayResults = instance.where(GankDayResults.class).equalTo(
                "iOS.who", "Lhw").findFirst();
        if (gankDayResults != null) {
            Timber.d(gankDayResults.toString());
            /*
            copy element from realm to be serialized
             */
            GankDayResults newGankDayResults = instance.copyFromRealm(gankDayResults);
            Timber.d("%s\n%s", gankDayResults.toString(), new Gson().toJson(newGankDayResults));

            instance.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    gankDayResults.cascadeDelete();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
        } else {
            if (lifecycle != null) {
                lifecycle.onNext(0);
            }
        }
    }

    @Subscribe
    public void updateHeaderImage(NavigationHeaderUpdateEvent navigationHeaderUpdateEvent) {
        if (commonImageView != null && navigationHeaderUpdateEvent != null) {
            commonImageView.setImageURI(navigationHeaderUpdateEvent.url);
        }
    }

    @Subscribe()
    public void consumeName(String name) {
        Timber.d("One Fragment Attached, Name is %s", name);
    }
}
