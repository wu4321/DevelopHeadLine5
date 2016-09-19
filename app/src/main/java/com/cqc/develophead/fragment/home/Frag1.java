package com.cqc.develophead.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.cqc.develophead.R;
import com.cqc.develophead.adapter.GuildPagerAdapter;
import com.cqc.develophead.adapter.HomeFragmentPagerAdapter;
import com.cqc.develophead.adapter.MyRecyclerAdapter;
import com.cqc.develophead.bean.Frag1Bean;
import com.cqc.develophead.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${cqc} on 2016/9/13.
 */
public class Frag1 extends Fragment {

    public List<Frag1Bean> list = new ArrayList<Frag1Bean>();
    public List<String> titleList = new ArrayList<String>();
    public List<ImageView> dotsList = new ArrayList<ImageView>();
    public List<ImageView> imagesList = new ArrayList<ImageView>();
    private RecyclerView recyclerView;
    private Context context;

    private View headView;
    private ViewPager viewPager;
    private TextView tv_desc;
    private TextView tv_ratio;
    private LinearLayout ll_dots;
    private MyRecyclerAdapter adapter;
    private View view;
    private PtrClassicFrameLayout ptrFlameLayout;
    private RecyclerAdapterWithHF mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);
        findViews();
        initData();
        initViews();
        return view;
    }

    private void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        adapter = new MyRecyclerAdapter(context, list);
        mAdapter = new RecyclerAdapterWithHF((RecyclerView.Adapter)adapter);
        mAdapter.addCarousel(initHeadView());
        recyclerView.setAdapter(mAdapter);

        ptrFlameLayout.setPtrHandler(ptrDefaultHandler);
        ptrFlameLayout.setOnLoadMoreListener(onLoadMoreListener);
        ptrFlameLayout.setLoadMoreEnable(true);
        //先setAdapter，才添加headView，因为添加headView调用了方法，notifyItemInserted(0);这个需要在setadapter后调用
//        initHeadView();
    }

    //上拉加载更多
    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore() {
            mHandler.sendEmptyMessageDelayed(0,3000);
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Frag1Bean bean = new Frag1Bean();
            bean.id = 100;
            bean.title = "新增数据";
            list.add(bean);
            mAdapter.notifyDataSetChanged();
            ptrFlameLayout.loadMoreComplete(true);
        }
    };
    //下拉刷新
    private PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            initData();//初始化数据
            mAdapter.notifyDataSetChanged();//更新ui
            ptrFlameLayout.refreshComplete();//刷新完成
        }
    };

    private void findViews() {
        ptrFlameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrFlameLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private View initHeadView() {
        initTitleList();
        initImagesList();

        headView = View.inflate(context, R.layout.layout_banner, null);
        viewPager = (ViewPager) headView.findViewById(R.id.viewpager_banner);
        tv_desc = (TextView) headView.findViewById(R.id.tv_desc);
        tv_ratio = (TextView) headView.findViewById(R.id.tv_ratio);
        ll_dots = (LinearLayout) headView.findViewById(R.id.ll_dots);
        initDots();

        viewPager.setCurrentItem(0);
        GuildPagerAdapter pagerAdapter = new GuildPagerAdapter(context, imagesList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tv_desc.setText(titleList.get(position));
                tv_ratio.setText(1 + position + "/" + titleList.size());
                for (int i = 0; i < dotsList.size(); i++) {
                    if (i == position) {
                        dotsList.get(i).setBackgroundResource(
                                R.mipmap.page_indicator_focused);
                    } else {
                        dotsList.get(i).setBackgroundResource(
                                R.mipmap.page_indicator_unfocused);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //添加headView
//        adapter.setHeadView(headView);
        return headView;
    }



    private void initImagesList() {
        imagesList.clear();

        ImageView iv1 = new ImageView(context);
        ImageView iv2 = new ImageView(context);
        ImageView iv3 = new ImageView(context);

        iv1.setBackgroundResource(R.mipmap.icon_selected_carousel_one);
        iv2.setBackgroundResource(R.mipmap.icon_selected_carousel_two);
        iv3.setBackgroundResource(R.mipmap.icon_selected_carousel_three);

        imagesList.add(iv1);
        imagesList.add(iv2);
        imagesList.add(iv3);
    }

    private void initTitleList() {
        titleList.clear();
        titleList.add("这是第一张图的标题");
        titleList.add("这是第二张图的标题");
        titleList.add("这是第三张图的标题");
    }

    private void initData() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            Frag1Bean bean = new Frag1Bean();
            bean.id = i;
            bean.title = "Android开发666";
            bean.likeNumbers = i;
            bean.commentsNumbers = i;
            list.add(bean);
        }
    }

    private void initDots() {
        ll_dots.removeAllViews();
        dotsList.clear();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        for (int i = 0; i < titleList.size(); i++) {
            ImageView iv = new ImageView(context);
            if (i == 0) {
                iv.setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                iv.setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }

            ll_dots.addView(iv, params);
            dotsList.add(iv);
        }
    }

    private Handler handler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % imagesList.size());
                handler.sendEmptyMessageDelayed(0, 2000);
                Log.d("tag","sendEmptyMessageDelayed");
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    //移除消息，防止内存泄漏，但由于viewpager的缓存，导致只有缓存页面数量之后才可以 没有内存泄漏
    @Override
    public void onPause() {
        handler.removeMessages(0);
        super.onPause();
    }
}
