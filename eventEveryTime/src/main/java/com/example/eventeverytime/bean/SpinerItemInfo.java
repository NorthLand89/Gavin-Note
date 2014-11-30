package com.example.eventeverytime.bean;

import java.io.Serializable;

import android.R.integer;

public class SpinerItemInfo implements Serializable{
	/**
	 * Activity全局跳转,fragment更新携带的信息类
	 * DataType 枚举数据类型 用于接受者判断
	 * ID 数据库所需数据ID
	 * name 用于在Spinner中显示
	 */
	private static final long serialVersionUID = 1L;
	private DataType dataType;

	public DataType getDataType() {
		return dataType;
	}

	public SpinerItemInfo(String name,int id,DataType dataType){
		super();
		this.name = name;
		this.id = id;
		this.dataType = dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	private String name;
	private int id;

	public SpinerItemInfo(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
