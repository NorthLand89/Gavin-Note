package com.example.eventeverytime.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Switch;

/**
 * 用于获取，更改及保存设置的类
 * 
 * @author 世欣
 * 
 */
public class Setting {
	static Setting setting = null;

	private static SharedPreferences sharedPreferences;
	//是否开启提醒
	private static boolean remindMe;
	//是否在挂断后弹出添加事件对话框
	private static boolean remindCall;
	//是否自动保存信息
	private static boolean saveMessage;
	//SharedPreferences编辑器
	private static SharedPreferences.Editor editor;
	//私有构造方法
	private Setting(Context context) {
		super();
		sharedPreferences = context.getSharedPreferences("setting",
				Context.MODE_APPEND);
		editor = sharedPreferences.edit();
		remindMe = sharedPreferences.getBoolean("remindMe", false);
		remindCall = sharedPreferences.getBoolean("remindCall", false);
		saveMessage = sharedPreferences.getBoolean("saveMessage", false);
	}
	//获取对象
	public static Setting getInstance(Context context) {
		if (setting == null) {
			setting = new Setting(context);
		}
		return setting;
	}
	//设置提醒
	public boolean switchRemindMe(boolean isRemindMe) {
		remindMe = isRemindMe;
		editor.putBoolean("remindMe", isRemindMe);
		editor.commit();
		return remindMe;
	}
	/**
	 * 设置挂断弹出
	 * @param isRemindCall
	 * @return
	 */
	public boolean switchRemindCall(boolean isRemindCall) {
		remindCall = isRemindCall;
		editor.putBoolean("remindCall", isRemindCall);
		boolean is = editor.commit();
		return remindCall;
	}
	/**
	 * 设置信息自动保存
	 * @param isSaveMessage
	 * @return
	 */
	public boolean switchSaveMessage(boolean isSaveMessage) {
		saveMessage = isSaveMessage;
		editor.putBoolean("saveMessage", isSaveMessage);
		editor.commit();
		return saveMessage;
	}
	/**
	 * 获取是否提醒
	 * @return
	 */
	public static boolean isRemindMe() {
		return remindMe;
	}

	public static void setRemindMe(boolean remindMe) {
		Setting.remindMe = remindMe;
	}

	public static boolean isRemindCall() {
		return remindCall;
	}

	public static void setRemindCall(boolean remindCall) {
		Setting.remindCall = remindCall;
	}

	public static boolean isSaveMessage() {
		return saveMessage;
	}

	public static void setSaveMessage(boolean saveMessage) {
		Setting.saveMessage = saveMessage;
	}

	public void initSwitch(Switch phone, Switch message, Switch remind) {
		phone.setChecked(remindCall);
		message.setChecked(saveMessage);
		remind.setChecked(remindMe);
	}
	/**
	 * 使用后保存
	 */
	public void backUp() {
		editor.putBoolean("saveMessage", saveMessage);
		editor.commit();
		editor.putBoolean("remindMe", remindMe);
		editor.commit();
		editor.putBoolean("remindCall", remindCall);
		editor.commit();
		Log.i("zhixingle", "zhixingle");
	}

}
