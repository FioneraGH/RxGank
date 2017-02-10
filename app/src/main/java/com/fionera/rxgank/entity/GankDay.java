package com.fionera.rxgank.entity;

import java.util.List;

/**
 * GankDay
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankDay
        extends BaseEntity {
    public List<String> categary;
    public GankDayResults results;

    @Override
    public String toString() {
        return "GankDay{" + "categary=" + categary + ", results=" + results + '}';
    }
}
