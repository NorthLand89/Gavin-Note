package com.example.eventeverytime.fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.factory.Refreshable;
import com.example.eventeverytime.listadapters.CompanyListAdapter;
import com.example.eventeverytime.listadapters.EventListAdapter;
import com.example.eventeverytime.listadapters.PersonListAdapter;
import com.example.eventeverytime.listadapters.ProjectListAdapter;

public class MainFragment extends Fragment implements ControlFragment ,Refreshable{

	ArrayList<Event> events;
	ArrayList<Person> persons;
	ArrayList<Project> projects;
	ArrayList<Company>companies;
	MyDB myDB;
	BaseAdapter adapter;
	ListView listView;
	DataType type;
	public MainFragment() {
		this(DataType.EVENT);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadData();
		switch (type) {
		case PERSON:
			adapter = new PersonListAdapter(getActivity(), persons);
			
			break;
		case PROJECT:
			adapter = new ProjectListAdapter(getActivity(), projects);
			break;
		case EVENT:
			adapter = new EventListAdapter(getActivity(), events);
			break;
		case COMPANY:
			adapter = new CompanyListAdapter(getActivity(), companies);
		default:
			
			break;
		}
		loadData();
	}

	public MainFragment(DataType type) {
		this.type=type;

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		listView = new ListView(getActivity());
		View v = inflater.inflate(R.layout.list_container,null);
		listView = (ListView) v.findViewById(R.id.lv_main_fragment_container);
		listView .setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
		return v;
	}
	
	
	public void loadData(){
		myDB = MyDB.getInstance(getActivity());
		myDB.load(getActivity());
		projects=myDB.getAllProjects();
		persons = myDB.getAllPersons();
		events = myDB.getAllEvent();
		companies = myDB.getAllCompanies();
		Log.i("companyCount", companies.size()+"--");
	}

	@Override
	public void setListView(DataType type) {
		this.type = type;
		loadData();
		switch (type) {
		case PERSON:
			adapter = new PersonListAdapter(getActivity(), persons);
			listView.setAdapter(adapter);
			break;
		case PROJECT:
			adapter = new ProjectListAdapter(getActivity(), projects);
			listView.setAdapter(adapter);
			break;
		case EVENT:
			adapter = new EventListAdapter(getActivity(), events);
			listView.setAdapter(adapter);
			break;
		case COMPANY:
			adapter = new CompanyListAdapter(getActivity(), companies);
			listView.setAdapter(adapter);
		default:
			break;
		}
		
	}

	@Override
	public void refresh(SpinerItemInfo info) {
		loadData();
		switch (type) {
		case PERSON:
			adapter = new PersonListAdapter(getActivity(), persons);
			listView.setAdapter(adapter);
			break;
		case PROJECT:
			adapter = new ProjectListAdapter(getActivity(), projects);
			listView.setAdapter(adapter);
			break;
		case EVENT:
			adapter = new EventListAdapter(getActivity(), events);
			listView.setAdapter(adapter);
			break;
		case COMPANY:
			adapter = new CompanyListAdapter(getActivity(), companies);
			listView.setAdapter(adapter);
		default:
	}
		listView.setAdapter(adapter);
	}
	
	

}
