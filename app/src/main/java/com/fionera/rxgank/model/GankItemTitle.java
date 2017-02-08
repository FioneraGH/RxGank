package com.fionera.rxgank.model;

/**
 * GankItemTitle
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankItemTitle
        extends GankItem {
    public GankItemTitle() {
    }

    public GankItemTitle(GankItem item) {
        type = item.type;
        desc = item.desc;
        who = item.who;
        url = item.url;
        images = item.images;
        createdAt = item.createdAt;
        publishedAt = item.publishedAt;
    }
}
