package com.fionera.rxgank.test;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * TestApiManager
 * Created by fionera on 17-2-23 in MVPPractice.
 */

public class TestApiManager {
    public static Observable<List<TestEntity>> test(Map<String, String> options) {
        Observable<BaseEntity<List<TestEntity>>> observable = TestApi.getInstance().getApiService()
                .test(options);
        return wrapObservable(observable);
    }

    private static <T> Observable<T> wrapObservable(Observable<BaseEntity<T>> observable) {
        return observable.map(new CommonFilter<T>()).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread());
    }

    private static class CommonFilter<T>
            implements Func1<BaseEntity<T>, T> {
        @Override
        public T call(BaseEntity<T> t) {
            if (t.getError_code() != 0) {
                throw new HttpTimeException(t.getReason());
            }
            return t.getResult();
        }
    }

    private static class HttpTimeException
            extends RuntimeException {

        HttpTimeException(String message) {
            super(message);
        }
    }
}
