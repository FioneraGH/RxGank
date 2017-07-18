package com.fionera.base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * CommonImageView
 * Created by fionera on 17-3-15 in MVPPractice.
 */

public class CommonImageView
        extends SimpleDraweeView {

    public CommonImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public CommonImageView(Context context) {
        super(context);
    }

    public CommonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageURI(@Nullable String uriString) {
        super.setImageURI(uriString);
    }
}
