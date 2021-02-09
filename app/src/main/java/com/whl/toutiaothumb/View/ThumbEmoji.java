package com.whl.toutiaothumb.View;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.whl.toutiaothumb.R;

/**
 * author  honglei92
 * date    2021/1/9
 */
public class ThumbEmoji extends View implements View.OnClickListener {
    private static final String TAG = "ArticleThumb";
    public static final int DURATION = 600;
    public static final int[] emojiArray = {R.drawable.emoji1, R.drawable.emoji2, R.drawable.emoji3, R.drawable.emoji4,
            R.drawable.emoji5, R.drawable.emoji6, R.drawable.emoji7, R.drawable.emoji8};
    private Bitmap mThumbImage;
    private Paint mBitmapPaint;
    private Context mContext;
    private int emojiType;
    private AnimatorListener mAnimatorListener;

    public int getEmojiType() {
        return emojiType;
    }

    public void setEmojiType(int emojiType) {
        this.emojiType = emojiType;
        init();
    }

    public ThumbEmoji(Context context) {
        this(context, null);
        mContext = context;
    }

    public ThumbEmoji(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbEmoji(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThumbEmoji(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        initSize();
        //初始化paint
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        //初始化图片
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mThumbImage = BitmapFactory.decodeResource(getResources(), emojiArray[emojiType], opt).copy(Bitmap.Config.ARGB_8888, true);
        mThumbImage.setDensity(getResources().getDisplayMetrics().densityDpi);
        setOnClickListener(this);
    }

    private void initSize() {
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "点击了");
    }

    private void showThumbDownAni(ArticleRl articleThumbRl) {
        float topX = -(1080 - 200) + (float) ((2160 - 400) * Math.random());
        float topY = -300 + (float) (-700 * Math.random());
        //上升动画
        //抛物线动画 x方向
        ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(this, "translationX",
                0, topX);
        translateAnimationX.setDuration(DURATION);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        //y方向
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(this, "translationY",
                0, topY);
        translateAnimationY.setDuration(DURATION);
        translateAnimationY.setInterpolator(new DecelerateInterpolator());
        //动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateAnimationX).with(translateAnimationY);

        //下降动画
        //抛物线动画，原理：两个位移动画，一个横向匀速移动，一个纵向变速移动，两个动画同时执行，就有了抛物线的效果。
        ObjectAnimator translateAnimationXDown = ObjectAnimator.ofFloat(this, "translationX", topX, topX * 1.2f);
        translateAnimationXDown.setDuration(DURATION / 5);
        translateAnimationXDown.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateAnimationYDown = ObjectAnimator.ofFloat(this, "translationY", topY, topY * 0.8f);
        translateAnimationYDown.setDuration(DURATION / 5);
        translateAnimationYDown.setInterpolator(new AccelerateInterpolator());
        //透明度
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this, "alpha", 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0f);
        alphaAnimation.setDuration(DURATION / 5);
        AnimatorSet animatorSetDown = new AnimatorSet();//设置动画播放顺序
        //播放上升动画
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSetDown.play(translateAnimationXDown).with(translateAnimationYDown).with(alphaAnimation);
                animatorSetDown.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSetDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                articleThumbRl.removeView(ThumbEmoji.this);
                mAnimatorListener.onAnimationEmojiEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 100);//该方法进行宽高设置
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
        Rect dst = new Rect();
        dst.left = 0;
        dst.top = 0;
        dst.right = 100;
        dst.bottom = 100;
        canvas.drawBitmap(mThumbImage, null, dst, mBitmapPaint);
    }

    public void setThumb(boolean isThumb, ArticleRl articleThumbRl) {
        showThumbDownAni(articleThumbRl);
    }

    public void setmAnimatorListener(AnimatorListener animatorListener) {
        this.mAnimatorListener = animatorListener;
    }

    public interface AnimatorListener {
        void onAnimationEmojiEnd();
    }
}
