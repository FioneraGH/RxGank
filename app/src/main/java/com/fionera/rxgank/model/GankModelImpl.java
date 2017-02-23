package com.fionera.rxgank.model;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.entity.GankDay;
import com.fionera.rxgank.entity.GankDayResults;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.http.Api;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * Created by fionera on 2017/02/08
 */

public class GankModelImpl
        implements GankContract.Model {

    private RequestParams requestParams;
    private Subject<Void, Void> lifecycle;
    private ResultListener resultListener;

    public GankModelImpl(Subject<Void, Void> lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public void fetchData(final boolean isLoadMore, ResultListener resultListener) {
        this.resultListener = resultListener;
        if(!isLoadMore){
            requestParams = new RequestParams();
        }

        Api.getInstance().getApiService().getGankDay(requestParams.year, requestParams.month,
                requestParams.day).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(
                new Func1<GankDay, Boolean>() {
                    @Override
                    public Boolean call(GankDay gankDay) {
                        return gankDay != null;
                    }
                }).filter(new Func1<GankDay, Boolean>() {
            @Override
            public Boolean call(GankDay gankDay) {
                return gankDay.results != null;
            }
        }).map(new Func1<GankDay, List<Object>>() {
            @Override
            public List<Object> call(GankDay gankDay) {
                return flatGankDay2List(gankDay);
            }
        }).takeUntil(lifecycle).subscribe(new Action1<List<Object>>() {
            @Override
            public void call(List<Object> objects) {
                if (Utils.notEmpty(objects)) {
                    requestParams.onSuccess();
                } else {
                    requestParams.onEmpty();
                }
                processRequestResult(objects, isLoadMore);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
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