package com.fionera.rxgank.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * NumberTextView
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class NumberTextView
        extends AppCompatTextView {


    public NumberTextView(Context context) {
        super(context);
        init(context);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "number.otf");
        setTypeface(typeface);
    }
}
