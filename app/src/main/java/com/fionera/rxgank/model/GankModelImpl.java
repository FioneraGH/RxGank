package com.fionera.rxgank.model;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.entity.GankDay;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.http.Api;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by fionera on 2017/02/08
 */

public class GankModelImpl
        implements GankContract.Model {

    private RequestParams requestParams;
    private Subject<Void> lifecycle;
    private ResultListener resultListener;

    public GankModelImpl(Subject<Void> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void fetchData(final boolean isLoadMore, ResultListener resultListener) {
        this.resultListener = resultListener;
        if(!isLoadMore){
            requestParams = new RequestParams();
        }

        Api.getInstance().getApiService().getGankDay(requestParams.year, requestParams.month,
                requestParams.day).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).filter(new Predicate<GankDay>() {
            @Override
            public boolean test(GankDay gankDay) throws Exception {
                return gankDay != null;
            }
        }).filter(new Predicate<GankDay>() {
            @Override
            public boolean test(GankDay gankDay) throws Exception {
                return gankDay.results != null;
            }
        }).map(new Function<GankDay, List<Object>>() {
            @Override
            public List<Object> apply(GankDay gankDay) throws Exception {
                return flatGankDay2List(gankDay);
            }
        }).takeUntil(lifecycle).subscribe(new Consumer<List<Object>>() {
            @Override
            public void accept(List<Object> objects) throws Exception {
                if (Utils.notEmpty(objects)) {
                    requestParams.onSuccess();
                } else {
                    requestParams.onEmpty();
                }
                processRequestResult(objects, isLoadMore);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
                requestParams.onError();
                processRequestResult(null, isLoadMore);
            }
        });
    }

    private List<Object> flatGankDay2List(GankDay gankDay) {
        List<Object> list = new ArrayList<>();
        GankDayResults results = gankDay.results;
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

    private void processRequestResult(List<Object> list, boolean isLoadMore) {
        if (!requestParams.isComplete()) {
            resultListener.onSuccess(list, isLoadMore);
            /*
            here to fetch 3 days
             */
            fetchData(true, resultListener);
        } else {
            requestParams.onComplete();
            if (Utils.notEmpty(list)) {
                resultListener.onSuccess(list, isLoadMore);
            } else {
                resultListener.onError();
            }
        }
    }
}