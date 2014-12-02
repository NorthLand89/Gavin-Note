package com.example.eventeverytime.factory;

import java.util.ArrayList;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.util.TimeUtility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ItemFactory {
	
	private static TextView personName;
	private static TextView personPosition;
	private static TextView personCompany;
	
	private static TextView projectItemNameTextView;
	
	private static TextView eventItemNameTextView;
	private static TextView firstGuyTextView;
	private static TextView secondGuyTextView;
	private static TextView thirdGuyTextView;
	private static TextView forthGuyTextView;
	private static TextView eventItemTimeTextView;
	private static TextView eventItemProjectTextView;
	
	private static TextView companyNameTextView;
	

	public static View getPersonItemView(final Context context,Person person,View convertView){
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_person, null);
		Log.i("one", "one");
		personName=(TextView) convertView.findViewById(R.id.tv_item_person_name);
		personCompany=(TextView)convertView.findViewById(R.id.tv_item_person_company);
		personPosition=(TextView) convertView.findViewById(R.id.tv_item_person_position);
		}
		Log.i("tow",person.getName());
		MyDB.getInstance(context).load(context);
		Company company = MyDB.getInstance(context).getCompanyById(person.getCompanyId());
		
		personName.setText(person.getName());
//		personName.setBackgroundResource(R.drawable.torquoise_banner);
		Log.i("aaa",person.toString());
		personCompany.setText(company.getName());
		personPosition.setText(person.getPosition());
		SpinerItemInfo spinerItemInfo=new SpinerItemInfo(null, person.getId(), DataType.PERSON);
		final Intent intent = new Intent("browserEvent");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("querry",spinerItemInfo );
		convertView.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(intent);
			}
		});
		
		return convertView;
	}
	
	
	public static View getProjectItemView(Context context,Project project,View convertView){
		final Context tempContext=context;
		if(convertView==null){
			convertView = View.inflate(tempContext, R.layout.item_project, null);
		 projectItemNameTextView = (TextView) convertView.findViewById(R.id.tv_item_project_name);
	
		}
		projectItemNameTextView.setText(project.getName());
		

		convertView.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
		
		return convertView;
	}
	
	public static View getEventItemView(Context context,Event event,View convertView){
		final ArrayList<Person> list = MyDB.getInstance(context).getPersonsByEventId(event.getId());
	
		Log.i("lennnngth",event.getProjectId()+"");
		final Project project = MyDB.getInstance(context).getProjectById(event.getProjectId());
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_event, null);
			firstGuyTextView=(TextView) convertView.findViewById(R.id.tv_item_event_first);
			secondGuyTextView=(TextView) convertView.findViewById(R.id.tv_item_event_second);
			thirdGuyTextView=(TextView) convertView.findViewById(R.id.tv_item_event_third);
			forthGuyTextView=(TextView) convertView.findViewById(R.id.tv_item_event_forth);
			eventItemTimeTextView=(TextView)convertView.findViewById(R.id.tv_item_event_time);
			eventItemNameTextView=(TextView)convertView.findViewById(R.id.tv_item_event_name);
			eventItemProjectTextView=(TextView)convertView.findViewById(R.id.tv_item_event_project);
		}
		eventItemProjectTextView.setText(project.getName());
		eventItemNameTextView.setText(event.getName());
		eventItemTimeTextView.setText(TimeUtility.LongStringToDateString(event.getTime()+""));

        firstGuyTextView.setText("");
        secondGuyTextView.setText("");
        thirdGuyTextView.setText("");
        forthGuyTextView.setText("");
		if(list.size()>0){
			firstGuyTextView.setText(list.get(0).getName()+"  ");
		}
		if(list.size()>1){
			secondGuyTextView.setText(list.get(1).getName()+"  ");
		}
		if(list.size()>2){
			thirdGuyTextView.setText(list.get(2).getName()+"  ");
		}
		if(list.size()>3){
			forthGuyTextView.setText("...");
		}
		
		

		convertView.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
		
	
	
		return convertView;
	}
	public static View getCompanyItemView(Context context,Company company,View convertView){
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_company, null);
			companyNameTextView =(TextView) convertView.findViewById(R.id.tv_item_company_name);
		}
		companyNameTextView.setText(company.getName());

		convertView.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
		
		
		return convertView;
	}
	public static View getGroupTitleView(Context context,String title,View convertView){
		TextView groupTitle=new TextView(context);
		groupTitle.setText(title);
		groupTitle.setTextSize(40);
		groupTitle.setTextColor(Color.BLACK);
		return groupTitle;
	}
}
