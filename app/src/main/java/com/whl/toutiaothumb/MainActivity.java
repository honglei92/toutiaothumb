package com.whl.toutiaothumb;

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

public class MainActivity extends AppCompatActivity {
    private ImageView ivThumb, ivThumbBottom;
    private ArticleRl articleThumbRl;
    final Handler handler = new Handler();
    final Runnable mLongPressed = new Runnable() {
        @Override
        public void run() {
            articleThumbRl.setVisibility(View.VISIBLE);
            articleThumbRl.setThumb(true, x, y, articleThumbRl);
            handler.postDelayed(mLongPressed, 100);
        }
    };
    private int x;
    private int y;
    private long lastDownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivThumb = findViewById(R.id.ivThumb);
        ivThumbBottom = findViewById(R.id.ivThumbBottom);
        articleThumbRl = findViewById(R.id.articleThumbRl);
        ivThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                int x = location[0]; // view距离window 左边的距离（即x轴方向）
                int y = location[1]; // view距离window 顶边的距离（即y轴方向）
                articleThumbRl.setVisibility(View.VISIBLE);
                articleThumbRl.setThumb(true, x, y, articleThumbRl);
            }
        });
/*
        ivThumbBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                int x = location[0]; // view距离window 左边的距离（即x轴方向）
                int y = location[1]; // view距离window 顶边的距离（即y轴方向）
                articleThumbRl.setVisibility(View.VISIBLE);
                articleThumbRl.setThumb(true, x, y, articleThumbRl);
            }
        });
*/
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
                        articleThumbRl.setThumb(true, x, y, articleThumbRl);
                        handler.removeCallbacks(mLongPressed);
                    } else {//判断为长按事件后松开
                        handler.removeCallbacks(mLongPressed);
                    }
                }
                return true;
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