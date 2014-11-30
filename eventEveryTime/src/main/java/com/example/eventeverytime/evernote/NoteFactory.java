package com.example.eventeverytime.evernote;

import java.util.ArrayList;
import java.util.Collections;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evernote.edam.type.Note;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.util.NoteBuilder;
import com.example.eventeverytime.util.TimeUtility;
/**
 * 用于从数据库读取信息并创建发送笔记的类
 * @author 世欣
 *
 */
public class NoteFactory {
	Context context;
	SpinerItemInfo info;
public NoteFactory(Context context,SpinerItemInfo info){
	this.context = context;
	this.info=info;
}
	public Note getNote(){
		switch (info.getDataType()) {
		case PERSON:
			return getPersonNote();
		case PROJECT:
			return getProjectNote();

		case EVENT:
			return getEventNote();

		case COMPANY:
			return getConpanyNote();


		default:
			return new NoteBuilder("数据错误").getNote();
		}
	}
	
	public Note getPersonNote(){
		Person person = MyDB.getInstance(context).getPersonById(info.getId());
		ArrayList<Event> events = MyDB.getInstance(context).getEventsByPersonId(context, person.getId());
		NoteBuilder noteBuilder =new NoteBuilder(person.getName()) ;
		Log.i("nameee",person.getName());
		noteBuilder.addSeparator();
		noteBuilder.addLine(person.getNote());
		noteBuilder.addSeparator();
		for(int i =0;i<events.size();i++){
			Event event = events.get(i);
			ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(info.getId());
			
			noteBuilder.addLine(event.getNote());
			noteBuilder.addLine("时间"+TimeUtility.longToString(event.getTime()));
			noteBuilder.addBlankLine();
			noteBuilder.addLine("参与人:");
			noteBuilder.addSeparator();
			for(int j = 0;j<persons.size();j++){
				noteBuilder.addLine(persons.get(j).getName());
			}
			noteBuilder.addBlankLine();
			noteBuilder.addBlankLine();
		}
		
		return noteBuilder.getNote();
	}
	public Note getProjectNote(){
		Project project = MyDB.getInstance(context).getProjectById(info.getId());
		ArrayList<Event>events = MyDB.getInstance(context).getEventsByProject(project);
		Collections.sort(events);
		NoteBuilder noteBuilder = new NoteBuilder("项目:"+project.getName());
		noteBuilder.addSeparator();
		noteBuilder.addLine(project.getNote());
		noteBuilder.addSeparator();
		noteBuilder.addBlankLine();
		for(int i = 0;i<events.size();i++){
			Event event = events.get(i);
			ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(event.getId());
			
			noteBuilder.addLine(event.getNote());
			noteBuilder.addLine("时间"+TimeUtility.longToString(event.getTime()));
			noteBuilder.addBlankLine();
			noteBuilder.addLine("参与人:");
			noteBuilder.addSeparator();
			for(int j = 0;j<persons.size();j++){
				noteBuilder.addLine(persons.get(j).getName());
			}
			noteBuilder.addBlankLine();
			noteBuilder.addBlankLine();
		}
		
		return noteBuilder.getNote();
	}
	public Note getEventNote(){
		Event event = MyDB.getInstance(context).getEventById(info.getId());
		ArrayList<Person>persons=MyDB.getInstance(context).getPersonsByEventId(info.getId());
		NoteBuilder noteBuilder = new NoteBuilder("事件:"+event.getName());
		noteBuilder.addLine(event.getNote());
		noteBuilder.addSeparator();
		noteBuilder.addBlankLine();
		noteBuilder.addLine("参与人:");
		noteBuilder.addSeparator();
		for(int i = 0;i<persons.size();i++){
			noteBuilder.addLine(persons.get(i).getName());
		}
		return noteBuilder.getNote();
	}
	public Note getConpanyNote(){
		Company company = MyDB.getInstance(context).getCompanyById(info.getId());
		ArrayList<Person>persons = MyDB.getInstance(context).getPersonsByCompanyId(info.getId());
		NoteBuilder noteBuilder = new NoteBuilder("公司:"+company.getName());
		noteBuilder.addSeparator();
		noteBuilder.addLine(company.getName());
		noteBuilder.addSeparator();
		noteBuilder.addLine("相关人物");
		noteBuilder.addBlankLine();
		for(int i=0;i<persons.size();i++){
			noteBuilder.addLine(persons.get(i).getName());
		}
	
		return noteBuilder.getNote();
	}
	public void sendNote(){
		Intent intent = new Intent("note");
		intent.putExtra("note", getNote());
		context.startActivity(intent);
	}
	
}
