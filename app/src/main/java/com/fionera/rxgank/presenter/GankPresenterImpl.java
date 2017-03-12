package com.fionera.rxgank.presenter;

import com.fionera.base.util.Utils;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.http.ApiService;
import com.fionera.rxgank.model.GankModelImpl;
import com.fionera.rxgank.model.RequestParams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by fionera on 2017/02/08
 */

public class GankPresenterImpl
        implements GankContract.Presenter {

    private GankContract.View view;
    private GankContract.Model model;

    private RequestParams requestParams;

    @Inject
    GankPresenterImpl(GankContract.View view, ApiService apiService) {
        this.view = view;
        this.model = new GankModelImpl(apiService);
    }

    @Override
    public void init() {
        if (view != null) {
            view.setPresenter(this);
            view.onAttachToPresenter();
        }
    }

    @Override
    public void unInit() {
        if (view != null) {
            view.onDetachToPresenter();
        }
    }

    @Override
    public void onRefresh() {
        view.onLoading();
        commonSubscriber(false);
    }

    @Override
    public void onLoadMore() {
        commonSubscriber(true);
    }

    private void commonSubscriber(final boolean isLoadMore) {
        if (!isLoadMore) {
            requestParams = new RequestParams();
        }

        model.fetchData(isLoadMore, requestParams).compose(view.<List<Object>>bindLifecycle())
                .subscribe(new Consumer<List<Object>>() {
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

    private void processRequestResult(List<Object> list, boolean isLoadMore) {
        if (!requestParams.isComplete()) {
            view.onSuccess(list, isLoadMore);
            /*
            here to fetch 3 days
             */
            commonSubscriber(true);
        } else {
            requestParams.onComplete();
            if (Utils.notEmpty(list)) {
                view.onSuccess(list, isLoadMore);
            } else {
                view.onError();
            }
        }
    }
}