package com.example.eventeverytime.util;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InfoShare {
	Context context;
	SpinerItemInfo info;
	String title;
	String content;
	ShareBuilder shareBuiler = new ShareBuilder();
public InfoShare(Context context,SpinerItemInfo info){
	this.context = context;
	this.info = info;
}
public void Send(){
	switch (info.getDataType()) {
	case PERSON:
		title=handlePersonInfo();
		break;
	case PROJECT:
		title=handleProjectInfo();
		break;
	case EVENT:
		title=handleEventInfo();
		break;
	case COMPANY:
		title=handleCompanyInfo();
		break;

	default:
		break;
	}
	
	share();
}
public String handlePersonInfo(){
	Person person = MyDB.getInstance(context).getPersonById(info.getId());
	ArrayList<Event> events = MyDB.getInstance(context).getEventsByPersonId(context, person.getId());
	shareBuiler.addLine(person.getName());
	shareBuiler.addSeparator();
	shareBuiler.addLine(person.getNote());
	if(person.getMobilePhone()!=null){
	shareBuiler.addLine(context.getString(R.string.cellphone)+":  "+person.getMobilePhone());
	}
	if(person.getPhone()!=null){
	shareBuiler.addLine(context.getString(R.string.phone)+":  "+person.getPhone());
	}
	if(person.getEmail()!=null){
	shareBuiler.addLine(context.getString(R.string.email)+":"+person.getEmail());
	}
	shareBuiler.addSeparator();
	for(int i =0;i<events.size();i++){
		Event event = events.get(i);
		ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(info.getId());
		shareBuiler.addLine(context.getString(R.string.event)+":"+event.getName());
		shareBuiler.addLine(event.getNote());
		shareBuiler.addLine(context.getString(R.string.time)+TimeUtility.longToString(event.getTime()));
		shareBuiler.addBlankLine();
		shareBuiler.addLine(context.getString(R.string.participater)+":");
		shareBuiler.addSeparator();
		for(int j = 0;j<persons.size();j++){
			shareBuiler.addLine(persons.get(j).getName());
		}
		shareBuiler.addBlankLine();
		shareBuiler.addBlankLine();
	}
	content = shareBuiler.toString();
	return person.getName();
}
public String handleProjectInfo(){
	Project project = MyDB.getInstance(context).getProjectById(info.getId());
	ArrayList<Event>events = MyDB.getInstance(context).getEventsByProject(project);
	Collections.sort(events);
	shareBuiler.addLine(context.getString(R.string.project)+":"+project.getName());
	shareBuiler.addSeparator();
	shareBuiler.addLine(context.getString(R.string.note)+":"+project.getNote());
	shareBuiler.addSeparator();
	shareBuiler.addBlankLine();
	for(int i = 0;i<events.size();i++){
		Event event = events.get(i);
		ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(event.getId());
		shareBuiler.addLine(event.getName());
		shareBuiler.addLine(event.getNote());
		shareBuiler.addLine(context.getString(R.string.time)+":"+TimeUtility.longToString(event.getTime()));
		shareBuiler.addBlankLine();
		shareBuiler.addLine(context.getString(R.string.participater)+":");
		shareBuiler.addSeparator();
		for(int j = 0;j<persons.size();j++){
			shareBuiler.addLine(persons.get(j).getName());
		}
		shareBuiler.addBlankLine();
		shareBuiler.addBlankLine();
	}
	content = shareBuiler.toString();
	return project.getName();
}
public String handleEventInfo(){
	Event event = MyDB.getInstance(context).getEventById(info.getId());
	ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(info.getId());
	shareBuiler.addLine(event.getName());
	shareBuiler.addLine(event.getNote());
	shareBuiler.addSeparator();
	shareBuiler.addBlankLine();
	shareBuiler.addLine(context.getString(R.string.participater)+":");
	shareBuiler.addSeparator();
	for(int i = 0;i<persons.size();i++){
		shareBuiler.addLine(persons.get(i).getName());
	}
	content = shareBuiler.toString();
	return event.getName();
}
public String handleCompanyInfo(){
	Company company = MyDB.getInstance(context).getCompanyById(info.getId());
	ArrayList<Person>persons = MyDB.getInstance(context).getPersonsByCompanyId(info.getId());
	shareBuiler.addSeparator();
	shareBuiler.addLine(company.getName());
	shareBuiler.addSeparator();
	shareBuiler.addLine(context.getString(R.string.participater));
	shareBuiler.addBlankLine();
	for(int i=0;i<persons.size();i++){
		shareBuiler.addLine(persons.get(i).getName());
	}
	content = shareBuiler.toString();
	return company.getName();
}

private void share(){
	Intent intent = new Intent(Intent.ACTION_SEND);
	intent.putExtra(Intent.EXTRA_TEXT, content);
	intent.putExtra(Intent.EXTRA_TITLE,title);
	
//	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.setType("text/plain"); 
	context.startActivity(intent);
}


}
