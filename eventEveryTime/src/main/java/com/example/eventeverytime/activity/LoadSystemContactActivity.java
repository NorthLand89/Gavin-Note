package com.example.eventeverytime.activity;

import java.util.ArrayList;

import com.example.eventeverytime.R;
import com.example.eventeverytime.R.drawable;
import com.example.eventeverytime.R.id;
import com.example.eventeverytime.R.layout;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.db.SystemContacts;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LoadSystemContactActivity extends Activity {
	ArrayList<ContactItem> contacts = new ArrayList<ContactItem>();
	ListView listView;
	Button selectAll;
	Button selectNone;
	Button add;
	MySelectListAdapter adapter;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.load_contact_dialog);
			init();
			
			
		}
		
		public void init(){
			ArrayList<Person>persons = new SystemContacts(getApplicationContext()).getAllContacts();
			for(int i = 0;i<persons.size();i++){
				contacts.add(new ContactItem(persons.get(i)));
			}
			listView = (ListView) findViewById(R.id.lv_load_contact_select);
			adapter = new MySelectListAdapter(contacts);
			listView.setAdapter(adapter);
			selectAll=(Button)findViewById(R.id.bt_select_all);
			selectNone = (Button)findViewById(R.id.bt_select_none);
			add = (Button)findViewById(R.id.bt_add_contact);
			selectAll.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for(int i = 0;i<contacts.size();i++){
						contacts.get(i).setSelected(true);
						adapter.notifyDataSetChanged();
					}
					
				}
			});
			selectNone.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					for(int i = 0;i<contacts.size();i++){
						contacts.get(i).setSelected(false);
						adapter.notifyDataSetChanged();
					}
					
				}
			});
			add.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MyDB myDB=MyDB.getInstance(getApplicationContext());
					for(int i = 0;i<contacts.size();i++){
						ContactItem item = contacts.get(i);
						if(item.isSelected){
							myDB.insertPerson(item.getPerson().getContValues());
						}
					}
					finish();
					
				}
			});
			
		}
		
		class MySelectListAdapter extends BaseAdapter{
			ArrayList<ContactItem>contacts;
			public MySelectListAdapter(ArrayList<ContactItem>contacts){
				this.contacts= contacts;
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return contacts.size();
			}
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView==null){
					convertView = new TextView(getApplicationContext());
					((TextView)convertView).setTextSize(40);
					((TextView)convertView).setTextColor(Color.BLACK);
				}
				
				final ContactItem contact = contacts.get(position);
				if(contact.isSelected){
					convertView.setBackgroundResource(R.drawable.shadow_blue);
				}else {
					convertView.setBackgroundResource(R.drawable.announce_btn_dark_grey_disabled);
				}
				((TextView)convertView).setText(contact.getPerson().getName());
				convertView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					contact.setSelected(!contact.isSelected);
					notifyDataSetChanged();
					}
				});
				return convertView;
			}
			
		}
		
		class ContactItem{
			Person person;
			boolean isSelected;
			public ContactItem(Person person, boolean isSelected) {
				super();
				this.person = person;
				this.isSelected = isSelected;
			}
			public ContactItem(Person person){
				super();
				this.person = person;
			}
			public Person getPerson() {
				return person;
			}
			public void setPerson(Person person) {
				this.person = person;
			}
			public boolean isSelected() {
				return isSelected;
			}
			public void setSelected(boolean isSelected) {
				this.isSelected = isSelected;
			}
		}

	
}
