package com.example.eventeverytime.alarm;

import java.security.Provider;

import com.example.eventeverytime.config.Setting;
import com.example.eventeverytime.factory.DialogFactory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;
/**
 * 电话的广播监听
 * 用于触发#AddEventActivity
 * @author 世欣
 *
 */
public class PhoneStateReceiver extends BroadcastReceiver {
	public final static String B_PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String number = intent.getExtras().getString("incoming_number");
		TelephonyManager tm=(TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		int statu=tm.getCallState();
		Setting setting=Setting.getInstance(context);
		//判断配置文件 如果未开启电话提醒则返回
		if(!setting.isRemindCall()){
			setting.backUp();
			return;
		}
		
		if(number==null&&statu==0){
			//呼出电话挂断时
			Intent newIntent= new Intent(context,AddEventActivity.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			newIntent.putExtra("number", "");
			newIntent.putExtra("note", "");
			context.startActivity(newIntent);
			
		}else if(statu ==0){
			//呼入电话挂断时
		StringBuilder builder = new StringBuilder(number);
		if(builder.length()>4){
		builder.delete(0, 3);
		}
			Intent newIntent= new Intent(context,AddEventActivity.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			newIntent.putExtra("number", builder.toString().trim());
			newIntent.putExtra("note", "");
			context.startActivity(newIntent);
		}else {
			
		}
		//备份配置
		setting.backUp();
	}
}
