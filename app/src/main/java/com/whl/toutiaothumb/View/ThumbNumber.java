package com.whl.toutiaothumb.View;

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
 * date    2021/2/9
 */
class ThumbNumber extends View {
    private Paint textPaint;
    private int number;
    public static final int TEXT_SIZE = 80;
    public static final int STROKE_WIDTH = 10;
    private Bitmap mTalk1;
    private Bitmap mTalk2;
    private Bitmap mTalk3;
    private Bitmap bitmapTalk;
    private int textWidth;
    private int imageWidth;


    public ThumbNumber(Context context) {
        this(context, null);
        init();
    }

    public ThumbNumber(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThumbNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setStrokeWidth(STROKE_WIDTH);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mTalk1 = BitmapFactory.decodeResource(getResources(), R.drawable.talk1, opt).copy(Bitmap.Config.ARGB_8888, true);
        mTalk1.setDensity(getResources().getDisplayMetrics().densityDpi);
        mTalk2 = BitmapFactory.decodeResource(getResources(), R.drawable.talk2, opt).copy(Bitmap.Config.ARGB_8888, true);
        mTalk2.setDensity(getResources().getDisplayMetrics().densityDpi);
        mTalk3 = BitmapFactory.decodeResource(getResources(), R.drawable.talk3, opt).copy(Bitmap.Config.ARGB_8888, true);
        mTalk3.setDensity(getResources().getDisplayMetrics().densityDpi);
        bitmapTalk = mTalk1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textWidth = (int) textPaint.measureText(number + "");
        imageWidth = bitmapTalk.getWidth();
        Log.i("tag", textWidth + ";;;onMeasure" + imageWidth);
//        setMeasuredDimension(textWidth + imageWidth, 90);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        super.onLayout(true, 0, 0, textWidth + imageWidth, 90);
        Log.i("tag", textWidth + imageWidth + "onLayout;;;");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(imageWidth + textWidth, h, oldw, oldh);
        Log.i("tag", textWidth + imageWidth + "onSizeChanged;;;");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(number + "", 40, 90, textPaint);//这里的坐标位置原理值得深究
        Rect dst = new Rect();
        dst.left = 40 + textWidth;
        dst.top = 20;
        dst.right = 40 + textWidth + imageWidth;
        dst.bottom = 95;
        canvas.drawBitmap(bitmapTalk, null, dst, textPaint);//这里的坐标位置原理值得深究

    }

    public void setNumber(int number) {
        this.number = number;
        if (number < 20) {
            bitmapTalk = mTalk1;
        } else if (number < 40) {
            bitmapTalk = mTalk2;
        } else {
            bitmapTalk = mTalk3;
        }
        requestLayout();// 他跟invalidate()相反，他只调用measure()和layout()过程，不会调用draw()。
        invalidate();
    }
}
