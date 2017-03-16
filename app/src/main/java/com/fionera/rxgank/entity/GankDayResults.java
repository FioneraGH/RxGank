package com.fionera.rxgank.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * GankDayResults
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankDayResults
        extends RealmObject {
    @PrimaryKey
    @Required
    private String gankDate;

    @SerializedName("福利")
    private RealmList<GankItemGirl> image;
    private RealmList<GankItem> Android;
    private RealmList<GankItem> iOS;
    private RealmList<GankItem> App;
    @SerializedName("瞎推荐")
    private RealmList<GankItem> recommend;
    @SerializedName("休息视频")
    private RealmList<GankItem> video;

    public String getGankDate() {
        return gankDate;
    }

    public void setGankDate(String gankDate) {
        this.gankDate = gankDate;
    }

    public RealmList<GankItemGirl> getImage() {
        return image;
    }

    public void setImage(RealmList<GankItemGirl> image) {
        this.image = image;
    }

    public RealmList<GankItem> getAndroid() {
        return Android;
    }

    public void setAndroid(RealmList<GankItem> android) {
        Android = android;
    }

    public RealmList<GankItem> getiOS() {
        return iOS;
    }

    public void setiOS(RealmList<GankItem> iOS) {
        this.iOS = iOS;
    }

    public RealmList<GankItem> getApp() {
        return App;
    }

    public void setApp(RealmList<GankItem> app) {
        App = app;
    }

    public RealmList<GankItem> getRecommend() {
        return recommend;
    }

    public void setRecommend(RealmList<GankItem> recommend) {
        this.recommend = recommend;
    }

    public RealmList<GankItem> getVideo() {
        return video;
    }

    public void setVideo(RealmList<GankItem> video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "GankDayResults{" + "福利=" + image + ", Android=" + Android + ", iOS=" + iOS + ", "
                + "App=" + App + ", 瞎推荐=" + recommend + ", 休息视频=" + video + '}';
    }

    public void cascadeDelete() {
        try {
            image.deleteAllFromRealm();
            Android.deleteAllFromRealm();
            iOS.deleteAllFromRealm();
            App.deleteAllFromRealm();
            recommend.deleteAllFromRealm();
            video.deleteAllFromRealm();
            deleteFromRealm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
