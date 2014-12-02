package com.example.eventeverytime.alarm;

import java.util.ArrayList;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.util.Jumper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 *
 */
public class AlarmActivity extends Activity{
	int id;
	Event event;
	ArrayList<Person> persons;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	id = getIntent().getIntExtra("id", -1);
	MyDB.getInstance(AlarmActivity.this).load(AlarmActivity.this);
	event = MyDB.getInstance(getApplicationContext()).getEventById(id);
	persons = MyDB.getInstance(getApplicationContext()).getPersonsByEventId(id);
	Log.i("xinxi",event.getName());
	showDialog();
	new MyAlarmManager(AlarmActivity.this).resetDBAlarm(id);
//	new MyAlarmManager(getApplicationContext()).cancelAlarm(id).resetDBAlarm(id);
}
/**
 */
public void showDialog(){
	AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
	builder.setTitle(getString(R.string.eventremind));
	View view = View.inflate(getApplicationContext(), R.layout.alarm_dialog, null);
	TextView title = (TextView) view.findViewById(R.id.tv_alarm_title);
	TextView noteTextView = (TextView) view.findViewById(R.id.tv_alarm_note);
	ListView personsListView=(ListView) view.findViewById(R.id.lv_alarm);
	title.setText(event.getName());
	title.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Jumper().jump(getApplicationContext(), new SpinerItemInfo("",event.getId(),DataType.EVENT ));
		}
	});
	noteTextView.setText(event.getNote());
	noteTextView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Jumper().jump(getApplicationContext(), new SpinerItemInfo("", event.getId(),DataType.EVENT));
		}
	});
	personsListView.setAdapter(new MyAlertListViewAdapter(getApplicationContext(), persons));
	builder.setView(view);
	builder.setPositiveButton(getString(R.string.doing), new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
			
		}
	});
	builder.setNegativeButton(getString(R.string.cancelEvent), new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			MyDB.getInstance(getApplicationContext()).removeEventById(id);
			finish();
			
		}
	});
	builder.create().show();
	
	 Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
     Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);  
     r.play();  
     new MyAlarmManager(AlarmActivity.this).resetDBAlarm(id);
	
}
class MyAlertListViewAdapter extends BaseAdapter{
	ArrayList<Person>persons;
	Context context;
	public MyAlertListViewAdapter(Context context,ArrayList<Person>persons){
		this.persons = persons;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = new TextView(context);
		}
		final Person person = persons.get(position);
		((TextView)convertView).setText(person.getName());
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent("browserEvent");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("querry",new SpinerItemInfo("",person.getId(),DataType.PERSON) );
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
		return convertView;
	}
}

}
