package com.fionera.base.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;

import java.util.Stack;

/**
 * ActivityStackManager
 * Created by fionera on 17-3-16 in RxGank.
 */

public class ActivityStackManager implements Application.ActivityLifecycleCallbacks{

    private static final ActivityStackManager instance = new ActivityStackManager();

    public static ActivityStackManager getInstance() {
        return instance;
    }

    public void init(Application application){
        application.registerActivityLifecycleCallbacks(this);
    }

    private Stack<Activity> stack = new Stack<>();
    private int runningPage = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        stack.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(runningPage == 0){
            L.d("ActivityStackManager:BackToFront");
        }
        runningPage ++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        runningPage --;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        stack.remove(activity);
        killProcess();
    }

    private void killProcess() {
        if (stack.empty()) {
            Process.killProcess(Process.myPid());
        }
    }

    public Activity getCurrentActivity() {
        if (!stack.empty()) {
            return stack.get(stack.size() - 1);
        } else {
            return null;
        }
    }

    public Activity getRootActivity() {
        if (!stack.empty()) {
            return stack.get(0);
        } else {
            return null;
        }
    }
}
