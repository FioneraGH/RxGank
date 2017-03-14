package com.fionera.rxgank.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fionera.base.activity.BaseActivity;
import com.fionera.base.util.L;
import com.fionera.rxgank.R;

public class ImageDetailActivity
        extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        final SimpleDraweeView ivImageDetailPreview = (SimpleDraweeView) findViewById(R.id.iv_image_detail_preview);
        ivImageDetailPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        /*
         delay show temp
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivImageDetailPreview.setImageURI(getIntent().getStringExtra("imageUrl"));
            }
        }, 500);
    }

    /**
     * IMMERSIVE MODE
     */
    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        L.d("ImageDetail WindowFocus:" + hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
