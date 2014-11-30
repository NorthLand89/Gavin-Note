package com.example.eventeverytime.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import com.example.eventeverytime.alarm.MyAlarmManager;
import com.example.eventeverytime.bean.*;
import com.example.eventeverytime.listener.IsDataChangeListener;

/**
 * Created by 世欣 on 14-11-15.
 */
public class MyDB {
	Context context;
    private static MyDB myDB = null;
    private static MyOpenHelper openHelper = null;
    private static SQLiteDatabase db = null;
    private static IsDataChangeListener onDataChanedListener;
    //用于加载数据的链表,现在很少用
    private static ArrayList<Person> allPersonInfo;
    private static ArrayList<Project> allProjectInfo;
    private static ArrayList<Event> allEventInfo;
    private static ArrayList<Company> allCompanyInfo;
    private static ArrayList<Index> allIndex;
    
    
    private MyDB(Context context) {
        openHelper = new MyOpenHelper(context, 1);
        db = openHelper.getReadableDatabase();
        this.context = context;
    }

    
    
    
    
    
    public static MyDB getInstance(Context context) {
        if (myDB == null) {
            myDB = new MyDB(context);
        }
        return myDB;
    }

/**
 * 添加事件
 * @param values
 * @return
 */
    public int insertEvent(ContentValues values) {
        int row = (int) db.insert("Event", null, values);
        return row;
    }
    /**
     * 添加包含索引的完整事项
     * 哈希表中存有人物信息
     * @param eventValues
     * @param personInfos
     */
    public int insertEvent(ContentValues eventValues,HashMap<Integer,SpinerItemInfo> personInfos){
    	
    	int eventId = insertEvent(eventValues);
    	
    	Iterator<Integer> iterator=personInfos.keySet().iterator();
    	while(iterator.hasNext()) {
    		insertIndex(new Index(0, eventId, personInfos.get(iterator.next()).getId()).getContValues());
    		}
    	
//    	for(int i = 0;i<personInfos.size();i++){
//    		insertIndex(new Index(0, eventId, personInfos.get(i).getId()).getContValues());
//    	}

        notifyDataChanged();
        return eventId;
    }
    /**
     * 根据事件ID和SpinnerInfo哈希表添加索引
     * @param eventId
     * @param personInfos
     */
    public void insertIndexes(int eventId,HashMap<Integer, SpinerItemInfo> personInfos){
    	Iterator<Integer> iterator=personInfos.keySet().iterator();
    	while(iterator.hasNext()) {
    		insertIndex(new Index(0, eventId, personInfos.get(iterator.next()).getId()).getContValues());
    		}
    }
    /**
     * 添加单条索引
     * @param values
     */
    public void insertIndex(ContentValues values) {
    	
        db.insert("Indexx", null, values);
    }
    /**
     * 设置用于异步操作的监听 已废弃
     * @param listener
     */
    public void setDataChangedListener(IsDataChangeListener listener){
    	onDataChanedListener = listener;
    }
    /**
     * 添加人物
     * @param values
     * @return
     */
    public int insertPerson(ContentValues values) {
       int newId= (int) db.insert("Person", null, values);
        notifyDataChanged();
        return newId;
    }
    /**
     * 回调通知数据更新
     */
    public void notifyDataChanged(){
    	if(onDataChanedListener!=null){
    		onDataChanedListener.notifyDataChanged();
    	}
    }
    /**
     * 添加项目
     * @param values
     * @return
     */
    public int insertProject(ContentValues values) {
    	int id=(int) db.insert("Project", null, values);
        notifyDataChanged();
        return id;
    }
    /**
     * 添加公司
     * @param values
     * @return
     */
    public int insertCompany(ContentValues values) {
        int id=(int) db.insert("Company", null, values);
        notifyDataChanged();
        return id;
    }
    /**
     * 根据项目获取该项目下所有的事件
     * @param project
     * @return
     */
    public ArrayList<Event> getEventsByProject(Containable project) {
        Cursor cursor = db.query("Event",
        							null,
        							"projectId=?",
        							new String[]{String.valueOf(project.getId())},
        							null,
        							null,
        							null);
        ArrayList<Event> list = new ArrayList<Event>();
        if (cursor.moveToFirst()) {
            do {
                Event event = (Event)new Event().getObjectByCursor(cursor);
                list.add(event);
            } while (cursor.moveToNext());
        }
        Collections.sort(list);
        return list;
    }
/**
 * 根据事件ID获取所有人物信息
 * @param id
 * @return
 */
    public ArrayList<Integer> getPersonsIdByEventId(int id) {
        ArrayList<Integer> personIdArraylist = new ArrayList<Integer>();
        Cursor cursor = db.query("Indexx", null, "eventId=?", new String[]{id + ""}, null, null, null);
        if (!cursor.moveToFirst()) {

        } else {
            do {
                personIdArraylist.add(cursor.getInt(cursor.getColumnIndex("personId")));
            } while (cursor.moveToNext());
        }
        return personIdArraylist;

    }
/**
 * 根据ID获取人物对象
 * @param id
 * @return
 */
    public Person getPersonById(int id) {
        Person person=null;
        Cursor cursor = db.query("Person", null, "id=?", new String[]{id + ""}, null, null, null);
        if (cursor.moveToFirst()) {
            person = (Person)new Person().getObjectByCursor(cursor);
        }
        return person;
    }
/**
 * 根据事件ID获取所有参与人对象链表
 * @param id
 * @return
 */
    public ArrayList<Person> getPersonsByEventId(int id) {
        ArrayList<Integer> list = getPersonsIdByEventId(id);
        
        ArrayList<Person> Persons = new ArrayList<Person>();
        for (Integer p : list) {
            Persons.add(getPersonById(p.intValue()));
        }
        return Persons;
    }
/**
 * 根据项目id获取项目对象
 * @param id
 * @return
 */
    public Project getProjectById(int id){
        Project project = null;
        Cursor cursor = db.query("Project",null,"id=?",new String[]{id+""},null,null,null);
        if(cursor.moveToFirst()){
            project = (Project)new Project().getObjectByCursor(cursor);
        }
        return project;
    }
/**
 * 获取所有事件
 * @return
 */
    public  ArrayList<Event> getAllEvent(){
        ArrayList<Event>events=new ArrayList<Event>();
        Cursor cursor = db.rawQuery("select * from Event;",new String[0]);
        if(cursor.moveToFirst()){
            do {
                events.add((Event) new Event().getObjectByCursor(cursor));
            }while(cursor.moveToNext());
        }
        Collections.sort(events);
        return events;
    }
    /**
     * 通过人物ID 获取数据库中此人参与过的活动ID
     * @param personId
     * @return eventIds
     */
    public ArrayList<Integer> getEventIdsByPersonId(int personId){
    	ArrayList<Integer> eventIdList= new ArrayList<Integer>();
    	Cursor cursor = db.query("Indexx", null, "personId=?", new String[]{personId+""},null, null, null);
        if(cursor.moveToFirst()){
            do {
                eventIdList.add(cursor.getInt(cursor.getColumnIndex("eventId")));
            }while(cursor.moveToNext());
        }
    	Collections.sort(eventIdList);
    	return eventIdList;
    }
    /**
     * 根据人物ID获取所有参与过的时间
     * @param context
     * @param personId
     * @return
     */
    public ArrayList<Event> getEventsByPersonId(Context context,int personId){
    	ArrayList<Event> events=new ArrayList<Event>();
    	for(int i = 0;i<allIndex.size();i++){
    		if(allIndex.get(i).getPersonId()==personId){
    			for(int j = 0;j<allEventInfo.size();j++){
    				if(allEventInfo.get(j).getId()==allIndex.get(i).getEventId()){
    					events.add(allEventInfo.get(j));
    					break;
    				}
    			}
    		}
    	}
    	Collections.sort(events);
    	return events;
    }
    /**
     * 根据事件ID获取事件对象
     * @param eventId
     * @return
     */
    public Event getEventById(int eventId){
    	Cursor cursor=db.query("Event", null,"id=?", new String[]{eventId+""}, null,null, null);
    	Event event = new Event();
    	if(cursor.moveToFirst()){
    		event.getObjectByCursor(cursor);
    	}else{
    	
    	}
    	return event;
    }
    
