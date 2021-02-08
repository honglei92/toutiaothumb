package com.whl.toutiaothumb.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

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
//        addThumbImage(context, x, y);
    }

    private void addThumbImage(Context context, float x, float y) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 5; i++) {
            LayoutParams layoutParams = new LayoutParams(100, 100);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;

            layoutParams.setMargins((int) x, (int) y, 0, 0);
            ArticleThumb articleThumb = new ArticleThumb(context);
            articleThumb.setEmojiType(list.get(i));
            this.addView(articleThumb, layoutParams);
        }
    }

    public ArticleRl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setThumb(boolean isThumb, float x, float y, ArticleRl articleThumbRl) {
        if (System.currentTimeMillis() - lastClickTime > 800) {
//            if (getChildCount() < 5) {
                addThumbImage(mContext, x, y);
//            }
            lastClickTime = System.currentTimeMillis();
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ArticleThumb)
                    ((ArticleThumb) getChildAt(i)).setThumb(true, articleThumbRl);
            }
        } else {
            Log.i(TAG, "当前动画化正在执行");
//            if (getChildCount() < 5) {
                addThumbImage(mContext, x, y);
//            }
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ArticleThumb)
                    ((ArticleThumb) getChildAt(i)).setThumb(true, articleThumbRl);
            }
        }
    }
}
