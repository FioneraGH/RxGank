package com.fionera.rxgank.model;

import java.util.List;

/**
 * GankItem
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankItem {
    public String _id;
    public String type;
    public String desc;
    public String who;
    public String url;
    public List<String> images;
    public String createdAt;
    public String publishedAt;

    @Override
    public String toString() {
        return "GankItem{" + "_id='" + _id + '\'' + ", type='" + type + '\'' + ", desc='" + desc
                + '\'' + ", who='" + who + '\'' + ", url='" + url + '\'' + ", images=" + images +
                ", createdAt='" + createdAt + '\'' + ", publishedAt='" + publishedAt + '\'' + '}';
    }
}
