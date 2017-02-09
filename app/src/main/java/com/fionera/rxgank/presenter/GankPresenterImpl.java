package com.fionera.rxgank.presenter;

import android.text.TextUtils;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.GankApp;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.http.Api;
import com.fionera.rxgank.model.GankDay;
import com.fionera.rxgank.model.GankDayResults;
import com.fionera.rxgank.model.GankItemGirl;
import com.fionera.rxgank.model.GankItemTitle;
import com.fionera.rxgank.model.RequestParams;
import com.fionera.rxgank.view.GankView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * Created by fionera on 2017/02/08
 */

public class GankPresenterImpl
        implements GankContract.Presenter {

    private GankView gankView;
    private RequestParams requestParams;
    private Subject<Void, Void> lifecycle = new SerializedSubject<>(
            PublishSubject.<Void>create());

    public GankPresenterImpl(GankView view) {
        gankView = view;
    }

    @Override
    public void init() {
        requestParams = new RequestParams();
        if (gankView != null) {
            gankView.setPresenter(this);
            gankView.onAttach();
        }
    }

    @Override
    public void unInit() {
        lifecycle.onNext(null);
        lifecycle.onCompleted();
        if (gankView != null) {
            gankView.onDetach();
        }
    }

    @Override
    public void onRefresh() {
        gankView.onLoading();
        requestParams = new RequestParams();
        getGankDayList(false);
    }

    @Override
    public void onLoadMore() {
        getGankDayList(true);
    }

    private void getGankDayList(final boolean isLoadMore) {
        Api.getInstance().getApiService().getGankDay(requestParams.year, requestParams.month,
                requestParams.day).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).filter(
                new Func1<GankDay, Boolean>() {
                    @Override
                    public Boolean call(GankDay gankDay) {
                        return gankDay!=null;
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
            processGirls(list);
            gankView.onSuccess(list, isLoadMore);
            /*
            here to fetch 3 days
             */
            getGankDayList(true);
        } else {
            requestParams.onComplete();
            if (Utils.notEmpty(list)) {
                processGirls(list);
                gankView.onSuccess(list, isLoadMore);
            } else {
                gankView.onError();
            }
        }
    }

    private void processGirls(List<Object> list) {
        if (Utils.isEmpty(list) || GankApp.girls == null) {
            GankApp.girls = new ArrayList<>();
            return;
        }
        GankApp.girls.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof GankItemGirl) {
                GankItemGirl girl = (GankItemGirl) list.get(i);
                GankApp.girls.add(TextUtils.isEmpty(girl.url) ? "" : girl.url);
            }
        }
    }
}