package com.fionera.rxgank.presenter;

import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.model.GankModelImpl;
import com.fionera.rxgank.view.GankView;

import java.util.List;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * Created by fionera on 2017/02/08
 */

public class GankPresenterImpl
        implements GankContract.Presenter,GankContract.Model.ResultListener {

    private GankView gankView;
    private GankContract.Model model;

    private Subject<Void, Void> lifecycle = new SerializedSubject<>(
            PublishSubject.<Void>create());

    public GankPresenterImpl(GankView view) {
        gankView = view;
    }

    @Override
    public void init() {
        if (gankView != null) {
            gankView.setPresenter(this);
            gankView.onAttach();
        }
        model = new GankModelImpl(lifecycle);
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
        model.fetchData(false,this);
    }

    @Override
    public void onLoadMore() {
        model.fetchData(true,this);
    }


    @Override
    public void onSuccess(List<Object> list, boolean isLoadMore) {
        gankView.onSuccess(list, isLoadMore);
    }

    @Override
    public void onError() {
        gankView.onError();
    }
}