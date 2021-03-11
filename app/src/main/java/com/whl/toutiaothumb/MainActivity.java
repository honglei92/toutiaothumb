package com.whl.toutiaothumb;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.whl.toutiaothumb.View.ArticleRl;
import com.whl.toutiaothumb.love.Love;

public class MainActivity extends AppCompatActivity {
    private ImageView ivThumb, ivThumbBottom;
    private ArticleRl articleThumbRl;
    private Love mLove;
    final Handler handler = new Handler();
    final Runnable mLongPressed = new Runnable() {
        @Override
        public void run() {
            articleThumbRl.setVisibility(View.VISIBLE);
            articleThumbRl.setThumb(x, y, articleThumbRl);
            handler.postDelayed(mLongPressed, 100);
        }
    };
    private int x;
    private int y;
    private long lastDownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);*/
        setContentView(R.layout.activity_main);
        ivThumb = findViewById(R.id.ivThumb);
        ivThumbBottom = findViewById(R.id.ivThumbBottom);
        articleThumbRl = findViewById(R.id.articleThumbRl);
        ivThumb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastDownTime = System.currentTimeMillis();
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    Log.i("aaa", (System.currentTimeMillis() - lastDownTime) + "");
                    handler.postDelayed(mLongPressed, 100);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("aaa", (System.currentTimeMillis() - lastDownTime) + "");
                    if (System.currentTimeMillis() - lastDownTime < 100) {//判断为单击事件
                        articleThumbRl.setVisibility(View.VISIBLE);
                        articleThumbRl.setThumb(x, y, articleThumbRl);
                        handler.removeCallbacks(mLongPressed);
                    } else {//判断为长按事件后松开
                        handler.removeCallbacks(mLongPressed);
                    }
                }
                return true;
            }
        });

        ivThumbBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastDownTime = System.currentTimeMillis();
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    Log.i("aaa", (System.currentTimeMillis() - lastDownTime) + "");
                    handler.postDelayed(mLongPressed, 100);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("aaa", (System.currentTimeMillis() - lastDownTime) + "");
                    if (System.currentTimeMillis() - lastDownTime < 100) {//判断为单击事件
                        articleThumbRl.setVisibility(View.VISIBLE);
                        articleThumbRl.setThumb(x, y, articleThumbRl);
                        handler.removeCallbacks(mLongPressed);
                    } else {//判断为长按事件后松开
                        handler.removeCallbacks(mLongPressed);
                    }
                }
                return true;
            }
        });
        //爱心发射
        mLove = findViewById(R.id.love);
        mLove.setChecked(true);
        mLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLove.Onclick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}