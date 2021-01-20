package com.turdroid.library.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.DecelerateInterpolator;


public class LoadingView extends CircleImageView {

    private int[] colors = {
            0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFF8B00FF
    };

    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;

    private MaterialProgressDrawable mProgressDrawable;

    private ValueAnimator valueAnimator;

    private boolean start = false;

    private boolean visible = false;

    public LoadingView(Context context) {
        super(context, CIRCLE_BG_LIGHT);

        create(context);

    }

    private void create(Context context) {

        mProgressDrawable = new MaterialProgressDrawable(context, this);
        mProgressDrawable.setBackgroundColor(CIRCLE_BG_LIGHT);
        //圈圈颜色,可以是多种颜色
        mProgressDrawable.setColorSchemeColors(colors);
        //设置圈圈的各种大小
        mProgressDrawable.updateSizes(MaterialProgressDrawable.DEFAULT);

        setImageDrawable(mProgressDrawable);

    }

    public void setColorSchemeColors(int... colors) {
        mProgressDrawable.setColorSchemeColors(colors);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        visible();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        stop();
    }

    private void visible() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator.setDuration(600);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float n = (float) animation.getAnimatedValue();
                    //圈圈的旋转角度
                    mProgressDrawable.setProgressRotation(n * 0.5f);
                    //圈圈周长，0f-1F
                    mProgressDrawable.setStartEndTrim(0f, n * 0.8f);
                    //箭头大小，0f-1F
                    mProgressDrawable.setArrowScale(n);
                    //透明度，0-255
                    mProgressDrawable.setAlpha((int) (255 * n));
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    visible = true;
                    start();
                }
            });
        }

        if (!valueAnimator.isRunning() && !visible) {
            //是否显示箭头
            mProgressDrawable.showArrow(true);
            valueAnimator.start();
        }
    }

    private void start() {
        if (visible && !start) {
            mProgressDrawable.start();
            start = true;
        }
    }

    private void stop() {
        if (start) {
            mProgressDrawable.stop();
            mProgressDrawable.setVisible(false, false);
            start = false;
            visible = false;
        }
    }
}
