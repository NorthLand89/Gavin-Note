package com.example.eventeverytime.bean;
//用于传到Itemfactory中创建监听事件
public interface Commandable {
	public void nextView(Command command);
}
