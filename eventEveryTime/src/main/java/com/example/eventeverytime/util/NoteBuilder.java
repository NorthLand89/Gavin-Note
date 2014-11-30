package com.example.eventeverytime.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evernote.client.android.EvernoteUtil;
import com.evernote.edam.type.Note;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.define.Final;

public class NoteBuilder {
	private ArrayList<String>tags=new ArrayList<String>();
private Note note=new Note();
StringBuilder builder=new StringBuilder();
	public NoteBuilder(String title){
		note.setTitle(title);
		Log.i("builerrr",title);
	}
	public void addLine(String line){
		builder.append("<div>"+line+"</div> ");
	}
	public void addBlankLine(){
		builder.append("<div><br /></div> ");
	}
	public void addSeparator(){
		builder.append("<div><hr/></div> ");
	}
	public Note getNote(){
			note.setTagNames(tags);
		note.setContent(EvernoteUtil.NOTE_PREFIX+(builder.toString())+EvernoteUtil.NOTE_SUFFIX);
		return note;
	}
	public void addTag(String tag){
		tags.add(tag);
	}
	
}
