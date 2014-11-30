package com.example.eventeverytime.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.evernote.client.android.EvernoteSession;
import com.example.eventeverytime.R;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.bean.SpinerItemInfo;
import com.example.eventeverytime.config.Setting;
import com.example.eventeverytime.db.MyDB;
import com.example.eventeverytime.factory.Refreshable;
import com.example.eventeverytime.fragment.ControlFragment;
import com.example.eventeverytime.fragment.MainFragment;
import com.example.eventeverytime.fragment.MenuFragment;
import com.example.eventeverytime.listener.IsDataChangeListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
public class MySlidingActivity extends SlidingActivity implements Refreshable{
	protected EvernoteSession mEvernoteSession;

	//controlFragment接口,用于菜单碎片和主碎片通信
	ControlFragment controller;
	MainFragment mainFragment;
	MenuFragment menuFragment;
	SlidingMenu sm;
	IsDataChangeListener dataChangeListener;
	public void init(){
		MyDB myDB = MyDB.getInstance(getApplicationContext());
		
		actionBar = getActionBar();
		//初始化主界面
		mainFragment = new MainFragment();
		//初始化菜单界面
		menuFragment = new MenuFragment(new ControlFragment() {
			
			@Override
			public void setListView(DataType type) {

				mainFragment.setListView(type);
				sm.showContent();
				switch (type) {
				case PERSON:
					actionBar.setTitle("人物列表");
					break;
				case PROJECT:
					actionBar.setTitle("项目列表");
					break;
				case EVENT:
					actionBar.setTitle("事件列表");
					break;
				case COMPANY:
					actionBar.setTitle("公司列表");
					break;

				default:
					break;
				}
			}

			@Override
			public void refresh(SpinerItemInfo info) {
				// TODO Auto-generated method stub
				
			}
		});
		//通过接口将两个碎片链接起来
		
		dataChangeListener =new IsDataChangeListener() {
			
			@Override
			public void notifyDataChanged() {
				mainFragment.refresh(null);
				
			}
		};
		myDB.setDataChangedListener(dataChangeListener);
	}

	ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		/**
		 * 测试功能
		 */
		
		
		
		
		
		
		
		
		init();
		setContentView(R.layout.main_frame);
		//设置标题
		
		
		
		setBehindContentView(R.layout.menu_frame);
		
	       sm = getSlidingMenu();
	        sm.setShadowWidth(20);
	        sm.setShadowDrawable(R.drawable.shadow);
	        sm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth()-300);
	        sm.setFadeDegree(0.35f);
	        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	        setSlidingActionBarEnabled(false);
		
		//获得转换器
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		//获取menufragment
		
		//开始转换
		transaction.replace(R.id.menu, menuFragment);
		transaction.replace(R.id.main, mainFragment);
		transaction.commit();
		
		//设置滑动菜单样式
		

	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //自动判断是打开还是关闭
            toggle();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void refresh(SpinerItemInfo info) {
		// TODO Auto-generated method stub
		mainFragment.refresh(null);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh(null);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Setting.getInstance(getApplicationContext()).backUp();
		super.onPause();
	}
	

}