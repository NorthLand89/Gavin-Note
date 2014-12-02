package com.example.eventeverytime.listadapters;

import java.util.ArrayList;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.ItemFactory;
import com.example.eventeverytime.listener.MyItemListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ViewSwitcher.ViewFactory;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{
	ArrayList<Person>persons;
	ArrayList<Event>events;
	ArrayList<Project>projects;
	ArrayList<Company>companies;
	Context context;
	
	public MyExpandableListAdapter(ArrayList<Person> persons,
									ArrayList<Event>events,
									ArrayList<Project>projects,
									ArrayList<Company>companies,
									Context context){
		this.persons = persons;
		this.events = events;
		this.projects = projects;
		this.companies=companies;
		this.context = context;
		
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		switch (groupPosition) {
		case 0:
			return persons.size();
		case 1:
			return events.size();
		case 2:
			return projects.size();
		case 3:
			return companies.size();

		default:
			break;
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		switch (groupPosition) {
		case 0:
			return ItemFactory.getGroupTitleView(context, "      "+context.getString(R.string.person), convertView);
		case 1:
			return ItemFactory.getGroupTitleView(context, "      "+context.getString(R.string.event), convertView);
		case 2:
			return ItemFactory.getGroupTitleView(context, "      "+context.getString(R.string.project), convertView);
		case 3:
			return ItemFactory.getGroupTitleView(context, "      "+context.getString(R.string.company), convertView);

		default:
			break;
		}
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		switch (groupPosition) {
		case 0:
			convertView=ItemFactory.getPersonItemView(context, persons.get(childPosition), null);
			convertView.setOnClickListener(new MyItemListener(context, new SpinerItemInfo("", persons.get(childPosition).getId(),DataType.PERSON)));
			return convertView;
		case 1:
			convertView = ItemFactory.getEventItemView(context, events.get(childPosition), null);
			convertView.setOnClickListener(new MyItemListener(context, new SpinerItemInfo("", events.get(childPosition).getId(),DataType.EVENT)));
			return convertView;
		case 2:
			convertView = ItemFactory.getProjectItemView(context, projects.get(childPosition), null);
			convertView.setOnClickListener(new MyItemListener(context, new SpinerItemInfo("", projects.get(childPosition).getId(),DataType.PROJECT)));
			return convertView;
		case 3:
			convertView =  ItemFactory.getCompanyItemView(context, companies.get(childPosition), null);
			convertView.setOnClickListener(new MyItemListener(context, new SpinerItemInfo("", companies.get(childPosition).getId(),DataType.COMPANY)));
			return convertView;

		default:
			break;
		}
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}

