package com.example.eventeverytime.util;

import java.util.ArrayList;
import java.util.List;

import com.example.eventeverytime.bean.Company;
import com.example.eventeverytime.bean.Event;
import com.example.eventeverytime.bean.Person;
import com.example.eventeverytime.bean.Project;
import com.example.eventeverytime.bean.SpinerItemInfo;

public class SpinnerTransFormer {
	ArrayList<SpinerItemInfo> personSpinerItemInfos;
	ArrayList<SpinerItemInfo> projectSpinerItemInfos;
	public static ArrayList<SpinerItemInfo> getPersonSpinerItemInfos(List<Person> persons){

		ArrayList<SpinerItemInfo> personSpinerItemInfos=new ArrayList<SpinerItemInfo>();
		for(Person person:persons){
			personSpinerItemInfos.add(new SpinerItemInfo(person.getName(), person.getId()));
		}
		
		return personSpinerItemInfos;
		
	}
	public static ArrayList<SpinerItemInfo> getProjectSpinerItemInfos(List<Project> projects){

		ArrayList<SpinerItemInfo> projectSpinerItemInfos=new ArrayList<SpinerItemInfo>();
		for(Project project:projects){
			projectSpinerItemInfos.add(new SpinerItemInfo(project.getName(), project.getId()));
		}
		
		return projectSpinerItemInfos;
		
	}
	public static ArrayList<SpinerItemInfo> getEventSpinerItemInfos(List<Event> events){

		ArrayList<SpinerItemInfo> eventSpinerItemInfos=new ArrayList<SpinerItemInfo>();
		for(Event event:events){
			eventSpinerItemInfos.add(new SpinerItemInfo(event.getName(), event.getId()));
		}
		
		return eventSpinerItemInfos;
		
	}
	public static ArrayList<SpinerItemInfo> getcompanySpinerItemInfos(List<Company> companys){

		ArrayList<SpinerItemInfo> companySpinerItemInfos=new ArrayList<SpinerItemInfo>();
		for(Company company:companys){
			companySpinerItemInfos.add(new SpinerItemInfo(company.getName(), company.getId()));
		}
		
		return companySpinerItemInfos;
		
	}

	
}
