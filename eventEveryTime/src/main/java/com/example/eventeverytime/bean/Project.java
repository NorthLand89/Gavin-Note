package com.example.eventeverytime.bean;


import android.content.ContentValues;
import android.database.Cursor;

/**
 * 项目的实体类
 * Created by gavin on 14-11-15.
 */
public class Project implements ContentValuesGetable ,Containable,CursorCreateable{
    private int id;
    private String name;
    private String note;


    public Project(){
        super();
    }

    public Project(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public ContentValues getContValues() {
        ContentValues values=new ContentValues();
//        values.put("id",id);
        values.put("name",name);
        values.put("note",note);
        return values;
    }
    public ContentValues getFullContentValues() {
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("note",note);
        return values;
    }

    @Override
    public Object getObjectByCursor(Cursor cursor) {
        setNote(cursor.getString(cursor.getColumnIndex("note")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        return this;
    }
}
