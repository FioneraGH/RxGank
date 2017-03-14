package com.fionera.rxgank.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * UserPo
 * Created by fionera on 17-3-14 in MVPPractice.
 */

public class UserPo
        extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String headUrl;
    private long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
