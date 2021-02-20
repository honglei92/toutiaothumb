package com.whl.toutiaothumb.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.whl.toutiaothumb.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * author  honglei92
 * date    2021/1/9
 */
public class ArticleRl extends RelativeLayout implements ThumbEmoji.AnimatorListener {
    private static final String TAG = "ArticleThumb";
    private long lastClickTime;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private int currentNumber;
    private ThumbNumber thumbNumber;


    public ArticleRl(Context context) {
        this(context, null);
    }

    public ArticleRl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleRl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        mContext = context;
    }

    private void init(Context context) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.thumb);
    }

    private void addThumbImage(Context context, float x, float y, ThumbEmoji.AnimatorListener animatorListener) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        Collections.shuffle(list);//打乱顺序
        for (int i = 0; i < 5; i++) {
            LayoutParams layoutParams = new LayoutParams(100, 100);
            //获取屏幕尺寸
           /* DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;*/

            layoutParams.setMargins((int) x, (int) y - 50, 0, 0);
            ThumbEmoji articleThumb = new ThumbEmoji(context);
            articleThumb.setEmojiType(list.get(i));
            articleThumb.setmAnimatorListener(animatorListener);
            this.addView(articleThumb, -1, layoutParams);
        }
    }

    public ArticleRl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setThumb(float x, float y, ArticleRl articleThumbRl) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(0);//重复点击时，从头开始播放
        } else {
            mMediaPlayer.start();
        }
        if (System.currentTimeMillis() - lastClickTime > 800) {//单次点击
            addThumbImage(mContext, x, y, this);
            lastClickTime = System.currentTimeMillis();
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ThumbEmoji) {
                    ((ThumbEmoji) getChildAt(i)).setThumb(true, articleThumbRl);
                }
            }
            currentNumber = 0;
            if (thumbNumber != null) {
                removeView(thumbNumber);
                thumbNumber = null;
            }
        } else {//连续点击
            lastClickTime = System.currentTimeMillis();
            Log.i(TAG, "当前动画化正在执行");
            addThumbImage(mContext, x, y, this);
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ThumbEmoji) {
                    ((ThumbEmoji) getChildAt(i)).setThumb(true, articleThumbRl);
                }
            }
            currentNumber++;
            //添加数字连击view
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            layoutParams.setMargins((int)x - 300, (int) (y) - 300, 0, 150);
            if (thumbNumber == null) {
                thumbNumber = new ThumbNumber(mContext);
                addView(thumbNumber, layoutParams);
            }
            thumbNumber.setNumber(currentNumber);
        }
    }

    @Override
    public void onAnimationEmojiEnd() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (thumbNumber != null && System.currentTimeMillis() - lastClickTime > 800) {
                    removeView(thumbNumber);
                    thumbNumber = null;
                }
            }
        }, 1000);
    }
}
