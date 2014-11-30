package com.example.eventeverytime.alarm;

import java.util.Date;

import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.db.MyDB;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ���ڹ������ӵ��� ������� ɾ������
 * 
 * @author ����
 * 
 */
public class MyAlarmManager {
	String ACTION_RECEIVE_MESSAGE;
	Context context;

	public MyAlarmManager(Context context) {
		this.context = context;
	}
/**
 * ���¼�ID��Ϊ�����������
 * @param id
 */
	public void sendAlarm(int id) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Event event = MyDB.getInstance(context).getEventById(id);
		long time = event.getTime();
		Intent intent = new Intent(context, AlarmRecivier.class);
		intent.putExtra("id", id);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
/**
 * ȡ��id�¼�������
 * @param id
 */
	public MyAlarmManager cancelAlarm(int id) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmRecivier.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, 0);
		am.cancel(pi);
		return this;
	}
	public void resetDBAlarm(int id){
		Event event = MyDB.getInstance(context).getEventById(id);
		event.setAlarm(false);
		MyDB.getInstance(context).modifyEvent(event);
	}
}
