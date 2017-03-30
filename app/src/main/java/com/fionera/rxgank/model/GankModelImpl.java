package com.fionera.rxgank.model;

import android.text.TextUtils;

import com.fionera.base.util.L;
import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.http.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by fionera on 2017/02/08
 */

public class GankModelImpl
        implements GankContract.Model {

    @Override
    public Observable<List<Object>> fetchData(final boolean isLoadMore,
                                              final RequestParams requestParams) {
        final String gankDate = String.format(Locale.CHINA, "%d-%d-%d", requestParams.year,
                requestParams.month, requestParams.day);
        return fetchFromNetwork(requestParams).filter(
                new Predicate<GankDayResults>() {
                    @Override
                    public boolean test(GankDayResults gankDayResults) throws Exception {
                        return gankDayResults != null;
                    }
                }).filter(new Predicate<GankDayResults>() {
            @Override
            public boolean test(GankDayResults gankDayResults) throws Exception {
                return gankDayResults != null;
            }
        }).map(new Function<GankDayResults, List<Object>>() {
            @Override
            public List<Object> apply(GankDayResults gankDayResults) throws Exception {
                saveToRealm(gankDate, gankDayResults);
                return flatGankDay2List(gankDayResults);
            }
        });
    }

    private Observable<GankDayResults> fetchFromNetwork(RequestParams requestParams) {
        return ApiManager.getGankDay(requestParams.year, requestParams.month, requestParams.day);
    }

    private Observable<GankDayResults> fetchFromRealm(final String gankDate) {
        return Observable.create(new ObservableOnSubscribe<GankDayResults>() {
            @Override
            public void subscribe(ObservableEmitter<GankDayResults> e) throws Exception {
                final Realm instance = Realm.getDefaultInstance();
                instance.beginTransaction();
                GankDayResults gankDayResults = instance.where(GankDayResults.class).equalTo(
                        "gankDate", gankDate).findFirst();
                Timber.d("Model Read Data:%s", gankDayResults);
                instance.commitTransaction();
                e.onNext(gankDayResults);
                e.onComplete();
                instance.close();
            }
        });
    }

    private void saveToRealm(final String gankDate, final GankDayResults gankDayResults) {
        final Realm instance = Realm.getDefaultInstance();
        instance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                /*
                empty from network
                 */
                if (TextUtils.isEmpty(gankDayResults.getGankDate())) {
                    long startTime = System.currentTimeMillis();
                    Timber.d("Model Save Data Start:%d", startTime);
                    gankDayResults.setGankDate(gankDate);
                    instance.copyToRealmOrUpdate(gankDayResults);
                    long endTime = System.currentTimeMillis();
                    Timber.d("Model Save Data End:%d", endTime);
                    Timber.d("Model Save Data Period:%d", endTime - startTime);
                }
            }
        });
        instance.close();
    }

    private List<Object> flatGankDay2List(GankDayResults results) {
        List<Object> list = new ArrayList<>();
        if (Utils.notEmpty(results.getImage())) {
            list.addAll(results.getImage());
        }
        if (Utils.notEmpty(results.getAndroid())) {
            list.add(new GankItemTitle(results.getAndroid().get(0)));
            list.addAll(results.getAndroid());
        }
        if (Utils.notEmpty(results.getiOS())) {
            list.add(new GankItemTitle(results.getiOS().get(0)));
            list.addAll(results.getiOS());
        }
        if (Utils.notEmpty(results.getApp())) {
            list.add(new GankItemTitle(results.getApp().get(0)));
            list.addAll(results.getApp());
        }
        if (Utils.notEmpty(results.getRecommend())) {
            list.add(new GankItemTitle(results.getRecommend().get(0)));
            list.addAll(results.getRecommend());
        }
        if (Utils.notEmpty(results.getVideo())) {
            list.add(new GankItemTitle(results.getVideo().get(0)));
            list.addAll(results.getVideo());
        }
        return list;
    }
}