package com.example.eventeverytime.listadapters;

import java.util.ArrayList;

import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.ItemFactory;
import com.example.eventeverytime.util.Mbinder;

import android.content.Context;
import android.os.Binder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 用于显示项目列表的适配器
 * @author 世欣
 *
 */
public class ProjectListAdapter extends BaseAdapter {
ArrayList<Project>projects;
Context context;
	public ProjectListAdapter(Context context ,ArrayList<Project>projects){
		this.projects=projects;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return projects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return projects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//从item工厂中获取convertView;
		convertView=ItemFactory.getProjectItemView(context, projects.get(position),convertView);
		//绑定监听
		Mbinder.bindOnClick(context,
							new SpinerItemInfo(projects.get(position).getName(),
												projects.get(position).getId(), 
												DataType.PROJECT) , 
												convertView);
		return convertView;
	}

}
