package com.fionera.rxgank.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * GankDayResults
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankDayResults {
    @SerializedName("福利")
    private List<GankItemGirl> image;
    private List<GankItem> Android;
    private List<GankItem> iOS;
    private List<GankItem> App;
    @SerializedName("瞎推荐")
    private List<GankItem> recommend;
    @SerializedName("休息视频")
    private List<GankItem> video;

    public List<GankItemGirl> getImage() {
        return image;
    }

    public void setImage(List<GankItemGirl> image) {
        this.image = image;
    }

    public List<GankItem> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GankItem> android) {
        Android = android;
    }

    public List<GankItem> getiOS() {
        return iOS;
    }

    public void setiOS(List<GankItem> iOS) {
        this.iOS = iOS;
    }

    public List<GankItem> getApp() {
        return App;
    }

    public void setApp(List<GankItem> app) {
        App = app;
    }

    public List<GankItem> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<GankItem> recommend) {
        this.recommend = recommend;
    }

    public List<GankItem> getVideo() {
        return video;
    }

    public void setVideo(List<GankItem> video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "GankDayResults{" + "福利=" + image + ", Android=" + Android + ", iOS=" + iOS + ", "
                + "App=" + App + ", 瞎推荐=" + recommend + ", 休息视频=" + video + '}';
    }
}
