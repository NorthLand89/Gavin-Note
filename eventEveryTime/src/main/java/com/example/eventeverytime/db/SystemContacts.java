package com.example.eventeverytime.db;

import java.util.ArrayList;

import com.example.eventeverytime.bean.Person;

import android.R.array;
import android.R.integer;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class SystemContacts {
	Context context;
	ContentResolver resolver;

	public SystemContacts(Context context) {
		this.context = context;
		resolver = context.getContentResolver();
	}

	public ArrayList<Person> getAllContacts() {
		ArrayList<Person> persons = new ArrayList<Person>();
		Uri uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {

			Person person = new Person();
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			person.setId(Integer.parseInt(contactId));
			person.setName(name);

			Cursor phoneCursor = resolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			for (int i = 0; i < 2 && phoneCursor.moveToNext(); i++) {
				String number = phoneCursor.getString(phoneCursor
						.getColumnIndex("data1"));
				if(number.indexOf("1")==0){
					person.setMobilePhone(trimNonsence(number));
				}else {
					person.setPhone(trimNonsence(number));
				}
			}
			phoneCursor.close();
			Cursor emails = resolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			if (emails.moveToNext()) {
				person.setEmail(emails.getString(emails.getColumnIndex("data1")));

			}
			persons.add(person);
			emails.close();

		}

//
		cursor.close();
		return persons;

	}
	public String trimNonsence(String number){
		StringBuilder builder= new StringBuilder(number);
		while(builder.indexOf("-")!=-1){
			builder.deleteCharAt(builder.indexOf("-"));
		}
		while(builder.indexOf("(")!=-1){
			builder.deleteCharAt(builder.indexOf("("));
		}
		while(builder.indexOf(")")!=-1){
			builder.deleteCharAt(builder.indexOf(")"));
		}
		while(builder.indexOf(" ")!=-1){
			builder.deleteCharAt(builder.indexOf(" "));
		}
		return builder.toString();
	}

}
