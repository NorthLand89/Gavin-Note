package com.example.eventeverytime.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Switch;

/**
 *
 */
public class Setting {
	static Setting setting = null;

	private static SharedPreferences sharedPreferences;
	private static boolean remindMe;
	private static boolean remindCall;
	private static boolean saveMessage;
	private static SharedPreferences.Editor editor;
	private Setting(Context context) {
		super();
		sharedPreferences = context.getSharedPreferences("setting",
				Context.MODE_APPEND);
		editor = sharedPreferences.edit();
		remindMe = sharedPreferences.getBoolean("remindMe", false);
		remindCall = sharedPreferences.getBoolean("remindCall", false);
		saveMessage = sharedPreferences.getBoolean("saveMessage", false);
	}
	public static Setting getInstance(Context context) {
		if (setting == null) {
			setting = new Setting(context);
		}
		return setting;
	}
	public boolean switchRemindMe(boolean isRemindMe) {
		remindMe = isRemindMe;
		editor.putBoolean("remindMe", isRemindMe);
		editor.commit();
		return remindMe;
	}
	/**
	 */
	public boolean switchRemindCall(boolean isRemindCall) {
		remindCall = isRemindCall;
		editor.putBoolean("remindCall", isRemindCall);
		boolean is = editor.commit();
		return remindCall;
	}
	/**
	 */
	public boolean switchSaveMessage(boolean isSaveMessage) {
		saveMessage = isSaveMessage;
		editor.putBoolean("saveMessage", isSaveMessage);
		editor.commit();
		return saveMessage;
	}
	/**
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
	 */
	public void backUp() {
		editor.putBoolean("saveMessage", saveMessage);
		editor.commit();
		editor.putBoolean("remindMe", remindMe);
		editor.commit();
		editor.putBoolean("remindCall", remindCall);
		editor.commit();
	}

}
