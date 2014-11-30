package com.example.eventeverytime.listener;

import com.example.eventeverytime.bean.SpinerItemInfo;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyItemListener implements OnClickListener {

	Context context;
	SpinerItemInfo info;
	public MyItemListener(Context context,SpinerItemInfo info){
		this.context = context;
		this.info=info;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent("browserEvent");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("querry", info);
		context.startActivity(intent);
	}

}
