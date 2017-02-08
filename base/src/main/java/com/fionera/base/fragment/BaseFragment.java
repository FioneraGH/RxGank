package com.fionera.base.fragment;

import android.app.Activity;
import android.content.Context;

import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * BaseFragment
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class BaseFragment
        extends RxFragment {

    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }
}
