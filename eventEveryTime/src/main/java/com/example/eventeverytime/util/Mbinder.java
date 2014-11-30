package com.example.eventeverytime.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.SpinerItemInfo;

public class Mbinder {
	/**
	 * 专门用于绑定可跳转对象的监听器绑定工具
	 * @param context
	 * @param target
	 * @param view
	 */
	public static void bindOnClick(Context context,SpinerItemInfo target,View view){
		final	SpinerItemInfo tempTarget=target;
		final Context tempContext=context;
		view.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("browserEvent");
				intent.putExtra("querry", tempTarget);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				tempContext.startActivity(intent);				
			}
		});
	}
}
