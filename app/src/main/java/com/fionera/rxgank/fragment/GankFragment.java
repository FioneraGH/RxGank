package com.fionera.rxgank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fionera.base.fragment.BaseFragment;
import com.fionera.rxgank.presenter.GankPresenterImpl;
import com.fionera.rxgank.view.GankView;

/**
 * GankFragment
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankFragment
        extends BaseFragment {
    public static GankFragment getInstance() {
        GankFragment gankFragment = new GankFragment();
        Bundle bundle = new Bundle();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    private GankPresenterImpl presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GankView view = new GankView(mContext);
        presenter = new GankPresenterImpl(view);
        presenter.init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unInit();
        }
    }
}
