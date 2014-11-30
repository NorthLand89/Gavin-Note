package com.example.eventeverytime.bean;


import android.content.ContentValues;
import android.database.Cursor;

/**
 * 用于存储人物的类
 * Created by gavin on 14-11-15.
 */
public class Person implements ContentValuesGetable,Containable,CursorCreateable{
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + companyId;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (companyId != other.companyId)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", companyId="
				+ companyId + ", position=" + position + ", note=" + note
				+ ", email=" + email + ", phone=" + phone + ", mobilePhone="
				+ mobilePhone + "]";
	}

	private int id;
    private String name;
    private int companyId;
    private String position;
    private String note;
    private String email;
    private String phone;
    private String mobilePhone;

    public Person(){
        super();
    }

    public Person(int id, String name, int companyId, String position, String note) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.position = position;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Override
    public ContentValues getContValues() {
        ContentValues values=new ContentValues();
//        values.put("id",id);
        values.put("name",name);
        values.put("companyId",companyId);
        values.put("position",position);
        values.put("note",note);
        values.put("email", email);
        values.put("phone", phone);
        values.put("mobilePhone", mobilePhone);
        return values;
    }
    public ContentValues getFullContentValues() {
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("name",name);
        values.put("companyId",companyId);
        values.put("position",position);
        values.put("note",note);
        values.put("email", email);
        values.put("phone", phone);
        values.put("mobilePhone", mobilePhone);
        return values;
    }

    public Person(String name, int companyId, String position, String note,
			String email, String phone, String mobilePhone) {
		super();
		this.name = name;
		this.companyId = companyId;
		this.position = position;
		this.note = note;
		this.email = email;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
	}

	@Override
    public Object getObjectByCursor(Cursor cursor) {
        setId(cursor.getInt(cursor.getColumnIndex("id")));
        setName(cursor.getString(cursor.getColumnIndex("name")));
        setNote(cursor.getString(cursor.getColumnIndex("note")));
        setPosition(cursor.getString(cursor.getColumnIndex("position")));
        setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        setEmail(cursor.getString(cursor.getColumnIndex("email")));
        setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        setMobilePhone(cursor.getString(cursor.getColumnIndex("mobilePhone")));
        return this;
    }
}

