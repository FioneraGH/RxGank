package com.fionera.rxgank.entity;

/**
 * BaseEntity
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class BaseEntity<T> {
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
