package com.cqc.develophead.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cqc.develophead.R;
import com.cqc.develophead.adapter.HomeFragmentPagerAdapter;
import com.cqc.develophead.fragment.home.Frag1;
import com.cqc.develophead.fragment.home.Frag2;
import com.cqc.develophead.fragment.home.Frag3;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;
    public TextView tv;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private HomeFragmentPagerAdapter adapter;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);

        initData();
        adapter = new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
//        viewPager.setOffscreenPageLimit(3);//左右两边各3页
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void initData() {
        list.clear();
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();

        list.add(frag1);
        list.add(frag2);
        list.add(frag3);
    }
}
