package com.example.eventeverytime.factory;

import com.example.eventeverytime.bean.SpinerItemInfo;
/**
 * 应用中用于刷新数据的接口
 * @author 世欣
 *
 */
public interface Refreshable {
	public void refresh(SpinerItemInfo info);
}
