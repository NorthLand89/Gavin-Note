package com.example.eventeverytime.activity;

import android.R.integer;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.factory.Refreshable;
import com.example.eventeverytime.fragment.BrowserMenuFragment;
import com.example.eventeverytime.fragment.BrowserMainFragment;
import com.example.eventeverytime.fragment.ControlFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class SlideBroswerActivity extends SlidingActivity {
	//controlFragment接口,用于菜单碎片和主碎片通信
		ControlFragment controller;
		BrowserMainFragment mainFragment;
		BrowserMenuFragment menuFragment;
		SlidingMenu sm;
		SpinerItemInfo info;
		Refreshable refresh;
		
		public void init(){
			info = (SpinerItemInfo)getIntent().getSerializableExtra("querry");
			//初始化主界面
			mainFragment = new BrowserMainFragment(info);
			
			//通过接口将两个碎片链接起来
			refresh=new Refreshable() {
				
				@Override
				public void refresh(SpinerItemInfo info) {
					if(info==null){
						finish();
						return;
					}else{
						mainFragment.refresh(info);
					}
				}
			};
			//初始化菜单界面
			menuFragment = new BrowserMenuFragment(refresh,info);
			
			
		}

		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
			setContentView(R.layout.main_frame);
			//设置标题
			setBehindContentView(R.layout.menu_frame);
			
		       sm = getSlidingMenu();
		        sm.setShadowWidth(200);
		        sm.setShadowDrawable(R.drawable.shadow);
		        sm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth()-70);
		        sm.setFadeDegree(0.35f);
		        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		        setSlidingActionBarEnabled(false);
//		        sm.setMode(SlidingMenu.RIGHT) ;
			
			//获得转换器
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			//获取menufragment
			
			//开始转换
			transaction.replace(R.id.main, mainFragment);
			transaction.replace(R.id.menu, menuFragment);
			transaction.commit();
			
			//设置滑动菜单样式
	 
		}
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	            //toggle就是程序自动判断是打开还是关闭
	            toggle();
//	          getSlidingMenu().showMenu();// show menu
//	          getSlidingMenu().showContent();//show content
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
		
	
}
