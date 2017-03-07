package com.fionera.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fionera.base.mvp.BindHelper;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * BaseFragment
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class BaseFragment
        extends RxFragment
        implements BindHelper {

    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return super.bindToLifecycle();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntil(@NonNull ActivityEvent event) {
        return null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntil(@NonNull FragmentEvent event) {
        return super.bindUntilEvent(event);
    }
}
