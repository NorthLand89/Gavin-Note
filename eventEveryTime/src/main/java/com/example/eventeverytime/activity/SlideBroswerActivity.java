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
		ControlFragment controller;
		BrowserMainFragment mainFragment;
		BrowserMenuFragment menuFragment;
		SlidingMenu sm;
		SpinerItemInfo info;
		Refreshable refresh;
		
		public void init(){
			info = (SpinerItemInfo)getIntent().getSerializableExtra("querry");
			mainFragment = new BrowserMainFragment(info);
			
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
			menuFragment = new BrowserMenuFragment(refresh,info);
			
			
		}

		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
			setContentView(R.layout.main_frame);
			setBehindContentView(R.layout.menu_frame);
			
		       sm = getSlidingMenu();
		        sm.setShadowWidth(200);
		        sm.setShadowDrawable(R.drawable.shadow);
		        sm.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth()-70);
		        sm.setFadeDegree(0.35f);
		        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		        setSlidingActionBarEnabled(false);
//		        sm.setMode(SlidingMenu.RIGHT) ;
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			transaction.replace(R.id.main, mainFragment);
			transaction.replace(R.id.menu, menuFragment);
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
		
	
}
