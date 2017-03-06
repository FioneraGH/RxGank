package com.fionera.rxgank.http;

import com.fionera.rxgank.entity.BaseEntity;
import com.fionera.rxgank.entity.GankDayResults;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * TestApiManager
 * Created by fionera on 17-2-23 in MVPPractice.
 */

public class ApiManager {
    public static Observable<GankDayResults> getGankDay(int year, int mouth, int day) {
        return Api.getInstance().getApiService().getGankDay(year, mouth, day).map(
                new CommonFilter<GankDayResults>()).compose(
                ApiManager.<GankDayResults>httpTransformer());
    }

    private static class CommonFilter<T>
            implements Function<BaseEntity<T>, T> {
        @Override
        public T apply(BaseEntity<T> t) throws Exception {
            if (t.error) {
                throw new HttpTimeException("网络错误");
            }
            return t.results;
        }
    }

    private static class HttpTimeException
            extends RuntimeException {
        HttpTimeException(String message) {
            super(message);
        }
    }

    private static <T> ObservableTransformer<T, T> httpTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(
                        AndroidSchedulers.mainThread());
            }
        };
    }
}
