package com.example.eventeverytime.alarm;

import com.example.eventeverytime.R;
import com.example.eventeverytime.factory.DialogFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * 被系统监听启动的添加事件Activity
 * @author 世欣
 *
 */
public class AddEventActivity extends Activity {
	String number;
	String note;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	Intent intent = getIntent();
	
	if(Intent.ACTION_SEND.equals(intent.getAction())){
	note = intent.getStringExtra(Intent.EXTRA_TEXT);
	number=intent.getStringExtra(Intent.EXTRA_TITLE);
	}else{
	number = intent.getStringExtra("extra");
	note = intent.getStringExtra("note");
	}
	new DialogFactory(AddEventActivity.this).addEventDialog(number,note);
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	finish();
}
}
