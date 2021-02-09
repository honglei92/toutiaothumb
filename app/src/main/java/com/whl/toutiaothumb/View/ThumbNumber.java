package com.whl.toutiaothumb.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author  honglei92
 * date    2021/2/9
 */
class ThumbNumber extends View {
    private Paint textPaint;
    private int number;
    public static final int TEXT_SIZE = 80;
    public static final int STROKE_WIDTH = 10;


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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(number + "鼓励", 40, 90, textPaint);//这里的坐标位置原理值得深究
    }

    public void setNumber(int number) {
        this.number = number;
        invalidate();
    }
}
