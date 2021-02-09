package com.whl.toutiaothumb.View;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.whl.toutiaothumb.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author  honglei92
 * date    2021/1/9
 */
public class ArticleRl extends RelativeLayout {
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
//        addThumbImage(context, x, y);
    }

    private void addThumbImage(Context context, float x, float y) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(i);
        }
        Collections.shuffle(list);//打乱顺序
        for (int i = 0; i < 5; i++) {
            LayoutParams layoutParams = new LayoutParams(100, 100);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;

            layoutParams.setMargins((int) x, (int) y, 0, 0);
            ThumbEmoji articleThumb = new ThumbEmoji(context);
            articleThumb.setEmojiType(list.get(i));
            this.addView(articleThumb, layoutParams);
        }
    }

    public ArticleRl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setThumb(boolean isThumb, float x, float y, ArticleRl articleThumbRl) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(0);
        } else {
            mMediaPlayer.start();
        }
        if (System.currentTimeMillis() - lastClickTime > 800) {
            addThumbImage(mContext, x, y);
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
        } else {
            lastClickTime = System.currentTimeMillis();
            Log.i(TAG, "当前动画化正在执行");
            addThumbImage(mContext, x, y);
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ThumbEmoji) {
                    ((ThumbEmoji) getChildAt(i)).setThumb(true, articleThumbRl);
                }
            }
            currentNumber++;
            //添加数字连击view
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            layoutParams.setMargins((int) (x), (int) (y) - 300, 0, 150);
            if (thumbNumber == null) {
                thumbNumber = new ThumbNumber(mContext);
                addView(thumbNumber, layoutParams);
            }
            thumbNumber.setNumber(currentNumber);
        }
    }
}
