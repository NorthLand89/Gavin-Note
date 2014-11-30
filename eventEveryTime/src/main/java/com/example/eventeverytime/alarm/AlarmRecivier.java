package com.example.eventeverytime.alarm;

import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.DialogFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AlarmRecivier extends BroadcastReceiver {
/**
 * 监听AlarmManager闹钟广播
 * 调用#AlarmActivity
 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent newIntent = new Intent(context, AlarmActivity.class);
		//从intent中取出ID放入新Intent中 用于在唤醒时显示事件信息
		newIntent.putExtra("id", intent.getIntExtra("id", -1));
		//设置flag 不然报错
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(newIntent);
	}

	
}
