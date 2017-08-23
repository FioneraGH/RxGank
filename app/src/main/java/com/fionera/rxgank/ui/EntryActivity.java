package com.fionera.rxgank.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fionera.base.activity.BaseActivity;
import com.fionera.rxgank.R;

public class EntryActivity
        extends BaseActivity {

    private final static float INITIAL_SCALE = 1f;
    private final static float INITIAL_ROTATION = 0f;
    private final static float STIFFNESS = SpringForce.STIFFNESS_MEDIUM;
    private final static float DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;

    private ViewPropertyAnimator logoScaleAnimator;
    private ViewPropertyAnimator nameRotationAnimator;

    private SpringAnimation saScaleX;
    private SpringAnimation saScaleY;
    private SpringAnimation saRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ImageView ivSplashLogo = findViewById(R.id.iv_splash_logo);
        TextView tvSplashName = findViewById(R.id.tv_splash_name);

        saScaleX = createSpringAnimation(ivSplashLogo, SpringAnimation.SCALE_X, INITIAL_SCALE,
                STIFFNESS, DAMPING_RATIO);
        saScaleY = createSpringAnimation(ivSplashLogo, SpringAnimation.SCALE_Y, INITIAL_SCALE,
                STIFFNESS, DAMPING_RATIO);
        saRotation = createSpringAnimation(tvSplashName, SpringAnimation.ROTATION, INITIAL_ROTATION,
                STIFFNESS, DAMPING_RATIO);
        saRotation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value,
                                       float velocity) {
                if (!canceled) {
                    startActivity(new Intent(mContext, MainActivity.class));
                    finish();
                }
            }
        });

        logoScaleAnimator = ivSplashLogo.animate().withLayer().scaleX(2.5f).scaleY(2.5f)
                .setDuration(700).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        saScaleX.start();
                        saScaleY.start();
                        saRotation.start();
                    }
                });
        nameRotationAnimator = tvSplashName.animate().withLayer().rotation(180f).setDuration(600);

        logoScaleAnimator.start();
        nameRotationAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (logoScaleAnimator != null) {
            logoScaleAnimator.cancel();
        }
        if (nameRotationAnimator != null) {
            nameRotationAnimator.cancel();
        }
        if (saScaleX != null) {
            saScaleX.cancel();
        }
        if (saScaleY != null) {
            saScaleY.cancel();
        }
        if (saRotation != null) {
            saRotation.cancel();
        }
    }

    private SpringAnimation createSpringAnimation(View view,
                                                  DynamicAnimation.ViewProperty scaleProperty,
                                                  float initialState, float stiffness,
                                                  float dampingRatio) {
        SpringAnimation springAnimation = new SpringAnimation(view, scaleProperty);
        SpringForce springForce = new SpringForce(initialState);
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        springAnimation.setSpring(springForce);
        return springAnimation;
    }
}
