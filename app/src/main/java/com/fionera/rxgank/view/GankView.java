package com.fionera.rxgank.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.fionera.base.widget.AutoRecyclerView;
import com.fionera.rxgank.R;
import com.fionera.rxgank.adapter.GankDayAdapter;
import com.fionera.rxgank.contract.GankContract;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * GankView
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankView
        extends FrameLayout
        implements GankContract.View {
    private Context context;
    private GankContract.Presenter presenter;
    private List<Object> gankDayList = new ArrayList<>();

    private SwipeRefreshLayout srGankDay;
    private AutoRecyclerView rvGankDay;

    public GankView(Context context) {
        super(context);
        this.context = context;
    }

    public GankView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onAttach() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_gank, this);
        initView(view);

        srGankDay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (presenter != null) {
                    presenter.onRefresh();
                }
            }
        });

        rvGankDay.setLayoutManager(new LinearLayoutManager(context));
        GankDayAdapter gankDayAdapter = new GankDayAdapter(context, gankDayList);
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
    }

    @Override
    public void onDetach() {

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

    private void initView(View view) {
        srGankDay = (SwipeRefreshLayout) findViewById(R.id.sr_gank_day);
        rvGankDay = (AutoRecyclerView) view.findViewById(R.id.rv_gank_day);
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return ((RxAppCompatActivity) context).bindToLifecycle();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntil(@NonNull ActivityEvent event) {
        return null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntil(@NonNull FragmentEvent event) {
        return null;
    }
}
