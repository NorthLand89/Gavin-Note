package com.example.eventeverytime.listadapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.example.eventeverytime.bean.SpinerItemInfo;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class MySpinerAdapter extends BaseAdapter {
	Context context;
	List<SpinerItemInfo> list;
	HashMap<Integer, SpinerItemInfo> map;

	public MySpinerAdapter(List<SpinerItemInfo> list, Context context) {
		this.list = list;
		this.context = context;
	}
	
	@Override
	public void notifyDataSetChanged() {
		this.list=new ArrayList<SpinerItemInfo>();
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			this.list.add((SpinerItemInfo) map.get(iterator.next()));
		}
		super.notifyDataSetChanged();
	}
	public MySpinerAdapter(HashMap map,Context context){
		this.map=map;
		this.list = new ArrayList<SpinerItemInfo>();
		
		Iterator<Integer> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			this.list.add((SpinerItemInfo) map.get(iterator.next()));
		}
		this.context=context;
		
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return list.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = new TextView(context);
		}
		((TextView)convertView).setText(list.get(position).getName());
		return convertView;
	}

}
