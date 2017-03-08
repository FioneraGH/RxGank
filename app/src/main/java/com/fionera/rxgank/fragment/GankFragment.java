package com.fionera.rxgank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fionera.base.fragment.BaseFragment;
import com.fionera.rxgank.dagger.AppComponentHolder;
import com.fionera.rxgank.dagger.component.DaggerGankComponent;
import com.fionera.rxgank.dagger.component.GankComponent;
import com.fionera.rxgank.dagger.module.GankModule;
import com.fionera.rxgank.presenter.GankPresenterImpl;
import com.fionera.rxgank.view.GankView;

import javax.inject.Inject;

import dagger.Lazy;

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

    @Inject
    public Lazy<GankPresenterImpl> presenter;

    private GankComponent gankComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GankView view = new GankView(mContext);
        gankComponent = DaggerGankComponent.builder().appComponent(
                AppComponentHolder.getAppComponent()).gankModule(new GankModule(view)).build();
        gankComponent.inject(this);
        presenter.get().init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.get().onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.get().unInit();
        }
        if (gankComponent != null) {
            gankComponent = null;
        }
    }
}
