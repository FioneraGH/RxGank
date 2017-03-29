package com.fionera.rxgank.adapter;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fionera.base.receiver.ActionBroadcastReceiver;
import com.fionera.base.util.customtabs.CustomTabActivityHelper;
import com.fionera.base.util.customtabs.WebViewFallback;
import com.fionera.rxgank.R;
import com.fionera.rxgank.entity.GankItem;
import com.fionera.rxgank.entity.GankItemGirl;
import com.fionera.rxgank.entity.GankItemTitle;
import com.fionera.rxgank.ui.ImageDetailActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
                final GankDayGirlHolder gankDayGirlHolder = (GankDayGirlHolder) viewHolder;
                final GankItemGirl gankItemGirl = (GankItemGirl) list.get(position);
                gankDayGirlHolder.iv_girl.setImageURI(gankItemGirl.getUrl());
                gankDayGirlHolder.tv_time.setText(gankItemGirl.getPublishedAt());
                RxView.clicks(gankDayGirlHolder.itemView).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(@NonNull Object o) throws Exception {
                                ((Activity) context).setExitSharedElementCallback(
                                        new SharedElementCallback() {
                                            @Override
                                            public void onSharedElementStart(
                                                    List<String> sharedElementNames,
                                                    List<View> sharedElements,
                                                    List<View> sharedElementSnapshots) {
                                                super.onSharedElementStart(sharedElementNames,
                                                        sharedElements, sharedElementSnapshots);
                                                for (View view : sharedElements) {
                                                    if (view instanceof SimpleDraweeView) {
                                                        view.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }
                                        });
                                ActivityCompat.startActivity(context,
                                        new Intent(context, ImageDetailActivity.class)
                                                .putExtra("imageUrl", gankItemGirl.getUrl()),
                                        ActivityOptionsCompat
                                                .makeSceneTransitionAnimation((Activity) context,
                                                        gankDayGirlHolder.iv_girl,
                                                        context.getString(R.string.share_image))
                                                .toBundle());
                            }
                        });
                break;
            case TYPE_TITLE:
                GankDayTitleHolder gankTitleHolder = (GankDayTitleHolder) viewHolder;
                GankItemTitle gankItemTitle = (GankItemTitle) list.get(position);
                gankTitleHolder.tv_title.setText(gankItemTitle.getType());
                RxView.clicks(gankTitleHolder.itemView).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(@NonNull Object o) throws Exception {
                            }
                        });
                break;
            case TYPE_NORMAL:
                GankDayHolder gankDayHolder = (GankDayHolder) viewHolder;
                final GankItem gankItem = (GankItem) list.get(position);
                if (TextUtils.isEmpty(gankItem.getWho())) {
                    gankDayHolder.tv_desc.setText(gankItem.getDesc());
                } else {
                    int start = gankItem.getDesc().length();
                    int end = start + gankItem.getWho().length() + 3;
                    int color = ContextCompat.getColor(context, R.color.text_default);
                    SpannableStringBuilder builder = new SpannableStringBuilder(
                            gankItem.getDesc() + " - " + gankItem.getWho());
                    builder.setSpan(new ForegroundColorSpan(color), start, end,
                            Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    builder.setSpan(new RelativeSizeSpan(0.85f), start, end,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    gankDayHolder.tv_desc.setText(builder);
                }
                RxView.clicks(gankDayHolder.itemView).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                        new Consumer<Object>() {
                            @Override
                            public void accept(@NonNull Object o) throws Exception {
                                PendingIntent pendingIntent = createPendingIntent();
                                CustomTabsIntent intent = new CustomTabsIntent.Builder()
                                        .setCloseButtonIcon(BitmapFactory
                                                .decodeResource(context.getResources(),
                                                        R.drawable.ic_action_back)).setShowTitle(
                                                true).setToolbarColor(ContextCompat
                                                .getColor(context, android.R.color.holo_blue_light))
                                        .enableUrlBarHiding().addDefaultShareMenuItem()
                                        .setActionButton(BitmapFactory
                                                        .decodeResource(context.getResources(),
                                                                R.drawable.ic_action_more), "fuck" +
                                                        " to show",
                                                pendingIntent).build();
                                CustomTabActivityHelper.openCustomTab((Activity) context, intent,
                                        Uri.parse(gankItem.getUrl()), new WebViewFallback());
                            }
                        });
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

    private PendingIntent createPendingIntent() {
        Intent actionIntent = new Intent(context, ActionBroadcastReceiver.class);
        actionIntent.putExtra(ActionBroadcastReceiver.KEY_ACTION_SOURCE, 0);
        return PendingIntent.getBroadcast(context, 0, actionIntent, 0);
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
        SimpleDraweeView iv_girl;
        TextView tv_time;

        GankDayGirlHolder(View itemView) {
            super(itemView);
            iv_girl = (SimpleDraweeView) itemView.findViewById(R.id.iv_girl);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
