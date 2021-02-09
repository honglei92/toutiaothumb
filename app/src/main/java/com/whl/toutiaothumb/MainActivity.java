package com.whl.toutiaothumb;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.whl.toutiaothumb.View.ArticleRl;

public class MainActivity extends AppCompatActivity {
    private ImageView ivThumb, ivThumbBottom;
    private ArticleRl articleThumbRl;

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