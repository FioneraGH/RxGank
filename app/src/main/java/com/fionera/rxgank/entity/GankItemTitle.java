package com.fionera.rxgank.entity;

/**
 * GankItemTitle
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankItemTitle
        extends GankItem {

    public GankItemTitle(GankItem item) {
        setType(item.getType());
        setDesc(item.getDesc());
        setWho(item.getWho());
        setUrl(item.getUrl());
        setImages(item.getImages());
        setCreatedAt(item.getCreatedAt());
        setPublishedAt(item.getPublishedAt());
    }
}
