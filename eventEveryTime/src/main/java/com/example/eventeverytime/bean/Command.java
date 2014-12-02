package com.example.eventeverytime.bean;
public class Command {
	public Command(DataType type,int id){
		this.type=type;
		this.id = id;
	}
	DataType type;
	int id;
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
