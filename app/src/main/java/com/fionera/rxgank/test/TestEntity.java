package com.fionera.rxgank.test;

import java.util.List;

/**
 * TestEntity
 * Created by fionera on 17-2-23 in MVPPractice.
 */

public class TestEntity {
    private String id;
    private String title;
    private String des;
    private List<String> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
