package com.cqc.develophead.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
	private static Toast toast;

	public static void showShortToast(Context context,String text){
		if (toast == null) {
			toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		toast.setText(text);
		toast.show();
	} 
}