    /**
     * 获取所有人物对象
     * @return
     */
    public ArrayList<Person>getAllPersons(){
    	ArrayList<Person> persons = new ArrayList<Person>();
    	
    	Cursor cursor = db.query("Person", null, null, null, null, null, null, null);
    	if(cursor.moveToFirst()){
    		do{
    			persons.add((Person)new Person().getObjectByCursor(cursor));
    		}while(cursor.moveToNext());
    	}
    	return persons;
    }
    /**
     * 获取多有项目对象
     * @return
     */
    public  ArrayList<Project> getAllProjects(){
    	ArrayList<Project> projects=new ArrayList<Project>();
    			Cursor cursor = db.query("Project",null,null,null,null,null,null);
    			if(cursor.moveToFirst()){
    	    		do{
    	    			projects.add((Project)new Project().getObjectByCursor(cursor));
    	    		}while(cursor.moveToNext());
    	    	}
    	return projects;
    }
    /**
     * 获取所有公司对象
     * @return
     */
    public ArrayList<Company> getAllCompanies(){
    	ArrayList<Company>companies=new ArrayList<Company>();
    	Cursor cursor = db.query("Company",null,null,null,null,null,null);
		if(cursor.moveToFirst()){
    		do{
    			companies.add((Company)new Company().getObjectByCursor(cursor));
    		}while(cursor.moveToNext());
    	}
		return companies;
    }
    /**
     * 加载 基本废弃
     * @param context
     * @return
     */
    public MyDB load(Context context){
//    	getInstance(context);
    	allCompanyInfo=getAllCompanies();
    	allEventInfo=getAllEvent();
    	allPersonInfo=getAllPersons();
    	allProjectInfo=getAllProjects();
    	allIndex = getAllIndex();
    	return this;
    }
    /**
     * 获取所有索引
     * @return
     */
    public ArrayList<Index> getAllIndex(){
    	allIndex = new ArrayList<Index>();
    	Cursor cursor = db.query("Indexx", null, null, null, null, null, null);
    	if(cursor.moveToFirst()){
    		do{
    			Index index = (Index)new Index().getObjectByCursor(cursor);
    			allIndex.add(index);
    		}while(cursor.moveToNext());
    	}
    	return allIndex;
    }

