package com.example.eventeverytime.listadapters;

import java.util.ArrayList;

import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.factory.ItemFactory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 人物列表适配器
 * @author 世欣
 *
 */
public class PersonListAdapter extends BaseAdapter {
	Context context;
	ArrayList<Person> persons;
	public PersonListAdapter(Context context,ArrayList<Person> persons){
		this.context=context;
		this.persons = persons;
	}
	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=ItemFactory.getPersonItemView(context, persons.get(position),convertView);
		return convertView;
	}

}
