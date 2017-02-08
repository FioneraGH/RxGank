package com.fionera.rxgank.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fionera.base.util.ShowToast;
import com.fionera.base.widget.AutoRecyclerView;
import com.fionera.rxgank.R;
import com.fionera.rxgank.adapter.GankDayAdapter;
import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.model.GankItemGirl;

import java.util.ArrayList;
import java.util.List;

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
    private int lastPos = -1;
    private GankItemGirl curGirl;

    private SwipeRefreshLayout srGankDay;
    private AutoRecyclerView rvGankDay;
    private RelativeLayout rlGirl;

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
        rvGankDay.setAdapter(new GankDayAdapter(context, gankDayList));
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
        if (rvGankDay != null) {
            rvGankDay.removeAllViews();
            rvGankDay = null;
        }
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
        rlGirl = (RelativeLayout) view.findViewById(R.id.rl_girl);
    }
}
