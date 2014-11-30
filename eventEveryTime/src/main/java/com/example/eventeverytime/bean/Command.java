package com.example.eventeverytime.bean;
//用于传输命令
public class Command {
	/**
	 * 已废弃
	 * 由SpinerItemInfo取代
	 * @author 世欣
	 *
	 */
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
