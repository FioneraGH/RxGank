package com.fionera.rxgank.model;

import java.util.Calendar;
import java.util.Date;

public class RequestParams {

    private static final int MS_OF_DAY = 24 * 60 * 60 * 1000;

    int year;
    int month;
    int day;

    private Date currentDate;
    private int successTimes;
    private int errorTimes;
    private int emptyTimes;

    public RequestParams() {
        this.successTimes = 0;
        this.currentDate = new Date();
        processDate(currentDate);
    }

    public void onSuccess() {
        this.successTimes++;
        this.currentDate = new Date(currentDate.getTime() - MS_OF_DAY);
        processDate(currentDate);
    }

    public void onError() {
        this.errorTimes++;
        processDate(currentDate);
    }

    public void onEmpty() {
        this.emptyTimes++;
        this.currentDate = new Date(currentDate.getTime() - MS_OF_DAY);
        processDate(currentDate);
    }

    public void onComplete() {
        this.successTimes = 0;
        this.errorTimes = 0;
        this.emptyTimes = 0;
    }

    public boolean isComplete() {
        return successTimes >= 1 || errorTimes >= 10 || emptyTimes >= 30;
    }

    private void processDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", successTimes=" + successTimes +
                ", errorTimes=" + errorTimes +
                ", emptyTimes=" + emptyTimes +
                '}';
    }
}
