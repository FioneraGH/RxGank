package com.fionera.rxgank.test;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * ApiService
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public interface TestApiService {
    @GET("dream/query")
    Observable<BaseEntity<List<TestEntity>>> test(@QueryMap Map<String,String> options);
}
