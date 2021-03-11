package com.whl.toutiaothumb.love;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.whl.toutiaothumb.R;

/**
 * author  honglei92
 * date    2021/3/11
 */
public class Love extends View {
    private static final String TAG = "LOVE";
    private Bitmap mLove;
    private Bitmap mLoveUnSelect;
    private Bitmap currentBitmap;
    private Paint mPaint;
    private boolean checked = false;
    private Paint mCirclePaint;
    private Paint mSmallCirclePaint;
    private static int WIDTH = 200;//view宽度的6/5
    private static int HALF_VIEW_WIDTH = 120;//整个view宽度的一半
    private static final int TIME = 1000;
    private float smallCircleRadius = 6;

    public float getSmallCircleScale() {
        return smallCircleScale;
    }

    public void setSmallCircleScale(float smallCircleScale) {
        this.smallCircleScale = smallCircleScale;
    }

    private float smallCircleScale = 0.5f;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    private float radius;

    public int getRightLength() {
        return rightLength;
    }

    public void setRightLength(int rightLength) {
        this.rightLength = rightLength;
    }

    public int getBottomLength() {
        return bottomLength;
    }

    public void setBottomLength(int bottomLength) {
        this.bottomLength = bottomLength;
    }

    private int rightLength;
    private int bottomLength;

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    private int locationX;

    public Love(Context context) {
        this(context, null);
    }

    public Love(Context context, @Nullable AttributeSet attrs) {//从项目里布局加载首先会执行这里
        this(context, attrs, 0);
    }

    public Love(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public Love(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    private void init() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mLove = BitmapFactory.decodeResource(getResources(), R.drawable.tmnis_like_red, opt).copy(Bitmap.Config.ARGB_8888, true);
        mLove.setDensity(getResources().getDisplayMetrics().densityDpi);

        mLoveUnSelect = BitmapFactory.decodeResource(getResources(), R.drawable.tmnis_like_white, opt).copy(Bitmap.Config.ARGB_8888, true);
        mLoveUnSelect.setDensity(getResources().getDisplayMetrics().densityDpi);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //大圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(3);
        mCirclePaint.setColor(Color.parseColor("#ff0000"));

        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
        mSmallCirclePaint.setColor(Color.parseColor("#ff0000"));

        currentBitmap = mLoveUnSelect;

//        setBackgroundColor(Color.parseColor("#999999"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, getWidth() + "---" + getMeasuredWidth());
        WIDTH = (int) (getMeasuredWidth() / 6f * 5);
        HALF_VIEW_WIDTH = (int) (getMeasuredWidth() / 2f);
        rightLength = bottomLength = WIDTH;
        locationX = WIDTH / 10;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (checked) {
            currentBitmap = mLove;
        } else {
            currentBitmap = mLoveUnSelect;
        }
        Rect dst = new Rect();
        dst.left = locationX;
        dst.top = locationX;
        dst.right = rightLength + locationX;
        dst.bottom = rightLength + locationX;
        canvas.drawBitmap(currentBitmap, null, dst, mPaint);
        if (radius <= HALF_VIEW_WIDTH) {
            canvas.drawCircle(WIDTH / 5 * 3, WIDTH / 5 * 3, radius, mCirclePaint);
        }
        float sin30 = (float) (HALF_VIEW_WIDTH * Math.sin(Math.PI * 30d / 180)) * smallCircleScale;
        float sin60 = (float) (HALF_VIEW_WIDTH * Math.sin(Math.PI * 60d / 180)) * smallCircleScale;
        if (smallCircleScale < 1.1 && smallCircleScale != 0.5) {
            canvas.drawCircle(HALF_VIEW_WIDTH, HALF_VIEW_WIDTH - WIDTH / 2 * smallCircleScale, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
            canvas.drawCircle(HALF_VIEW_WIDTH + sin60, HALF_VIEW_WIDTH - sin30, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
            canvas.drawCircle(HALF_VIEW_WIDTH + sin60, HALF_VIEW_WIDTH + sin30, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
            canvas.drawCircle(HALF_VIEW_WIDTH - sin60, HALF_VIEW_WIDTH - sin30, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
            canvas.drawCircle(HALF_VIEW_WIDTH - sin60, HALF_VIEW_WIDTH + sin30, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
            canvas.drawCircle(HALF_VIEW_WIDTH, HALF_VIEW_WIDTH + WIDTH / 2 * smallCircleScale, smallCircleRadius * smallCircleScale, mSmallCirclePaint);
        }
    }

    public void Onclick() {
        if (!checked) {
            invalidate();
            handleLike();
        } else {
            invalidate();
            handleUnLike();
        }
        checked = !checked;
    }

    private void handleUnLike() {
        int time = 300;
        ObjectAnimator scaleAni = ObjectAnimator.ofInt(this, "rightLength",
                WIDTH, WIDTH * 4 / 5, WIDTH);
        scaleAni.setDuration(time);
        ObjectAnimator scaleAniY = ObjectAnimator.ofInt(this, "bottomLength",
                WIDTH, WIDTH * 4 / 5, WIDTH);
        scaleAniY.setDuration(time);
        ObjectAnimator moveX = ObjectAnimator.ofInt(this, "locationX",
                WIDTH / 10, WIDTH / 10 + WIDTH / 10, WIDTH / 10);
        moveX.setDuration(time);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAni).with(scaleAniY).with(moveX);
        moveX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        animatorSet.start();
    }

    private void handleLike() {
        ObjectAnimator scaleAni = ObjectAnimator.ofInt(this, "rightLength",
                WIDTH, 0, WIDTH * 6 / 5, WIDTH);
        scaleAni.setDuration(TIME);
        ObjectAnimator scaleAniY = ObjectAnimator.ofInt(this, "bottomLength",
                WIDTH, 0, WIDTH * 6 / 5, WIDTH);
        scaleAniY.setDuration(TIME);
        ObjectAnimator moveX = ObjectAnimator.ofInt(this, "locationX",
                WIDTH / 10, HALF_VIEW_WIDTH, 0, WIDTH / 10);
        moveX.setDuration(TIME);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAni).with(scaleAniY).with(moveX);
        scaleAniY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        scaleAniY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                radius = 0;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        postDelayed(new Runnable() {
            @Override
            public void run() {
                animatorSet.start();
            }
        }, TIME / 10);

        //圆环和原点发射
        ObjectAnimator radiusAni = ObjectAnimator.ofFloat(this, "radius",
                0, 0, 0, 0, HALF_VIEW_WIDTH / 2, HALF_VIEW_WIDTH - 2);
        radiusAni.setDuration(TIME / 4 * 3);
        ObjectAnimator smallAni = ObjectAnimator.ofFloat(this, "smallCircleScale",
                0f, 0f, 0f, 0f, 0.5f, 1.1f);
        smallAni.setDuration(TIME);
        radiusAni.start();
        smallAni.start();
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }
}
