package com.fionera.rxgank.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * GankItem
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankItem {
    @SerializedName("_id")
    private String id;
    private String type;
    private String desc;
    private String who;
    private String url;
    private List<String> images;
    private String createdAt;
    private String publishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "GankItem{" + "_id='" + id + '\'' + ", type='" + type + '\'' + ", desc='" + desc +
                '\'' + ", who='" + who + '\'' + ", url='" + url + '\'' + ", images=" + images +
                ", createdAt='" + createdAt + '\'' + ", publishedAt='" + publishedAt + '\'' + '}';
    }
}
