package com.example.eventeverytime.util;

import com.example.eventeverytime.bean.SpinerItemInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
/**
 * 用于提供界面跳转的工具类
 * @author 世欣
 *
 */
public class Jumper {
/**
 * 跳转到拨号
 * @param context
 * @param address
 */
	public static void dial(Context context,String address){
        Uri uri=Uri.parse("tel:"+address.trim());  
        Intent intent=new Intent();  
        intent.setAction(Intent.ACTION_CALL);  
        intent.setData(uri);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
	}
	/**
	 * 跳转到短信
	 * @param context
	 * @param address
	 */
	public static void message(Context context,String address){
		Intent intent = new Intent(Intent.ACTION_VIEW);   
        intent.putExtra("address",address.trim());   
        intent.putExtra("sms_body", "");   
        intent.setType("vnd.android-dir/mms-sms");   
        context.startActivity(intent); 
	}
	/**
	 * 跳转到发送邮件
	 * @param context
	 * @param address
	 */
	public static void email(Context context,String address){
		Intent intent = new Intent(android.content.Intent.ACTION_SEND); 
        // 文本格式 
        intent.setType("text/plain"); 
        // 对方邮件地址 
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{address}); 
        // 标题内容 
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, ""); 
        // 邮件文本内容 
        intent .putExtra(android.content.Intent.EXTRA_TEXT,""); 
		context.startActivity(Intent.createChooser(intent,"Choose Email Client")); 


	}
	public static void jump(Context context,SpinerItemInfo info){
		Intent intent = new Intent("browserEvent");
		intent.putExtra("querry", info);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
