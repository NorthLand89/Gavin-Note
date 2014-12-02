package com.example.eventeverytime.activity;

import java.util.ArrayList;
import java.util.List;


import com.example.eventeverytime.R;
import com.example.eventeverytime.R.id;
import com.example.eventeverytime.R.layout;
import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.listadapters.*;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class SearchActivity extends Activity {

	ArrayList<Person>persons = new ArrayList<Person>();
	ArrayList<Integer>personsId=new ArrayList<Integer>();
	ArrayList<Event>events = new ArrayList<Event>();
	ArrayList<Project>projects = new ArrayList<Project>();
	ArrayList<Company>companies = new ArrayList<Company>();
String keyWord;
	MyDB myDB;
	ListView personListView;
	ListView eventListView;
	ListView projectlListView;
	ListView companyListView;
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_list);
		keyWord = getIntent().getStringExtra("keyWord");
		getActionBar().setTitle(keyWord);
		init();
		myDB = MyDB.getInstance(getApplicationContext());
		searchPerson();
		searchEvent();
		searchCompany();
		searchProject();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.expand_list);
//		setAdapter();
		listView.setAdapter(new MyExpandableListAdapter(persons, events, projects, companies, getApplicationContext()));
		
	}

	public void init(){
		personListView=(ListView) findViewById(R.id.lv_search_result_person);
		eventListView = (ListView) findViewById(R.id.lv_search_result_event);
		projectlListView = (ListView) findViewById(R.id.lv_search_result_project);
		companyListView=(ListView) findViewById(R.id.lv_search_result_company);
	}
	public void searchPerson(){
		List <Person>allPerson=myDB.getAllPersons();
		for(int i=0;i<allPerson.size();i++){
			Person person = allPerson.get(i);
			if(person.getName().indexOf(keyWord)>=0||(person.getNote()!=null&&person.getNote().indexOf(keyWord)>=0)){
				
				persons.add(person);
				personsId.add(person.getId());
				ArrayList<Integer>eventsId=myDB.getEventIdsByPersonId(person.getId());
				for(int j=0;j<eventsId.size();j++){
					events.add(myDB.getEventById(eventsId.get(j)));
				}
			}
		}
	}
	public void searchEvent(){
		List <Event>allEvents = MyDB.getInstance(getApplicationContext()).getAllEvent();
		for(int i=0;i<allEvents.size();i++){
			Event event = allEvents.get(i);
			if(event.getName().indexOf(keyWord)>=0||event.getNote().indexOf(keyWord)>=0){
				events.add(event);
			}
		}
	}
	public void searchProject(){
		List<Project>allProjects = myDB.getAllProjects();
		for(int i = 0;i<allProjects.size();i++){
			Project project = allProjects.get(i);
			if(project.getName().indexOf(keyWord)>=0||project.getNote().indexOf(keyWord)>=0){
				projects.add(project);
			}
		}
	}
	public void searchCompany(){
		List<Company>allCompanies = myDB.getAllCompanies();
		for(int i =0;i<allCompanies.size();i++){
			Company company = allCompanies.get(i);
			if(company.getName().indexOf(keyWord)>=0||company.getNote().indexOf(keyWord)>=0){
				companies.add(company);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
