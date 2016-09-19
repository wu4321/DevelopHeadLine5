package com.cqc.develophead.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqc.develophead.R;
import com.cqc.develophead.adapter.GuildPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${cqc} on 2016/9/13.
 */
public class GuildActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPager viewPager;
    private TextView tv;
    private LinearLayout layout_indicator;
    private List<ImageView> list = new ArrayList<ImageView>();
    private List<ImageView> indicatorList = new ArrayList<ImageView>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        context = this;
        //判断是不是第一次进入
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if (!isFirst) {//进入主页面
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            GuildActivity.this.finish();
        }
        //第一次进入后修改为false
        sp.edit().putBoolean("isFirst", false).commit();
        findViews();
        initViews();
    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_guild);
        tv = (TextView) findViewById(R.id.tv_guild);
        layout_indicator = (LinearLayout) findViewById(R.id.layout_indicator_guild);
    }

    private void initViews() {
        initData();
        GuildPagerAdapter adapter = new GuildPagerAdapter(context, list);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(this);
		viewPager.setCurrentItem(0);
        tv.setOnClickListener(this);

        initLayoutIndicator();

    }

    private void initData() {
        list.clear();
        ImageView iv1 = new ImageView(context);
        ImageView iv2 = new ImageView(context);
        ImageView iv3 = new ImageView(context);
        ImageView iv4 = new ImageView(context);

        iv1.setImageResource(R.mipmap.tutorial_1);
        iv2.setImageResource(R.mipmap.tutorial_2);
        iv3.setImageResource(R.mipmap.tutorial_3);
        iv4.setImageResource(R.mipmap.tutorial_4);

//		iv1.setBackgroundResource(R.drawable.tutorial_1);
//		iv2.setBackgroundResource(R.drawable.tutorial_2);
//		iv3.setBackgroundResource(R.drawable.tutorial_3);
//		iv4.setBackgroundResource(R.drawable.tutorial_4);
        //居中显示，若设置setBackgroundResource（），图片为铺满背景
        iv1.setScaleType(ImageView.ScaleType.CENTER);
        iv2.setScaleType(ImageView.ScaleType.CENTER);
        iv3.setScaleType(ImageView.ScaleType.CENTER);
        iv4.setScaleType(ImageView.ScaleType.CENTER);

        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
        list.add(iv4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guild:
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                GuildActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == list.size() - 1) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == position) {
                indicatorList.get(i).setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                indicatorList.get(i).setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initLayoutIndicator() {
        indicatorList.clear();
        layout_indicator.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 0);
        for (int i = 0; i < list.size(); i++) {
            ImageView iv = new ImageView(context);
            if (i == 0) {
                iv.setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                iv.setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }

            indicatorList.add(iv);
            layout_indicator.addView(iv, params);
        }
    }
}
