package com.example.eventeverytime.fragment;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.InvalidAuthenticationException;
import com.example.eventeverytime.R;
import com.example.eventeverytime.activity.LoadSystemContactActivity;
import com.example.eventeverytime.bean.DataType;
import com.example.eventeverytime.config.Setting;
import com.example.eventeverytime.define.Final;
import com.example.eventeverytime.factory.DialogFactory;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment {
	protected EvernoteSession mEvernoteSession;
	private ControlFragment controller;
	
	TextView projectTV;
	TextView eventTV;
	TextView personTV;
	TextView companyTV;
	Button addEventButton;
	Button addPersonButton;
	Button addProjectButton;
	Button addCompanyButton;
	Button loadContactButton;
	Button addButton;
	Button searchButton;
	EditText editText;
	Switch evernoteSwitch;
	Switch phoneSwitch;
	Switch messageSwitch;
	Switch reminderSwitch;
	View view;
	public MenuFragment(ControlFragment controller){
		this.controller = controller;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mEvernoteSession = EvernoteSession.getInstance(getActivity(), Final.CONSUMER_KEY, Final.CONSUMER_SECRET, Final.EVERNOTE_SERVICE, Final.SUPPORT_APP_LINKED_NOTEBOOKS);
		  
		view = inflater.inflate(R.layout.menu, null);
		initView(view);
		
		
		return view;
	}
	public void initView(View view){
		projectTV = (TextView) view.findViewById(R.id.tv_menu_project);
		projectTV.setOnClickListener(new MenuOnClickListener());
		eventTV = (TextView) view.findViewById(R.id.tv_menu_Event);
		eventTV .setOnClickListener(new MenuOnClickListener());
		personTV = (TextView) view.findViewById(R.id.tv_menu_Person);
		personTV.setOnClickListener(new MenuOnClickListener());
		companyTV=(TextView)view.findViewById(R.id.tv_menu_Company);
		companyTV.setOnClickListener(new MenuOnClickListener());
		addButton = (Button) view.findViewById(R.id.bt_menu_add);
		addButton.setOnClickListener(new MenuOnClickListener());
		addPersonButton=(Button) view.findViewById(R.id.bt_menu_add_person);
		addPersonButton.setOnClickListener(new MenuOnClickListener());
		addProjectButton=(Button) view.findViewById(R.id.bt_menu_add_project);
		addProjectButton.setOnClickListener(new MenuOnClickListener());
		addEventButton = (Button) view.findViewById(R.id.bt_menu_add_event);
		addEventButton.setOnClickListener(new MenuOnClickListener());
		addCompanyButton=(Button) view.findViewById(R.id.bt_menu_add_company);
		addCompanyButton.setOnClickListener(new MenuOnClickListener());
		searchButton =(Button)view.findViewById(R.id.bt_menu_search);
		searchButton.setOnClickListener(new MenuOnClickListener());
		loadContactButton=(Button) view.findViewById(R.id.bt_start_load_contact);
		loadContactButton.setOnClickListener(new MenuOnClickListener());
		editText = (EditText) view.findViewById(R.id.et_menu_search);
		evernoteSwitch = (Switch)view.findViewById(R.id.sw_bind_evernote);
		phoneSwitch = (Switch) view.findViewById(R.id.sw_switch_phone_reminder);
		phoneSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Setting.getInstance(getActivity()).switchRemindCall(isChecked);
				Log.i("phone","ok");
			}
		});
		messageSwitch=(Switch) view.findViewById(R.id.sw_switch_message_reminder);
		messageSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Setting.getInstance(getActivity()).switchSaveMessage(isChecked);
			}
		});
		reminderSwitch=(Switch) view.findViewById(R.id.sw_switch_reminder);
		reminderSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Setting.getInstance(getActivity()).switchRemindMe(isChecked);
				
			}
		});
		evernoteSwitch.setChecked(mEvernoteSession.isLoggedIn());
		evernoteSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mEvernoteSession.authenticate(getActivity());
				}else{
					try {
						mEvernoteSession.logOut(getActivity());
					} catch (InvalidAuthenticationException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getActivity(), "取消授权失败", Toast.LENGTH_LONG);
					}
				}
			}
		});
		Setting.getInstance(getActivity()).initSwitch(phoneSwitch, messageSwitch, reminderSwitch);
	}
	//菜单的监听器
	class MenuOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {


				switch (v.getId()) {
				case R.id.tv_menu_Event:
					controller.setListView(DataType.EVENT);
					break;

				case R.id.tv_menu_Person:
					controller.setListView(DataType.PERSON);
					break;

				case R.id.tv_menu_project:
					controller.setListView(DataType.PROJECT);
					break;
				case R.id.tv_menu_Company:
					controller.setListView(DataType.COMPANY);
					break;
				case R.id.bt_menu_add:
					switchButtonStatu();
					break;
				case R.id.bt_menu_add_company:
					new DialogFactory(getActivity()).addCompanyDialog(editText.getText().toString());
					break;
				case R.id.bt_menu_add_person:
					new DialogFactory(getActivity()).addPersonDialog(editText.getText().toString());
					break;
				case R.id.bt_menu_add_event:
					new DialogFactory(getActivity()).addEventDialog(editText.getText().toString());
					break;
				case R.id.bt_menu_add_project:
					new DialogFactory(getActivity()).addProjectDialog(editText.getText().toString());
					break;
				case R.id.bt_menu_search:
					search();
					break;
				case R.id.bt_start_load_contact:
					startLoad();
					break;
				default:
					break;
				}
			
		}

	}
	public void startLoad(){
		Intent intent = new Intent();
		intent.setClass(getActivity(),LoadSystemContactActivity.class);
		startActivity(intent);
	}
	public void search(){
		String keyWord = editText.getText().toString().trim();
		if(keyWord.length()>0){
			Intent intent = new Intent("search");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("keyWord", keyWord);
			startActivity(intent);
		}else {
			Toast.makeText(getActivity(), "请输入关键字", Toast.LENGTH_SHORT);
		}
	}
	//详细添加按钮的隐藏与显示
	public void switchButtonStatu(){
		if(addCompanyButton.getVisibility()==Button.VISIBLE){
			addCompanyButton.setVisibility(Button.GONE);
			addEventButton.setVisibility(Button.GONE);
			addPersonButton.setVisibility(Button.GONE);
			addProjectButton.setVisibility(Button.GONE);
		}else{
			addCompanyButton.setVisibility(Button.VISIBLE);
			addEventButton.setVisibility(Button.VISIBLE);
			addPersonButton.setVisibility(Button.VISIBLE);
			addProjectButton.setVisibility(Button.VISIBLE);
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		evernoteSwitch.setChecked(mEvernoteSession.isLoggedIn());
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		switchButtonStatu();
		super.onHiddenChanged(hidden);
	}
}
