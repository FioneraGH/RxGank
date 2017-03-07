package com.fionera.rxgank.contract;

import com.fionera.base.mvp.BasePresenter;
import com.fionera.base.mvp.BaseView;
import com.fionera.rxgank.model.RequestParams;

import java.util.List;

import io.reactivex.Observable;

/**
 * GankContract
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankContract {

    public interface View
            extends BaseView<Presenter> {
        void onLoading();
        void onSuccess(List<Object> list,boolean isLoadMore);
        void onError();
        void onEmpty();
    }

    public interface Presenter
            extends BasePresenter {
        void onRefresh();
        void onLoadMore();
    }

    public interface Model {
        Observable<List<Object>> fetchData(boolean isLoadMore, RequestParams requestParams);
    }
}