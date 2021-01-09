package com.whl.toutiaothumb.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.whl.toutiaothumb.R;

import java.util.Random;

/**
 * author  honglei92
 * date    2021/1/9
 */
public class ArticleThumb extends View implements View.OnClickListener {
    private static final String TAG = "ArticleThumb";
    private Bitmap mThumbImage;
    private Bitmap mThumbImage1;
    private Bitmap mThumbImage2;
    private Bitmap mThumbImage3;
    private Bitmap mThumbImage4;
    private Paint mBitmapPaint;
    private int offsetX = 10;
    private int offsetY = 10;

    public float getEmoji1x() {
        return emoji1x;
    }

    public void setEmoji1x(float emoji1x) {
        this.emoji1x = emoji1x;
    }

    public float getEmoji1y() {
        return emoji1y;
    }

    public void setEmoji1y(float emoji1y) {
        this.emoji1y = emoji1y;
    }

    private float emoji1x;
    private float emoji1y;
    private int emojiType;

    public ArticleThumb(Context context) {
        this(context, null);
    }

    public ArticleThumb(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleThumb(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initSize();
        //初始化paint
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        //初始化图片
        emojiType = 0;
        switch (emojiType) {
            case 1:
                mThumbImage1 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji2);
                break;
            case 2:
                mThumbImage2 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji3);
                break;
            case 3:
                mThumbImage3 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji4);
                break;
            case 4:
                mThumbImage4 = BitmapFactory.decodeResource(getResources(), R.drawable.emoji5);
                break;
            default:
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inScaled = true;
                opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
                mThumbImage = BitmapFactory.decodeResource(getResources(), R.drawable.emoji1, opt).copy(Bitmap.Config.ARGB_8888, true);
                mThumbImage.setDensity(getResources().getDisplayMetrics().densityDpi);
        }

        setOnClickListener(this);
    }

    private void initSize() {
    }

    public ArticleThumb(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "点击了");
    }

    private void showThumbDownAni() {
        boolean isX = ((int) (Math.random() * 100) % 2 == 0);
        Log.i(TAG, "showThumbDownAni " + isX + ":" + Math.random() * 10 % 2);
        //获取起点坐标
        int[] location2 = new int[2];
        getLocationInWindow(location2);
        int x1 = location2[0];
        int y1 = location2[1];
        //获取终点坐标，最近拍摄的坐标
        int[] location = new int[2];
        getLocationInWindow(location);
        int x2 = location[0];
        int y2 = location[1];

        //抛物线动画
        ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(this, "translationX", 0,
                isX ? -1180 : (float) (-1080 * Math.random()));
        translateAnimationX.setDuration(800);
        translateAnimationX.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(this, "translationY", 0,
                isX ? (float) (-700 * Math.random()) : (float) (-700));
        translateAnimationY.setDuration(800);
        translateAnimationY.setInterpolator(new LinearInterpolator());


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateAnimationX).with(translateAnimationY);
        animatorSet.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawThumbImage(canvas);
    }


    private void drawThumbImage(Canvas canvas) {
        switch (emojiType) {
            case 1:
                canvas.drawBitmap(mThumbImage1, 0, 0, mBitmapPaint);
                break;
            case 2:
                canvas.drawBitmap(mThumbImage2, 0, 0, mBitmapPaint);
                break;
            case 3:
                canvas.drawBitmap(mThumbImage3, 0, 0, mBitmapPaint);
                break;
            case 4:
                canvas.drawBitmap(mThumbImage4, 0, 0, mBitmapPaint);
                break;
            default:
                canvas.drawBitmap(mThumbImage, 0, 0, mBitmapPaint);

        }
    }

    public void setThumb(boolean isThumb) {
        showThumbDownAni();
    }
}
