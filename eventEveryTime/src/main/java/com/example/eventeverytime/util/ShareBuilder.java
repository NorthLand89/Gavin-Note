package com.example.eventeverytime.util;

public class ShareBuilder {
StringBuilder builder = new StringBuilder();

public void append(String s){
	builder.append(s);
}
public void addLine(String s){
	builder.append("\n"+s);
}
public void addBlankLine(){
	builder.append("\n");
}
public void addSeparator(){
	builder.append("\n*****************");
}
public String toString(){
	return builder.toString();
}
}
