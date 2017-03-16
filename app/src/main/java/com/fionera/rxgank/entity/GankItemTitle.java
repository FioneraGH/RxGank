package com.fionera.rxgank.entity;

/**
 * GankItemTitle
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankItemTitle {

    private String type;

    public GankItemTitle(GankItem item) {
        setType(item.getType());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
