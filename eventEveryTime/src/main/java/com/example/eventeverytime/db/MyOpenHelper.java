package com.example.eventeverytime.db;


import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gavin on 14-11-15.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    final  static String DB_NAME="EventEveryThing.db";
    final static String CREATE_PERSON="CREATE TABLE Person(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,companyId INTEGER,position VARCHAR,note varchar,phone varchar,email varchar,mobilePhone varchar);";
    final static String CREATE_EVENT= "CREATE TABLE Event(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,projectId INTEGER,note VARCHAR,time VARCHAR,alarm Integer DEFAULT 0);";
    final static String CREATE_PROJECT= "CREATE TABLE Project(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,note VARCHAR);";
    final static String CREATE_COMPANY="CREATE TABLE Company(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,note VARCHAR);";
    final static String CREATE_INDEX= "CREATE TABLE Indexx(id INTEGER PRIMARY KEY AUTOINCREMENT,eventId INTEGER,personId INTEGER);";





    public MyOpenHelper(Context context,int version){
        super(context,DB_NAME,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        db.execSQL(CREATE_COMPANY);
        db.execSQL(CREATE_EVENT);
        db.execSQL(CREATE_INDEX);
        db.execSQL(CREATE_PERSON);
        db.execSQL(CREATE_PROJECT);
    	db.insert("Project", null, new Project(0, "Unfiled", "").getFullContentValues());
    	db.insert("Company", null, new Company(0, "Unfiled", "").getFullContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

