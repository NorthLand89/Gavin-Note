package com.example.eventeverytime.listadapters;

import java.util.ArrayList;

import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.ItemFactory;
import com.example.eventeverytime.listener.MyItemListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CompanyListAdapter extends BaseAdapter {
ArrayList<Company> companies;
Context context;
	public CompanyListAdapter(Context context,ArrayList<Company>companies){
		this.companies = companies;
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return companies.size();
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
		// TODO Auto-generated method stub
		convertView=ItemFactory.getCompanyItemView(context, companies.get(position), convertView);
		convertView.setOnClickListener(new MyItemListener(context, new SpinerItemInfo("", companies.get(position).getId(), DataType.COMPANY)));
		return convertView;
	}

}
