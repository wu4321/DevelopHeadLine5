package com.cqc.develophead.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * FragmentPagerAdapter
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;
	private FragmentManager fm;
	private static String[] titles = {"精选","订阅","发现"};
	public HomeFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.fm = fm;
		this.list = list;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	//返回tab的标题
	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}


}
