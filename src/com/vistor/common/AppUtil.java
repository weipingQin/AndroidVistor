package com.vistor.common;

import android.content.Context;
import android.widget.Toast;

public class AppUtil {
	
	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0 || str.trim().equals("null");
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static void showShortMessage(Context mContext, CharSequence text) {
		if (text != null && text.length() > 0) {
			Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void sleep(long time){
		try {
			Thread.currentThread();
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
