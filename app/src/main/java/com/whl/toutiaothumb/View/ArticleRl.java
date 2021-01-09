package com.whl.toutiaothumb.View;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
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
        addThumbImage(context);
    }

    private void addThumbImage(Context context) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 5; i++) {
            LayoutParams layoutParams = new LayoutParams(100, 100);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            switch (list.get(i)) {
                case 1:
                    this.addView(new ArticleThumb1(context), layoutParams);
                    break;
                case 2:
                    this.addView(new ArticleThumb2(context), layoutParams);
                    break;
                case 3:
                    this.addView(new ArticleThumb3(context), layoutParams);
                    break;
                case 4:
                    this.addView(new ArticleThumb4(context), layoutParams);
                    break;
                default:
                    this.addView(new ArticleThumb(context), layoutParams);
            }
        }
    }

    public ArticleRl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setThumb(boolean isThumb) {
        if (System.currentTimeMillis() - lastClickTime > 800) {
            lastClickTime = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                if (getChildAt(i) instanceof ArticleThumb)
                    ((ArticleThumb) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb1)
                    ((ArticleThumb1) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb2)
                    ((ArticleThumb2) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb3)
                    ((ArticleThumb3) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb4)
                    ((ArticleThumb4) getChildAt(i)).setThumb(true);
            }
        } else {
            Log.i(TAG, "当前动画化正在执行");
            addThumbImage(mContext);
            for (int i = getChildCount() - 5; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof ArticleThumb)
                    ((ArticleThumb) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb1)
                    ((ArticleThumb1) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb2)
                    ((ArticleThumb2) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb3)
                    ((ArticleThumb3) getChildAt(i)).setThumb(true);
                else if (getChildAt(i) instanceof ArticleThumb4)
                    ((ArticleThumb4) getChildAt(i)).setThumb(true);
            }

        }
    }
}
