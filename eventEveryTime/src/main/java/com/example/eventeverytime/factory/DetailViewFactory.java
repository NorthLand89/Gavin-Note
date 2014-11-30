package com.example.eventeverytime.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.listadapters.EventListAdapter;
import com.example.eventeverytime.listadapters.PersonListAdapter;
import com.example.eventeverytime.util.Jumper;
import com.example.eventeverytime.util.Mbinder;
import com.example.eventeverytime.util.TimeUtility;
/**
 * 详情界面分为两部分,对象详情和相关对象链表
 * 这是对象详情的viewFactory
 * @author 世欣
 *
 */
public class DetailViewFactory {
ViewGroup viewGroup;
ListView listView;
View view;
SpinerItemInfo info;
Context context;
ArrayList<Event> list;
public DetailViewFactory(SpinerItemInfo info,Context context){
	this.view = View.inflate(context, R.layout.detail_container, null);
	this.viewGroup=(ViewGroup) view.findViewById(R.id.container_detail);
	this.listView=(ListView)view.findViewById(R.id.lv_relevant_event);
	Log.i(viewGroup.toString(),"as");
	this.context=context;
	this.info=info;
}
/**
 * 根据穿入info的DataType判断生成哪种View
 * @return
 */
	public View getView(){
		switch (info.getDataType()) {
		case PERSON:
			return getPersonDetailView();
		case EVENT:
			return getEventDetailView();
		case PROJECT:
			return getProjectDetailView();
		case COMPANY:
			return getCompanyDetailView();

		default:
			break;
		}
		
		return view;
	}
	
