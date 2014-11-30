package com.example.eventeverytime.evernote;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.client.android.OnClientCallback;
import com.evernote.edam.type.Note;
import com.evernote.thrift.transport.TTransportException;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.define.Final;
import com.example.eventeverytime.util.NoteBuilder;

public class MyNote {
private Note note;
SpinerItemInfo info;
Context context;
public MyNote(SpinerItemInfo info,Context context){
	this.info = info;
	this.context=context;
	note = new Note();
}
public void sendNote(){
	switch (info.getDataType()) {
	case PERSON:
		
		break;
	case PROJECT:
		
		break;
	case EVENT:
		
		break;
	case COMPANY:
		
		break;

	default:
		break;
	}
}

public void sendSimpleNote(){
	NoteBuilder builder = new NoteBuilder("xx");
	builder.addBlankLine();
	builder.addLine("go");
	builder.addBlankLine();
	builder.addSeparator();
	builder.addLine("go");
	note = builder.getNote();
	Intent intent = new Intent("note");
	intent.putExtra("note", note);
	context.startActivity(intent);
}
public void sendPersonNote(OnClientCallback<Note> callback){
	Person person = MyDB.getInstance(context).getPersonById(info.getId());
	note.setTitle("ÈËÎï"+person.getName());
	StringBuilder contentBuilder=new StringBuilder();
	contentBuilder.append("±¸×¢:"+"\n"+person.getNote());
}
public void sendProjectNote(){
	
	
}
public void sendEventNote(){
	
}
public void sendCompanyNote(){
	
}

}
	

