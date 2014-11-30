package com.example.eventeverytime.alarm;

import java.util.ArrayList;

import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Index;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.config.Setting;
import com.example.eventeverytime.db.MyDB;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
  
public class SMSBroadcastReceiver extends BroadcastReceiver {  
    @Override  
    public void onReceive(Context context, Intent intent) {
    	Setting setting = Setting.getInstance(context);
    	if(setting.isSaveMessage()){
        Object[] pduses = (Object[])intent.getExtras().get("pdus");  
        for(Object pdus : pduses){  
            byte[] pdusmessage = (byte[]) pdus;
            SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);  
            String mobile = sms.getOriginatingAddress();  
            String content = sms.getMessageBody();
            StringBuilder builder = new StringBuilder(mobile);
            try{
            builder.delete(0, 4);
            }catch(Exception e){
            	
            }
            Log.i(builder.toString(),builder.toString());
            Person person = null;
            MyDB.getInstance(context).load(context);
            ArrayList<Person>persons = MyDB.getInstance(context).getAllPersons();
            Log.i("tag", persons.size()+"");
            for(int i = 0;i<persons.size();i++){
            	Person tempPerson=persons.get(i);
            	if(tempPerson.getMobilePhone().indexOf(builder.toString().trim())>=0){
            		person=tempPerson;
            		break;
            	}
            }
            
            int eventId=MyDB.getInstance(context).insertEvent(new Event(0, "来自"+(person==null?builder.toString():person.getName())+"的信息", 0, content, sms.getTimestampMillis()).getContValues());
           if(person!=null)
        	   MyDB.getInstance(context).insertIndex(new Index(0, eventId, person.getId()).getContValues());
          
        }  
    	}
        setting.backUp();
    }  
  
}  