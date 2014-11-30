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
 * Created by ���� on 14-11-15.
 */
public class MyDB {
	Context context;
    private static MyDB myDB = null;
    private static MyOpenHelper openHelper = null;
    private static SQLiteDatabase db = null;
    private static IsDataChangeListener onDataChanedListener;
    //���ڼ������ݵ�����,���ں�����
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
 * ����¼�
 * @param values
 * @return
 */
    public int insertEvent(ContentValues values) {
        int row = (int) db.insert("Event", null, values);
        return row;
    }
    /**
     * ��Ӱ�����������������
     * ��ϣ���д���������Ϣ
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
     * �����¼�ID��SpinnerInfo��ϣ���������
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
     * ��ӵ�������
     * @param values
     */
    public void insertIndex(ContentValues values) {
    	
        db.insert("Indexx", null, values);
    }
    /**
     * ���������첽�����ļ��� �ѷ���
     * @param listener
     */
    public void setDataChangedListener(IsDataChangeListener listener){
    	onDataChanedListener = listener;
    }
    /**
     * �������
     * @param values
     * @return
     */
    public int insertPerson(ContentValues values) {
       int newId= (int) db.insert("Person", null, values);
        notifyDataChanged();
        return newId;
    }
    /**
     * �ص�֪ͨ���ݸ���
     */
    public void notifyDataChanged(){
    	if(onDataChanedListener!=null){
    		onDataChanedListener.notifyDataChanged();
    	}
    }
    /**
     * �����Ŀ
     * @param values
     * @return
     */
    public int insertProject(ContentValues values) {
    	int id=(int) db.insert("Project", null, values);
        notifyDataChanged();
        return id;
    }
    /**
     * ��ӹ�˾
     * @param values
     * @return
     */
    public int insertCompany(ContentValues values) {
        int id=(int) db.insert("Company", null, values);
        notifyDataChanged();
        return id;
    }
    /**
     * ������Ŀ��ȡ����Ŀ�����е��¼�
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
 * �����¼�ID��ȡ����������Ϣ
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
 * ����ID��ȡ�������
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
 * �����¼�ID��ȡ���в����˶�������
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
 * ������Ŀid��ȡ��Ŀ����
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
 * ��ȡ�����¼�
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
     * ͨ������ID ��ȡ���ݿ��д��˲�����ĻID
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
     * ��������ID��ȡ���в������ʱ��
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
     * �����¼�ID��ȡ�¼�����
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
     * ��ȡ�����������
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
     * ��ȡ������Ŀ����
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
     * ��ȡ���й�˾����
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
     * ���� ��������
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
     * ��ȡ��������
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
	 * ����Ϊ��������Ĳ�ѯ����
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
	 * �޸�������Ϣ
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
	 * �޸��¼���Ϣ
	 * @param event
	 */
	public void modifyEvent(Event event){
	//	int newId = insertEvent(event.getContValues());
//		changeEventId(oldId, newId);
//		removeEventById(oldId);
		db.replace("Event", null, event.getFullContentValues());
}
	/**
	 * �޸���Ŀ��Ϣ
	 * @param project
	 */
	public void modifyProject(Project project){
		db.replace("Project", null, project.getFullContentValues());
		Log.i("go","go");
	}
	/**
	 * �޸Ĺ�˾��Ϣ
	 * @param company
	 */
	public void modifyCompany(Company company){
		db.replace("Company", null, company.getFullContentValues());
		
	}
	/**
	 * ���ݹ�˾ID��ȡ���������Ϣ
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
	 * ��������IDɾ������
	 * @param id
	 */
	public void removePersonById(int id){
		db.delete("Person", "id=?", new String[]{id+""});
		removeIndexByPersonId(id);
	}
	/**
	 * ������ĿIDɾ����Ŀ
	 * @param id
	 */
	public void removeProjectById(int id){
		db.delete("Project", "id=?",new String[]{id+""});
	}
	/**
	 * ��������IDɾ������
	 * @param id
	 */
	public void removeIndexByPersonId(int id){
		db.delete("Indexx", "personId=?", new String[]{id+""});
	}
	/**
	 * �����¼�IDɾ������
	 * @param id
	 */
	public void removeIndexByEventId(int id){
		db.delete("Indexx", "eventId=?", new String[]{id+""});
	}
	/**
	 * ɾ���¼�
	 * @param id
	 */
	public void removeEventById(int id){
		db.delete("Event", "id=?", new String[]{id+""});
		new MyAlarmManager(context).cancelAlarm(id);
		removeIndexByEventId(id);
	}
	/**
	 * ɾ����˾
	 * �������������ù�˾����,���޷�ɾ��
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
	 * ɾ������� ����DataTypeѡ��ɾ������
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
	 * ���������ֻ������ȡ�����SpinerInfo��Ϣ
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