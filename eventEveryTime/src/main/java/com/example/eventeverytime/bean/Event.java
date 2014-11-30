package com.example.eventeverytime.bean;


import android.content.ContentValues;
import android.database.Cursor;

/**
 * 用于存储事件的类
 * Created by 世欣 on 14-11-15.
 */
public class Event implements ContentValuesGetable,Containable,CursorCreateable,Comparable<Event>{
    private int id;
    private String name;
    private int projectId;
    private String note;
    private Long time;
    private boolean isAlarm;

    public Event(){
        super();
    }
    
 
	public Event(int id, String name, int projectId, String note, Long time,
			boolean isAlarm) {
		super();
		this.id = id;
		this.name = name;
		this.projectId = projectId;
		this.note = note;
		this.time = time;
		this.isAlarm = isAlarm;
	}


	public Event(int id, String name, int projectId, String note, Long time) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
        this.note = note;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public ContentValues getContValues() {
        ContentValues values = new ContentValues();
//        values.put("id",id);
        values.put("name",name);
        values.put("projectId",projectId);
        values.put("note",note);
        values.put("time",time+"");
        if(isAlarm){
      	  values.put("alarm", Integer.valueOf(1));
        }
        return values;
    }
    public ContentValues getFullContentValues(){
        ContentValues values = new ContentValues();
      values.put("id",id);
      values.put("name",name);
      values.put("projectId",projectId);
      values.put("note",note);
      values.put("time",time+"");
      
      if(isAlarm){
    	  values.put("alarm", Integer.valueOf(1));
      }
      return values;
    }

    public boolean isAlarm() {
		return isAlarm;
	}
	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}
	@Override
    public Object getObjectByCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setNote(cursor.getString(cursor.getColumnIndex("note")));
        setProjectId(cursor.getInt(cursor.getColumnIndex("projectId")));
        setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex("time"))));
        isAlarm=(cursor.getInt(cursor.getColumnIndex("alarm"))==1);
        if(time<System.currentTimeMillis()){
        	isAlarm=false;
        }
        
        return this;
    }


	@Override
	public int compareTo(Event another) {
		if(another.getTime()>this.time){
			return 1;
		}
		else {
			return -1;
		}
	}
}
