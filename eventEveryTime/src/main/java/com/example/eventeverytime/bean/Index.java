package com.example.eventeverytime.bean;


import android.content.ContentValues;
import android.database.Cursor;

/**用于存储索引的类
 * Created by gavin on 14-11-15.
 */
public class Index implements ContentValuesGetable,Containable,CursorCreateable{
    private  int id;
    private int eventId;
    private int personId;


    public Index() {
        super();
    }

    public Index(int id, int eventId, int personId) {
        this.id = id;
        this.eventId = eventId;
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public ContentValues getContValues() {
        ContentValues values = new ContentValues();
//        values.put("id", id);
        values.put("eventId", eventId);
        values.put("personId", personId);
        return values;
    }

    @Override
    public Object getObjectByCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setEventId(cursor.getInt(cursor.getColumnIndex("eventId")));
        setPersonId(cursor.getInt(cursor.getColumnIndex("personId")));
        return this;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}

