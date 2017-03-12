package com.fionera.rxgank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fionera.base.fragment.BaseFragment;
import com.fionera.base.widget.AutoRecyclerView;
import com.fionera.rxgank.R;
import com.fionera.rxgank.adapter.GankDayAdapter;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.dagger.AppComponentHolder;
import com.fionera.rxgank.dagger.component.DaggerGankComponent;
import com.fionera.rxgank.dagger.component.GankComponent;
import com.fionera.rxgank.dagger.module.GankModule;
import com.fionera.rxgank.presenter.GankPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * GankFragment
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankFragment
        extends BaseFragment
        implements GankContract.View {
    public static GankFragment getInstance() {
        GankFragment gankFragment = new GankFragment();
        Bundle bundle = new Bundle();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Inject
    public GankPresenterImpl presenter;

    private SwipeRefreshLayout srGankDay;
    private AutoRecyclerView rvGankDay;

    private List<Object> gankDayList = new ArrayList<>();

    private GankComponent gankComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_gank, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gankComponent = DaggerGankComponent.builder().appComponent(
                AppComponentHolder.getAppComponent()).gankModule(new GankModule(this)).build();
        gankComponent.inject(this);
        srGankDay = (SwipeRefreshLayout) view.findViewById(R.id.sr_gank_day);
        rvGankDay = (AutoRecyclerView) view.findViewById(R.id.rv_gank_day);
        presenter.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unInit();
        }
        if (gankComponent != null) {
            gankComponent = null;
        }
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.presenter = (GankPresenterImpl) presenter;
    }

    @Override
    public void onAttachToPresenter() {
        srGankDay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (presenter != null) {
                    presenter.onRefresh();
                }
            }
        });

        rvGankDay.setLayoutManager(new LinearLayoutManager(mContext));
        GankDayAdapter gankDayAdapter = new GankDayAdapter(mContext, gankDayList);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(gankDayAdapter);
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(alphaInAnimationAdapter);
        rvGankDay.setAdapter(slideInBottomAnimationAdapter);
        rvGankDay.setLoadDataListener(new AutoRecyclerView.LoadDataListener() {
            @Override
            public void onLoadMore() {
                if (presenter != null) {
                    presenter.onLoadMore();
                }
            }
        });

        presenter.onRefresh();
    }

    @Override
    public void onDetachToPresenter() {

    }

    @Override
    public void onLoading() {
        srGankDay.setRefreshing(true);
    }

    @Override
    public void onSuccess(List<Object> list, boolean isLoadMore) {
        srGankDay.setRefreshing(false);
        if (!isLoadMore) {
            gankDayList.clear();
        }
        if (list == null) {
            return;
        }
        gankDayList.addAll(list);
        rvGankDay.getAdapter().notifyDataSetChanged();
        rvGankDay.loadMoreComplete(false);
    }

    @Override
    public void onError() {
        srGankDay.setRefreshing(false);
        rvGankDay.loadMoreComplete(false);
    }

    @Override
    public void onEmpty() {
        srGankDay.setRefreshing(false);
        rvGankDay.loadMoreComplete(false);
    }
}
