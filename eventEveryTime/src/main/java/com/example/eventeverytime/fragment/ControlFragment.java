package com.example.eventeverytime.fragment;

import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.SpinerItemInfo;

public interface ControlFragment {
	public void setListView(DataType type);
	public void refresh(SpinerItemInfo info);
}
