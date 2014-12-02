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

	ControlFragment controller;
	MainFragment mainFragment;
	MenuFragment menuFragment;
	SlidingMenu sm;
	IsDataChangeListener dataChangeListener;
	public void init(){
		MyDB myDB = MyDB.getInstance(getApplicationContext());
		
		actionBar = getActionBar();
		mainFragment = new MainFragment();
		menuFragment = new MenuFragment(new ControlFragment() {
			
			@Override
			public void setListView(DataType type) {

				mainFragment.setListView(type);
				sm.showContent();
				switch (type) {
				case PERSON:
					actionBar.setTitle(getString(R.string.person));
					break;
				case PROJECT:
					actionBar.setTitle(getResources().getString(R.string.project));
					break;
				case EVENT:
					actionBar.setTitle(getResources().getString(R.string.event));
					break;
				case COMPANY:
					actionBar.setTitle(getResources().getString(R.string.company));
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
		 */
		
		
		
		
		
		
		
		
		init();
		setContentView(R.layout.main_frame);

		
		
		setBehindContentView(R.layout.menu_frame);
		
	       sm = getSlidingMenu();
	        sm.setShadowWidth(20);
	        sm.setShadowDrawable(R.drawable.shadow);
	        sm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth()-300);
	        sm.setFadeDegree(0.35f);
	        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	        setSlidingActionBarEnabled(false);
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.menu, menuFragment);
		transaction.replace(R.id.main, mainFragment);
		transaction.commit();
		


	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
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