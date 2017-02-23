package com.fionera.rxgank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fionera.base.util.ImageUtil;
import com.fionera.base.util.ShowToast;
import com.fionera.rxgank.R;
import com.fionera.rxgank.entity.GankItem;
import com.fionera.rxgank.entity.GankItemGirl;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.ui.TestActivity;

import java.util.List;

/**
 * GankDayAdapter
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class GankDayAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NORMAL = 1000;
    private static final int TYPE_TITLE = 1001;
    private static final int TYPE_GIRL = 1002;

    private Context context;
    private List<Object> list;
    private LayoutInflater inflater;

    public GankDayAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (TYPE_NORMAL == viewType) ? new GankDayHolder(
                inflater.inflate(R.layout.rv_gank_day_item, parent,
                        false)) : (TYPE_TITLE == viewType) ? new GankDayTitleHolder(
                inflater.inflate(R.layout.rv_gank_day_title_item, parent,
                        false)) : new GankDayGirlHolder(
                inflater.inflate(R.layout.rv_gank_girl_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_GIRL:
                GankDayGirlHolder gankDayGirlHolder = (GankDayGirlHolder) viewHolder;
                GankItemGirl gankItemGirl = (GankItemGirl) list.get(position);
                ImageUtil.loadImage(gankItemGirl.url, gankDayGirlHolder.iv_girl);
                gankDayGirlHolder.tv_time.setText(gankItemGirl.publishedAt);
                gankDayGirlHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowToast.show("GankGirl");
                    }
                });
                break;
            case TYPE_TITLE:
                GankDayTitleHolder gankTitleHolder = (GankDayTitleHolder) viewHolder;
                GankItemTitle gankItemTitle = (GankItemTitle) list.get(position);
                gankTitleHolder.tv_title.setText(gankItemTitle.type + "");
                gankTitleHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowToast.show("GankTitle");
                    }
                });
                break;
            case TYPE_NORMAL:
                GankDayHolder gankDayHolder = (GankDayHolder) viewHolder;
                GankItem gankItem = (GankItem) list.get(position);
                if (TextUtils.isEmpty(gankItem.who)) {
                    gankDayHolder.tv_desc.setText(gankItem.desc);
                } else {
                    int start = gankItem.desc.length();
                    int end = start + gankItem.who.length() + 3;
                    int color = ContextCompat.getColor(context, R.color.text_default);
                    SpannableStringBuilder builder = new SpannableStringBuilder(
                            gankItem.desc + " - " + gankItem.who);
                    builder.setSpan(new ForegroundColorSpan(color), start, end,
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    builder.setSpan(new RelativeSizeSpan(0.85f), start, end,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    gankDayHolder.tv_desc.setText(builder);
                    gankDayHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context, TestActivity.class));
                        }
                    });
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (list.get(position) instanceof GankItemTitle) ? TYPE_TITLE : (list.get(
                position) instanceof GankItemGirl) ? TYPE_GIRL : TYPE_NORMAL;
    }

    private class GankDayHolder
            extends RecyclerView.ViewHolder {
        TextView tv_desc;

        GankDayHolder(View itemView) {
            super(itemView);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    private class GankDayTitleHolder
            extends RecyclerView.ViewHolder {
        TextView tv_title;

        GankDayTitleHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    private class GankDayGirlHolder
            extends RecyclerView.ViewHolder {
        ImageView iv_girl;
        TextView tv_time;

        GankDayGirlHolder(View itemView) {
            super(itemView);
            iv_girl = (ImageView) itemView.findViewById(R.id.iv_girl);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