	/**
	 * 生成人物详情视图
	 * @return
	 */
	public  View getPersonDetailView(){
		list= MyDB.getInstance(context).getEventsByPersonId(context, info.getId());
		Collections.sort(list);
		Log.i("list",list.size()+"");
		final Person person = MyDB.getInstance(context).getPersonById(info.getId());
		Company company = MyDB.getInstance(context).getCompanyById(person.getCompanyId());
		View child=View.inflate(context, R.layout.detail_person, null);
		
		TextView companyTextView = (TextView) child.findViewById(R.id.tv_detail_person_company);
		companyTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Jumper.jump(context, new SpinerItemInfo("", person.getCompanyId(),DataType.COMPANY));	
			}
		});
		TextView mobilePhoneTextView=(TextView)child.findViewById(R.id.tv_detail_person_cellphone);
		mobilePhoneTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFactory.dialOrMsgdialog(context, person.getMobilePhone());
			}
		});
		TextView  phoneTextView=(TextView)child.findViewById(R.id.tv_detail_person_phone);
		phoneTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Jumper.dial(context, person.getPhone());
				
			}
		});
		TextView emailTextView = (TextView )child.findViewById(R.id.tv_detail_person_email);
			emailTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(person.getEmail()!=null&&person.getEmail().length()>3){
					Jumper.email(context, person.getEmail());
					}
				}
			});
		TextView  positionTextView=(TextView)child.findViewById(R.id.tv_detail_person_position);
		TextView noteTextView = (TextView)child.findViewById(R.id.tv_detail_person_note);
		
		
		
		companyTextView.setText(company.getName());
		TextView name = (TextView)child.findViewById(R.id.tv_detail_person_name);
		name.setText(person.getName());
		phoneTextView.setText(person.getPhone());
		mobilePhoneTextView.setText(person.getMobilePhone());
		emailTextView.setText(person.getEmail());
		positionTextView.setText(person.getPosition());
		noteTextView.setText(person.getNote());
		listView.setAdapter(new EventListAdapter(context, list));
		

		
		
		
		viewGroup.addView(child);
		return view;
	}
	/**
	 * 生成事件详情
	 * @return
	 */
	public View getEventDetailView(){
		View child=View.inflate(context, R.layout.detail_event, null);
		int half;
		//准备数据
		Event event = MyDB.getInstance(context).getEventById(info.getId());
		MyDB myDB = MyDB.getInstance(context);
		myDB.load(context);
		ArrayList<Person>persons = myDB.getPersonsByEventId(info.getId());
		ArrayList<SpinerItemInfo>column1=new ArrayList<SpinerItemInfo>();
		ArrayList<SpinerItemInfo>column2=new ArrayList<SpinerItemInfo>();

		
		half=persons.size()/2;
		
		for(int i=0;i<persons.size();i++){
			if(i>half){
				column2.add(new SpinerItemInfo(persons.get(i).getName(),persons.get(i).getId(),DataType.PERSON));
			}else {
				column1.add(new SpinerItemInfo(persons.get(i).getName(),persons.get(i).getId(),DataType.PERSON));
			}
		}
	//找到视图
		ListView nameListView1=(ListView) child.findViewById(R.id.listView1);
		ListView nameListView2=(ListView) child.findViewById(R.id.listView2);
		TextView eventTextView=(TextView) child.findViewById(R.id.tv_detail_event_name);
		TextView noteTextView= (TextView) child.findViewById(R.id.tv_detail_event_note);
		TextView projectTextView=(TextView) child.findViewById(R.id.tv_detail_event_project);
		projectTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		TextView eventTimeTextView=(TextView) child.findViewById(R.id.tv_detail_events_time);
		ImageView lineImageView =(ImageView)child.findViewById(R.id.ImageView002);
		if(persons.size()==0){
			lineImageView.setVisibility(ImageView.INVISIBLE);
		}
		
	//设置视图
		eventTextView.setText(event.getName());
		final Project project = MyDB.getInstance(context).getProjectById(event.getProjectId());
		projectTextView.setText(project.getName());
	projectTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Jumper.jump(context, new SpinerItemInfo("", project.getId(),DataType.PROJECT));
			}
		});
		nameListView1.setAdapter(new NameListAdapter(context,column1));
		nameListView2.setAdapter(new NameListAdapter(context, column2));
		noteTextView.setText(event.getNote());
		eventTimeTextView.setText(TimeUtility.longToString(event.getTime()));
	
		viewGroup.addView(child);
		
		return view;
	}
	/**
	 * 生成项目详情
	 * @return
	 */
	public View getProjectDetailView(){
		View child=View.inflate(context, R.layout.detail_project, null);
		Project project = MyDB.getInstance(context).getProjectById(info.getId());
		ArrayList<Event>events = MyDB.getInstance(context).getEventsByProject(project);
		viewGroup.addView(child);
		TextView projectNameTextView = (TextView) child.findViewById(R.id.tv_detail_project_name);
		TextView projectNoteTextView= (TextView) child.findViewById(R.id.tv_detail_project_note);
		
		projectNameTextView.setText(project.getName());
		projectNoteTextView.setText(project.getNote());
		listView.setAdapter(new EventListAdapter(context, events));

		
		return view;
	}
	/**
	 * 生成公司详情
	 * @return
	 */
	public View getCompanyDetailView(){
		View child = View.inflate(context, R.layout.detail_project, null);
		Company company = MyDB.getInstance(context).getCompanyById(info.getId());
		TextView companyNameTextView = (TextView) child.findViewById(R.id.tv_detail_project_name);
		TextView companyNoteTextView= (TextView) child.findViewById(R.id.tv_detail_project_note);
		companyNameTextView.setText(company.getName());
		companyNoteTextView.setText(company.getNote());
		viewGroup.addView(child);
		MyDB.getInstance(context).load(context);
		ArrayList<Person>persons = MyDB.getInstance(context).getPersonsByCompanyId(info.getId());
		Toast.makeText(context, persons.size()+"", Toast.LENGTH_LONG).show();
		listView.setAdapter(new PersonListAdapter(context, persons));

		
		return view;
	}
	
	/**
	 * 绑定事件item的监听
	 * @param context
	 * @param target
	 * @param view
	 */
	public void bindOnClick(Context context,SpinerItemInfo target,View view){
		final	SpinerItemInfo tempTarget=target;
		final Context tempContext=context;
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("browserEvent");
				intent.putExtra("querry", tempTarget);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				tempContext.startActivity(intent);				
			}
		});
	}
	
	/**
	 * 事件listView的适配器
	 * @author 世欣
	 *
	 */
	class juniorEventAdapter extends BaseAdapter{
		Context context;
		List<Event> list;
		public juniorEventAdapter(Context context ,List<Event>list){
			this.context=context ;
			this.list=list;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
		
		
	}
	/**
	 * 人名listView的适配器
	 * @author 世欣
	 *
	 */
	class NameListAdapter extends BaseAdapter{
		private Context context;
		private List<SpinerItemInfo> list;
		public NameListAdapter(Context context,List<SpinerItemInfo> list){
			this.context = context;
			this.list = list;
//			Toast.makeText(context,list.size()+"", Toast.LENGTH_SHORT).show();
		}
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=new TextView(context);
				((TextView)convertView).setTextSize(30);
			}
			((TextView)convertView).setText(list.get(position).getName());
			bindOnClick(context,list.get(position) , convertView);
			
			return convertView;
		}
		
	}
	
	/**
	 * 设置人名listView的点击事件
	 * 点击从HashMap中移除
	 * @author 世欣
	 *
	 */
	class NameOnClickListener implements OnClickListener{
		Context context;
		SpinerItemInfo info;
		public NameOnClickListener(Context context,SpinerItemInfo info){
			this.context=context;
			this.info=info;
		}
		@Override
		public void onClick(View v) {
			Intent intent= new Intent("browserEvent");
			intent.putExtra("querry",info);
			context.startActivity(intent);
		}
	}

	
}
