package com.fionera.rxgank.test;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
            implements Function<BaseEntity<T>, T> {
        @Override
        public T apply(BaseEntity<T> t) throws Exception {
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
