package com.example.eventeverytime.bean;


import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by gavin on 14-11-15.
 */
public class Company implements ContentValuesGetable,Containable,CursorCreateable{

    private int id;
    private String name;
    private String note;


    public Company(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }
    public Company(String name,String note){
    	this.name = name;
    	this.note = note;
    }

    public Company() {
        super();
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
        ContentValues values = new ContentValues();
//        values.put("id",id);
        values.put("name",name);
        values.put("note",note);
        return values;
    }
    public ContentValues getFullContentValues() {
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("note",note);
        return values;
    }

    @Override
    public Object getObjectByCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setNote(cursor.getString(cursor.getColumnIndex("note")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        return this;
    }
}
