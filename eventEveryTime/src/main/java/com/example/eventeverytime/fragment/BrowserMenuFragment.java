package com.example.eventeverytime.fragment;

import com.example.eventeverytime.R;
import com.example.eventeverytime.alarm.MyAlarmManager;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.evernote.MyNote;
import com.example.eventeverytime.evernote.NoteFactory;
import com.example.eventeverytime.factory.DialogFactory;
import com.example.eventeverytime.factory.Refreshable;
import com.example.eventeverytime.util.InfoShare;
import com.example.eventeverytime.util.NoteBuilder;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class BrowserMenuFragment extends Fragment {
	SpinerItemInfo info;
	Refreshable refresh;
	ImageButton edit;
	ImageButton remove;
	ImageButton send;
	ImageButton ifRemind;
	ImageButton share;
	boolean isRemind;
	Event event;
public BrowserMenuFragment(Refreshable refresh,SpinerItemInfo info){
	this.refresh = refresh;
	this.info = info;
}
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		
		Bundle savedInstanceState) {
	Toast.makeText(getActivity(), "create", Toast.LENGTH_LONG);
	View view = inflater.inflate(R.layout.browser_bottom, null);
	edit=(ImageButton) view.findViewById(R.id.bt_edit);
	edit.setOnClickListener(new BottomButtonOnClickListener(getActivity(), info));
	remove=(ImageButton) view.findViewById(R.id.bt_remove);
	remove.setOnClickListener(new BottomButtonOnClickListener(getActivity(), info));
	send=(ImageButton) view.findViewById(R.id.bt_send_to_evernote);
	send.setOnClickListener(new BottomButtonOnClickListener(getActivity(), info));
	share=(ImageButton) view.findViewById(R.id.bt_send_to_share);
	share.setOnClickListener(new BottomButtonOnClickListener(getActivity(), info));
	ifRemind=(ImageButton)view.findViewById(R.id.bt_isreminder);
	if(info.getDataType()==DataType.EVENT){
		ifRemind.setVisibility(ImageButton.VISIBLE);
		event = MyDB.getInstance(getActivity()).getEventById(info.getId());
		Toast.makeText(getActivity(), event.isAlarm()+"", Toast.LENGTH_SHORT).show();
		isRemind = event.isAlarm();
		ifRemind.setBackgroundResource(isRemind?R.drawable.ic_action_reminder_on:R.drawable.ic_action_reminder_off);
	}
	ifRemind.setOnClickListener(new BottomButtonOnClickListener(getActivity(), info));
	return view;
}


class BottomButtonOnClickListener implements View.OnClickListener{
	final Context context;
	final SpinerItemInfo info;
	public BottomButtonOnClickListener(Context context,SpinerItemInfo info){
		this.context = context;
		this.info = info;
	}
	public void switchReminder(){
		isRemind=!isRemind;
		if(isRemind){
			new MyAlarmManager(getActivity()).sendAlarm(info.getId());
		}else{
			new MyAlarmManager(getActivity()).cancelAlarm(info.getId());
		}
		event.setAlarm(!event.isAlarm());
		ifRemind.setBackgroundResource(isRemind?R.drawable.ic_action_reminder_on:R.drawable.ic_action_reminder_off);
		MyDB.getInstance(getActivity()).modifyEvent(event);
	}
	@Override
	public void onClick(View v) {
		Log.i("modify","onclick");
		switch (v.getId()) {
		case R.id.bt_edit:
			new DialogFactory(context).modifyDialog(info,refresh);
			break;
		case R.id.bt_remove:
			new DialogFactory(context).confirmDeleteDialog(info,refresh);
			break;
		case R.id.bt_send_to_evernote:
			new NoteFactory(getActivity(), info).sendNote();
			break;
		case R.id.bt_isreminder:
			switchReminder();
			break;
		case R.id.bt_send_to_share:
			new InfoShare(getActivity(), info).Send();
		
		default:
			break;
		}
	}

}

}
