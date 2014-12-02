package com.example.eventeverytime.util;

import com.example.eventeverytime.bean.SpinerItemInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
/**
 *
 */
public class Jumper {
/**
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
	 * @param context
	 * @param address
	 */
	public static void email(Context context,String address){
		Intent intent = new Intent(android.content.Intent.ACTION_SEND); 
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{address});
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
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