	public ArrayList<Person> getAllPersonInfo() {
		return allPersonInfo;
	}

	public ArrayList<Project> getAllProjectInfo() {
		return allProjectInfo;
	}

	public ArrayList<Event> getAllEventInfo() {
		return allEventInfo;
	}

	public ArrayList<Company> getAllCompanyInfo() {
		return allCompanyInfo;
	}
	
	
	/**
	 * 以下为遍历链表的查询函数
	 */
	
	public Company getCompanyByPerson(Person person){
		for(int i = 0;i<allCompanyInfo.size();i++){
			if(allCompanyInfo.get(i).getId()==person.getId()){
				return allCompanyInfo.get(i);
			}
		}
		return null;
	}
	public Company getCompanyById(int id){
		for(int i = 0;i<allCompanyInfo.size();i++){
			if(allCompanyInfo.get(i).getId()==id){
				return allCompanyInfo.get(i);
			}
		}
		return new Company();
	}
	
	public void changePersonId(int oldId,int newId){
		db.execSQL("update from Indexx set personId =? where personId = ?;", new String[]{newId+"",oldId+""});
		
	}
	public void changeEventId(int oldId,int newId){
		db.execSQL("update from Indexx set eventId =(?) where eventId = (?);", new String[]{newId+"",oldId+""});
	}
	public void changeProjectId(int oldId,int newId){
		db.execSQL("update from Event set projectId =? where projectId = ?;", new String[]{newId+"",oldId+""});
	}
	public void changeCompanyId(int oldId,int newId){
		db.execSQL("update from Person set companyId =? where companyId = ?;", new String[]{newId+"",oldId+""});
		
	}
	/**
	 * 修改人物信息
	 * @param oldId
	 * @param person
	 * @return
	 */
	public int modifyPerson(int oldId,Person person){
//		int newId = insertPerson(person.getContValues());
//		changePersonId(oldId, newId);
//		removePersonById(oldId);
		db.replace("Person", null, person.getFullContentValues());
		return oldId;
	}
	/**
	 * 修改事件信息
	 * @param event
	 */
	public void modifyEvent(Event event){
	//	int newId = insertEvent(event.getContValues());
//		changeEventId(oldId, newId);
//		removeEventById(oldId);
		db.replace("Event", null, event.getFullContentValues());
}
	/**
	 * 修改项目信息
	 * @param project
	 */
	public void modifyProject(Project project){
		db.replace("Project", null, project.getFullContentValues());
		Log.i("go","go");
	}
	/**
	 * 修改公司信息
	 * @param company
	 */
	public void modifyCompany(Company company){
		db.replace("Company", null, company.getFullContentValues());
		
	}
	/**
	 * 根据公司ID获取相关人物信息
	 * @param id
	 * @return
	 */
	public ArrayList<Person> getPersonsByCompanyId(int id){
		ArrayList<Person> persons= new ArrayList<Person>();
		Cursor cursor = db.query("Person",
				null,
				"companyId=?",
				new String[]{id+""},
				null, 
				null, 
				null);
		if(cursor.moveToFirst()){
			do{
				Person person = (Person) new Person().getObjectByCursor(cursor);
				if(person.getCompanyId()==id){
					persons.add(person);
				}
			}while(cursor.moveToNext());
		}
		return persons;
	}
	/**
	 * 根据人物ID删除人物
	 * @param id
	 */
	public void removePersonById(int id){
		db.delete("Person", "id=?", new String[]{id+""});
		removeIndexByPersonId(id);
	}
	/**
	 * 根据项目ID删除项目
	 * @param id
	 */
	public void removeProjectById(int id){
		db.delete("Project", "id=?",new String[]{id+""});
	}
	/**
	 * 根据人物ID删除索引
	 * @param id
	 */
	public void removeIndexByPersonId(int id){
		db.delete("Indexx", "personId=?", new String[]{id+""});
	}
	/**
	 * 根据事件ID删除索引
	 * @param id
	 */
	public void removeIndexByEventId(int id){
		db.delete("Indexx", "eventId=?", new String[]{id+""});
	}
	/**
	 * 删除事件
	 * @param id
	 */
	public void removeEventById(int id){
		db.delete("Event", "id=?", new String[]{id+""});
		new MyAlarmManager(context).cancelAlarm(id);
		removeIndexByEventId(id);
	}
	/**
	 * 删除公司
	 * 如果还有人物与该公司关联,则无法删除
	 * @param id
	 * @return
	 */
	public boolean removeCompanyById(int id){
		Cursor cursor = db.query("Person", null, "PersonId=?", new String[]{id+""}, null, null, null);
		if(cursor.moveToFirst()){
			return false;
		}else{
			db.delete("Company", "id=?", new String[]{id+""});
			return true;
		}
	}
	/**
	 * 删除的入口 根据DataType选择删除对象
	 * @param info
	 * @return
	 */
	public boolean remove(SpinerItemInfo info){
		switch (info.getDataType()) {
		case PERSON:
			removePersonById(info.getId());
			return true;
		case PROJECT:
			removeProjectById(info.getId());
			return true;
		case EVENT:
			removeEventById(info.getId());
			return true;
		case COMPANY:
			return removeCompanyById(info.getId());
		default:
			return false;
		}
	}
	/**
	 * 根据人物手机号码获取人物的SpinerInfo信息
	 * @param number
	 * @return
	 */
	public SpinerItemInfo getPersonInfoByPhone(String number){
		if(number==null||number.equals("")){
			return null;
		}
		for(int i = 0;i<allPersonInfo.size();i++){
			Person person = allPersonInfo.get(i);
			if(person.getMobilePhone().length()<number.length()){
				return null;
			}
			if(person.getMobilePhone().indexOf(number)>0
					||person.getPhone().indexOf(number)>0){
				return new SpinerItemInfo(person.getName(), person.getId(),DataType.PERSON);
			}
		}
		return null;
	}
}