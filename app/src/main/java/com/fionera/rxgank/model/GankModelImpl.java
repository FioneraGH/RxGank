package com.fionera.rxgank.model;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.http.ApiManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fionera on 2017/02/08
 */

public class GankModelImpl
        implements GankContract.Model {

    @Override
    public Observable<List<Object>> fetchData(final boolean isLoadMore,
                                              RequestParams requestParams) {
        return ApiManager.getGankDay(requestParams.year, requestParams.month,
                requestParams.day).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).filter(new Predicate<GankDayResults>() {
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
                return flatGankDay2List(gankDayResults);
            }
        });
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