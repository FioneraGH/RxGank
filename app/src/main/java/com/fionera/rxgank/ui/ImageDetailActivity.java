package com.fionera.rxgank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fionera.base.activity.BaseActivity;
import com.fionera.base.util.ImageUtil;
import com.fionera.rxgank.R;

public class ImageDetailActivity
        extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        ImageView ivImageDetailPreview = (ImageView) findViewById(R.id.iv_image_detail_preview);
        ivImageDetailPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageUtil.loadImage(getIntent().getStringExtra("imageUrl"), ivImageDetailPreview);
    }
}
