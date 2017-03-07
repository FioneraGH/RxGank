package com.fionera.rxgank.model;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.http.ApiManager;
import com.fionera.rxgank.http.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fionera on 2017/02/08
 */

public class GankModelImpl
        implements GankContract.Model {

    private ApiService apiService;

    public GankModelImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<List<Object>> fetchData(final boolean isLoadMore,
                                              RequestParams requestParams) {
        return ApiManager.getGankDay(apiService, requestParams.year, requestParams.month,
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
        if (Utils.notEmpty(results.福利)) list.addAll(results.福利);
        if (Utils.notEmpty(results.Android)) {
            list.add(new GankItemTitle(results.Android.get(0)));
            list.addAll(results.Android);
        }
        if (Utils.notEmpty(results.iOS)) {
            list.add(new GankItemTitle(results.iOS.get(0)));
            list.addAll(results.iOS);
        }
        if (Utils.notEmpty(results.App)) {
            list.add(new GankItemTitle(results.App.get(0)));
            list.addAll(results.App);
        }
        if (Utils.notEmpty(results.瞎推荐)) {
            list.add(new GankItemTitle(results.瞎推荐.get(0)));
            list.addAll(results.瞎推荐);
        }
        if (Utils.notEmpty(results.休息视频)) {
            list.add(new GankItemTitle(results.休息视频.get(0)));
            list.addAll(results.休息视频);
        }
        return list;
    }
}