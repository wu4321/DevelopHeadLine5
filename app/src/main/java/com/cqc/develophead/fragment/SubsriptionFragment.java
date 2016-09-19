package com.cqc.develophead.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SubsriptionFragment extends Fragment {

	private Context context;
	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tv = new TextView(context);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(30);
		tv.setText("这是我的订阅");
		return tv;
	}
	
	public void setText(String text){
		tv.setText(text);
	}
}
