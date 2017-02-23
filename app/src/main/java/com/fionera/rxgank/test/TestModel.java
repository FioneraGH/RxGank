package com.fionera.rxgank.test;


import com.fionera.rxgank.test.TestApiManager;
import com.fionera.rxgank.test.TestEntity;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * TestModel
 * Created by fionera on 17-2-23 in MVPPractice.
 */

public class TestModel {
    public Observable<List<TestEntity>> test(Map<String, String> options) {
        return TestApiManager.test(options);
    }
}
