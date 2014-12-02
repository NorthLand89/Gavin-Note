package com.example.eventeverytime.listadapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.ItemFactory;
public class EventListAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<Event> events;
	
	public EventListAdapter(Context context,ArrayList<Event> events){
		Collections.sort(events);
		this.context=context;
		this.events=events;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=ItemFactory.getEventItemView(context, events.get(position), convertView);
		convertView.setOnClickListener(new ItemOnClickListener(context, events.get(position)));
		if(events.get(position).isAlarm()){
			convertView.setBackgroundResource(R.drawable.blue_shadow);
		}
		return convertView;
	}
		
	
	

	class ItemOnClickListener implements OnClickListener{
		Event event;
		Context context;
		public ItemOnClickListener (Context context,Event event){
			this.context = context;
			this.event = event;
		}
		@Override
		public void onClick(View v) {
			SpinerItemInfo info= new SpinerItemInfo(event.getName(), event.getId(), DataType.EVENT);
			Log.i("intent",event.getId()+"");
			Intent intent = new Intent("browserEvent");
			intent.putExtra("querry", info);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			
		}
	}
	
}
