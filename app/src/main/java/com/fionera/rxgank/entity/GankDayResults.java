package com.fionera.rxgank.entity;

import java.util.List;

/**
 * GankDayResults
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankDayResults {
    public List<GankItemGirl> 福利;
    public List<GankItem> Android;
    public List<GankItem> iOS;
    public List<GankItem> App;
    public List<GankItem> 瞎推荐;
    public List<GankItem> 休息视频;

    @Override
    public String toString() {
        return "GankDayResults{" + "福利=" + 福利 + ", Android=" + Android + ", iOS=" + iOS + ", " +
                "App=" + App + ", 瞎推荐=" + 瞎推荐 + ", 休息视频=" + 休息视频 + '}';
    }
}
